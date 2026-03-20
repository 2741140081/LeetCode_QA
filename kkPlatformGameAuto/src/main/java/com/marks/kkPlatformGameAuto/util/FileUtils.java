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
import java.util.ArrayList;
import java.util.List;

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
     * @return 文件名（不含扩展名），如果 imagePath 为空则返回 "Error"
     */
    public String extractImageName(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return "Error";
        }

        // 使用 File 类获取文件名，自动处理不同操作系统的路径分隔符
        File file = new File(imagePath);
        String fileName = file.getName();

        // 移除扩展名
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }

        return fileName;
    }

    /**
     * 获取指定目录下的所有文件完整路径
     * 递归遍历子目录（如果需要非递归，可以添加参数控制）
     *
     * @param directoryPath 目录路径
     * @return 文件完整路径列表，如果目录不存在或为空则返回空列表
     */
    public List<String> getAllFilePathsInDirectory(String directoryPath) {
        return getAllFilePathsInDirectory(directoryPath, false);
    }

    /**
     * 获取指定目录下的所有文件完整路径
     *
     * @param directoryPath 目录路径
     * @param recursive 是否递归遍历子目录
     * @return 文件完整路径列表，如果目录不存在或为空则返回空列表
     */
    public List<String> getAllFilePathsInDirectory(String directoryPath, boolean recursive) {
        List<String> filePaths = new ArrayList<>();

        if (directoryPath == null || directoryPath.isEmpty()) {
            log.warn("目录路径为空");
            return filePaths;
        }

        File directory = new File(directoryPath);

        if (!directory.exists()) {
            log.warn("目录不存在：{}", directoryPath);
            return filePaths;
        }

        if (!directory.isDirectory()) {
            log.warn("路径不是目录：{}", directoryPath);
            return filePaths;
        }

        collectFilePaths(directory, filePaths, recursive);
        log.info("获取到目录 {} 下的 {} 个文件", directoryPath, filePaths.size());
        return filePaths;
    }

    /**
     * 获取指定目录下的所有文件完整路径（按扩展名过滤）
     *
     * @param directoryPath 目录路径
     * @param extension 文件扩展名（例如："png", "jpg"），为 null 时不过滤
     * @return 文件完整路径列表，如果目录不存在或为空则返回空列表
     */
    public List<String> getFilePathsByExtension(String directoryPath, String extension) {
        return getFilePathsByExtension(directoryPath, extension, false);
    }

    /**
     * 获取指定目录下的所有文件完整路径（按扩展名过滤）
     *
     * @param directoryPath 目录路径
     * @param extension 文件扩展名（例如："png", "jpg"），为 null 时不过滤
     * @param recursive 是否递归遍历子目录
     * @return 文件完整路径列表，如果目录不存在或为空则返回空列表
     */
    public List<String> getFilePathsByExtension(String directoryPath, String extension, boolean recursive) {
        List<String> filePaths = new ArrayList<>();

        if (directoryPath == null || directoryPath.isEmpty()) {
            log.warn("目录路径为空");
            return filePaths;
        }

        File directory = new File(directoryPath);

        if (!directory.exists()) {
            log.warn("目录不存在：{}", directoryPath);
            return filePaths;
        }

        if (!directory.isDirectory()) {
            log.warn("路径不是目录：{}", directoryPath);
            return filePaths;
        }

        collectFilePathsWithExtension(directory, filePaths, extension, recursive);
        log.info("获取到目录 {} 下扩展名为 {} 的文件 {} 个", directoryPath, extension, filePaths.size());
        return filePaths;
    }

    /**
     * 递归收集文件路径
     */
    private void collectFilePaths(File dir, List<String> filePaths, boolean recursive) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                filePaths.add(file.getAbsolutePath());
            } else if (file.isDirectory() && recursive) {
                collectFilePaths(file, filePaths, recursive);
            }
        }
    }

    /**
     * 递归收集指定扩展名的文件路径
     */
    private void collectFilePathsWithExtension(File dir, List<String> filePaths, String extension, boolean recursive) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                if (extension == null || extension.isEmpty() || hasExtension(file.getName(), extension)) {
                    filePaths.add(file.getAbsolutePath());
                }
            } else if (file.isDirectory() && recursive) {
                collectFilePathsWithExtension(file, filePaths, extension, recursive);
            }
        }
    }

    /**
     * 检查文件名是否具有指定的扩展名
     */
    private boolean hasExtension(String fileName, String extension) {
        if (fileName == null || extension == null) {
            return false;
        }
        return fileName.toLowerCase().endsWith("." + extension.toLowerCase());
    }
}


