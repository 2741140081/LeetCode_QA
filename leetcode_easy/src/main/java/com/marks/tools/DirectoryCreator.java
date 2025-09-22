package com.marks.tools;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Arrays;

/**
 * 目录创建工具类，用于生成Bill Edit目录结构
 * 
 * @author Lingma
 * @version 1.0
 */
public class DirectoryCreator {
    
    // 基础路径配置
    private static final String BASE_PATH = "D:/daily_work/Bill Edit";
    private static final String START_MONTH = "202510"; // 开始月份：2025年10月
    private static final String END_MONTH = "202609";   // 结束月份：2026年9月
    
    // 每日固定的子文件夹名称
    private static final String[] DAILY_SUB_DIRS = {
        "batch_1", "batch_2", "batch_3", 
        "end_summary", "start_summary"
    };

    private static int[] days = {1, 4, 7, 10, 14, 17, 21, 24, 27};
    
    /**
     * 创建指定范围内的目录结构
     * 
     * @param basePath 基础路径
     * @param startMonth 开始月份（格式：YYYYMM）
     * @param endMonth 结束月份（格式：YYYYMM）
     */
    public void createDirectoryStructure(String basePath, String startMonth, String endMonth) {
        try {
            // 解析开始和结束月份
            LocalDate startDate = parseMonthString(startMonth);
            LocalDate endDate = parseMonthString(endMonth);
            
            // 遍历每个月份
            LocalDate currentMonth = startDate;
            while (!currentMonth.isAfter(endDate)) {
                // 创建月份文件夹
                String monthDir = basePath + "/" + currentMonth.getYear() + String.format("%02d", currentMonth.getMonthValue());
                File monthFile = new File(monthDir);
                if (!monthFile.exists()) {
                    monthFile.mkdirs();
                }
                
                // 创建每日文件夹（1-27日）
                for (int i = 0; i < days.length; i++) {
                    String dateDir = monthDir + "/" + currentMonth.getYear() + String.format("%02d", currentMonth.getMonthValue()) + String.format("%02d", days[i]);
                    File dateFile = new File(dateDir);
                    if (!dateFile.exists()) {
                        dateFile.mkdirs();
                    }
                    
                    // 创建每日的5个子文件夹
                    for (String subDir : DAILY_SUB_DIRS) {
                        File subFile = new File(dateDir + "/" + subDir);
                        if (!subFile.exists()) {
                            subFile.mkdirs();
                        }
                    }
                }
                
                // 移动到下一个月
                currentMonth = currentMonth.plusMonths(1);
            }
            
            System.out.println("目录结构创建完成！");
            
        } catch (Exception e) {
            System.err.println("创建目录结构时发生错误：" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 将YYYYMM格式的字符串解析为LocalDate对象
     * 
     * @param monthString YYYYMM格式的字符串
     * @return 对应的LocalDate对象
     * @throws IllegalArgumentException 当输入格式不正确时抛出异常
     */
    private LocalDate parseMonthString(String monthString) {
        if (monthString == null || monthString.length() != 6) {
            throw new IllegalArgumentException("月份格式必须为YYYYMM");
        }
        
        int year = Integer.parseInt(monthString.substring(0, 4));
        int month = Integer.parseInt(monthString.substring(4, 6));
        
        return LocalDate.of(year, month, 1);
    }
    
    /**
     * 主方法，用于测试目录创建功能
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        DirectoryCreator creator = new DirectoryCreator();
        creator.createDirectoryStructure(BASE_PATH, START_MONTH, END_MONTH);
    }
}