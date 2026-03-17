package com.marks.kkPlatformGameAuto.util;

import com.marks.kkPlatformGameAuto.config.properties.GameAutoProperties;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>项目名称：LeetCode_QA </p>
 * <p>文件名称：FileUtils </p>
 * <p>描述：文件处理组件，用于图像保存等操作 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17 11:13
 * @update [序号][日期 YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component
public class FileUtils {

    @Autowired
    private GameAutoProperties gameAutoProperties;

    /**
     * 保存 Mat 图像到临时目录
     *
     * @param mat 要保存的 Mat 图像
     * @param imagePath 原始图片路径，用于生成文件名
     * @return 保存的文件路径，失败返回 null
     */
    public String saveMatToTempDir(Mat mat, String imagePath) {
        try {
            // 从配置获取临时目录
            String tempDir = gameAutoProperties.getTempDir();
            // 获取格式
            String imageFormat = gameAutoProperties.getFormat();
            // 通过 imagePath 生成文件名
            String imageName = extractImageName(imagePath);

            // 确保目录存在
            File dir = new File(tempDir);
            if (!dir.exists()) {
                dir.mkdirs();
                log.info("创建临时目录：{}", tempDir);
            }

            // 生成带时间戳的文件名
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
            String timestamp = LocalDateTime.now().format(formatter);
            String fileName = imageName + "_" + timestamp + "." + imageFormat;
            String fullPath = tempDir + File.separator + fileName;

            // 保存图像
            boolean success = Imgcodecs.imwrite(fullPath, mat);
            if (success) {
                log.info("截图已保存到：{}", fullPath);
                return fullPath;
            } else {
                log.error("保存截图失败：{}", fullPath);
                return null;
            }
        } catch (Exception e) {
            log.error("保存截图异常", e);
            return null;
        }
    }

    /**
     * 保存 Mat 图像到指定目录
     *
     * @param mat 要保存的 Mat 图像
     * @param targetDir 目标目录
     * @param fileName 文件名
     * @return 保存的文件路径，失败返回 null
     */
    public String saveMatToDirectory(Mat mat, String targetDir, String fileName) {
        try {
            // 确保目录存在
            File dir = new File(targetDir);
            if (!dir.exists()) {
                dir.mkdirs();
                log.info("创建目录：{}", targetDir);
            }

            String fullPath = targetDir + File.separator + fileName;
            boolean success = Imgcodecs.imwrite(fullPath, mat);
            if (success) {
                log.info("图像已保存到：{}", fullPath);
                return fullPath;
            } else {
                log.error("保存图像失败：{}", fullPath);
                return null;
            }
        } catch (Exception e) {
            log.error("保存图像异常", e);
            return null;
        }
    }

    /**
     * 从图片路径中提取文件名（不含扩展名）
     *
     * @param imagePath 图片路径
     * @return 文件名（不含扩展名）
     */
    private String extractImageName(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return "match_result";
        }

        // 处理不同操作系统的路径分隔符
        File file = new File(imagePath);
        String fileName = file.getName();

        // 移除扩展名
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }

        return fileName;
    }
}


