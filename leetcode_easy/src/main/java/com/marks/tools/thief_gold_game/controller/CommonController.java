package com.marks.tools.thief_gold_game.controller;


import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.tools.kkplatform.WindowSwitcherUtils;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.marks.tools.kkplatform.common.KingOfBeastsConstants.GAME_TITLE;
import static com.marks.tools.kkplatform.common.KingOfBeastsConstants.MODIFIER_TITLE;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: CommonController </p>
 * <p>描述: 公共操作类，提供基础的图像识别、鼠标键盘操作 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/12 11:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class CommonController {
    protected ImageRecognitionAutomation automation;
    protected WindowSwitcherUtils windowSwitcher;

    protected static final int CLICK_DELAY = 250;
    // 分别配置3s、5s、10s的超时时间
    protected static final int TIMEOUT_3_S = 3000;
    protected static final int TIMEOUT_5_S = 5000;
    protected static final int TIMEOUT_10_S = 10000;

    // 图片路径常量
    protected static final String TEMPLATE_DIR = "D:/images/automation/thief_gold";
    protected static final String COMMON_FOLDER = "common/";  // 物品图片放置在common/ 文件夹下, 名称是 w291.png 类似

    protected static final String LOCKER_BUILDING = "common/locker_building";
    protected static final String THIEF_LEFT_TOP_FLAG = "common/thief_left_top_flag"; // 小偷左上角标志图片
    protected static final String VICTORY_BUTTON = "common/victory_button"; // 游戏胜利按钮

    // 可能在GameFlowController 中使用, 使用 public 访问权限，方便 GameFlowController 中使用
    public static final String[] FIRST_ITEM_NAMES = {"w291"};
    public static final String[] SECOND_ITEM_NAMES = {"w293", "w293", "w290", "w290", "w291"};
    public static final String[] CONSUMER_ITEM_NAMES = {"ys04", "ys04", "ys04", "ys04", "ys04", "ys04"}; // 消费者的名称
    public static final String[] PRODUCER_ITEM_NAMES = {"w293", "w293", "w293", "w293", "w293", "w293"}; // 生产者的名称

    // 等待时间配置（毫秒）
    protected static int WAIT_FOR_SHOP_TIME = 4500;     // 等待 10 s去商店
    protected static boolean isChallenge5 = false;


    public CommonController(ImageRecognitionAutomation automation) {
        this.automation = automation;
        this.windowSwitcher = WindowSwitcherUtils.getInstance();
    }

    /**
     * 查找并点击图片
     * @param imageName 图片名称（相对于 TEMPLATE_DIR 的子目录）
     * @return 是否成功
     */
    public boolean findAndClickImage(String imageName) {
        return findAndClickImage(imageName, TIMEOUT_3_S);
    }

    /**
     * 查找并点击图片（支持容差）
     * @param imageName 图片名称
     * @param timeout ms 超时时间
     * @return 是否成功
     */
    public boolean findAndClickImage(String imageName, int timeout) {
        LogUtil.info("开始查找并点击图片：{}, 超时时间：{}ms", imageName, timeout);
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeout) {
            Point point = findImage(imageName, false);
            if (point != null) {
                automation.click(point.x, point.y);
                return true;
            }
        }

        LogUtil.info("未找到图片：{}, 耗时：{}ms", imageName, System.currentTimeMillis() - startTime);
        return false;
    }

    /**
     * 1. 查找图片并返回坐标, 需要封装一下, 由于 调用的是 automation.findImage(), 里面是固定的 TEMPLATE_DIR, 不满足当前需求，所以封装一下
     * @param imageName 图片名称
     * @return 坐标点，未找到返回 null
     */
    public Point findImage(String imageName) {
        return findImage(imageName, true);
    }

    public Point findImage(String imageName, boolean isNecessary) {
        // 构建模板图片完整路径
        String templatePath = Paths.get(TEMPLATE_DIR, imageName + ".png").toString();
        File templateFile = new File(templatePath);
        if (!templateFile.exists()) {
            throw new RuntimeException("模板图片不存在：" + templatePath);
        }
        imageName = templateFile.getName();
        return findImage(imageName, templatePath, isNecessary);
    }

    public Point findImage(String imageName, String imagePath, boolean isNecessary) {
        return automation.findImage(imageName, imagePath, isNecessary);
    }


    public boolean waitForImage(String imageName, int timeout, int delayTime) {
        return getPointByWait(imageName, timeout, delayTime) != null;
    }

    /**
     * 等待图片出现
     * @param imageName 图片名称
     * @param timeout 超时时间（毫秒）
     * @return 是否找到
     */
    public Point getPointByWait(String imageName, int timeout, int delayTime) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeout) {
            Point point = findImage(imageName, false);
            if (point != null) {
                LogUtil.info("找到图片：" + imageName);
                return point;
            }
            automation.delay(delayTime);
        }
        LogUtil.info("等待图片超时：" + imageName);
        return null;
    }

    /**
     * 检查图片是否存在
     * @param imageName 图片名称
     * @return 是否存在
     */
    public boolean isImageExists(String imageName) {
        Point point = findImage(imageName, false);
        return point != null;
    }

    /**
     * 移动鼠标到指定坐标
     * @param x X 坐标
     * @param y Y 坐标
     */
    public void moveTo(int x, int y) {
        automation.robot.mouseMove(x, y);
        automation.delay(100);
        LogUtil.info("移动鼠标到坐标：(" + x + ", " + y + ")");
    }

    /**
     * 左键单击
     */
    public void leftClick() {
        automation.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        automation.delay(50);
        automation.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        automation.delay(CLICK_DELAY);
        LogUtil.info("左键单击");
    }

    /**
     * 右键单击
     */
    public void rightClick() {
        automation.robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        automation.delay(50);
        automation.robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        automation.delay(CLICK_DELAY);
        LogUtil.info("右键单击");
    }

    /**
     * 双击
     * @param x X 坐标
     * @param y Y 坐标
     */
    public void doubleClick(int x, int y) {
        automation.doubleClick(x, y);
        automation.delay(CLICK_DELAY);
    }

    /**
     * 输入文本
     * @param text 要输入的文本
     */
    public void inputText(String text) {
        automation.typeText(text);
        automation.delay(200);
    }

    /**
     * 按下组合键
     * @param controlKey 控制键（如 KeyEvent.VK_CONTROL）
     * @param keyCode 目标键（如 KeyEvent.VK_A）
     */
    public void pressCombinationKey(int controlKey, int keyCode) {
        automation.robot.keyPress(controlKey);
        automation.robot.keyPress(keyCode);
        automation.delay(50);
        automation.robot.keyRelease(keyCode);
        automation.robot.keyRelease(controlKey);
        automation.delay(CLICK_DELAY);
        LogUtil.info("按下组合键：" + KeyEvent.getKeyText(controlKey) + "+" + KeyEvent.getKeyText(keyCode));
    }

    /**
     * 按下功能键
     * @param keyCode 键值
     */
    public void pressKey(int keyCode) {
        automation.robot.keyPress(keyCode);
        automation.delay(100);
        automation.robot.keyRelease(keyCode);
        automation.delay(50);
    }


    public void pressNumber(int number) {
        int keyCode = getNumberKeyCode(number);
        pressKey(keyCode);
    }

    /**
     * 编号操作（Ctrl + 数字键）
     * @param number 编号 0-9
     */
    public void selectNumber(int number) {
        int keyCode = getNumberKeyCode(number);
        pressCombinationKey(KeyEvent.VK_CONTROL, keyCode);
    }

    /**
     * 获取数字键的 keyCode
     * @param number 数字 (0-9)
     * @return keyCode 值，如果无效返回 KeyEvent.CHAR_UNDEFINED
     */
    public int getNumberKeyCode(int number) {
        if (number < 0 || number > 9) {
            LogUtil.error("无效的数字：{}，必须在 0-9 之间", number);
            return KeyEvent.CHAR_UNDEFINED;
        }

        return KeyEvent.getExtendedKeyCodeForChar(Character.forDigit(number, 10));
    }

    /**
     * 切换到游戏窗口
     */
    public void switchToGameWindow() {
        LogUtil.info("=== 切换到游戏窗口 ===");
        while (!windowSwitcher.switchToWindow(GAME_TITLE)) {
            LogUtil.info("未找到游戏窗口，等待 1s 后继续查找");
            automation.delay(1000);
        }
    }

    /**
     * 切换到修改器窗口
     */
    public boolean switchToModifierWindow() {
        LogUtil.info("=== 切换到修改器窗口 ===");
        return windowSwitcher.switchToWindow(MODIFIER_TITLE);
    }

    /**
     * 延迟指定毫秒
     * @param milliseconds 毫秒数
     */
    public void delay(int milliseconds) {
        automation.delay(milliseconds);
    }

    /**
     * 处理坐标点的偏移操作
     *
     * @param point   原始坐标点
     * @param xOffset X 轴偏移量（正数向右，负数向左）
     * @param yOffset Y 轴偏移量（正数向下，负数向上）
     */
    public void offsetPoint(Point point, int xOffset, int yOffset) {
        if (point == null) {
            LogUtil.error("坐标点为空");
            return;
        }

        point.x += xOffset;
        point.y += yOffset;
    }

    /**
     * 返回基于原始坐标点的偏移后的新坐标点
     * @param point   原始坐标点参照点
     * @param xOffset X 轴偏移量（正数向右，负数向左）
     * @param yOffset Y 轴偏移量（正数向下，负数向上）
     * @return 新的坐标点
     */
    public Point getPointByOffset(Point point, int xOffset, int yOffset) {
        if (point == null) {
            LogUtil.error("坐标点为空");
            return null;
        }

        return new Point(point.x + xOffset, point.y + yOffset);
    }


    public List<String> getAllItemNames() {
        Set<String> itemNames = new HashSet<>();
        // 遍历第一次和第二次的物品名称, 添加到 itemNames 中
        itemNames.addAll(Arrays.asList(FIRST_ITEM_NAMES));
        itemNames.addAll(Arrays.asList(SECOND_ITEM_NAMES));
        // 将 itemNames 转换为List
        return itemNames.stream().toList();
    }
}
