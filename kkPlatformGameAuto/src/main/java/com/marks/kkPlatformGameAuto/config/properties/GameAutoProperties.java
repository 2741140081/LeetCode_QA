package com.marks.kkPlatformGameAuto.config.properties;

import com.marks.kkPlatformGameAuto.config.*;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Scalar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: GameAutoProperties </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17 14:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "game.auto")
public class GameAutoProperties {
    /**
     * 基础路径
     */
    private String baseDir;

    /**
     * 图像识别配置
     */
    @Autowired
    private ImageRecognitionConfig imageRecognition;

    /**
     * 超时配置
     */
    @Autowired
    private TimeoutConfig timeout;

    /**
     * 等待时间配置
     */
    @Autowired
    private WaitTimeConfig waitTime;

    /**
     * 重试配置
     */
    @Autowired
    private RetryConfig retry;

    /**
     * 操作配置
     */
    @Autowired
    private OperationConfig operation;

    /**
     * 窗口配置
     */
    @Autowired
    private WindowConfig window;

    /**
     * 截图配置
     */
    @Autowired
    private ScreenshotConfig screenshot;

    /**
     * 初始化后验证配置
     */
    @PostConstruct
    public void init() {
        log.info("=== 游戏自动化配置加载 ===");
        log.info("基础路径：{}", baseDir);

        // 验证必填配置
        if (!StringUtils.hasText(baseDir)) {
            throw new IllegalStateException(
                    "配置项 game.auto.base-dir 未设置，请在 application.yml 或 game-common.yml 中配置"
            );
        }

        log.info("图像识别默认缩放比例：{}", imageRecognition.getDefaultScale());
        log.info("查找图片超时：{}ms", timeout.getFindImageTime());
        log.info("最大重试次数：{}", retry.getMaxAttempts());
        log.info("==============================");
    }

    // ==================== 便捷方法 ====================

    /**
     * 获取默认超时时间
     */
    public int getDefaultTimeout() {
        return timeout.getDefaultTime();
    }

    /**
     * 获取默认等待时间
     */
    public int getDefaultWaitTime() {
        return waitTime.getDefaultTime();
    }

    /**
     * 获取默认缩放比例
     */
    public double getDefaultScale() {
        return imageRecognition.getDefaultScale();
    }

    /**
     * 获取相似度阈值
     */
    public double getSimilarityThreshold() {
        return imageRecognition.getSimilarityThreshold();
    }

    /**
     * 获取默认图片识别间隔
     */
    public int getInterval() {
        return imageRecognition.getInterval();
    }

    /**
     * 获取矩形颜色
     */
    public Scalar getRectColor() {
        return new Scalar(
                imageRecognition.getRectColorR(),
                imageRecognition.getRectColorG(),
                imageRecognition.getRectColorB()
        );
    }

    /**
     * 获取临时文件目录
     */
    public String getTempDir() {
        return screenshot.getTempDir();
    }

    /**
     * 获取游戏运行结果文件目录
     */
    public String getResultDir() {
        return screenshot.getResultDir();
    }

    /**
     * 获取文件格式
     */
    public String getFormat() {
        return screenshot.getFormat();
    }


}
