package com.mangareader.service.impl;

import com.mangareader.config.MangaProperties;
import com.mangareader.mapper.ChapterMapper;
import com.mangareader.mapper.MangaImageMapper;
import com.mangareader.mapper.MangaMapper;
import com.mangareader.mapper.ShelfFolderMapper;
import com.mangareader.mapper.ShelfMangaMapper;
import com.mangareader.model.common.BusinessException;
import com.mangareader.model.entity.Chapter;
import com.mangareader.model.entity.Manga;
import com.mangareader.model.entity.MangaImage;
import com.mangareader.model.entity.ShelfFolder;
import com.mangareader.model.entity.ShelfManga;
import com.mangareader.model.vo.ShelfFolderVO;
import com.mangareader.model.vo.ShelfMangaVO;
import com.mangareader.service.ShelfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 书架服务实现
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShelfServiceImpl implements ShelfService {

    private final ShelfFolderMapper shelfFolderMapper;
    private final ShelfMangaMapper shelfMangaMapper;
    private final MangaMapper mangaMapper;
    private final ChapterMapper chapterMapper;
    private final MangaImageMapper mangaImageMapper;
    private final MangaProperties mangaProperties;

    @Override
    public List<ShelfFolderVO> getFolders(Long userId) {
        List<ShelfFolder> folders = shelfFolderMapper.findByUserId(userId);
        List<ShelfManga> allMangas = shelfMangaMapper.findByUserId(userId);

        // 统计每个文件夹的漫画数
        Map<Long, Long> countMap = allMangas.stream()
                .filter(m -> m.getFolderId() != null)
                .collect(Collectors.groupingBy(ShelfManga::getFolderId, Collectors.counting()));

        return folders.stream().map(f -> {
            ShelfFolderVO vo = new ShelfFolderVO();
            vo.setFolderId(f.getFolderId());
            vo.setFolderName(f.getFolderName());
            vo.setSortOrder(f.getSortOrder());
            vo.setMangaCount(countMap.getOrDefault(f.getFolderId(), 0L).intValue());
            vo.setCreatedAt(f.getCreatedAt());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public ShelfFolderVO createFolder(Long userId, String folderName) {
        Integer maxSort = shelfFolderMapper.getMaxSortOrder(userId);

        ShelfFolder folder = new ShelfFolder();
        folder.setUserId(userId);
        folder.setFolderName(folderName);
        folder.setSortOrder(maxSort + 1);
        shelfFolderMapper.insert(folder);

        ShelfFolderVO vo = new ShelfFolderVO();
        vo.setFolderId(folder.getFolderId());
        vo.setFolderName(folder.getFolderName());
        vo.setSortOrder(folder.getSortOrder());
        vo.setMangaCount(0);
        vo.setCreatedAt(folder.getCreatedAt());
        return vo;
    }

    @Override
    public void renameFolder(Long userId, Long folderId, String folderName) {
        ShelfFolder folder = shelfFolderMapper.findByFolderId(folderId);
        if (folder == null || !folder.getUserId().equals(userId)) {
            throw new BusinessException(404, "文件夹不存在");
        }
        shelfFolderMapper.updateFolderName(folderId, folderName);
    }

    @Override
    @Transactional
    public void deleteFolder(Long userId, Long folderId) {
        ShelfFolder folder = shelfFolderMapper.findByFolderId(folderId);
        if (folder == null || !folder.getUserId().equals(userId)) {
            throw new BusinessException(404, "文件夹不存在");
        }
        // 将文件夹内的漫画移至未分类
        shelfMangaMapper.clearFolder(folderId);
        shelfFolderMapper.deleteByFolderId(folderId);
    }

    @Override
    public List<ShelfMangaVO> getShelfMangas(Long userId, Long folderId) {
        if (folderId != null) {
            List<ShelfManga> shelfMangas = shelfMangaMapper.findByUserIdAndFolderId(userId, folderId);
            return toShelfMangaVOList(shelfMangas, userId);
        } else {
            List<Manga> completedMangas = mangaMapper.selectAllManga();
            return completedMangas.stream()
                    .map(this::toShelfMangaVOFromManga)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<ShelfMangaVO> getUncategorizedMangas(Long userId) {
        List<ShelfManga> shelfMangas = shelfMangaMapper.findUncategorized(userId);
        return toShelfMangaVOList(shelfMangas, userId);
    }

    private List<ShelfMangaVO> toShelfMangaVOList(List<ShelfManga> shelfMangas, Long userId) {
        // 获取文件夹名称映射
        List<ShelfFolder> folders = shelfFolderMapper.findByUserId(userId);
        Map<Long, String> folderNameMap = folders.stream()
                .collect(Collectors.toMap(ShelfFolder::getFolderId, ShelfFolder::getFolderName));

        return shelfMangas.stream().map(sm -> {
            Manga manga = mangaMapper.selectMangaById(sm.getMangaId());
            if (manga == null) return null;
            return toShelfMangaVO(manga, sm, folderNameMap);
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public void addMangaToShelf(Long userId, Long mangaId, Long folderId) {
        // 检查是否已在书架
        ShelfManga existing = shelfMangaMapper.findByUserIdAndMangaId(userId, mangaId);
        if (existing != null) {
            throw new BusinessException(400, "漫画已在书架中");
        }
        // 检查漫画是否存在
        Manga manga = mangaMapper.selectMangaById(mangaId);
        if (manga == null) {
            throw new BusinessException(404, "漫画不存在");
        }
        // 验证文件夹归属
        if (folderId != null) {
            ShelfFolder folder = shelfFolderMapper.findByFolderId(folderId);
            if (folder == null || !folder.getUserId().equals(userId)) {
                throw new BusinessException(404, "文件夹不存在");
            }
        }

        ShelfManga shelfManga = new ShelfManga();
        shelfManga.setUserId(userId);
        shelfManga.setMangaId(mangaId);
        shelfManga.setFolderId(folderId);
        shelfMangaMapper.insert(shelfManga);
    }

    @Override
    public void moveMangaToFolder(Long userId, Long mangaId, Long folderId) {
        ShelfManga existing = shelfMangaMapper.findByUserIdAndMangaId(userId, mangaId);
        if (existing == null) {
            throw new BusinessException(404, "漫画不在书架中");
        }
        if (folderId != null) {
            ShelfFolder folder = shelfFolderMapper.findByFolderId(folderId);
            if (folder == null || !folder.getUserId().equals(userId)) {
                throw new BusinessException(404, "文件夹不存在");
            }
        }
        shelfMangaMapper.updateFolder(userId, mangaId, folderId);
    }

    @Override
    public void removeMangaFromShelf(Long userId, Long mangaId) {
        shelfMangaMapper.deleteByUserIdAndMangaId(userId, mangaId);
    }

    private ShelfMangaVO toShelfMangaVO(Manga manga, ShelfManga shelfManga, Map<Long, String> folderNameMap) {
        ShelfMangaVO vo = new ShelfMangaVO();
        vo.setMangaId(manga.getMangaId());
        vo.setMangaName(manga.getMangaName());
        vo.setTotalChapters(manga.getTotalChapters());
        vo.setProcessedChapters(manga.getProcessedChapters());
        vo.setFolderId(shelfManga.getFolderId());
        vo.setFolderName(shelfManga.getFolderId() != null ? folderNameMap.get(shelfManga.getFolderId()) : null);
        vo.setAddedAt(shelfManga.getAddedAt());

        if (manga.getMangaStatus() != null) {
            vo.setMangaStatusCode(manga.getMangaStatus().getCode());
            vo.setMangaStatusDesc(manga.getMangaStatus().getDesc());
        }

        vo.setCoverUrl(buildCoverUrl(manga));
        return vo;
    }

    private ShelfMangaVO toShelfMangaVOFromManga(Manga manga) {
        ShelfMangaVO vo = new ShelfMangaVO();
        vo.setMangaId(manga.getMangaId());
        vo.setMangaName(manga.getMangaName());
        vo.setTotalChapters(manga.getTotalChapters());
        vo.setProcessedChapters(manga.getProcessedChapters());
        vo.setFolderId(null);
        vo.setFolderName(null);
        vo.setAddedAt(manga.getUpdatedAt());

        if (manga.getMangaStatus() != null) {
            vo.setMangaStatusCode(manga.getMangaStatus().getCode());
            vo.setMangaStatusDesc(manga.getMangaStatus().getDesc());
        }

        vo.setCoverUrl(buildCoverUrl(manga));
        return vo;
    }

    /**
     * 构建封面图片 URL
     * coverImage 存储的是相对于 imagePath 的路径，通过 /images/ 端点访问
     * 若 coverImage 为空，则懒加载：查询第一章第一图并持久化到数据库
     */
    private String buildCoverUrl(Manga manga) {
        if (manga.getCoverImage() != null && !manga.getCoverImage().isEmpty()) {
            return "/images/" + manga.getCoverImage();
        }
        // 懒加载：查询第一章第一图作为封面
        return resolveAndPersistCoverImage(manga);
    }

    /**
     * 懒加载封面：查询第一章的第一张图片，计算相对路径，持久化到数据库并返回 URL
     */
    private String resolveAndPersistCoverImage(Manga manga) {
        try {
            Chapter firstChapter = chapterMapper.findByMangaIdAndChapterNum(manga.getMangaId(), 0);
            if (firstChapter == null) return null;

            MangaImage firstImage = mangaImageMapper.findFirstImageByChapterId(firstChapter.getChapterId());
            if (firstImage == null) return null;

            String basePath = mangaProperties.getStorage().getImagePath();
            String fullPath = firstImage.getImageUrl() + File.separator
                    + firstImage.getImageName() + "." + firstImage.getImageType();

            if (fullPath.startsWith(basePath)) {
                String relativePath = fullPath.substring(basePath.length());
                relativePath = relativePath.replace("\\", "/");
                if (relativePath.startsWith("/")) {
                    relativePath = relativePath.substring(1);
                }
                // 持久化到数据库（仅当 cover_image 为空时更新）
                mangaMapper.updateCoverImage(manga.getMangaId(), relativePath);
                return "/images/" + relativePath;
            }
        } catch (Exception e) {
            log.warn("漫画[{}]懒加载封面失败: {}", manga.getMangaName(), e.getMessage());
        }
        return null;
    }
}
