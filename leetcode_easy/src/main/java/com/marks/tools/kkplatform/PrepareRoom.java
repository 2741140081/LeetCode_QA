package com.marks.tools.kkplatform;

import com.marks.tools.kkplatform.common.GameOperationCommon;
import com.marks.utils.LogUtil;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.awt.Point;
import java.util.List;

import static com.marks.tools.kkplatform.common.KingOfBeastsConstants.PREPARE_ROOM_TITLE;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: PrepareRoom </p>
 * <p>描述: 游戏的准备房间页面
 * 1. 通过图片识别(最佳缩放和迭代二分查找最佳), 找到按钮"开始游戏"
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/5 11:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class PrepareRoom extends GameOperationCommon {
    private static final String START_GAME_BUTTON = "start_game_btn";
    private static final String ROOM_NUM_FLAG = "room_number_flag"; // 房间编号标识
    private static final String RED_PACKET_POP_FLAG = "red_packet_pop_up_flag"; // 红包弹窗标识
    private ImageRecognitionAutomation automation;
    private WindowSwitcherUtils windowSwitcher;

    public PrepareRoom(ImageRecognitionAutomation automation) {
        super(automation);
        this.automation = automation;
        this.windowSwitcher = WindowSwitcherUtils.getInstance();
    }

    /**
     * 在准备房间页面点击"开始游戏"按钮
     * 修改等待时间为20s
     * @return 是否成功
     */
    public boolean startGame() {
        LogUtil.info("=== 准备房间：寻找开始游戏按钮 ===");

        if (findAndClickImage(START_GAME_BUTTON)) {
            LogUtil.info("已点击开始游戏按钮，等待游戏加载...");
            automation.delay(10000);
            LogUtil.info("游戏加载完成");
            return true;
        }

        LogUtil.error("未找到开始游戏按钮");
        return false;
    }

    /**
     * @Description:
     * 处理同名窗口
     * 1. 遍历窗口列表，将窗口放到前面, 然后截图匹配是否有 ROOM_NUM_FLAG
     * 2. 由于现在我不确定红包的弹窗是一个独立的窗口还是现有窗口的子窗口
     * 3. 先关闭红包, 然后再切换到准备房间窗口
     * @param: sameNameWindows
     * @return com.sun.jna.platform.win32.WinDef.HWND
     * @author marks
     * @CreateDate: 2026/03/11 16:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private User32.HWND getPrepareRoomWindow(List<User32.HWND> sameNameWindows) {
        // 检测所有窗口是否有红包弹窗
        LogUtil.info("=== 检测所有窗口是否有红包弹窗 ===");
        // 遍历窗口列表
        for (User32.HWND hwnd : sameNameWindows) {
            windowSwitcher.switchToWindowByHWND(hwnd);
            // 延迟1s
            automation.delay(1000);
            // 判断 RED_PACKET_POP_FLAG
            Point redPoint = automation.findImage(RED_PACKET_POP_FLAG, false);
            if (redPoint != null) {
                // 点击红包弹窗的关闭按钮
                automation.click(redPoint.x, redPoint.y);
                // 测试时, 延迟 10s, 方便找到关闭点与当前点击位置的偏移量
                automation.delay(10000);
            }
        }

        // 遍历窗口列表
        for (User32.HWND hwnd : sameNameWindows) {
            windowSwitcher.switchToWindowByHWND(hwnd);
            // 延迟1s
            automation.delay(1000);
            // 判断ROOM_NUM_FLAG
            if (automation.findImage(ROOM_NUM_FLAG, false) != null) {
                return hwnd;
            }
        }
        return null;
    }

    /**
     * 切换到准备房间界面
     */
    public boolean switchToPrepareRoom() {
        LogUtil.info("=== 切换到准备房间界面 ===");
        // 通过名称获取所有同名的窗口句柄信息
        List<WinDef.HWND> sameNameWindowByTitle = windowSwitcher.findSameNameWindowByTitle(PREPARE_ROOM_TITLE);
        // 通过处理获取正确的窗口句柄
        WinDef.HWND roomWindow = getPrepareRoomWindow(sameNameWindowByTitle);
        // 通过窗口句柄切换窗口
        return windowSwitcher.switchToWindowByHWND(roomWindow);
    }
}
