package com.marks.kkPlatformGameAuto.util;

import com.marks.kkPlatformGameAuto.config.ImageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 图片路径工具类
 */
@Component
public class ImagePathUtils {

    @Autowired
    private ImageConfig imageConfig;

    /**
     * 构建图片完整路径
     * @param imageDir 图片目录
     * @param imageName 图片名称（包含扩展名）
     * @return 完整路径
     */
    public String buildImagePath(String imageDir, String imageName) {
        if (imageName == null || imageName.isEmpty()) {
            return null;
        }
        return imageDir + File.separator + imageName;
    }

    /**
     * 构建图片完整路径（自动添加扩展名）
     * @param imageDir 图片目录
     * @param imageName 图片名称（不包含扩展名）
     * @return 完整路径
     */
    public String buildImagePathWithExtension(String imageDir, String imageName) {
        if (imageName == null || imageName.isEmpty()) {
            return null;
        }

        // 如果图片名已经包含扩展名，直接返回
        if (imageName.contains(".")) {
            return imageDir + File.separator + imageName;
        }

        // 自动添加扩展名
        if (imageConfig.getAutoAddExtension()) {
            return imageDir + File.separator + imageName + "." + imageConfig.getDefaultFormat();
        }

        return imageDir + File.separator + imageName;
    }

    /**
     * 获取默认图片格式
     * @return 图片格式（如 "png"）
     */
    public String getDefaultFormat() {
        return imageConfig.getDefaultFormat();
    }

    /**
     * 获取带点的图片格式
     * @return 带点的格式（如 ".png"）
     */
    public String getFormattedExtension() {
        return "." + imageConfig.getDefaultFormat();
    }
}
