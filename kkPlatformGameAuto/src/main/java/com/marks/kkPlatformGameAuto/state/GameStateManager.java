package com.marks.kkPlatformGameAuto.state;

import com.marks.kkPlatformGameAuto.enums.GameStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/**
 * 游戏状态管理器
 * 管理游戏的暂停、恢复、停止等状态
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/23
 */
@Slf4j
@Component
public class GameStateManager {

    // 当前游戏状态
    private volatile GameStatus currentStatus = GameStatus.NOT_STARTED;

    // 暂停锁
    private final ReentrantLock pauseLock = new ReentrantLock();

    // 用于等待恢复的条件
    private final Condition resumedCondition = pauseLock.newCondition();

    /**
     * 开始游戏（设置状态为运行中）
     */
    public void startGame() {
        currentStatus = GameStatus.RUNNING;
        log.info("游戏状态变更为：RUNNING");
    }

    /**
     * 暂停游戏
     */
    public void pauseGame() {
        if (currentStatus == GameStatus.RUNNING) {
            currentStatus = GameStatus.PAUSED;
            log.info("游戏状态变更为：PAUSED");
        } else {
            log.warn("当前状态不能暂停：{}", currentStatus);
        }
    }

    /**
     * 恢复游戏
     */
    public void resumeGame() {
        if (currentStatus == GameStatus.PAUSED) {
            currentStatus = GameStatus.RUNNING;
            log.info("游戏状态变更为：RUNNING");

            // 唤醒所有等待的线程
            pauseLock.lock();
            try {
                resumedCondition.signalAll();
            } finally {
                pauseLock.unlock();
            }
        } else {
            log.warn("当前状态不能恢复：{}", currentStatus);
        }
    }

    /**
     * 停止游戏
     */
    public void stopGame() {
        currentStatus = GameStatus.STOPPED;
        log.info("游戏状态变更为：STOPPED");

        // 唤醒所有等待的线程，让它们退出
        pauseLock.lock();
        try {
            resumedCondition.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    /**
     * 检查是否需要暂停
     * 如果当前状态是 PAUSED，则阻塞直到恢复或停止
     * @return true 如果应该继续执行，false 如果应该停止
     */
    public boolean checkShouldPause() {
        // 如果已停止，直接返回 false
        if (currentStatus == GameStatus.STOPPED) {
            return false;
        }

        // 如果不是暂停状态，直接返回 true
        if (currentStatus != GameStatus.PAUSED) {
            return true;
        }

        // 进入暂停等待
        pauseLock.lock();
        try {
            log.debug("游戏暂停中，等待恢复...");

            // 等待恢复或停止
            while (currentStatus == GameStatus.PAUSED) {
                resumedCondition.await();
            }

            // 如果是停止状态，返回 false
            return currentStatus != GameStatus.STOPPED;
        } catch (InterruptedException e) {
            log.error("暂停等待被中断", e);
            Thread.currentThread().interrupt();
            return false;
        } finally {
            pauseLock.unlock();
        }
    }

    /**
     * 获取当前状态
     * @return 当前游戏状态
     */
    public GameStatus getCurrentStatus() {
        return currentStatus;
    }

    /**
     * 判断是否在运行中
     * @return true 如果在运行中
     */
    public boolean isRunning() {
        return currentStatus == GameStatus.RUNNING;
    }

    /**
     * 判断是否已暂停
     * @return true 如果已暂停
     */
    public boolean isPaused() {
        return currentStatus == GameStatus.PAUSED;
    }

    /**
     * 判断是否已停止
     * @return true 如果已停止
     */
    public boolean isStopped() {
        return currentStatus == GameStatus.STOPPED;
    }
}
