package com.marks.tools.video;

import com.marks.utils.DBUtil;

import java.io.File;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DBInitializerPlus {
    private static final Pattern IMG_PATTERN = Pattern.compile("img(\\d+)\\.webp");
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        System.out.println("开始初始化数据库...");
        DBInitializerPlus.initialize();
        long endTime = System.currentTimeMillis();
        System.out.println("数据库初始化完成");
        System.out.println("总耗时: " + ((endTime - startTime) / 60000) + " minute");

        // 关闭线程池
        executor.shutdown();
    }

    public static void initialize() {
        File dataDir = new File("D:\\spider\\data");
        File[] folders = dataDir.listFiles(File::isDirectory);

        if (folders == null) return;

        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            // 获取数据库中现有的文件夹信息
            Map<String, Integer> existingFolders = getExistingFolders(conn);

            // 获取文件系统中的文件夹名称
            Map<String, Integer> fileSystemFolders = Arrays.stream(folders)
                    .collect(Collectors.toMap(File::getName, f -> 1));

            // 找出需要删除、新增、更新的文件夹
            Set<String> foldersToDelete = new HashSet<>(existingFolders.keySet());
            foldersToDelete.removeAll(fileSystemFolders.keySet());

            Set<String> foldersToAdd = new HashSet<>(fileSystemFolders.keySet());
            foldersToAdd.removeAll(existingFolders.keySet());

            Set<String> foldersToUpdate = new HashSet<>(existingFolders.keySet());
            foldersToUpdate.retainAll(fileSystemFolders.keySet());

            // 并行处理删除操作
            if (!foldersToDelete.isEmpty()) {
                deleteFolders(conn, foldersToDelete);
            }

            // 并行处理新增操作
            if (!foldersToAdd.isEmpty()) {
                addNewFoldersParallel(conn, foldersToAdd, folders);
                existingFolders = getExistingFolders(conn);
            }

            // 并行处理更新操作
            if (!foldersToUpdate.isEmpty()) {
                updateExistingFoldersParallel(conn, folders, foldersToUpdate);
            }

            // 并行处理图片详情插入
            insertImageDetailsForFoldersParallel(conn, folders, existingFolders);

            conn.commit();
            System.out.println("数据库更新完成：删除" + foldersToDelete.size() + "个文件夹，新增" + foldersToAdd.size() + "个文件夹");

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DBUtil.close(conn, null, null);
        }
    }

    private static void addNewFoldersParallel(Connection conn, Set<String> foldersToAdd, File[] allFolders) throws SQLException {
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 将文件夹分批处理
        List<List<File>> batches = createBatches(allFolders, 50);

        for (List<File> batch : batches) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    addNewFoldersBatch(conn, foldersToAdd, batch);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }, executor);

            futures.add(future);
        }

        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private static void addNewFoldersBatch(Connection conn, Set<String> foldersToAdd, List<File> batch) throws SQLException {
        List<File> filteredFolders = batch.stream()
                .filter(folder -> foldersToAdd.contains(folder.getName()))
                .toList();

        if (filteredFolders.isEmpty()) return;

        // 批量插入
        String sql = "INSERT INTO image_type_detail (image_name, image_rotate_degree, image_format, image_max_count, image_his_index) VALUES (?, 0, ?, ?, 0)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (File folder : filteredFolders) {
                String folderName = folder.getName();
                String format = detectImageFormat(folder);
                int maxCount = getImageCount(folder);

                pstmt.setString(1, folderName);
                pstmt.setString(2, format);
                pstmt.setInt(3, maxCount);
                pstmt.addBatch();

                if (maxCount % 100 == 0) { // 每100个执行一次
                    pstmt.executeBatch();
                }
            }
            pstmt.executeBatch(); // 执行剩余批次
            System.out.println("新增文件夹记录: " + filteredFolders.size() + "个");
        }
    }

    private static void updateExistingFoldersParallel(Connection conn, File[] folders, Set<String> foldersToUpdate) throws SQLException {
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 将文件夹分批处理
        List<List<File>> batches = createBatches(folders, 50);

        for (List<File> batch : batches) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    updateExistingFoldersBatch(conn, batch, foldersToUpdate);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }, executor);

            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private static void updateExistingFoldersBatch(Connection conn, List<File> batch, Set<String> foldersToUpdate) throws SQLException {
        List<File> filteredFolders = batch.stream()
                .filter(folder -> foldersToUpdate.contains(folder.getName()))
                .toList();

        if (filteredFolders.isEmpty()) return;

        // 批量更新
        String sql = "UPDATE image_type_detail SET image_format = ?, image_max_count = ? WHERE image_name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (File folder : filteredFolders) {
                String folderName = folder.getName();
                String format = detectImageFormat(folder);
                int maxCount = getImageCount(folder);

                pstmt.setString(1, format);
                pstmt.setInt(2, maxCount);
                pstmt.setString(3, folderName);
                pstmt.addBatch();

                if (maxCount % 100 == 0) {
                    pstmt.executeBatch();
                }
            }
            pstmt.executeBatch();
        }
    }

    private static void insertImageDetailsForFoldersParallel(Connection conn, File[] folders, Map<String, Integer> folderIds) throws SQLException {
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 将文件夹分批处理
        List<List<File>> batches = createBatches(folders, 20);

        for (List<File> batch : batches) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    insertImageDetailsForFoldersBatch(conn, batch, folderIds);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }, executor);

            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private static void insertImageDetailsForFoldersBatch(Connection conn, List<File> batch, Map<String, Integer> folderIds) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM image_display_detail WHERE image_type_id = ? AND image_detail_name = ?";
        String insertSql = "INSERT INTO image_display_detail (image_type_id, image_detail_name, image_name_index, image_name_format) VALUES (?, ?, ?, ?)";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            for (File folder : batch) {
                String folderName = folder.getName();
                if (folderIds.containsKey(folderName)) {
                    int folderId = folderIds.get(folderName);
                    importNewImageDetailsOptimized(conn, checkStmt, insertStmt, folderId, folder);
                }
            }
        }
    }

    private static void importNewImageDetailsOptimized(Connection conn, PreparedStatement checkStmt, PreparedStatement insertStmt,
                                                       int folderId, File folder) throws SQLException {
        File resultDir = new File(folder, "result");
        if (!resultDir.exists()) return;

        File[] imageFiles = resultDir.listFiles((dir, name) ->
                name.toLowerCase().matches("img\\d+\\.(webp|jpg|jpeg|png)"));

        if (imageFiles == null || imageFiles.length == 0) return;

        // 按图片数字ID排序
        List<File> sortedImages = new ArrayList<>(List.of(imageFiles));
        sortedImages.sort((f1, f2) -> {
            int id1 = extractImageId(f1.getName());
            int id2 = extractImageId(f2.getName());
            return Integer.compare(id1, id2);
        });

        // 首先检查哪些图片已经存在
        Set<String> existingImages = new HashSet<>();
        try (PreparedStatement selectExistingStmt = conn.prepareStatement(
                "SELECT image_detail_name FROM image_display_detail WHERE image_type_id = ?")) {
            selectExistingStmt.setInt(1, folderId);
            try (ResultSet rs = selectExistingStmt.executeQuery()) {
                while (rs.next()) {
                    existingImages.add(rs.getString("image_detail_name"));
                }
            }
        }

        // 只插入不存在的图片
        int count = 0;
        for (File imageFile : sortedImages) {
            String fileName = imageFile.getName();

            if (!existingImages.contains(fileName)) {
                int index = extractImageId(fileName);
                String format = fileName.substring(fileName.lastIndexOf('.') + 1);

                insertStmt.setInt(1, folderId);
                insertStmt.setString(2, fileName);
                insertStmt.setInt(3, index);
                insertStmt.setString(4, format);
                insertStmt.addBatch();
                count++;
            }
        }

        // 执行批量插入
        if (count > 0) {
            insertStmt.executeBatch();
        }

        System.out.println("为文件夹 '" + folder.getName() + "' 新增了 " + count + " 张图片信息");
    }

    private static <T> List<List<T>> createBatches(T[] array, int batchSize) {
        List<List<T>> batches = new ArrayList<>();
        for (int i = 0; i < array.length; i += batchSize) {
            int end = Math.min(i + batchSize, array.length);
            batches.add(Arrays.asList(Arrays.copyOfRange(array, i, end)));
        }
        return batches;
    }

    private static <T> List<List<T>> createBatches(List<T> list, int batchSize) {
        List<List<T>> batches = new ArrayList<>();
        for (int i = 0; i < list.size(); i += batchSize) {
            int end = Math.min(i + batchSize, list.size());
            batches.add(list.subList(i, end));
        }
        return batches;
    }

    // 其他辅助方法保持不变...
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

    private static int extractImageId(String filename) {
        Matcher matcher = IMG_PATTERN.matcher(filename);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        // 对于其他格式的图片文件
        Pattern otherPattern = Pattern.compile("img(\\d+)\\.(webp|jpg|jpeg|png)", Pattern.CASE_INSENSITIVE);
        matcher = otherPattern.matcher(filename);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }

    private static String detectImageFormat(File folder) {
        File resultDir = new File(folder, "result");
        if (!resultDir.exists()) return ".webp";

        File[] files = resultDir.listFiles((dir, name) ->
                name.toLowerCase().matches("img\\d+\\.(webp|jpg|jpeg|png)"));

        if (files != null && files.length > 0) {
            String name = files[0].getName();
            return name.substring(name.lastIndexOf('.'));
        }
        return ".webp";
    }

    private static int getImageCount(File folder) {
        File resultDir = new File(folder, "result");
        if (!resultDir.exists()) return 0;

        File[] files = resultDir.listFiles((dir, name) ->
                name.toLowerCase().matches("img\\d+\\.(webp|jpg|jpeg|png)"));
        return files != null ? files.length : 0;
    }
}
