package com.mangareader.service.impl;

import com.mangareader.config.MangaProperties;
import com.mangareader.mapper.MangaImageMapper;
import com.mangareader.model.entity.MangaImage;
import com.mangareader.service.MangaImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

@Slf4j
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
    public String getFullImagePath(MangaImage image) {
        String path = image.getImageUrl();
        String imageName = image.getImageName();
        String imageType = image.getImageType();
        return path + File.separator + imageName + "." + imageType;
    }

    @Override
    public String getImageUrl(MangaImage image) {
        String basePath = mangaProperties.getStorage().getImagePath();
        String fullPath = getFullImagePath(image);

        // 将本地路径转换为 /images/ 开头的相对 URL
        if (fullPath.startsWith(basePath)) {
            String relativePath = fullPath.substring(basePath.length());
            // 替换反斜杠为正斜杠，并对文件名进行 URL 编码
            relativePath = relativePath.replace("\\", "/");
            if (relativePath.startsWith("/")) {
                relativePath = relativePath.substring(1);
            }
            // 对路径中的各段分别编码，保留目录结构
            String[] segments = relativePath.split("/");
            StringBuilder encoded = new StringBuilder("/images/");
            for (int i = 0; i < segments.length; i++) {
                if (i > 0) encoded.append("/");
                encoded.append(URLEncoder.encode(segments[i], StandardCharsets.UTF_8));
            }
            return encoded.toString();
        }

        // 如果路径不在基础目录下，返回文件名作为后备
        log.warn("图片路径不在配置的存储目录内: {}, basePath: {}", fullPath, basePath);
        String fileName = image.getImageName() + "." + image.getImageType();
        return "/images/" + URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    }
}
