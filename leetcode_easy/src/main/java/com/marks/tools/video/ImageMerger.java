package com.marks.tools.video;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ImageMerger {
    private static final String SOURCE_DIR = "D:\\spider\\data\\侍奉女神\\";
    private static final String TARGET_DIR =  SOURCE_DIR + "result\\";

    private static final Pattern IMG_PATTERN = Pattern.compile("img(\\d+)\\.webp");
    private static final Pattern DIR_PATTERN = Pattern.compile("(\\d+)");

    public static void main(String[] args) {

        try {
            Files.createDirectories(Paths.get(TARGET_DIR));
            File baseDir = new File(SOURCE_DIR);
            File[] subDirs = baseDir.listFiles(File::isDirectory);

            List<File> dirList = new ArrayList<>();
            for (File dir : subDirs) {
                if (!dir.getName().equals("result")) {
                    dirList.add(dir);
                }
            }

            // 按目录名称的数字顺序排序
            Collections.sort(dirList, (d1, d2) -> {
                int id1 = extractDirId(d1.getName());
                int id2 = extractDirId(d2.getName());
                return Integer.compare(id1, id2);
            });

            int globalIndex = 0;

            for (File dir : dirList) {
                File[] imageFiles = dir.listFiles((d, name) -> name.matches("img\\d+\\.webp"));

                // 按图片数字ID排序
                List<File> sortedImages = new ArrayList<>(List.of(imageFiles));
                Collections.sort(sortedImages, (f1, f2) -> {
                    int id1 = extractImageId(f1.getName());
                    int id2 = extractImageId(f2.getName());
                    return Integer.compare(id1, id2);
                });

                for (File image : sortedImages) {
                    String newName = "img" + globalIndex + ".webp";
                    Files.copy(image.toPath(), Paths.get(TARGET_DIR + newName),
                            StandardCopyOption.REPLACE_EXISTING);
                    globalIndex++;
                }
            }

            System.out.println("合并完成，共处理 " + globalIndex + " 张图片");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int extractImageId(String filename) {
        Matcher matcher = IMG_PATTERN.matcher(filename);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }

    private static int extractDirId(String dirname) {
        Matcher matcher = DIR_PATTERN.matcher(dirname);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }
}

