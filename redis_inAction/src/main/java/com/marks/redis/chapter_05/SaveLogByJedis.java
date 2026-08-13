package com.marks.redis.chapter_05;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: SaveLogByJedis </p>
 * <p>描述: 使用 Jedis 从 Redis 读取日志并保存到本地文件的日志记录类</p>
 * <p>功能特性:</p>
 * <ul>
 *   <li>基于生产者-消费者模式实现异步日志处理，提高系统吞吐量</li>
 *   <li>使用 Redis BLPOP 命令阻塞式读取日志队列，降低 CPU 占用</li>
 *   <li>支持 Redis 连接池管理，提高资源利用率和并发性能</li>
 *   <li>自动添加时间戳并格式化日志内容，便于日志分析</li>
 *   <li>支持优雅关闭和资源清理，确保数据不丢失</li>
 *   <li>线程安全设计，支持多线程环境下的日志写入</li>
 * </ul>
 * <p>工作流程:</p>
 * <ol>
 *   <li>生产者线程从 Redis 的 log_queue 列表中阻塞读取日志消息</li>
 *   <li>读取到的消息添加时间戳后放入内存缓冲队列</li>
 *   <li>消费者线程从缓冲队列取出消息并写入本地文件</li>
 *   <li>程序关闭时优雅停止所有线程并释放资源</li>
 * </ol>
 * <p>使用示例:</p>
 * <pre>{@code
 * // 创建实例（无密码）
 * SaveLogByJedis logSaver = new SaveLogByJedis("localhost", 6379, "logs/app.log");
 * 
 * // 或创建实例（带密码）
 * SaveLogByJedis logSaver = new SaveLogByJedis("localhost", 6379, "password", "logs/app.log");
 * 
 * // 启动服务
 * logSaver.start();
 * 
 * // 添加日志到 Redis
 * logSaver.addLog("这是一条测试日志");
 * 
 * // 停止服务
 * logSaver.stop();
 * }</pre>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/15 15:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class SaveLogByJedis {

    /** Redis 连接池，用于管理 Redis 连接 */
    private JedisPool jedisPool;
    
    /** 日志文件保存路径 */
    private String logFilePath;
    
    /** 运行状态标志，volatile 保证多线程可见性 */
    private volatile boolean running = true;
    
    /** 日志缓冲队列，用于生产者-消费者模式，实现异步日志处理 */
    private BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();
    
    /** 日期格式化工具，用于生成日志时间戳 */
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * 构造函数：创建无密码认证的 Redis 连接
     *
     * @param redisHost Redis 服务器主机地址
     * @param redisPort Redis 服务器端口号
     * @param logFilePath 日志文件保存路径
     */
    public SaveLogByJedis(String redisHost, int redisPort, String logFilePath) {
        this.logFilePath = logFilePath;
        
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        
        this.jedisPool = new JedisPool(poolConfig, redisHost, redisPort);
    }

    /**
     * 构造函数：创建带密码认证的 Redis 连接
     *
     * @param redisHost Redis 服务器主机地址
     * @param redisPort Redis 服务器端口号
     * @param password Redis 访问密码
     * @param logFilePath 日志文件保存路径
     */
    public SaveLogByJedis(String redisHost, int redisPort, String password, String logFilePath) {
        this.logFilePath = logFilePath;
        
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        
        this.jedisPool = new JedisPool(poolConfig, redisHost, redisPort, 2000, password);
    }

    /**
     * 启动日志服务，开启生产者和消费者线程
     * <p>生产者线程：从 Redis 读取日志消息</p>
     * <p>消费者线程：将日志消息写入本地文件</p>
     */
    public void start() {
        Thread consumerThread = new Thread(this::consumeLogs);
        consumerThread.setDaemon(true);
        consumerThread.setName("LogConsumer-Thread");
        consumerThread.start();

        Thread producerThread = new Thread(this::produceLogs);
        producerThread.setDaemon(true);
        producerThread.setName("LogProducer-Thread");
        producerThread.start();
    }

    /**
     * 生产者方法：从 Redis 的 log_queue 列表中阻塞读取日志消息
     * <p>使用 BLPOP 命令实现阻塞式读取，超时时间为 1 秒</p>
     * <p>读取到的消息会添加时间戳后放入内存缓冲队列</p>
     */
    private void produceLogs() {
        try (Jedis jedis = jedisPool.getResource()) {
            System.out.println("开始从 Redis 读取日志...");

            while (running) {
                try {
                    List<String> result = jedis.blpop(1, "log_queue");
                    if (result != null && !result.isEmpty()) {
                        String logMessage = result.get(1);
                        String timestamp = dateFormat.format(new Date());
                        String formattedLog = "[" + timestamp + "] " + logMessage;
                        logQueue.put(formattedLog);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    System.err.println("从 Redis 读取日志失败: " + e.getMessage());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
    }

    /**
     * 消费者方法：从内存缓冲队列中取出日志消息并写入本地文件
     * <p>使用 BufferedWriter 提高文件写入性能</p>
     * <p>以追加模式打开文件，保留历史日志</p>
     */
    private void consumeLogs() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            System.out.println("开始写入日志到文件: " + logFilePath);
            
            while (running || !logQueue.isEmpty()) {
                String log = logQueue.poll();
                if (log != null) {
                    writer.write(log);
                    writer.newLine();
                    writer.flush();
                } else {
                    Thread.sleep(100);
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("写入日志文件失败: " + e.getMessage());
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 停止日志服务，关闭所有线程和 Redis 连接池
     * <p>设置运行标志为 false，等待线程自然退出</p>
     * <p>关闭 Redis 连接池，释放所有连接资源</p>
     */
    public void stop() {
        running = false;
        if (jedisPool != null && !jedisPool.isClosed()) {
            jedisPool.close();
        }
    }

    /**
     * 添加日志消息到 Redis 队列
     * <p>使用 RPUSH 命令将消息添加到 log_queue 列表尾部</p>
     *
     * @param message 要添加的日志消息内容
     */
    public void addLog(String message) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.rpush("log_queue", message);
        } catch (Exception e) {
            System.err.println("添加日志到 Redis 失败: " + e.getMessage());
        }
    }

    /**
     * 主方法：演示日志服务的使用
     * <p>创建日志服务实例，启动服务，添加测试日志，然后停止服务</p>
     * <p>注册了 JVM 关闭钩子，确保程序退出时优雅关闭服务</p>
     *
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        String redisHost = "localhost";
        int redisPort = 6379;
        String logFilePath = "logs/app.log";

        SaveLogByJedis logSaver = new SaveLogByJedis(redisHost, redisPort, logFilePath);
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("正在关闭日志服务...");
            logSaver.stop();
        }));

        logSaver.start();

        for (int i = 0; i < 10; i++) {
            logSaver.addLog("测试日志消息 #" + (i + 1));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        logSaver.stop();
    }
}
