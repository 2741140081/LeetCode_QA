package com.marks.tools.kkplatform;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ImageRecognitionAutomation </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/4 16:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ImageRecognitionAutomation {
    private Robot robot;
    private Dimension screenSize;

    // 模板图片路径
    private static final String TEMPLATE_DIR = "D:\\images\\automation\\templates\\";

    // 颜色容差，用于图像匹配
    private static final int COLOR_TOLERANCE = 30;

    // 操作延迟（毫秒）
    private static final int DELAY_SHORT = 100;
    private static final int DELAY_MEDIUM = 500;
    private static final int DELAY_LONG = 1000;

    public ImageRecognitionAutomation() throws AWTException {
        robot = new Robot();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        robot.setAutoDelay(DELAY_SHORT);
    }

    /**
     * 在屏幕上查找匹配的图片
     * @param templateName 模板图片名称（不含扩展名）
     * @return 匹配到的坐标，未找到返回 null
     */
    public Point findImage(String templateName) {
        try {
            BufferedImage template = ImageIO.read(new File(TEMPLATE_DIR + templateName + ".png"));
            return matchTemplate(template);
        } catch (IOException e) {
            System.err.println("读取模板图片失败：" + templateName);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在屏幕上查找匹配的图片（支持容差）
     * @param templateName 模板图片名称
     * @param tolerance 颜色容差
     * @return 匹配到的坐标，未找到返回 null
     */
    public Point findImage(String templateName, int tolerance) {
        try {
            BufferedImage template = ImageIO.read(new File(TEMPLATE_DIR + templateName + ".png"));
            return matchTemplate(template, tolerance);
        } catch (IOException e) {
            System.err.println("读取模板图片失败：" + templateName);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 模板匹配算法（使用默认容差）
     */
    private Point matchTemplate(BufferedImage template) {
        return matchTemplate(template, COLOR_TOLERANCE);
    }

    /**
     * 模板匹配算法（支持自定义容差）
     */
    private Point matchTemplate(BufferedImage template, int tolerance) {
        Rectangle screenRect = new Rectangle(screenSize);
        BufferedImage screenshot = robot.createScreenCapture(screenRect);

        int screenWidth = screenshot.getWidth();
        int screenHeight = screenshot.getHeight();
        int templateWidth = template.getWidth();
        int templateHeight = template.getHeight();

        System.out.println("开始图像匹配 - 屏幕：" + screenWidth + "x" + screenHeight +
                ", 模板：" + templateWidth + "x" + templateHeight);

        // 遍历屏幕截图进行匹配
        for (int y = 0; y <= screenHeight - templateHeight; y++) {
            for (int x = 0; x <= screenWidth - templateWidth; x++) {
                if (matches(screenshot, template, x, y, tolerance)) {
                    System.out.println("图像匹配成功 - 位置：(" + x + ", " + y + ")");
                    // 返回匹配区域的中心点
                    return new Point(x + templateWidth / 2, y + templateHeight / 2);
                }
            }
        }
        System.out.println("图像匹配失败 - 未找到匹配项");
        return null;
    }

    /**
     * 像素级匹配（支持容差参数）
     */
    private boolean matches(BufferedImage screenshot, BufferedImage template, int startX, int startY, int tolerance) {
        for (int ty = 0; ty < template.getHeight(); ty++) {
            for (int tx = 0; tx < template.getWidth(); tx++) {
                Color templateColor = new Color(template.getRGB(tx, ty));
                Color screenColor = new Color(screenshot.getRGB(startX + tx, startY + ty));

                if (!colorsMatch(templateColor, screenColor, tolerance)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 颜色匹配（支持容差）
     */
    private boolean colorsMatch(Color c1, Color c2, int tolerance) {
        return Math.abs(c1.getRed() - c2.getRed()) <= tolerance &&
                Math.abs(c1.getGreen() - c2.getGreen()) <= tolerance &&
                Math.abs(c1.getBlue() - c2.getBlue()) <= tolerance;
    }

    /**
     * 点击指定坐标
     */
    public void click(int x, int y) {
        robot.mouseMove(x, y);
        robot.delay(DELAY_SHORT);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(50);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        System.out.println("点击坐标：(" + x + ", " + y + ")");
    }

    /**
     * 双击指定坐标
     */
    public void doubleClick(int x, int y) {
        click(x, y);
        robot.delay(100);
        click(x, y);
    }

    /**
     * 点击找到的图片
     */
    public boolean clickImage(String imageName) {
        Point point = findImage(imageName);
        if (point != null) {
            click(point.x, point.y);
            return true;
        }
        return false;
    }

    /**
     * 点击找到的图片（支持容差）
     */
    public boolean clickImage(String imageName, int tolerance) {
        Point point = findImage(imageName, tolerance);
        if (point != null) {
            click(point.x, point.y);
            return true;
        }
        return false;
    }

    /**
     * 输入文本
     */
    public void typeText(String text) {
        System.out.println("输入文本：" + text);
        char[] chars = text.toCharArray();
        for (char c : chars) {
            typeCharacter(c);
        }
    }

    /**
     * 输入单个字符
     */
    private void typeCharacter(char c) {
        int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
        if (keyCode == KeyEvent.CHAR_UNDEFINED) {
            // 处理特殊字符
            switch (c) {
                case '0': keyCode = KeyEvent.VK_0; break;
                case '1': keyCode = KeyEvent.VK_1; break;
                case '2': keyCode = KeyEvent.VK_2; break;
                case '3': keyCode = KeyEvent.VK_3; break;
                case '4': keyCode = KeyEvent.VK_4; break;
                case '5': keyCode = KeyEvent.VK_5; break;
                case '6': keyCode = KeyEvent.VK_6; break;
                case '7': keyCode = KeyEvent.VK_7; break;
                case '8': keyCode = KeyEvent.VK_8; break;
                case '9': keyCode = KeyEvent.VK_9; break;
                case '.': keyCode = KeyEvent.VK_PERIOD; break;
                case '-': keyCode = KeyEvent.VK_MINUS; break;
                case '_': keyCode = KeyEvent.VK_UNDERSCORE; break;
                default:
                    System.err.println("不支持的字符：" + c);
                    return;
            }
        }
        robot.keyPress(keyCode);
        robot.delay(50);
        robot.keyRelease(keyCode);
        robot.delay(50);
    }

    /**
     * 按下回车键
     */
    public void pressEnter() {
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_ENTER);
        System.out.println("按下回车键");
    }

    /**
     * 按下 Tab 键
     */
    public void pressTab() {
        robot.keyPress(KeyEvent.VK_TAB);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_TAB);
        System.out.println("按下 Tab 键");
    }

    /**
     * 按下方向键
     */
    public void pressArrowKey(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                robot.keyPress(keyCode);
                robot.delay(50);
                robot.keyRelease(keyCode);
                System.out.println("按下方向键：" + keyCode);
                break;
            default:
                System.err.println("无效的方向键");
        }
    }

    /**
     * 快捷键操作（如 Ctrl+C）
     */
    public void pressShortcut(int controlKey, int keyCode) {
        robot.keyPress(controlKey);
        robot.keyPress(keyCode);
        robot.delay(50);
        robot.keyRelease(keyCode);
        robot.keyRelease(controlKey);
        System.out.println("按下快捷键：" + KeyEvent.getKeyText(controlKey) + "+" + KeyEvent.getKeyText(keyCode));
    }

    /**
     * 截取屏幕并保存
     */
    public void captureScreen(String outputPath) {
        try {
            Rectangle screenRect = new Rectangle(screenSize);
            BufferedImage screenshot = robot.createScreenCapture(screenRect);
            File outputFile = new File(outputPath);
            ImageIO.write(screenshot, "png", outputFile);
            System.out.println("屏幕截图已保存到：" + outputPath);
        } catch (IOException e) {
            System.err.println("截图失败");
            e.printStackTrace();
        }
    }

    /**
     * 截取指定区域并保存
     */
    public void captureRegion(int x, int y, int width, int height, String outputPath) {
        try {
            Rectangle region = new Rectangle(x, y, width, height);
            BufferedImage screenshot = robot.createScreenCapture(region);
            File outputFile = new File(outputPath);
            ImageIO.write(screenshot, "png", outputFile);
            System.out.println("区域截图已保存到：" + outputPath);
        } catch (IOException e) {
            System.err.println("截图失败");
            e.printStackTrace();
        }
    }

    public void delay(int testDelay) {
        robot.delay(testDelay);
    }
}
