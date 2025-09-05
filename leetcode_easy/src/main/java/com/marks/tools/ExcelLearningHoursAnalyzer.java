package com.marks.tools;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Excel学习时长分析器
 * 用于读取Excel文件，统计特定用户在指定月份的学习时长，并将结果输出到Excel文件
 */
public class ExcelLearningHoursAnalyzer {

    /**
     * 主方法，用于测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        try {
            // 示例用法
            String dataExcelFilePath = "D:\\excel\\data.xlsx";  // 数据文件路径
            String userExcelFilePath = "D:\\excel\\user.xlsx";  // 用户文件路径
            String targetMonth = "2025-08";                                    // 目标月份
            String outputExcelPath = "D:\\excel\\output.xlsx";                           // 输出Excel文件路径
            // 获取当前系统时间
            long currentTimeMillis = System.currentTimeMillis();
            analyzeLearningHours(dataExcelFilePath, userExcelFilePath, targetMonth, outputExcelPath);
            // 获取结束时间
            long endTime = System.currentTimeMillis();
            System.out.println("耗时: " + (endTime - currentTimeMillis) + "ms");
            System.out.println("分析完成，结果已保存到 " + outputExcelPath);
        } catch (Exception e) {
            System.err.println("处理过程中出现错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 分析Excel文件中指定用户在指定月份的学习时长
     * 
     * @param dataExcelFilePath 数据Excel文件路径 (包含学习记录)
     * @param userExcelFilePath 用户Excel文件路径 (包含要查询的员工ID)
     * @param targetMonth 目标月份，格式为"yyyy-MM"
     * @param outputExcelPath 输出Excel文件路径
     * @throws IOException 当文件读写出现问题时抛出
     */
    public static void analyzeLearningHours(String dataExcelFilePath, String userExcelFilePath, 
                                          String targetMonth, String outputExcelPath) throws IOException {
        // 从user.xlsx读取员工ID
        String[] targetStaffIds = readStaffIdsFromExcel(userExcelFilePath);
        Set<String> targetStaffIdSet = new HashSet<>(Arrays.asList(targetStaffIds));
        
        // 解析目标月份
        YearMonth yearMonth = YearMonth.parse(targetMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
        
        // 存储结果的列表
        List<UserLearningData> results = new ArrayList<>();
        Map<String, Integer> staffIdSummary = new HashMap<>();
        
        // 读取数据Excel文件
        try (FileInputStream fis = new FileInputStream(dataExcelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            // 获取第一个工作表
            Sheet sheet = workbook.getSheet("Trainings");
            if (sheet == null) {
                throw new IllegalArgumentException("Excel文件中未找到名为'Trainings'的工作表");
            }
            
            // 获取表头行，确定各列的索引
            Row headerRow = sheet.getRow(0);
            Map<String, Integer> columnIndices = getColumnIndices(headerRow);
            
            // 检查必要的列是否存在
            if (!columnIndices.containsKey("Staff ID") || 
                !columnIndices.containsKey("IT Code") || 
                !columnIndices.containsKey("Name") || 
                !columnIndices.containsKey("Total Course Hours") || 
                !columnIndices.containsKey("Grow Course ID") ||
                !columnIndices.containsKey("StartDate") ||
                !columnIndices.containsKey("EndDate")) {
                throw new IllegalArgumentException("Excel文件缺少必要的列");
            }
            
            // 获取各列索引
            int staffIdCol = columnIndices.get("Staff ID");
            int itCodeCol = columnIndices.get("IT Code");
            int nameCol = columnIndices.get("Name");
            int startDateCol = columnIndices.get("StartDate");  // 第V列 (从0开始计数为第21列)
            int endDateCol = columnIndices.get("EndDate");      // 第W列 (从0开始计数为第22列)
            int totalCourseHoursCol = columnIndices.get("Total Course Hours");
            int growCourseIdCol = columnIndices.get("Grow Course ID");
            
            // 遍历数据行
            Map<String, UserLearningData> userDataMap = new HashMap<>();
            System.out.println("总行数"  + sheet.getLastRowNum());
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                // 获取员工ID
                Cell staffIdCell = row.getCell(staffIdCol);
                if (staffIdCell == null) continue;

                String staffId = getCellValueAsString(staffIdCell);
                // 确保员工ID不使用科学计数法
                if (staffId != null && staffId.contains("E")) {
                    try {
                        double numericValue = Double.parseDouble(staffId);
                        DecimalFormat df = new DecimalFormat("#");
                        staffId = df.format(numericValue);
                    } catch (NumberFormatException e) {
                        // 如果解析失败，保持原值
                    }
                }
                staffIdSummary.put(staffId, staffIdSummary.getOrDefault(staffId, 0) + 1);
                // 检查是否为目标员工
                if (!targetStaffIdSet.contains(staffId)) continue;
                
                // 获取Grow Course ID
                Cell growCourseIdCell = row.getCell(growCourseIdCol);
                String growCourseId = growCourseIdCell != null ? getCellValueAsString(growCourseIdCell) : null;
                
                // 如果Grow Course ID为空，则跳过该课程
                if (growCourseId == null || growCourseId.trim().isEmpty()) continue;
                
                // 获取课程开始日期和结束日期
                Cell startDateCell = row.getCell(startDateCol);
                Cell endDateCell = row.getCell(endDateCol);
                
                if (startDateCell == null || endDateCell == null) continue;
                
                String startDateStr = getCellValueAsString(startDateCell);
                String endDateStr = getCellValueAsString(endDateCell);
                
                if (startDateStr == null || startDateStr.trim().isEmpty() || 
                    endDateStr == null || endDateStr.trim().isEmpty()) continue;
                
                // 检查课程日期是否与目标月份有交集
                if (!isDateRangeOverlapTargetMonth(startDateStr, endDateStr, firstDayOfMonth, lastDayOfMonth)) continue;
                
                // 获取课程时长
                Cell totalHoursCell = row.getCell(totalCourseHoursCol);
                double totalHours = 0;
                if (totalHoursCell != null) {
                    switch (totalHoursCell.getCellType()) {
                        case NUMERIC:
                            totalHours = totalHoursCell.getNumericCellValue();
                            break;
                        case STRING:
                            try {
                                totalHours = Double.parseDouble(totalHoursCell.getStringCellValue());
                            } catch (NumberFormatException e) {
                                // 如果无法解析为数字，则视为0
                            }
                            break;
                    }
                }
                
                // 获取用户基本信息
                String itCode = "";
                String name = "";
                
                Cell itCodeCell = row.getCell(itCodeCol);
                if (itCodeCell != null) {
                    itCode = getCellValueAsString(itCodeCell);
                }
                
                Cell nameCell = row.getCell(nameCol);
                if (nameCell != null) {
                    name = getCellValueAsString(nameCell);
                }
//                System.out.println("课程ID:" + growCourseId + ", 课程时长" + totalHours);
                // 累加用户学习时长
                if (!userDataMap.containsKey(staffId)) {
                    userDataMap.put(staffId, new UserLearningData(staffId, itCode, name, 0.0));
                }
                UserLearningData userData = userDataMap.get(staffId);
                userData.totalHours += totalHours;
            }
            
            // 将结果添加到列表中
            results.addAll(userDataMap.values());
        }
        // 变量 staffSummary 用于记录员工ID出现的次数
        int sum = 0;
        for (Map.Entry<String, Integer> entry : staffIdSummary.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
            sum += entry.getValue();
        }
        System.out.println("员工ID总数：" + sum);
        
        // 将结果写入Excel文件
        writeResultsToExcel(results, outputExcelPath);
    }
    
    /**
     * 从Excel文件中读取员工ID
     * 
     * @param excelFilePath Excel文件路径
     * @return 员工ID数组
     * @throws IOException 当文件读取出现问题时抛出
     */
    private static String[] readStaffIdsFromExcel(String excelFilePath) throws IOException {
        List<String> staffIds = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            // 获取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);
            
            // 查找"Staff ID"列
            Row headerRow = sheet.getRow(0);
            Integer staffIdColIndex = null;
            
            if (headerRow != null) {
                for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                    Cell cell = headerRow.getCell(i);
                    if (cell != null) {
                        String columnName = getCellValueAsString(cell);
                        if ("Staff ID".equals(columnName)) {
                            staffIdColIndex = i;
                            break;
                        }
                    }
                }
            }
            
            // 如果找不到"Staff ID"列，则默认第一列为员工ID
            if (staffIdColIndex == null) {
                staffIdColIndex = 0;
            }
            
            // 从第二行开始读取员工ID（跳过表头）
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Cell cell = row.getCell(staffIdColIndex);
                    if (cell != null) {
                        String staffId = getCellValueAsString(cell);
                        if (staffId != null && !staffId.trim().isEmpty()) {
                            staffIds.add(staffId.trim());
                        }
                    }
                }
            }
        }
        
        return staffIds.toArray(new String[0]);
    }
    
    /**
     * 获取行中各列标题的索引
     * 
     * @param headerRow 表头行
     * @return 列标题到索引的映射
     */
    private static Map<String, Integer> getColumnIndices(Row headerRow) {
        Map<String, Integer> indices = new HashMap<>();
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null) {
                String columnName = getCellValueAsString(cell);
                if (columnName != null && !columnName.trim().isEmpty()) {
                    // 处理包含换行符的列名，使用第一行内容进行匹配
                    String trimmedColumnName = columnName.trim().split("\\r?\\n")[0].trim();
                    indices.put(trimmedColumnName, i);

                    // 同时也保留完整的列名（包含换行符的内容）
                    indices.put(columnName.trim(), i);
                }
            }
        }
        return indices;
    }
    

    /**
     * 获取单元格的字符串值，特别处理员工ID等需要保持原始格式的数值
     *
     * @param cell 单元格
     * @return 单元格的字符串值
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return null;

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 使用指定格式将日期转换为字符串
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.CHINA);
                    return sdf.format(date);
                } else {
                    // 对于数值类型，使用 DecimalFormat 避免科学计数法
                    DecimalFormat df = new DecimalFormat("#");
                    return df.format(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
    
    /**
     * 检查日期范围是否与目标月份有交集
     * 
     * @param startDateStr 开始日期字符串
     * @param endDateStr 结束日期字符串
     * @param firstDayOfMonth 目标月份的第一天
     * @param lastDayOfMonth 目标月份的最后一天
     * @return 如果日期范围与目标月份有交集返回true，否则返回false
     */
    private static boolean isDateRangeOverlapTargetMonth(String startDateStr, String endDateStr,
                                                         LocalDate firstDayOfMonth, LocalDate lastDayOfMonth) {
        try {
            // 尝试解析几种常见的日期格式
            String[] datePatterns = {"yyyy-MM-dd", "dd/MM/yyyy", "MM/dd/yyyy", "yyyy/MM/dd", "dd-MMM-yyyy"};

            LocalDate startDate = null;
            LocalDate endDate = null;

            // 解析开始日期
            for (String pattern : datePatterns) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.CHINA);
                    startDate = LocalDate.parse(startDateStr, formatter);
                    break;
                } catch (DateTimeParseException e) {
                    // 继续尝试下一个格式
                }
            }

            // 解析结束日期
            for (String pattern : datePatterns) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.CHINA);
                    endDate = LocalDate.parse(endDateStr, formatter);
                    break;
                } catch (DateTimeParseException e) {
                    // 继续尝试下一个格式
                }
            }

            // 如果解析成功，检查日期范围是否与目标月份有交集
            if (startDate != null && endDate != null) {
                // 两个日期范围有交集的条件是：
                // startDate <= lastDayOfMonth && endDate >= firstDayOfMonth
                return !startDate.isAfter(lastDayOfMonth) && !endDate.isBefore(firstDayOfMonth);
            }
        } catch (Exception e) {
            // 解析失败，返回false
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 将结果写入Excel文件
     * 
     * @param results 结果数据列表
     * @param outputExcelPath 输出Excel文件路径
     * @throws IOException 当文件写入出现问题时抛出
     */
    private static void writeResultsToExcel(List<UserLearningData> results, String outputExcelPath) throws IOException {
        // 添加排序：根据staff ID进行升序排序（按数值大小排序）
        results.sort((data1, data2) -> {
            if (data1.staffId == null && data2.staffId == null) return 0;
            if (data1.staffId == null) return -1;
            if (data2.staffId == null) return 1;

            try {
                // 将Staff ID转换为整数进行比较
                int id1 = Integer.parseInt(data1.staffId);
                int id2 = Integer.parseInt(data2.staffId);
                return Integer.compare(id1, id2);
            } catch (NumberFormatException e) {
                // 如果无法转换为整数，则按字符串排序
                return data1.staffId.compareTo(data2.staffId);
            }
        });

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("学习时长统计");
            
            // 创建表头
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Staff ID");
            headerRow.createCell(1).setCellValue("IT Code");
            headerRow.createCell(2).setCellValue("Name");
            headerRow.createCell(3).setCellValue("Total Course Hours");
            
            // 写入数据行
            int rowNum = 1;
            for (UserLearningData data : results) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.staffId);
                row.createCell(1).setCellValue(data.itCode);
                row.createCell(2).setCellValue(data.name);
                row.createCell(3).setCellValue(data.totalHours);
            }
            
            // 自动调整列宽
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // 写入文件
            try (FileOutputStream fos = new FileOutputStream(outputExcelPath)) {
                workbook.write(fos);
            }
        }
    }
    
    /**
     * 用户学习数据类
     */
    private static class UserLearningData {
        String staffId;
        String itCode;
        String name;
        double totalHours;
        
        UserLearningData(String staffId, String itCode, String name, double totalHours) {
            this.staffId = staffId;
            this.itCode = itCode;
            this.name = name;
            this.totalHours = totalHours;
        }
    }
    

}