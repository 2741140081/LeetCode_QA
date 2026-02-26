package com.marks.tools.download_cartoon;

import com.marks.tools.dbUtil.DatabasePool;
import com.marks.tools.download_cartoon.entity.ChapterInfo;
import com.marks.tools.download_cartoon.entity.ImageInfo;
import com.marks.tools.download_cartoon.utils.CartoonConstants;
import com.marks.tools.download_cartoon.utils.PdfConverterUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: CartoonDownloader </p>
 * <p>描述: 漫画下载器主类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/26 15:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class CartoonDownloader {
    // 线程池
    private ExecutorService chapterExecutor;
    private ExecutorService imageExecutor;

    // 目标网站URL（待定）
    private static final String TARGET_BASE_URL = "https://www.target.com"; // 待替换为目标漫画网站

    // 保存目录
    private String saveBaseDir;

    // 数据库相关
    private static final String CREATE_CHAPTER_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS " + CartoonConstants.CHAPTER_TABLE + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "chapter_id VARCHAR(100) NOT NULL UNIQUE, " +
                    "chapter_title VARCHAR(255) NOT NULL, " +
                    "chapter_url TEXT NOT NULL, " +
                    "chapter_index INT NOT NULL, " +
                    "status INT DEFAULT 0, " +
                    "created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

    private static final String CREATE_IMAGE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS " + CartoonConstants.IMAGE_TABLE + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "image_id VARCHAR(100) NOT NULL, " +
                    "image_url TEXT NOT NULL, " +
                    "chapter_id VARCHAR(100) NOT NULL, " +
                    "image_index INT NOT NULL, " +
                    "download_status INT DEFAULT 0, " +
                    "created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (chapter_id) REFERENCES " + CartoonConstants.CHAPTER_TABLE + "(chapter_id)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

    public CartoonDownloader() {
        this(CartoonConstants.DEFAULT_SAVE_DIR);
    }

    public CartoonDownloader(String saveDir) {
        this.saveBaseDir = saveDir.endsWith(File.separator) ? saveDir : saveDir + File.separator;
        initializeThreadPool();
        initializeDatabase();
    }

    /**
     * 初始化线程池
     */
    private void initializeThreadPool() {
        chapterExecutor = Executors.newFixedThreadPool(CartoonConstants.THREAD_POOL_SIZE);
        imageExecutor = Executors.newFixedThreadPool(CartoonConstants.IMAGE_DOWNLOAD_THREAD_POOL_SIZE);
        System.out.println("线程池初始化完成");
    }

    /**
     * 初始化数据库
     */
    private void initializeDatabase() {
        Connection conn = null;
        try {
            conn = DatabasePool.getConnection();

            // 创建章节表
            try (PreparedStatement stmt = conn.prepareStatement(CREATE_CHAPTER_TABLE_SQL)) {
                stmt.execute();
            }

            // 创建图片表
            try (PreparedStatement stmt = conn.prepareStatement(CREATE_IMAGE_TABLE_SQL)) {
                stmt.execute();
            }

            System.out.println("数据库表初始化完成");
        } catch (Exception e) {
            System.err.println("数据库初始化失败: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * 主下载流程
     */
    public void downloadCartoon(String cartoonBaseUrl) {
        long startTime = System.currentTimeMillis();
        System.out.println("开始下载漫画，目标URL: " + cartoonBaseUrl);

        try {
            // 1. 提取所有章节信息
            List<ChapterInfo> chapters = extractChapters(cartoonBaseUrl);
            System.out.println("共找到 " + chapters.size() + " 个章节");

            // 2. 保存章节信息到数据库
            saveChaptersToDatabase(chapters);

            // 3. 多线程处理每个章节
            processChaptersConcurrently(chapters);

            // 4. 合并图片为PDF
            convertChaptersToPdf(chapters);

            // 5. 清理临时图片文件
            cleanupTempImages(chapters);

            long endTime = System.currentTimeMillis();
            System.out.println("漫画下载完成，总耗时: " + (endTime - startTime) / 1000 + " 秒");

        } catch (Exception e) {
            System.err.println("下载过程中出现错误: " + e.getMessage());
            e.printStackTrace();
        } finally {
            shutdownExecutors();
            DatabasePool.closeDataSource();
        }
    }

    /**
     * 提取章节信息
     */
    private List<ChapterInfo> extractChapters(String baseUrl) {
        List<ChapterInfo> chapters = new ArrayList<>();

        try {
            System.out.println("正在提取章节信息...");
            Document doc = Jsoup.connect(baseUrl)
                    .userAgent(CartoonConstants.USER_AGENT)
                    .timeout(CartoonConstants.TIMEOUT)
                    .get();

            // 根据实际网站结构调整选择器
            Elements chapterElements = doc.select("a[href*=/chapter/]"); // 示例选择器

            int index = 1;
            for (Element element : chapterElements) {
                String chapterUrl = element.absUrl("href");
                String chapterTitle = element.text().trim();
                String chapterId = extractChapterId(chapterUrl);

                if (chapterId != null && !chapterTitle.isEmpty()) {
                    ChapterInfo chapter = new ChapterInfo(chapterId, chapterTitle, chapterUrl, index++);
                    chapters.add(chapter);
                }
            }

        } catch (Exception e) {
            System.err.println("提取章节信息失败: " + e.getMessage());
        }

        return chapters;
    }

    /**
     * 从URL中提取章节ID
     */
    private String extractChapterId(String url) {
        // 根据实际URL格式调整正则表达式
        Pattern pattern = Pattern.compile("/chapter/([^/]+)");
        Matcher matcher = pattern.matcher(url);
        return matcher.find() ? matcher.group(1) : null;
    }

    /**
     * 保存章节信息到数据库
     */
    private void saveChaptersToDatabase(List<ChapterInfo> chapters) {
        String insertSQL = "INSERT IGNORE INTO " + CartoonConstants.CHAPTER_TABLE +
                " (chapter_id, chapter_title, chapter_url, chapter_index, status) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DatabasePool.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                for (ChapterInfo chapter : chapters) {
                    pstmt.setString(1, chapter.getChapterId());
                    pstmt.setString(2, chapter.getChapterTitle());
                    pstmt.setString(3, chapter.getChapterUrl());
                    pstmt.setInt(4, chapter.getChapterIndex());
                    pstmt.setInt(5, chapter.getStatus());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                conn.commit();
                System.out.println("章节信息已保存到数据库");
            }
        } catch (Exception e) {
            System.err.println("保存章节信息到数据库失败: " + e.getMessage());
            rollbackTransaction(conn);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * 并发处理章节
     */
    private void processChaptersConcurrently(List<ChapterInfo> chapters) {
        List<Future<ChapterProcessResult>> futures = new ArrayList<>();
        List<ChapterInfo> completedChapters = new ArrayList<>();

        // 提交所有章节处理任务
        for (ChapterInfo chapter : chapters) {
            Future<ChapterProcessResult> future = chapterExecutor.submit(() -> processChapterWithResult(chapter));
            futures.add(future);
        }

        // 收集处理结果
        for (Future<ChapterProcessResult> future : futures) {
            try {
                ChapterProcessResult result = future.get();
                System.out.println(result.getMessage());
                if (result.isSuccess()) {
                    completedChapters.add(result.getChapterInfo());
                }
            } catch (Exception e) {
                System.err.println("章节处理任务执行失败: " + e.getMessage());
            }
        }

        // 批量更新章节状态
        if (!completedChapters.isEmpty()) {
            batchUpdateChapterStatus(completedChapters);
        }
    }

    /**
     * 处理单个章节并返回结果
     */
    private ChapterProcessResult processChapterWithResult(ChapterInfo chapter) {
        try {
            System.out.println("开始处理章节: " + chapter.getChapterTitle());

            // 1. 提取章节中的图片信息
            List<ImageInfo> images = extractImagesFromChapter(chapter);

            // 2. 保存图片信息到数据库
            saveImagesToDatabase(images);

            // 3. 下载图片
            downloadImages(images);

            // 更新章节状态为完成
            chapter.setStatus(CartoonConstants.STATUS_COMPLETED);

            return new ChapterProcessResult(true, "章节 " + chapter.getChapterTitle() + " 处理完成", chapter);

        } catch (Exception e) {
            System.err.println("处理章节 " + chapter.getChapterTitle() + " 失败: " + e.getMessage());
            // 更新章节状态为失败
            chapter.setStatus(CartoonConstants.STATUS_FAILED);
            return new ChapterProcessResult(false, "章节 " + chapter.getChapterTitle() + " 处理失败: " + e.getMessage(), chapter);
        }
    }

    /**
     * 批量更新章节状态
     */
    private void batchUpdateChapterStatus(List<ChapterInfo> chapters) {
        if (chapters.isEmpty()) {
            return;
        }

        String updateSQL = "UPDATE " + CartoonConstants.CHAPTER_TABLE + " SET status = ? WHERE chapter_id = ?";

        Connection conn = null;
        try {
            conn = DatabasePool.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                for (ChapterInfo chapter : chapters) {
                    pstmt.setInt(1, chapter.getStatus());
                    pstmt.setString(2, chapter.getChapterId());
                    pstmt.addBatch();
                }
                int[] results = pstmt.executeBatch();
                conn.commit();
                System.out.println("批量更新了 " + results.length + " 个章节的状态");
            }
        } catch (Exception e) {
            System.err.println("批量更新章节状态失败: " + e.getMessage());
            rollbackTransaction(conn);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * 从章节页面提取图片信息
     */
    private List<ImageInfo> extractImagesFromChapter(ChapterInfo chapter) {
        List<ImageInfo> images = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(chapter.getChapterUrl())
                    .userAgent(CartoonConstants.USER_AGENT)
                    .timeout(CartoonConstants.TIMEOUT)
                    .get();

            // 根据实际网站结构调整选择器
            Elements imgElements = doc.select("img[data-src], img[src]"); // 示例选择器

            int index = 0;
            for (Element img : imgElements) {
                String imgUrl = img.absUrl("data-src");
                if (imgUrl.isEmpty()) {
                    imgUrl = img.absUrl("src");
                }

                if (!imgUrl.isEmpty()) {
                    String imageId = String.format(CartoonConstants.IMAGE_NAME_FORMAT, index);
                    ImageInfo imageInfo = new ImageInfo(imageId, imgUrl, chapter.getChapterId(), index++);
                    images.add(imageInfo);
                }
            }

        } catch (Exception e) {
            System.err.println("提取章节 " + chapter.getChapterTitle() + " 的图片信息失败: " + e.getMessage());
        }

        return images;
    }

    /**
     * 保存图片信息到数据库
     */
    private void saveImagesToDatabase(List<ImageInfo> images) {
        String insertSQL = "INSERT IGNORE INTO " + CartoonConstants.IMAGE_TABLE +
                " (image_id, image_url, chapter_id, image_index, download_status) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DatabasePool.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                for (ImageInfo image : images) {
                    pstmt.setString(1, image.getImageId());
                    pstmt.setString(2, image.getImageUrl());
                    pstmt.setString(3, image.getChapterId());
                    pstmt.setInt(4, image.getImageIndex());
                    pstmt.setInt(5, image.getDownloadStatus());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                conn.commit();
            }
        } catch (Exception e) {
            System.err.println("保存图片信息到数据库失败: " + e.getMessage());
            rollbackTransaction(conn);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * 下载图片
     */
    private void downloadImages(List<ImageInfo> images) {
        List<Future<ImageDownloadResult>> futures = new ArrayList<>();
        List<ImageInfo> downloadedImages = new ArrayList<>();

        // 提交所有下载任务
        for (ImageInfo image : images) {
            Future<ImageDownloadResult> future = imageExecutor.submit(() -> downloadSingleImageWithResult(image));
            futures.add(future);
        }

        // 收集下载结果
        for (Future<ImageDownloadResult> future : futures) {
            try {
                ImageDownloadResult result = future.get();
                System.out.println(result.getMessage());
                if (result.isSuccess()) {
                    downloadedImages.add(result.getImageInfo());
                }
            } catch (Exception e) {
                System.err.println("图片下载任务执行失败: " + e.getMessage());
            }
        }

        // 批量更新数据库状态
        if (!downloadedImages.isEmpty()) {
            batchUpdateImageStatus(downloadedImages);
        }
    }

    /**
     * 下载单张图片并返回结果对象
     */
    private ImageDownloadResult downloadSingleImageWithResult(ImageInfo image) {
        try {
            // 创建章节目录
            String chapterDir = saveBaseDir + image.getChapterId() + File.separator;
            File dir = new File(chapterDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 下载图片
            String imagePath = chapterDir + image.getImageId();
            downloadImageFromUrl(image.getImageUrl(), imagePath);

            // 更新图片状态为成功
            image.setDownloadStatus(CartoonConstants.STATUS_COMPLETED);

            return new ImageDownloadResult(true, "图片下载完成: " + image.getImageId(), image);

        } catch (Exception e) {
            // 更新图片状态为失败
            image.setDownloadStatus(CartoonConstants.STATUS_FAILED);
            return new ImageDownloadResult(false, "图片下载失败: " + image.getImageId() + ", 错误: " + e.getMessage(), image);
        }
    }

    /**
     * 批量更新图片状态
     */
    private void batchUpdateImageStatus(List<ImageInfo> images) {
        if (images.isEmpty()) {
            return;
        }

        String updateSQL = "UPDATE " + CartoonConstants.IMAGE_TABLE +
                " SET download_status = ? WHERE image_id = ? AND chapter_id = ?";

        Connection conn = null;
        try {
            conn = DatabasePool.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                for (ImageInfo image : images) {
                    pstmt.setInt(1, image.getDownloadStatus());
                    pstmt.setString(2, image.getImageId());
                    pstmt.setString(3, image.getChapterId());
                    pstmt.addBatch();
                }
                int[] results = pstmt.executeBatch();
                conn.commit();
                System.out.println("批量更新了 " + results.length + " 张图片的状态");
            }
        } catch (Exception e) {
            System.err.println("批量更新图片状态失败: " + e.getMessage());
            rollbackTransaction(conn);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * 从URL下载图片
     */
    private void downloadImageFromUrl(String imageUrl, String savePath) throws IOException {
        URL url = new URL(imageUrl);
        try (InputStream in = url.openStream();
             FileOutputStream out = new FileOutputStream(savePath)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     * 将章节图片转换为PDF
     */
    private void convertChaptersToPdf(List<ChapterInfo> chapters) {
        System.out.println("开始转换图片为PDF...");

        String pdfDir = saveBaseDir + "pdf" + File.separator;
        File pdfDirectory = new File(pdfDir);
        if (!pdfDirectory.exists()) {
            pdfDirectory.mkdirs();
        }

        for (ChapterInfo chapter : chapters) {
            if (chapter.getStatus() == CartoonConstants.STATUS_COMPLETED) {
                try {
                    String chapterDir = saveBaseDir + chapter.getChapterId() + File.separator;
                    File dir = new File(chapterDir);

                    if (dir.exists() && dir.isDirectory()) {
                        File[] imageFiles = dir.listFiles((d, name) ->
                                name.toLowerCase().matches("img\\d+\\.(jpg|jpeg|png)"));

                        if (imageFiles != null && imageFiles.length > 0) {
                            List<File> imageFileList = Arrays.asList(imageFiles);
                            String pdfPath = pdfDir + sanitizeFileName(chapter.getChapterTitle()) + ".jpg";
                            PdfConverterUtils.convertImagesToPdf(imageFileList, pdfPath, chapter.getChapterTitle());
                        }
                    }
                } catch (Exception e) {
                    System.err.println("转换章节 " + chapter.getChapterTitle() + " 为PDF失败: " + e.getMessage());
                }
            }
        }
    }

    /**
     * 清理临时图片文件
     */
    private void cleanupTempImages(List<ChapterInfo> chapters) {
        System.out.println("开始清理临时图片文件...");

        for (ChapterInfo chapter : chapters) {
            if (chapter.getStatus() == CartoonConstants.STATUS_COMPLETED) {
                String chapterDir = saveBaseDir + chapter.getChapterId();
                File dir = new File(chapterDir);

                if (dir.exists() && dir.isDirectory()) {
                    File[] files = dir.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            if (file.isFile()) {
                                file.delete();
                            }
                        }
                        dir.delete(); // 删除空目录
                    }
                }
            }
        }

        System.out.println("临时图片文件清理完成");
    }

    /**
     * 执行更新操作
     */
    private void executeUpdate(String sql, Object... params) {
        Connection conn = null;
        try {
            conn = DatabasePool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("执行更新操作失败: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * 关闭数据库连接
     */
    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("关闭数据库连接失败: " + e.getMessage());
            }
        }
    }

    /**
     * 回滚事务
     */
    private void rollbackTransaction(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("回滚事务失败: " + e.getMessage());
            }
        }
    }

    /**
     * 关闭线程池
     */
    private void shutdownExecutors() {
        if (chapterExecutor != null && !chapterExecutor.isShutdown()) {
            chapterExecutor.shutdown();
        }
        if (imageExecutor != null && !imageExecutor.isShutdown()) {
            imageExecutor.shutdown();
        }

        try {
            if (chapterExecutor != null) {
                chapterExecutor.awaitTermination(1, TimeUnit.HOURS);
            }
            if (imageExecutor != null) {
                imageExecutor.awaitTermination(1, TimeUnit.HOURS);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 清理文件名中的非法字符
     */
    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[<>:\"/\\\\|?*]", "_").trim();
    }

    /**
     * 主方法 - 使用示例
     */
    public static void main(String[] args) {
        // 示例使用方式
        CartoonDownloader downloader = new CartoonDownloader();

        // 替换为目标漫画的URL
        String cartoonUrl = "https://www.target.com/cartoon/xxx"; // 待替换

        downloader.downloadCartoon(cartoonUrl);
    }

    /**
     * 图片下载结果封装类
     */
    private static class ImageDownloadResult {
        private final boolean success;
        private final String message;
        private final ImageInfo imageInfo;

        public ImageDownloadResult(boolean success, String message, ImageInfo imageInfo) {
            this.success = success;
            this.message = message;
            this.imageInfo = imageInfo;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public ImageInfo getImageInfo() {
            return imageInfo;
        }
    }

    /**
     * 章节处理结果封装类
     */
    private static class ChapterProcessResult {
        private final boolean success;
        private final String message;
        private final ChapterInfo chapterInfo;

        public ChapterProcessResult(boolean success, String message, ChapterInfo chapterInfo) {
            this.success = success;
            this.message = message;
            this.chapterInfo = chapterInfo;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public ChapterInfo getChapterInfo() {
            return chapterInfo;
        }
    }
}
