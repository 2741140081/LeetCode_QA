package com.marks.tools.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/22 14:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ImageCrawlerPlus {
    // 数据库连接配置
    private static final String DB_URL = "jdbc:mysql://localhost:3306/image_crawler?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    
    // 线程池配置
    private static final int THREAD_POOL_SIZE = 10;
    private static ExecutorService executor;
    
    // 网站基础URL
    private static final String BASE_URL = "https://www.images.com";
    
    // 重试配置
    private static final int MAX_RETRY = 3;
    private static final long RETRY_INTERVAL = 5000; // 5秒
    
    // 图片索引计数器
    private static int index = 1;
    
    // 静态代码块，用于加载MySQL驱动类
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC驱动加载失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("请提供CSV文件路径作为参数");
            return;
        }
        
        String csvFilePath = args[0];
        
        // 初始化线程池
        executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        
        try {
            // 1. 读取CSV文件数据
            String[] datas = readCsvFile(csvFilePath);
            
            // 2. 初始化数据库
            initDatabase();
            
            // 3. 遍历数据并处理网页
            for (String data : datas) {
                String currWebUrl = BASE_URL + "/" + data + ".html";
                processWebPage(currWebUrl, data);
            }
            
            // 4. 开始下载图片
            downloadImages();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5. 关闭线程池
            shutdownExecutor();
        }
    }
    
    /**
     * 关闭线程池
     */
    private static void shutdownExecutor() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * 读取CSV文件
     * @param filePath CSV文件路径
     * @return 数据数组
     * @throws IOException 读取文件异常
     */
    private static String[] readCsvFile(String filePath) throws IOException {
        List<String> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (String value : values) {
                    value = value.trim().replaceAll("^\"|\"$", ""); // 去除引号和空格
                    if (!value.isEmpty()) {
                        dataList.add(value);
                    }
                }
            }
        }
        return dataList.toArray(new String[0]);
    }
    
    /**
     * 初始化数据库
     */
    private static void initDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS image_download_info (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "image_web_url TEXT NOT NULL, " +
                "parent_page_url TEXT NOT NULL, " +
                "image_index INT NOT NULL, " +
                "storage_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "status INT DEFAULT 0" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("数据库初始化完成");
        } catch (SQLException e) {
            System.err.println("数据库初始化失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 处理网页数据
     * @param webUrl 网页URL
     * @param parentPageUrl 父页面URL
     */
    private static void processWebPage(String webUrl, String parentPageUrl) {
        int retryCount = 0;
        
        while (retryCount < MAX_RETRY) {
            try {
                System.out.println("正在处理页面: " + webUrl);
                
                // 使用Jsoup连接网页
                Document doc = Jsoup.connect(webUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                        .timeout(30000)
                        .get();
                
                // 解析图片信息并保存到数据库
                parseAndSaveImages(doc, parentPageUrl);
                
                // 查找下一页链接
                String nextWebUrl = findNextPageUrl(doc);
                if (nextWebUrl != null && !nextWebUrl.isEmpty()) {
                    processWebPage(nextWebUrl, parentPageUrl); // 递归处理下一页
                }
                
                break; // 成功则退出重试循环
            } catch (Exception e) {
                retryCount++;
                System.err.println("处理页面失败 (" + retryCount + "/" + MAX_RETRY + "): " + webUrl);
                if (retryCount >= MAX_RETRY) {
                    System.err.println("达到最大重试次数，跳过页面: " + webUrl);
                } else {
                    try {
                        Thread.sleep(RETRY_INTERVAL);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * 解析并保存图片信息到数据库
     * @param doc 网页文档
     * @param parentPageUrl 父页面URL
     */
    private static void parseAndSaveImages(Document doc, String parentPageUrl) {
        // 使用Map存储数据，key为index，value为imageUrl
        Map<Integer, String> imageMap = new LinkedHashMap<>();
        
        // 查找所有图片元素
        Elements images = doc.select("img");
        
        for (Element img : images) {
            String imageUrl = img.absUrl("src");
            if (imageUrl != null && !imageUrl.isEmpty()) {
                imageMap.put(index++, imageUrl);
            }
        }
        
        // 批量插入到数据库
        if (!imageMap.isEmpty()) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String insertSQL = "INSERT INTO image_download_info (image_web_url, parent_page_url, image_index, status) VALUES (?, ?, ?, ?)";
                
                // 使用批处理插入数据
                try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                    conn.setAutoCommit(false); // 开启事务
                    
                    for (Map.Entry<Integer, String> entry : imageMap.entrySet()) {
                        pstmt.setString(1, entry.getValue());
                        pstmt.setString(2, parentPageUrl);
                        pstmt.setInt(3, entry.getKey());
                        pstmt.setInt(4, 0); // 0:未进行下载
                        pstmt.addBatch();
                    }
                    
                    pstmt.executeBatch(); // 执行批处理
                    conn.commit(); // 提交事务
                    
                    System.out.println("从页面 " + parentPageUrl + " 解析并保存了 " + imageMap.size() + " 张图片信息");
                } catch (SQLException e) {
                    conn.rollback(); // 回滚事务
                    System.err.println("批量插入图片信息到数据库时出错: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    conn.setAutoCommit(true); // 恢复自动提交
                }
            } catch (SQLException e) {
                System.err.println("保存图片信息到数据库时出错: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 查找下一页链接
     * @param doc 网页文档
     * @return 下一页URL
     */
    private static String findNextPageUrl(Document doc) {
        // 尝试多种方式查找下一页链接
        Elements nextLinks = doc.select("a:contains(下一页)");
        if (!nextLinks.isEmpty()) {
            return nextLinks.first().absUrl("href");
        }
        
        nextLinks = doc.select("a[title=下一页]");
        if (!nextLinks.isEmpty()) {
            return nextLinks.first().absUrl("href");
        }
        
        // 使用正则表达式查找下一页链接
        Pattern pattern = Pattern.compile("<a[^>]*href=[\"']([^\"']*)[\"'][^>]*>下一页</a>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(doc.html());
        if (matcher.find()) {
            String relativeUrl = matcher.group(1);
            if (relativeUrl.startsWith("http")) {
                return relativeUrl;
            } else {
                // 处理相对URL
                return BASE_URL + (relativeUrl.startsWith("/") ? "" : "/") + relativeUrl;
            }
        }
        
        return null;
    }
    
    /**
     * 下载图片
     */
    private static void downloadImages() {
        System.out.println("开始下载图片...");
        
        String selectSQL = "SELECT id, image_web_url FROM image_download_info WHERE status = 0";
        String updateStatusSQL = "UPDATE image_download_info SET status = ? WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
             PreparedStatement updateStmt = conn.prepareStatement(updateStatusSQL)) {
            
            ResultSet rs = selectStmt.executeQuery();
            List<DownloadTask> tasks = new ArrayList<>();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String imageUrl = rs.getString("image_web_url");
                tasks.add(new DownloadTask(id, imageUrl, updateStmt));
            }
            
            // 提交下载任务到线程池
            List<Future<String>> futures = new ArrayList<>();
            for (DownloadTask task : tasks) {
                futures.add(executor.submit(task));
            }
            
            // 等待所有下载任务完成
            for (Future<String> future : futures) {
                try {
                    String result = future.get();
                    System.out.println(result);
                } catch (ExecutionException e) {
                    System.err.println("下载任务执行出错: " + e.getMessage());
                }
            }
            
        } catch (SQLException | InterruptedException e) {
            System.err.println("下载图片时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 图片下载任务
     */
    static class DownloadTask implements Callable<String> {
        private final int id;
        private final String imageUrl;
        private final PreparedStatement updateStmt;
        
        public DownloadTask(int id, String imageUrl, PreparedStatement updateStmt) {
            this.id = id;
            this.imageUrl = imageUrl;
            this.updateStmt = updateStmt;
        }
        
        @Override
        public String call() throws Exception {
            String result = "图片下载完成: " + imageUrl;
            int status = 2; // 默认下载完成
            
            try {
                // 更新状态为正在下载
                synchronized (updateStmt) {
                    updateStmt.setInt(1, 1); // 1:正在下载
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();
                }
                
                // 下载图片
                downloadSingleImage(imageUrl);
                
            } catch (Exception e) {
                result = "图片下载失败: " + imageUrl + ", 错误: " + e.getMessage();
                status = -1; // 下载失败
            } finally {
                // 更新最终状态
                synchronized (updateStmt) {
                    updateStmt.setInt(1, status);
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();
                }
            }
            
            return result;
        }
        
        /**
         * 下载单个图片
         * @param imageUrl 图片URL
         * @throws IOException 下载异常
         */
        private void downloadSingleImage(String imageUrl) throws IOException {
            // 创建下载目录
            File downloadDir = new File("downloaded_images");
            if (!downloadDir.exists()) {
                downloadDir.mkdirs();
            }
            
            // 获取文件名
            String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
            if (fileName.indexOf('?') > 0) {
                fileName = fileName.substring(0, fileName.indexOf('?'));
            }
            
            // 下载文件
            try (InputStream in = new java.net.URL(imageUrl).openStream();
                 FileOutputStream out = new FileOutputStream(new File(downloadDir, fileName))) {
                
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }
}