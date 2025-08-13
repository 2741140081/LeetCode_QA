package com.marks.tools;

import com.ibm.icu.text.Transliterator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/13 15:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class TraditionalToSimplifiedConverter {

    private static final Pattern ROW_ID_PATTERN = Pattern.compile(
            ",1275840000,1275840000,(\\d+),1650384000,1754409600,1650384000,");
    public static void main(String[] args) {
        String inputFilePath = "D:\\BMB\\data.sql"; // 输入文件路径
        String outputFilePath = "D:\\BMB\\data_simplified.sql"; // 输出文件路径

        try {
            convertAndReorder(inputFilePath, outputFilePath);
            System.out.println("转换完成，简体文件已保存至: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("文件处理出错: " + e.getMessage());
        }
    }

    private static void convertAndReorder(String inputPath, String outputPath) throws IOException {
        Transliterator converter = Transliterator.getInstance("Traditional-Simplified");
        int currentRowId = 97; // 从97开始编号

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputPath), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(new FileOutputStream(outputPath), StandardCharsets.UTF_8))) {

            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                // 处理首行（不修改row_id）
                if (isFirstLine) {
                    writer.write(converter.transliterate(line));
                    writer.newLine();
                    isFirstLine = false;
                    continue;
                }

                // 繁简转换
                String convertedLine = converter.transliterate(line);

                // 正则匹配并替换row_id部分
                // 处理row_id重编号
                String processedLine = processRowId(convertedLine, currentRowId++);

                writer.write(processedLine);
                writer.newLine();
            }
        }
    }

    private static String processRowId(String line, int newRowId) {
        // 匹配并替换row_id部分
        Matcher matcher = ROW_ID_PATTERN.matcher(line);
        StringBuffer sb = new StringBuffer();

        if (matcher.find()) {
            // 只替换第三个数字（row_id），其他保持不变
            matcher.appendReplacement(sb,
                    ",1275840000,1275840000," + newRowId++ +
                            ",1650384000,1754409600,1650384000,");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
