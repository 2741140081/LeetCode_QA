package com.marks.kkPlatformGameAuto.service.impl;

import com.marks.kkPlatformGameAuto.config.ClickerConfig;
import com.marks.kkPlatformGameAuto.service.ClickerService;
import com.marks.kkPlatformGameAuto.util.InputController;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

/**
 * 连点器服务实现
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/25
 */
@Slf4j
@Service
public class ClickerServiceImpl implements ClickerService {

    @Autowired
    private InputController input;

    @Autowired
    private ClickerConfig clickerConfig;

    private ThreadPoolTaskScheduler taskScheduler;

    private ScheduledFuture<?> leftClickTask;
    private ScheduledFuture<?> itemBarTask;

    private volatile boolean isClickerRunning = false;
    private volatile boolean isItemBarRunning = false;

    public ClickerServiceImpl() {
        // 初始化线程池调度器
        this.taskScheduler = new ThreadPoolTaskScheduler();
        this.taskScheduler.setPoolSize(2);
        this.taskScheduler.setThreadNamePrefix("clicker-");
        this.taskScheduler.initialize();
    }

    @Override
    public void startLeftClicker() {
        if (isClickerRunning) {
            log.warn("左键连点器已经在运行");
            return;
        }

        log.info("启动左键连点器，间隔：{}ms", clickerConfig.getInterval());

        leftClickTask = taskScheduler.scheduleAtFixedRate(() -> {
            try {
                input.leftClick();
            } catch (Exception e) {
                log.error("左键点击执行失败", e);
            }
        }, clickerConfig.getInterval());

        isClickerRunning = true;
    }

    @Override
    public void stopLeftClicker() {
        if (!isClickerRunning) {
            return;
        }

        log.info("停止左键连点器");

        if (leftClickTask != null) {
            leftClickTask.cancel(false);
            leftClickTask = null;
        }

        isClickerRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isClickerRunning;
    }

    @Override
    public void setInterval(int interval) {
        log.info("更新连点器间隔：{}ms", interval);
        clickerConfig.setInterval(interval);

        // 如果正在运行，重启任务
        if (isClickerRunning) {
            stopLeftClicker();
            startLeftClicker();
        }
    }

    @Override
    public void startItemBarAutoUse() {
        if (isItemBarRunning) {
            log.warn("物品栏自动使用已经在运行");
            return;
        }

        log.info("启动物品栏自动使用，间隔：{}ms", clickerConfig.getItemBar().getInterval());

        itemBarTask = taskScheduler.scheduleAtFixedRate(() -> {
            try {
                useEnabledItems();
            } catch (Exception e) {
                log.error("物品栏自动使用执行失败", e);
            }
        }, clickerConfig.getItemBar().getInterval());

        isItemBarRunning = true;
    }

    @Override
    public void stopItemBarAutoUse() {
        if (!isItemBarRunning) {
            return;
        }

        log.info("停止物品栏自动使用");

        if (itemBarTask != null) {
            itemBarTask.cancel(false);
            itemBarTask = null;
        }

        isItemBarRunning = false;
    }

    @Override
    public void setItemBarInterval(int interval) {
        log.info("更新物品栏使用间隔：{}ms", interval);
        clickerConfig.getItemBar().setInterval(interval);

        // 如果正在运行，重启任务
        if (isItemBarRunning) {
            stopItemBarAutoUse();
            startItemBarAutoUse();
        }
    }

    /**
     * 使用启用的物品
     */
    private void useEnabledItems() {
        if (clickerConfig.getItemBar().getEnabledSlots() == null ||
                clickerConfig.getItemBar().getEnabledSlots().isEmpty()) {
            return;
        }

        for (Integer slot : clickerConfig.getItemBar().getEnabledSlots()) {
            try {
                log.debug("使用物品槽位：{}", slot);
                input.pressNumber(slot);
                Thread.sleep(50); // 短暂延迟避免按键冲突
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("使用物品中断", e);
            }
        }
    }

    @PreDestroy
    public void destroy() {
        stopLeftClicker();
        stopItemBarAutoUse();
        if (taskScheduler != null) {
            taskScheduler.shutdown();
        }
    }
}
