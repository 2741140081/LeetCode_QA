package com.mangareader.service.impl;

import com.mangareader.mapper.MangaMapper;
import com.mangareader.model.entity.Manga;
import com.mangareader.service.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        // 设置默认状态为启用
        if (manga.getMangaStatus() == null) {
            manga.setMangaStatus(0);
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
}
