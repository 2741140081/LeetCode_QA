package com.marks.tools.spider;

import com.marks.tools.dbUtil.DatabasePool;
import com.marks.tools.video.TextToCsvProcessor;
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
    // 线程池配置
    private static final int THREAD_POOL_SIZE = 20;
    private static ExecutorService executor;
    // 网站基础URL
    private static final String BASE_URL = "https://tpmh3.com";
    // 重试配置
    private static final int MAX_RETRY = 5;
    private static final long RETRY_INTERVAL = 10000; // 10秒
    // 图片索引计数器
    private static int index = 1;

    private static long startTime;

    private static String insertSQL = "INSERT INTO image_download_info (image_web_url, parent_page_url, image_index, status) VALUES (?, ?, ?, ?)";

    private static String SAVE_DIR = "D:\\spider\\data\\艾\\result\\";
    public static void main(String[] args) {
        ImageCrawlerPlus imageCrawlerPlus = new ImageCrawlerPlus();
        TextToCsvProcessor csvProcessor = new TextToCsvProcessor();
        csvProcessor.solution();

        String csvFilePath = csvProcessor.outputPath;
        
        // 初始化线程池
        executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        
        try {
            // 获取当前时间
            startTime = System.currentTimeMillis();
            // 1. 读取CSV文件数据
            List<String> datas = imageCrawlerPlus.readCsvFile(csvFilePath);
            
            // 2. 初始化数据库
            imageCrawlerPlus.initDatabase();
            
            // 3. 遍历数据并处理网页
            for (String data : datas) {
                String currWebUrl = BASE_URL + "/chapter/" + data + ".html";
                imageCrawlerPlus.processWebPage(currWebUrl, data);
            }
            // 获取当前时间
            long midTime = System.currentTimeMillis();
            long processWebPageTime = midTime - startTime;

            // 4. 开始下载图片
            imageCrawlerPlus.downloadImages();
            long midTime2 = System.currentTimeMillis();
            long downloadImagesTime = midTime2 - midTime;
            // 5. 清理数据库下载表, 因为这个表相当于是一张临时表, 不希望这个表过大
            imageCrawlerPlus.clearDownloadTable();
            System.out.println("处理网页完成，耗时: " + processWebPageTime / 60000 + "min");
            System.out.println("下载图片完成，耗时: " + downloadImagesTime / 60000 + "min");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5. 关闭线程池
            shutdownExecutor();
            // 6. 关闭数据库连接池
            DatabasePool.closeDataSource();
            long endTime = System.currentTimeMillis();
            System.out.println("下载完成，总耗时: " + (endTime - startTime) / 60000 + "min");
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
    private List<String> readCsvFile(String filePath) throws IOException {
        List<String> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // 标记是否为首行
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // 跳过首行
                    continue;
                }
                String[] values = line.split(",");
                for (String value : values) {
                    value = value.trim().replaceAll("^\"|\"$", ""); // 去除引号和空格
                    if (!value.isEmpty()) {
                        dataList.add(value);
                    }
                }
            }
        }
        return dataList;
    }
    
    /**
     * 初始化数据库
     */
    private void initDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS image_download_info (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "image_web_url TEXT NOT NULL, " +
                "parent_page_url TEXT NOT NULL, " +
                "image_index INT NOT NULL, " +
                "storage_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "status INT DEFAULT 0" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

        Connection conn = null;
        try {
            // 从连接池获取连接
            conn = DatabasePool.getConnection();
            PreparedStatement stmt = conn.prepareStatement(createTableSQL);
            stmt.execute(createTableSQL);
            System.out.println("数据库初始化完成");
        } catch (Exception e) {
            System.err.println("数据库初始化失败: " + e.getMessage());
        } finally {
            // 确保连接被返回到连接池
            if (conn != null) {
                try {
                    conn.close(); // 将连接返回到连接池
                } catch (SQLException e) {
                    System.err.println("关闭数据库连接时出错: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * 处理网页数据
     * @param webUrl 网页URL
     * @param parentPageUrl 父页面URL
     */
    private void processWebPage(String webUrl, String parentPageUrl) {
        int retryCount = 0;
        
        while (retryCount < MAX_RETRY) {
            try {
                System.out.println("正在处理页面: " + webUrl);
                
                // 使用Jsoup连接网页
                Document doc = Jsoup.connect(webUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36 Edg/138.0.0.0")
                        .header("Accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
                        .header("Accept-encoding", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
                        .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                        .header("Referer", "https://tpmh3.com/")
                        .timeout(30000)
                        .get();
                
                // 解析图片信息并保存到数据库
                // parseAndSaveImages(doc, parentPageUrl);
                parseAndSaveImagesPlus(doc, parentPageUrl);
                // 查找下一页链接
                String nextWebUrl = findNextPageUrl(doc);
                if (nextWebUrl != null && !nextWebUrl.isEmpty()) {
                    processWebPage(BASE_URL + nextWebUrl, parentPageUrl); // 递归处理下一页
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

    private static void parseAndSaveImagesPlus(Document doc, String parentPageUrl) {
        // 使用List替代Map，提高遍历效率
        List<ImageInfo> imageList = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        // 使用Jsoup的CSS选择器替代正则表达式，效率更高
        Elements imgElements = doc.select("img[data-original][id]");

        for (Element img : imgElements) {
            String imageAddress = img.attr("data-original");
            String imageId = img.attr("id");
            if (!imageAddress.isEmpty()) {  // 确保图片地址不为空
                imageList.add(new ImageInfo(index++, imageAddress));
            }
        }
        if (!imageList.isEmpty()) {
            Connection conn = null;
            try {
                // 从数据库连接池获取连接
                conn = DatabasePool.getConnection();
                // 开启事务
                conn.setAutoCommit(false);
                PreparedStatement pstmt = conn.prepareStatement(insertSQL);
                for (ImageInfo imageInfo : imageList) {
                    pstmt.setString(1, imageInfo.url);
                    pstmt.setString(2, parentPageUrl);
                    pstmt.setInt(3, imageInfo.index);
                    pstmt.setInt(4, 0); // 0:未进行下载
                    pstmt.addBatch();
                }
                pstmt.executeBatch(); // 执行批处理
                conn.commit(); // 提交事务
                long endTime = System.currentTimeMillis();
                System.out.println("从页面 " + parentPageUrl + " 解析并保存了 " + imageList.size() + " 张图片信息, " +
                        "当前方法 parseAndSaveImages() 耗时: " + (endTime - startTime) + "毫秒");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 确保连接被返回到连接池
                if (conn != null) {
                    try {
                        conn.setAutoCommit(true); // 恢复自动提交
                        conn.close(); // 将连接返回到连接池
                    } catch (SQLException e) {
                        System.err.println("关闭数据库连接时出错: " + e.getMessage());
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
        // 查看当前方法的耗时
        long startTime = System.currentTimeMillis();
        // 3. 提取图片地址（正则匹配id属性）
        Pattern imgPattern = Pattern.compile("<img\\s+[^>]*data-original=[\"']([^\"']+)[\"'][^>]*id=[\"']([^\"']+)[\"'][^>]*>");
        Matcher imgMatcher = imgPattern.matcher(doc.html());

        while (imgMatcher.find()) {
            String imageAddress = imgMatcher.group(1);
            String imageId = imgMatcher.group(2);
            imageMap.put(index++, imageAddress);
            // System.out.println(imgMatcher.group(1) + ": " + imgMatcher.group(2));
        }
        
        // 批量插入到数据库
        if (!imageMap.isEmpty()) {
            Connection conn = null;
            try {
                conn = DatabasePool.getConnection();

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
                    long endTime = System.currentTimeMillis();
                    System.out.println("从页面 " + parentPageUrl + " 解析并保存了 " + imageMap.size() + " 张图片信息, " +
                            "当前方法 parseAndSaveImages() 耗时: " + (endTime - startTime) / 1000 + "秒");
                } catch (Exception e) {
                    conn.rollback(); // 回滚事务
                    System.err.println("批量插入图片信息到数据库时出错: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    conn.setAutoCommit(true); // 恢复自动提交
                }
            } catch (Exception e) {
                System.err.println("保存图片信息到数据库时出错: " + e.getMessage());
                e.printStackTrace();
            } finally {
                // 确保连接被返回到连接池
                if (conn != null) {
                    try {
                        conn.close(); // 将连接返回到连接池
                    } catch (SQLException e) {
                        System.err.println("关闭数据库连接时出错: " + e.getMessage());
                    }
                }
            }
        }
    }
    
    /**
     * 查找下一页链接
     * @param doc 网页文档
     * @return 下一页URL
     */
    private static String findNextPageUrl(Document doc) {
        // 2. 提取下一页地址（正则匹配）
        Pattern nextPagePattern = Pattern.compile("<a href=\"([^\"]*)\" class=\"down-page\"[^>]*>下一页</a>");
        Matcher matcher = nextPagePattern.matcher(doc.html());
        if (matcher.find()) {

            System.out.println("下一页地址: " + matcher.group(1));
            return matcher.group(1);
        }
        return null;
    }

    /**
     * @Description:
     * 批量删除下载表中的图片信息
     * @return void
     * @author marks
     * @CreateDate: 2026/01/12 16:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void clearDownloadTable() {
        // 删除 表 image_download_info 中 status == 2 的数据, 为2表示已下载完成
        Connection conn = null;
        try {
            conn = DatabasePool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM image_download_info WHERE status = 2");
            pstmt.executeUpdate();
            System.out.println("已删除 " + pstmt.getUpdateCount() + " 条数据");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close(); // 将连接返回到连接池
                } catch (SQLException e) {
                    System.err.println("关闭数据库连接时出错: " + e.getMessage());
                }
            }
        }
    }

    /**
     * 下载图片
     */
    private void downloadImages() {
        System.out.println("开始下载图片...");
        
        String selectSQL = "SELECT id, image_index, image_web_url FROM image_download_info WHERE status = 0";
        Connection conn = null;
        List<DownloadTask> tasks = new ArrayList<>();

        try {
            conn = DatabasePool.getConnection();
            PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
            ResultSet rs = selectStmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                int imgId = rs.getInt("image_index");
                String imageUrl = rs.getString("image_web_url");
                tasks.add(new DownloadTask(id, imgId, imageUrl));
            }
        } catch (Exception e) {
            System.err.println("下载图片时出错: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 确保资源被释放
            if (conn != null) {
                try {
                    conn.close(); // 将连接返回到连接池
                } catch (SQLException e) {
                    System.err.println("关闭数据库连接时出错: " + e.getMessage());
                }
            }
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
            } catch (Exception e) {
                System.err.println("下载任务执行出错: " + e.getMessage());
            }
        }
    }
    
    /**
     * 图片下载任务
     */
    class DownloadTask implements Callable<String> {
        private final int id;
        private final int imgId;
        private final String imageUrl;
        private final String updateStatusSQL = "UPDATE image_download_info SET status = ? WHERE id = ?";

        public DownloadTask(int id, int imgId, String imageUrl) {
            this.id = id;
            this.imgId = imgId;
            this.imageUrl = imageUrl;
        }
        
        @Override
        public String call() throws Exception {
            String result = "图片下载完成: " + imageUrl;
            int status = 2; // 默认下载完成
            Connection conn = null;
            PreparedStatement updateStmt = null;
            
            try {
                // 从连接池获取连接用于更新状态
                conn = DatabasePool.getConnection();
                updateStmt = conn.prepareStatement(updateStatusSQL);
                
                // 更新状态为正在下载
                updateStmt.setInt(1, 1); // 1:正在下载
                updateStmt.setInt(2, id);
                updateStmt.executeUpdate();
                
                // 下载图片
                downloadSingleImage(imgId, imageUrl);
                
            } catch (Exception e) {
                result = "图片下载失败: " + imageUrl + ", 错误: " + e.getMessage();
                status = -1; // 下载失败
            } finally {
                // 更新最终状态
                if (updateStmt != null) {
                    try {
                        updateStmt.setInt(1, status);
                        updateStmt.setInt(2, id);
                        updateStmt.executeUpdate();
                    } catch (SQLException e) {
                        System.err.println("更新状态失败: " + e.getMessage());
                    }
                }
                
                // 确保连接返回到连接池
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        System.err.println("关闭数据库连接时出错: " + e.getMessage());
                    }
                }
            }
            return result;
        }
        
        /**
         * 下载单个图片
         *
         * @param imageId
         * @param imageUrl 图片URL
         * @throws IOException 下载异常
         */
        private void downloadSingleImage(int imageId, String imageUrl) throws IOException {
            // 创建下载目录
            File downloadDir = new File(SAVE_DIR);
            if (!downloadDir.exists()) {
                downloadDir.mkdirs();
            }

            try {
                URL url = new URL(imageUrl);
                try (InputStream in = url.openStream();
                     FileOutputStream out = new FileOutputStream(SAVE_DIR +  "img" +imageId + ".webp")) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }
            } catch (Exception e) {
                System.err.println("下载失败: " + imageId + ", 错误: " + e.getMessage());
            }
        }
    }

    // 内部类
    private static class ImageInfo {
        final int index;
        final String url;

        ImageInfo(int index, String url) {
            this.index = index;
            this.url = url;
        }
    }
}