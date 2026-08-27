package com.mangareader.service.impl;

import com.mangareader.config.MangaProperties;
import com.mangareader.mapper.MangaMapper;
import com.mangareader.model.entity.Manga;
import com.mangareader.service.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaServiceImpl </p>
 * <p>描述: 漫画服务实现类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/20 14:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Service
public class MangaServiceImpl implements MangaService {

    @Autowired
    private MangaMapper mangaMapper;

    @Autowired
    private MangaProperties mangaProperties;

    @Override
    public List<Manga> getAllManga() {
        return mangaMapper.selectAllManga();
    }

    @Override
    public Manga getMangaById(Long mangaId) {
        if (mangaId == null) {
            return null;
        }
        return mangaMapper.selectMangaById(mangaId);
    }

    @Override
    public Manga addManga(Manga manga) {
        if (manga == null) {
            return null;
        }

        // 设置默认状态为待下载
        if (manga.getMangaStatus() == null) {
            manga.setMangaStatus(2);
        }

        // 设置创建和更新时间
        LocalDateTime now = LocalDateTime.now();
        manga.setCreatedAt(now);
        manga.setUpdatedAt(now);

        // 执行插入操作
        int result = mangaMapper.insertManga(manga);

        // 插入成功后，返回包含自增ID的漫画对象
        return result > 0 ? manga : null;
    }

    @Override
    public Manga addManga(String mangaName, String mangaUrl) {
        Manga manga = new Manga();
        manga.setMangaName(mangaName);
        manga.setMangaUrl(mangaUrl);
        // 根据 mangaName 创建本地文件
        String mangaBasePath = mangaProperties.getStorage().getImagePath();
        String fullPath = mangaBasePath + File.separator + mangaName;
        manga.setDirId(fullPath);
        // 根据 fullPath 创建本地文件夹
        File file = new File(fullPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        return addManga(manga);
    }

    @Override
    public void updateMangaStatus(Long mangaId, int mangaStatus) {
        mangaMapper.updateMangaStatus(mangaId, mangaStatus);
    }
}
