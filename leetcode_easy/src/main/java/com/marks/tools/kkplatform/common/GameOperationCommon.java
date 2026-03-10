package com.marks.tools.kkplatform.common;

import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.tools.kkplatform.WindowSwitcherUtils;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.awt.event.KeyEvent;

import static com.marks.tools.kkplatform.common.KingOfBeastsConstants.GAME_TITLE;
import static com.marks.tools.kkplatform.common.KingOfBeastsConstants.MODIFIER_TITLE;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: GameOperationCommon </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/5 14:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class GameOperationCommon {
    protected ImageRecognitionAutomation automation;
    private WindowSwitcherUtils windowSwitcher;

    protected static final int CLICK_DELAY = 500;
    protected static final int RETRY_TIMES = 3;

    public GameOperationCommon(ImageRecognitionAutomation automation) {
        this.automation = automation;
        this.windowSwitcher = WindowSwitcherUtils.getInstance();
    }

    /**
     * 通用方法：查找并点击图片
     * @param imageName 图片名称
     * @return 是否成功
     */
    public boolean findAndClickImage(String imageName) {
        return findAndClickImage(imageName, 30);
    }

    /**
     * 通用方法：查找并点击图片（支持容差）
     * @param imageName 图片名称
     * @param tolerance 颜色容差
     * @return 是否成功
     */
    public boolean findAndClickImage(String imageName, int tolerance) {
        for (int i = 0; i < RETRY_TIMES; i++) {
            Point point = automation.findImage(imageName);
            if (point != null) {
                automation.click(point.x, point.y);
                automation.delay(CLICK_DELAY);
                LogUtil.info("成功点击图片：" + imageName);
                return true;
            }
            automation.delay(1000);
        }
        LogUtil.info("未找到图片：" + imageName);
        return false;
    }

    /**
     * 通用方法：等待图片出现
     * @param imageName 图片名称
     * @param timeout 超时时间（毫秒）
     * @return 是否找到
     */
    public boolean waitForImage(String imageName, int timeout) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeout) {
            Point point = automation.findImage(imageName);
            if (point != null) {
                LogUtil.info("找到图片：" + imageName);
                return true;
            }
            automation.delay(1000);
        }
        LogUtil.info("等待图片超时：" + imageName);
        return false;
    }

    /**
     * 通用方法：按下功能键
     * @param keyCode 键值
     */
    public void pressFunctionKey(int keyCode) {
        automation.robot.keyPress(keyCode);
        automation.delay(50);
        automation.robot.keyRelease(keyCode);
        automation.delay(CLICK_DELAY);
        LogUtil.info("按下功能键：" + KeyEvent.getKeyText(keyCode));
    }

    /**
     * 通用方法：按下组合键
     * @param controlKey 控制键
     * @param keyCode 目标键
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
     * 通用方法：双击指定坐标
     * @param x X 坐标
     * @param y Y 坐标
     */
    public void doubleClickAt(int x, int y) {
        automation.doubleClick(x, y);
        automation.delay(CLICK_DELAY);
    }

    public void oneClickAt(int x, int y) {
        automation.oneClick(x, y);
        automation.delay(CLICK_DELAY);
    }

    /**
     * 通用方法：输入文本即可
     * @param text 要输入的文本
     */
    public void inputTextAndConfirm(String text) {
        automation.typeText(text);
        automation.delay(200);
        automation.delay(CLICK_DELAY);
    }


    /**
     * 切换到游戏窗口
     */
    public void switchToGameWindow() {
        LogUtil.info("=== 切换到游戏窗口 ===");
        // 做一个循环判断, 直到找到游戏窗口
        while (!windowSwitcher.switchToWindow(GAME_TITLE)) {
            LogUtil.info("未找到游戏窗口，等待1s后继续查找");
            automation.delay(1000);
        }
    }

    /**
     * 切换到修改器窗口
     */
    public boolean switchToModifiersWindow() {
        LogUtil.info("=== 切换到修改器窗口 ===");
        return windowSwitcher.switchToWindow(MODIFIER_TITLE);
    }
}
