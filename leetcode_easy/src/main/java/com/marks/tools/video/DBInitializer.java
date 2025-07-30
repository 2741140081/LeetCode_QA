package com.marks.tools.video;

import com.marks.utils.DBUtil;

import java.io.File;
import java.sql.*;

public class DBInitializer {
    public static void initialize() {
        File dataDir = new File("D:\\spider\\data");
        File[] folders = dataDir.listFiles(File::isDirectory);

        if (folders == null) return;

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();

            // 清空原有数据
            Statement stmt = conn.createStatement();
            stmt.execute("TRUNCATE TABLE image_type_detail");
            stmt.close();

            // 准备插入语句（添加image_max_count字段）
            pstmt = conn.prepareStatement(
                    "INSERT INTO image_type_detail (image_name, image_rotate_degree, image_format, image_max_count) VALUES (?, 0, ?, ?)");

            for (File folder : folders) {
                String folderName = folder.getName();
                String format = detectImageFormat(folder);
                int maxCount = getImageCount(folder);

                pstmt.setString(1, folderName);
                pstmt.setString(2, format);
                pstmt.setInt(3, maxCount);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    private static String detectImageFormat(File folder) {
        File resultDir = new File(folder, "result");
        if (!resultDir.exists()) return ".webp"; // 默认格式

        File[] files = resultDir.listFiles((dir, name) ->
                name.toLowerCase().matches("img\\d+\\.(webp|jpg|jpeg|png)"));

        if (files != null && files.length > 0) {
            String name = files[0].getName();
            return name.substring(name.lastIndexOf('.'));
        }
        return ".webp"; // 默认格式
    }

    private static int getImageCount(File folder) {
        File resultDir = new File(folder, "result");
        if (!resultDir.exists()) return 0;

        File[] files = resultDir.listFiles((dir, name) ->
                name.toLowerCase().matches("img\\d+\\.(webp|jpg|jpeg|png)"));

        return files != null ? files.length : 0;
    }

    public static void main(String[] args) {
        DBInitializer.initialize();
    }
}
