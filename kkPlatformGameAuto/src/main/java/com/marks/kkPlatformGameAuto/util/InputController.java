package com.marks.kkPlatformGameAuto.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 输入控制器（鼠标和键盘操作的统一门面）
 * 提供简化的 API，内部委托给 MouseUtils 和 KeyboardUtils
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17
 */
@Slf4j
@Component
public class InputController {

    @Autowired
    private MouseUtils mouseUtils;

    @Autowired
    private KeyboardUtils keyboardUtils;

    /**
     * 移动鼠标并点击
     */
    public void moveAndClick(int x, int y) {
        mouseUtils.moveTo(x, y);
        mouseUtils.leftClick();
    }

    /**
     * 点击指定坐标
     */
    public void click(int x, int y) {
        mouseUtils.clickAt(x, y);
    }

    /**
     * 右键点击指定坐标
     */
    public void rightClick(int x, int y) {
        mouseUtils.rightClickAt(x, y);
    }

    /**
     * 双击指定坐标
     */
    public void doubleClick(int x, int y) {
        mouseUtils.moveTo(x, y);
        mouseUtils.doubleClick();
    }

    /**
     * 按下按键
     */
    public void pressKey(int keyCode) {
        keyboardUtils.pressKey(keyCode);
    }

    /**
     * 按下组合键
     */
    public void pressCombo(int controlKey, int targetKey) {
        keyboardUtils.pressCombinationKey(controlKey, targetKey);
    }

    /**
     * Ctrl + 数字
     */
    public void selectUnit(int number) {
        keyboardUtils.selectWithCtrl(number);
    }

    /**
     * 输入文本
     */
    public void type(String text) {
        keyboardUtils.typeText(text);
    }

    /**
     * 链式调用：移动到坐标并右键点击
     */
    public InputController moveToAndRightClick(int x, int y) {
        mouseUtils.moveTo(x, y);
        mouseUtils.rightClick();
        return this;
    }

    /**
     * 链式调用：移动到坐标并左键点击
     */
    public InputController moveToAndLeftClick(int x, int y) {
        mouseUtils.moveTo(x, y);
        mouseUtils.leftClick();
        return this;
    }

    /**
     * 链式调用：延迟
     */
    public InputController delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            LogUtil.error("延迟被中断", e);
            Thread.currentThread().interrupt();
        }
        return this;
    }
}
