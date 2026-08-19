package com.mangareader.service.impl;

import com.mangareader.config.MangaProperties;
import com.mangareader.mapper.MangaImageMapper;
import com.mangareader.model.entity.MangaImage;
import com.mangareader.service.MangaImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaImageServiceImpl </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/18 17:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Service
public class MangaImageServiceImpl implements MangaImageService {

    @Autowired
    private MangaImageMapper mangaImageMapper;

    @Autowired
    private MangaProperties mangaProperties;

    @Override
    public List<MangaImage> getImagesByChapterId(Long chapterId) {
        return mangaImageMapper.findByChapterId(chapterId);
    }

    @Override
    public String getFullImagePath(String relativePath) {
        if (relativePath == null || relativePath.isEmpty()) {
            return "";
        }

        String basePath = mangaProperties.getStorage().getImagePath();
        // 确保路径分隔符正确
        if (!basePath.endsWith(File.separator) && !relativePath.startsWith(File.separator)) {
            return basePath + File.separator + relativePath;
        } else {
            return basePath + relativePath;
        }
    }
}
