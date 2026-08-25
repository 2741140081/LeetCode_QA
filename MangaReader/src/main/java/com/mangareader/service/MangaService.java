package com.mangareader.service;

import com.mangareader.model.entity.Manga;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaService </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/20 14:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface MangaService {

    List<Manga> getAllManga();

    Manga getMangaById(Long mangaId);

    Manga addManga(Manga manga);

    Manga addManga(String mangaName, String mangaUrl);
}
