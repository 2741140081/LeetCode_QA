package com.mangareader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaReaderApplicationTest </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/18 9:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class MangaReaderApplicationTest {
    // 编写一个测试用例, 将 D:\images\TestManga 文件夹下的图片收集并且重命名为 00x.jpeg 的格式


    /**
     * 测试图片收集和重命名功能
     */
    @Test
    void testCollectAndRenameImages() {
        // 源目录和目标目录
        String sourceDir = "D:/images/TestManga";
        String targetDir = "D:/images/TestManga_Renamed";

        // 创建目标目录
        File targetDirectory = new File(targetDir);
        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }

        // 收集图片文件
        List<File> imageFiles = collectImageFiles(sourceDir);
        System.out.println("找到 " + imageFiles.size() + " 张图片");

        // 按文件名中的数字排序
        imageFiles.sort(Comparator.comparing(File::getName, (f1, f2) -> {
            int n1 = extractNumber(f1);
            int n2 = extractNumber(f2);
            return Integer.compare(n1, n2);
        }));

        // 重命名并复制到目标目录
        renameAndCopyImages(imageFiles, targetDirectory);
        System.out.println("图片重命名完成，保存在: " + targetDir);
    }

    /**
     * 收集目录下的所有图片文件
     * @param dirPath 目录路径
     * @return 图片文件列表
     */
    private List<File> collectImageFiles(String dirPath) {
        List<File> imageFiles = new ArrayList<>();
        File dir = new File(dirPath);

        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("目录不存在或不是目录: " + dirPath);
            return imageFiles;
        }

        File[] files = dir.listFiles();
        if (files == null) {
            return imageFiles;
        }

        for (File file : files) {
            if (file.isFile()) {
                String name = file.getName().toLowerCase();
                if (name.endsWith(".jpg") || name.endsWith(".jpeg")
                        || name.endsWith(".png") || name.endsWith(".webp")
                        || name.endsWith(".gif")) {
                    imageFiles.add(file);
                }
            }
        }

        return imageFiles;
    }

    /**
     * 提取文件名中的数字
     * @param file 文件对象
     * @return 文件名中的数字，如果没有数字返回 0
     */
    private int extractNumber(File file) {
        return extractNumber(file.getName());
    }

    /**
     * 提取文件名中的数字
     * @param fileName 文件名
     * @return 文件名中的数字，如果没有数字返回 0
     */
    private int extractNumber(String fileName) {
        StringBuilder numSb = new StringBuilder();
        for (char c : fileName.toCharArray()) {
            if (Character.isDigit(c)) {
                numSb.append(c);
            }
        }
        return numSb.length() == 0 ? 0 : Integer.parseInt(numSb.toString());
    }

    /**
     * 重命名并复制图片文件
     * @param imageFiles 图片文件列表
     * @param targetDir 目标目录
     */
    private void renameAndCopyImages(List<File> imageFiles, File targetDir) {
        for (int i = 0; i < imageFiles.size(); i++) {
            File sourceFile = imageFiles.get(i);

            // 生成新文件名：00x.jpeg
            String newName = String.format("%03d.jpeg", i + 1);
            File targetFile = new File(targetDir, newName);

            try {
                // 复制文件
                Files.copy(sourceFile.toPath(), targetFile.toPath());
                System.out.println("复制: " + sourceFile.getName() + " -> " + newName);
            } catch (IOException e) {
                System.err.println("复制文件失败: " + sourceFile.getName());
                e.printStackTrace();
            }
        }
    }
}