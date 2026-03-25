package com.marks.kkPlatformGameAuto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 连点器和物品栏配置
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/25
 */
@Data
@Component
@ConfigurationProperties(prefix = "game.auto.clicker")
public class ClickerConfig {

    /**
     * 是否启用左键连点器
     */
    private boolean enabled;

    /**
     * 启动快捷键 (F11 或 F12)
     */
    private String startKey;

    /**
     * 点击间隔 (毫秒)
     */
    private int interval;

    /**
     * 物品栏配置
     */
    private ItemBarConfig itemBar;

    /**
     * 物品栏配置内部类
     */
    @Data
    public static class ItemBarConfig {
        /**
         * 是否启用自动使用物品
         */
        private boolean enabled;

        /**
         * 使用间隔 (毫秒)
         */
        private int interval;

        /**
         * 物品栏矩阵 3*2, 物品位置 [[7, 8], [4, 5], [1, 2]]
         */
        private List<List<Integer>> slots;

        /**
         * 启用的物品槽位 (勾选哪些就使用哪些)
         */
        private List<Integer> enabledSlots;
    }
}
