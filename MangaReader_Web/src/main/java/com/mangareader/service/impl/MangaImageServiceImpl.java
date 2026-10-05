package com.mangareader.service.impl;

import com.mangareader.config.MangaProperties;
import com.mangareader.mapper.MangaImageMapper;
import com.mangareader.model.entity.MangaImage;
import com.mangareader.service.MangaImageService;
import com.mangareader.util.ImageDimensionUtils;
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
    public List<MangaImage> getImagesByChapterIdPaged(Long chapterId, int offset, int limit) {
        return mangaImageMapper.findByChapterIdPaged(chapterId, offset, limit);
    }

    @Override
    public int countImagesByChapterId(Long chapterId) {
        return mangaImageMapper.countByChapterId(chapterId);
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

    @Override
    public void fillImageDimensions(MangaImage image) {
        // 如果宽高已有值，无需填充
        if (image.getImageWidth() != null && image.getImageHeight() != null) {
            return;
        }

        // 拼接本地文件路径
        String fullPath = getFullImagePath(image);
        File imageFile = new File(fullPath);
        if (!imageFile.exists()) {
            log.warn("[Image-{}] 懒填充跳过: 文件不存在, 路径: {}", image.getImageId(), fullPath);
            return;
        }

        int[] dimensions = ImageDimensionUtils.readDimensions(imageFile);
        if (dimensions != null) {
            int width = dimensions[0];
            int height = dimensions[1];
            image.setImageWidth(width);
            image.setImageHeight(height);
            // 回写 DB
            mangaImageMapper.updateImageDimensions(image.getImageId(), width, height);
            log.info("[Image-{}] 懒填充图片宽高: {}x{}, 路径: {}", image.getImageId(), width, height, fullPath);
        } else {
            log.warn("[Image-{}] 懒填充失败: 无法读取图片宽高, 路径: {}", image.getImageId(), fullPath);
        }
    }
}
