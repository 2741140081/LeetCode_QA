package com.marks.tools.webcrawler;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * WebDriver实例池管理类
 * 提供线程安全的WebDriver实例复用机制
 */
public class WebDriverPool {
    private static final Logger logger = Logger.getLogger(WebDriverPool.class.getName());

    // 池配置参数
    private static final int DEFAULT_POOL_SIZE = 5;
    private static final int DEFAULT_TIMEOUT_SECONDS = 30;

    // 实例池
    private final BlockingQueue<WebDriver> pool;
    private final AtomicInteger activeInstances = new AtomicInteger(0);

    // 配置信息
    private final String chromeDriverPath;
    private final ChromeOptions chromeOptions;
    private final int maxPoolSize;
    private final int timeoutSeconds;

    // 单例实例
    private static volatile WebDriverPool instance;

    /**
     * 私有构造函数
     */
    private WebDriverPool(String chromeDriverPath, int poolSize, int timeoutSeconds) {
        this.chromeDriverPath = chromeDriverPath;
        this.maxPoolSize = poolSize;
        this.timeoutSeconds = timeoutSeconds;
        this.pool = new LinkedBlockingQueue<>(poolSize);
        this.chromeOptions = createChromeOptions();

        // 屏蔽Selenium日志
        suppressSeleniumLogs();

        logger.info(String.format("WebDriver池初始化完成 - 最大实例数: %d, 超时时间: %d秒",
                poolSize, timeoutSeconds));
    }

    /**
     * 获取单例实例
     */
    public static WebDriverPool getInstance() {
        if (instance == null) {
            synchronized (WebDriverPool.class) {
                if (instance == null) {
                    instance = new WebDriverPool(
                            "D:/Project/chrome/chromedriver-win64/chromedriver.exe",
                            10,
                            300
                    );
                }
            }
        }
        return instance;
    }

    /**
     * 获取WebDriver实例（带超时）
     */
    public WebDriver borrowWebDriver() throws InterruptedException {
        WebDriver driver = pool.poll(timeoutSeconds, TimeUnit.SECONDS);

        if (driver == null) {
            // 池中无可用实例，创建新实例（如果未达到最大限制）
            if (activeInstances.get() < maxPoolSize) {
                driver = createNewWebDriver();
                activeInstances.incrementAndGet();
                logger.fine("创建新的WebDriver实例，当前活跃实例数: " + activeInstances.get());
            } else {
                // 达到最大限制，继续等待
                driver = pool.take();
            }
        }

        return driver;
    }

    /**
     * 归还WebDriver实例
     */
    public void returnWebDriver(WebDriver driver) {
        if (driver != null) {
            // 清理浏览器状态，确保实例干净
            try {
                driver.manage().deleteAllCookies();
                // 不要调用driver.get("")，这会改变页面状态
            } catch (Exception e) {
                logger.warning("清理WebDriver实例时发生错误: " + e.getMessage());
            }

            // 将实例放回池中
            if (!pool.offer(driver)) {
                // 池已满，关闭实例
                closeWebDriver(driver);
            }
        }
    }

    /**
     * 关闭并移除WebDriver实例
     */
    public void closeWebDriver(WebDriver driver) {
        if (driver != null) {
            try {
                driver.quit();
                activeInstances.decrementAndGet();
                logger.fine("关闭WebDriver实例，当前活跃实例数: " + activeInstances.get());
            } catch (Exception e) {
                logger.warning("关闭WebDriver实例时发生错误: " + e.getMessage());
            }
        }
    }

    /**
     * 关闭整个池
     */
    public void shutdown() {
        logger.info("开始关闭WebDriver池...");

        // 关闭池中所有实例
        WebDriver driver;
        while ((driver = pool.poll()) != null) {
            closeWebDriver(driver);
        }

        logger.info("WebDriver池已完全关闭");
    }

    /**
     * 获取当前池状态信息
     */
    public String getPoolStatus() {
        return String.format("池状态 - 可用实例: %d, 活跃实例: %d, 最大容量: %d",
                pool.size(), activeInstances.get(), maxPoolSize);
    }

    /**
     * 创建Chrome选项
     */
    private ChromeOptions createChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // 无头模式
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-plugins");
        options.addArguments("--disable-images"); // 禁用图片加载提高性能
        return options;
    }

    /**
     * 创建新的WebDriver实例
     */
    private WebDriver createNewWebDriver() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        return new ChromeDriver(chromeOptions);
    }

    /**
     * 屏蔽Selenium相关日志
     */
    private void suppressSeleniumLogs() {
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        Logger.getLogger("org.openqa.selenium.devtools").setLevel(Level.OFF);
        Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);
    }
}
