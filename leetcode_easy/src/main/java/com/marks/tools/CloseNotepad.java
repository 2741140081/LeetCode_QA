package com.marks.tools;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <p>项目名称: 关闭 Nopad++ </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/14 17:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class CloseNotepad {

    public static void main(String[] args) throws Exception {

        // 直接通过文件IO清空内容
        Files.write(Paths.get("C:\\test\\test.txt"), "".getBytes());

        // 启动记事本
        Process notepad = Runtime.getRuntime().exec("notepad.exe C:\\test\\test.txt");
        System.out.println("记事本已启动...");

        Robot robot = new Robot();
        String content = "In the digital realm, special characters like !@#$%^&*()_+{}|:\\\"<>?~ form "
                + "a unique language. The exclamation (!) shouts urgency, while @ connects us in emails "
                + "(user@domain.com). Hashtags (#) organize trends, and $ rules finance. "
                + "The caret (^) elevates exponents in math (2^3=8), and ampersands (&) link commands (A&B).";
        Thread.sleep(3000);

        // 激活窗口
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow("Notepad", "test.txt - 记事本");
        User32.INSTANCE.SetForegroundWindow(hwnd);
        Thread.sleep(1000);

        // 通过Shift切换

        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyRelease(KeyEvent.VK_SHIFT);

        robot.delay(1000);  // 确保切换完成

        typeString(robot, content, 100); // 50ms 键入延迟

        Thread.sleep(500);
        // 3. 模拟 Ctrl + S 保存
        robot.keyPress(KeyEvent.VK_CONTROL);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_S);
        Thread.sleep(500);
        robot.keyRelease(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        // 4. 模拟 Enter 确认保存
//        Thread.sleep(500);
//        robot.keyPress(KeyEvent.VK_ENTER);
//        robot.keyRelease(KeyEvent.VK_ENTER);

        // 等待5秒
        Thread.sleep(3000);

        // 5. 关闭记事本
        notepad.destroy();

        // 方式2：检查是否真正退出
        if (notepad.isAlive()) {
            notepad.destroyForcibly();
            notepad.waitFor();  // 确保进程终止
        }

        System.out.println("记事本已关闭");
    }

    public static void typeString(Robot robot, String str, int delayMs) {
        for (char c : str.toCharArray()) {
            // 处理特殊符号

            int keyCode = getKeyCodeForChar(c);

            // 处理需要Shift的特殊字符
            if ("!@#$%^&*()_+{}|:\"<>?~".indexOf(c) != -1) {
                robot.keyPress(KeyEvent.VK_SHIFT); // 按下Shift
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                robot.keyRelease(KeyEvent.VK_SHIFT); // 释放Shift
            }
            // 处理大写字母
            else if (Character.isUpperCase(c)) {
                robot.keyPress(KeyEvent.VK_SHIFT); // 按下Shift
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
            // 处理空格/逗号/小写字母（直接输入）
            else {
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode); // 无Shift
            }
            // 控制输入速度
            robot.delay(delayMs);
        }
    }

    // 获取字符对应的KeyEvent（支持常见符号）
    private static int getKeyCodeForChar(char c) {
        switch (c) {
            case '!': return KeyEvent.VK_1;
            case '@': return KeyEvent.VK_2;
            case '#': return KeyEvent.VK_3;
            case '$': return KeyEvent.VK_4;
            case '%': return KeyEvent.VK_5;
            case '^': return KeyEvent.VK_6;
            case '&': return KeyEvent.VK_7;
            case '*': return KeyEvent.VK_8;
            case '(': return KeyEvent.VK_9;
            case ')': return KeyEvent.VK_0;
            case '_': return KeyEvent.VK_MINUS;
            case '+': return KeyEvent.VK_EQUALS;
            case '{': return KeyEvent.VK_OPEN_BRACKET;
            case '}': return KeyEvent.VK_CLOSE_BRACKET;
            case ':': return KeyEvent.VK_SEMICOLON;
            case '<': return KeyEvent.VK_COMMA;
            case '>': return KeyEvent.VK_PERIOD;
            case '\"': return KeyEvent.VK_QUOTE;
            case '?': return KeyEvent.VK_SLASH;
            case '|': return KeyEvent.VK_BACK_SLASH;
            case '~': return KeyEvent.VK_BACK_QUOTE;
            default: return KeyEvent.getExtendedKeyCodeForChar(c);
        }
    }
}
