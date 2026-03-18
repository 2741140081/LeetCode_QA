package com.marks.kkPlatformGameAuto.util;

import com.marks.kkPlatformGameAuto.config.properties.GameAutoProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * 鼠标操作工具类
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17
 */
@Slf4j
@Component
public class MouseUtils {

    @Autowired
    private GameAutoProperties gameAutoProperties;

    private final Robot robot;

    private int eventDelay;

    public MouseUtils() throws AWTException {
        this.robot = new Robot();
        this.eventDelay = gameAutoProperties.getOperation().getEventDelay() != null
                ? gameAutoProperties.getOperation().getEventDelay()
                : 50;
        robot.setAutoDelay(eventDelay);
        log.info("MouseUtils 初始化完成，事件延迟时间：{}ms", eventDelay);
    }

    /**
     * 移动鼠标到指定坐标
     */
    public void moveTo(int x, int y) {
        LogUtil.debug("移动鼠标到坐标：({}, {})", x, y);
        robot.mouseMove(x, y);
        robot.delay(gameAutoProperties.getOperation().getMoveDuration());
    }

    /**
     * 左键单击
     */
    public void leftClick() {
        LogUtil.debug("执行左键单击");
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(gameAutoProperties.getOperation().getClickDelay());
    }

    /**
     * 右键单击
     */
    public void rightClick() {
        LogUtil.debug("执行右键单击");
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        robot.delay(gameAutoProperties.getOperation().getClickDelay());
    }

    /**
     * 双击
     */
    public void doubleClick() {
        LogUtil.debug("执行双击");
        leftClick();
        robot.delay(gameAutoProperties.getOperation().getDoubleClickInterval());
        leftClick();
    }

    /**
     * 在指定坐标点击
     */
    public void clickAt(int x, int y) {
        moveTo(x, y);
        leftClick();
    }

    /**
     * 右键点击指定坐标
     */
    public void rightClickAt(int x, int y) {
        moveTo(x, y);
        rightClick();
    }

    /**
     * 拖拽操作
     */
    public void drag(int fromX, int fromY, int toX, int toY) {
        LogUtil.debug("执行拖拽：从 ({}, {}) 到 ({}, {})", fromX, fromY, toX, toY);
        robot.mouseMove(fromX, fromY);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseMove(toX, toY);
        robot.delay(gameAutoProperties.getOperation().getMoveDuration());
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(gameAutoProperties.getOperation().getClickDelay());
    }

    /**
     * 滚动鼠标滚轮
     */
    public void mouseWheel(int wheelAmount) {
        LogUtil.debug("滚动鼠标滚轮：{}", wheelAmount);
        robot.mouseWheel(wheelAmount);
        robot.delay(gameAutoProperties.getOperation().getClickDelay());
    }

    /**
     * 按下鼠标左键（不释放）
     */
    public void pressLeftButton() {
        LogUtil.debug("按下鼠标左键");
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * 释放鼠标左键
     */
    public void releaseLeftButton() {
        LogUtil.debug("释放鼠标左键");
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
