package com.marks.kkPlatformGameAuto.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;

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
     * 左键单击（当前鼠标位置）
     */
    public void leftClick() {
        mouseUtils.leftClick();
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
     * 按下数字键
     * @param number 数字 (0-9)
     */
    public void pressNumber(int number) {
        keyboardUtils.pressNumber(number);
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
        keyboardUtils.delay(milliseconds);
        return this;
    }

    /**
     * 按下功能键
     * @param functionNumber 功能键编号 (1-12)
     */
    public void pressFunctionKey(int functionNumber) {
        keyboardUtils.pressFunctionKey(functionNumber);
    }

    /**
     * 获取大写字母对应的 keyCode
     * @param letter 大写字母 (A-Z)
     * @return 对应的 keyCode，如果输入无效返回 KeyEvent.VK_UNDEFINED
     */
    public int getLetterKeyCode(char letter) {
        return keyboardUtils.getLetterKeyCode(letter);
    }

    /**
     * 向上移动视角
     * @param durationMs 持续时间（毫秒）
     */
    public void moveViewUp(int durationMs) {
        keyboardUtils.pressKey(durationMs, KeyEvent.VK_UP);
    }

    /**
     * 向下移动视角
     * @param durationMs 持续时间（毫秒）
     */
    public void moveViewDown(int durationMs) {
        keyboardUtils.pressKey(durationMs, KeyEvent.VK_DOWN);
    }

    /**
     * 向左移动视角
     * @param durationMs 持续时间（毫秒）
     */
    public void moveViewLeft(int durationMs) {
        keyboardUtils.pressKey(durationMs, KeyEvent.VK_LEFT);
    }

    /**
     * 向右移动视角
     * @param durationMs 持续时间（毫秒）
     */
    public void moveViewRight(int durationMs) {
        keyboardUtils.pressKey(durationMs, KeyEvent.VK_RIGHT);
    }
}
