package com.marks.tools.video;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextToCsvProcessor {
    public String inputPath = "D:\\GitProject\\LeetCode_QA\\leetcode_easy\\src\\main\\resources\\data\\testCsvData\\saveDir.txt";
//    private String outputPath = "/data/testCsvData/DownloadImgTestData.csv";
    public String outputPath = "D:\\GitProject\\LeetCode_QA\\leetcode_easy\\src\\main\\resources\\data\\testCsvData\\DownloadImgTestData.csv";

    private static final Pattern LINK_PATTERN =
            Pattern.compile("<a href=\"/chapter/(\\d+).html\" title=\"([^\"]+)\">");

    public void solution() {
        try {
            // 1. 读取文本文件
            List<String> lines = readTextFile(inputPath);

            // 2. 处理内容（示例：去除空行和注释行）
            List<String> processedLines = processContent(lines);

            // 3. 写入CSV文件
            writeToCsv(processedLines, outputPath);

            System.out.println("文件处理完成，结果已保存到: " + outputPath);
        } catch (IOException e) {
            System.err.println("处理文件时出错: " + e.getMessage());
        }
    }

    private static List<String> readTextFile(String filePath) throws IOException {
        ArrayList<String> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = LINK_PATTERN.matcher(line);
                while (matcher.find()) {
                    String id = matcher.group(1);
                    result.add(id);
                }
            }
        }
        return result;
    }

    private static List<String> processContent(List<String> lines) {
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            // 示例处理逻辑：跳过空行和以#开头的注释行
            if (!line.trim().isEmpty() && !line.trim().startsWith("#")) {
                // 可以添加更多处理逻辑
                result.add(line);
            }
        }
        return result;
    }

    private static void writeToCsv(List<String> lines, String outputPath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(outputPath), StandardCharsets.UTF_8))) {
            // 写入CSV头
            writer.write("index");
            writer.newLine();

            for (String line : lines) {
                // 将每行内容作为CSV的一行（如果包含逗号会自动加引号）
                writer.write("\"" + line + "\"");
                writer.newLine();
            }
        }
    }
}
