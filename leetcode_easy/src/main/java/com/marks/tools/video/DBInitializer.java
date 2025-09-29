package com.marks.tools.video;

import com.marks.utils.DBUtil;
import lombok.val;

import java.io.File;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBInitializer {
    private static final Pattern IMG_PATTERN = Pattern.compile("img(\\d+)\\.webp");

    public static void main(String[] args) {
        System.out.println("开始初始化数据库...");
        DBInitializer.initialize();
        System.out.println("数据库初始化完成");
    }
    public static void initialize() {
        File dataDir = new File("D:\\spider\\data");
        File[] folders = dataDir.listFiles(File::isDirectory);

        if (folders == null) return;

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // 使用事务前先关闭自动提交

            // 获取数据库中现有的文件夹信息
            Map<String, Integer> existingFolders = getExistingFolders(conn);

            // 获取文件系统中的文件夹名称
            Set<String> fileSystemFolders = new HashSet<>();
            for (File folder : folders) {
                fileSystemFolders.add(folder.getName());
            }

            // 找出需要删除的文件夹（在数据库中但不在文件系统中）
            Set<String> foldersToDelete = new HashSet<>(existingFolders.keySet());
            foldersToDelete.removeAll(fileSystemFolders);

            // 找出需要新增的文件夹（在文件系统中但不在数据库中）
            Set<String> foldersToAdd = new HashSet<>(fileSystemFolders);
            foldersToAdd.removeAll(existingFolders.keySet());

            // 删除不存在的文件夹记录
            if (!foldersToDelete.isEmpty()) {
                deleteFolders(conn, foldersToDelete);
            }

            // 添加新的文件夹记录
            if (!foldersToAdd.isEmpty()) {
                addNewFolders(conn, foldersToAdd, folders);
                // 重新获取文件夹信息，包括新添加的
                existingFolders = getExistingFolders(conn);
            }

            // 更新现有文件夹的信息（格式和图片数量）
            updateExistingFolders(conn, folders);

            // 为每个文件夹新增图片详细信息（只新增，不删除）
            insertImageDetailsForFolders(conn, folders, existingFolders);

            conn.commit(); // 提交事务
            System.out.println("数据库更新完成：删除" + foldersToDelete.size() + "个文件夹，新增" + foldersToAdd.size() + "个文件夹");

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // 回滚事务
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // 恢复自动提交
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DBUtil.close(conn, null, null);
        }
    }

    private static Map<String, Integer> getExistingFolders(Connection conn) throws SQLException {
        Map<String, Integer> existingFolders = new HashMap<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement("SELECT image_id, image_name FROM image_type_detail");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                existingFolders.put(rs.getString("image_name"), rs.getInt("image_id"));
            }
        } finally {
            DBUtil.close(null, pstmt, rs);
        }

        return existingFolders;
    }

    private static void deleteFolders(Connection conn, Set<String> foldersToDelete) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            // 先删除关联的图片详细信息
            pstmt = conn.prepareStatement("DELETE FROM image_display_detail WHERE image_type_id IN (SELECT image_id FROM image_type_detail WHERE image_name = ?)");
            for (String folderName : foldersToDelete) {
                pstmt.setString(1, folderName);
                pstmt.executeUpdate();
            }

            // 再删除文件夹记录
            pstmt = conn.prepareStatement("DELETE FROM image_type_detail WHERE image_name = ?");
            for (String folderName : foldersToDelete) {
                pstmt.setString(1, folderName);
                pstmt.executeUpdate();
                System.out.println("删除文件夹记录: " + folderName);
            }
        } finally {
            DBUtil.close(null, pstmt, null);
        }
    }

    private static void addNewFolders(Connection conn, Set<String> foldersToAdd, File[] allFolders) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(
                    "INSERT INTO image_type_detail (image_name, image_rotate_degree, image_format, image_max_count, image_his_index) VALUES (?, 0, ?, ?, 0)");

            for (File folder : allFolders) {
                String folderName = folder.getName();
                if (foldersToAdd.contains(folderName)) {
                    String format = detectImageFormat(folder);
                    int maxCount = getImageCount(folder);

                    pstmt.setString(1, folderName);
                    pstmt.setString(2, format);
                    pstmt.setInt(3, maxCount);
                    pstmt.executeUpdate();
                    System.out.println("新增文件夹记录: " + folderName);
                }
            }
        } finally {
            DBUtil.close(null, pstmt, null);
        }
    }

    private static void updateExistingFolders(Connection conn, File[] folders) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(
                    "UPDATE image_type_detail SET image_format = ?, image_max_count = ? WHERE image_name = ?");

            for (File folder : folders) {
                String folderName = folder.getName();
                String format = detectImageFormat(folder);
                int maxCount = getImageCount(folder);

                pstmt.setString(1, format);
                pstmt.setInt(2, maxCount);
                pstmt.setString(3, folderName);
                pstmt.executeUpdate();
            }
        } finally {
            DBUtil.close(null, pstmt, null);
        }
    }

    private static void insertImageDetailsForFolders(Connection conn, File[] folders, Map<String, Integer> folderIds) throws SQLException {
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;

        try {
            // 检查图片是否已存在的语句
            checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM image_display_detail WHERE image_type_id = ? AND image_detail_name = ?");
            // 插入新图片信息的语句
            insertStmt = conn.prepareStatement(
                    "INSERT INTO image_display_detail (image_type_id, image_detail_name, image_name_index, image_name_format) VALUES (?, ?, ?, ?)");

            for (File folder : folders) {
                String folderName = folder.getName();
                if (folderIds.containsKey(folderName)) {
                    int folderId = folderIds.get(folderName);
                    // 为文件夹导入图片信息
                    importNewImageDetails(conn, checkStmt, insertStmt, folderId, folder);
                }
            }
        } finally {
            DBUtil.close(null, checkStmt, null);
            DBUtil.close(null, insertStmt, null);
        }
    }

    private static void importNewImageDetails(Connection conn, PreparedStatement checkStmt, PreparedStatement insertStmt,
                                              int folderId, File folder) throws SQLException {
        File resultDir = new File(folder, "result");
        if (!resultDir.exists()) return;

        File[] imageFiles = resultDir.listFiles((dir, name) ->
                name.toLowerCase().matches("img\\d+\\.(webp|jpg|jpeg|png)"));

        if (imageFiles == null) return;

        // 按图片数字ID排序
        List<File> sortedImages = new ArrayList<>(List.of(imageFiles));
        Collections.sort(sortedImages, (f1, f2) -> {
            int id1 = extractImageId(f1.getName());
            int id2 = extractImageId(f2.getName());
            return Integer.compare(id1, id2);
        });

        int count = 0;
        for (File imageFile : sortedImages) {
            String fileName = imageFile.getName();
            int index = extractImageId(fileName);
            String format = fileName.substring(fileName.lastIndexOf('.') + 1);

            // 检查图片是否已存在
            checkStmt.setInt(1, folderId);
            checkStmt.setString(2, fileName);

            ResultSet rs = checkStmt.executeQuery();
            boolean exists = false;
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
            rs.close();

            // 如果图片不存在，则插入新记录
            if (!exists) {
                insertStmt.setInt(1, folderId);
                insertStmt.setString(2, fileName);
                insertStmt.setInt(3, index);
                insertStmt.setString(4, format);
                insertStmt.executeUpdate();
                count++;
            }
        }

        System.out.println("为文件夹 '" + folder.getName() + "' 新增了 " + count + " 张图片信息");
    }

    private static int extractImageId(String filename) {
        Matcher matcher = IMG_PATTERN.matcher(filename);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
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

}
