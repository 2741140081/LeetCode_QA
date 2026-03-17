package com.marks.kkPlatformGameAuto.util;

import com.marks.kkPlatformGameAuto.config.properties.GameAutoProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

/**
 * 键盘操作工具类
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17
 */
@Slf4j
@Component
public class KeyboardUtils {

    @Autowired
    private GameAutoProperties gameAutoProperties;

    private final Robot robot;

    public KeyboardUtils() throws AWTException {
        this.robot = new Robot();
        LogUtil.info("KeyboardUtils 初始化完成");
    }

    /**
     * 按下并释放单个按键
     */
    public void pressKey(int keyCode) {
        LogUtil.debug("按下按键：{}", KeyEvent.getKeyText(keyCode));
        robot.keyPress(keyCode);
        robot.delay(gameAutoProperties.getOperation().getKeyPressDuration());
        robot.keyRelease(keyCode);
        robot.delay(gameAutoProperties.getOperation().getClickDelay());
    }

    /**
     * 按下组合键（如 Ctrl+A）
     */
    public void pressCombinationKey(int controlKey, int targetKey) {
        LogUtil.debug("按下组合键：{}+{}",
                KeyEvent.getKeyText(controlKey), KeyEvent.getKeyText(targetKey));

        robot.keyPress(controlKey);
        robot.keyPress(targetKey);
        robot.delay(50);
        robot.keyRelease(targetKey);
        robot.keyRelease(controlKey);
        robot.delay(gameAutoProperties.getOperation().getClickDelay());
    }

    /**
     * 按下多个组合键（如 Ctrl+Shift+A）
     */
    public void pressMultiKeys(int... keyCodes) {
        if (keyCodes == null || keyCodes.length == 0) {
            return;
        }

        StringBuilder keyNames = new StringBuilder();
        for (int keyCode : keyCodes) {
            robot.keyPress(keyCode);
            if (keyNames.length() > 0) {
                keyNames.append("+");
            }
            keyNames.append(KeyEvent.getKeyText(keyCode));
        }

        LogUtil.debug("按下组合键：{}", keyNames);

        robot.delay(50);

        // 反向释放
        for (int i = keyCodes.length - 1; i >= 0; i--) {
            robot.keyRelease(keyCodes[i]);
        }

        robot.delay(gameAutoProperties.getOperation().getClickDelay());
    }

    /**
     * 按下数字键
     */
    public void pressNumber(int number) {
        if (number < 0 || number > 9) {
            LogUtil.warn("无效的数字：{}，必须在 0-9 之间", number);
            return;
        }

        int keyCode = getNumberKeyCode(number);
        pressKey(keyCode);
    }

    /**
     * Ctrl + 数字键（编号操作）
     */
    public void selectWithCtrl(int number) {
        if (number < 0 || number > 9) {
            LogUtil.warn("无效的数字：{}，必须在 0-9 之间", number);
            return;
        }

        int keyCode = getNumberKeyCode(number);
        pressCombinationKey(KeyEvent.VK_CONTROL, keyCode);
        LogUtil.debug("按下 Ctrl+{}", number);
    }

    /**
     * 输入文本（通过剪贴板粘贴方式）
     * 优点：支持中文、特殊字符，避免输入法问题
     */
    public void typeText(String text) {
        LogUtil.debug("通过剪贴板输入文本：{}", text);

        if (text == null || text.isEmpty()) {
            LogUtil.warn("输入文本为空");
            return;
        }

        try {
            // 1. 将文本复制到系统剪贴板
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection stringSelection = new StringSelection(text);
            clipboard.setContents(stringSelection, null);

            // 2. 延迟等待剪贴板准备就绪
            robot.delay(100);

            // 3. 按下 Ctrl+V 粘贴
            pressCombinationKey(KeyEvent.VK_CONTROL, KeyEvent.VK_V);

            // 4. 额外延迟，确保粘贴完成
            robot.delay(gameAutoProperties.getOperation().getClickDelay());

            LogUtil.info("文本粘贴成功：{} 个字符", text.length());

        } catch (IllegalStateException e) {
            LogUtil.error("剪贴板操作失败：{}", e.getMessage(), e);
            throw new RuntimeException("无法访问系统剪贴板", e);
        } catch (Exception e) {
            LogUtil.error("文本输入失败：{}", e.getMessage(), e);
            throw new RuntimeException("文本输入异常", e);
        }
    }

    /**
     * 获取数字键的 keyCode
     */
    private int getNumberKeyCode(int number) {
        switch (number) {
            case 0: return KeyEvent.VK_0;
            case 1: return KeyEvent.VK_1;
            case 2: return KeyEvent.VK_2;
            case 3: return KeyEvent.VK_3;
            case 4: return KeyEvent.VK_4;
            case 5: return KeyEvent.VK_5;
            case 6: return KeyEvent.VK_6;
            case 7: return KeyEvent.VK_7;
            case 8: return KeyEvent.VK_8;
            case 9: return KeyEvent.VK_9;
            default: return KeyEvent.CHAR_UNDEFINED;
        }
    }

    /**
     * 按下功能键（F1-F12）
     */
    public void pressFunctionKey(int functionNumber) {
        if (functionNumber < 1 || functionNumber > 12) {
            LogUtil.warn("无效的功能键：F{}，必须在 F1-F12 之间", functionNumber);
            return;
        }

        int keyCode = KeyEvent.getExtendedKeyCodeForChar(KeyEvent.VK_F1 + functionNumber - 1);
        pressKey(keyCode);
    }
}
