package com.marks.kkPlatformGameAuto.service.impl;

import com.marks.kkPlatformGameAuto.service.WindowSwitcherService;

import com.marks.kkPlatformGameAuto.config.WindowConfig;
import com.marks.kkPlatformGameAuto.service.WindowSwitcherService;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>项目名称：LeetCode_QA </p>
 * <p>文件名称：WindowSwitcherServiceImpl </p>
 * <p>描述：窗口切换服务实现类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17
 * @update [序号][日期 YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Service
public class WindowSwitcherServiceImpl implements WindowSwitcherService {
    private static final int PROCESS_QUERY_INFORMATION = 0x0400;
    private static final int PROCESS_VM_READ = 0x0010;

    @Autowired
    private WindowConfig windowConfig;

    @Override
    public boolean switchToPlatform() {
        log.debug("切换到平台窗口");
        return switchToWindow(windowConfig.getPlatformAndRoomName());
    }

    @Override
    public boolean switchToReadyRoom() {
        log.debug("切换到准备房间窗口");
        return switchToWindow(windowConfig.getPlatformAndRoomName());
    }

    @Override
    public boolean switchToModifier() {
        log.debug("切换到修改器窗口");
        return switchToWindow(windowConfig.getModifierName());
    }

    @Override
    public boolean switchToGame() {
        log.debug("切换到游戏主体窗口");
        return switchToWindow(windowConfig.getGameName());
    }

    /**
     * 根据窗口标题切换到指定的应用程序窗口（内部方法）
     * @param windowTitle 窗口标题（支持模糊匹配）
     * @return true 如果切换成功，否则 false
     */
    private boolean switchToWindow(String windowTitle) {
        User32.HWND targetWindow = findWindow(windowTitle);
        if (targetWindow != null) {
            bringWindowToFront(targetWindow);
            log.info("成功切换到窗口：{}", windowTitle);
            return true;
        }
        log.warn("未找到窗口：{}", windowTitle);
        return false;
    }

    /**
     * 根据窗口标题查找窗口（内部方法）
     * @param windowTitle 窗口标题
     * @return 窗口句柄
     */
    private User32.HWND findWindow(String windowTitle) {
        final User32.HWND[] foundWindow = {null};
        final String searchTitle = windowTitle.toLowerCase();

        User32.INSTANCE.EnumWindows((hWnd, pointer) -> {
            if (User32.INSTANCE.IsWindowVisible(hWnd)) {
                char[] buffer = new char[512];
                User32.INSTANCE.GetWindowText(hWnd, buffer, 512);
                String title = Native.toString(buffer).trim();
                if (title.toLowerCase().contains(searchTitle)) {
                    foundWindow[0] = hWnd;
                    return false;
                }
            }
            return true;
        }, null);

        return foundWindow[0];
    }

    /**
     * 将窗口带到前台并激活（内部方法）
     * @param hWnd 窗口句柄
     */
    private void bringWindowToFront(User32.HWND hWnd) {
        User32.WINDOWPLACEMENT wp = new User32.WINDOWPLACEMENT();
        wp.length = wp.size();

        if (User32.INSTANCE.GetWindowPlacement(hWnd, wp).booleanValue()) {
            if ((wp.showCmd & User32.SW_SHOWMINIMIZED) != 0 ||
                    (wp.showCmd & User32.SW_MINIMIZE) != 0) {
                User32.INSTANCE.ShowWindow(hWnd, User32.SW_RESTORE);
            }
        }

        User32.INSTANCE.SetForegroundWindow(hWnd);

        try {
            Thread.sleep(windowConfig.getSwitchWaitTime());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("窗口切换等待被中断", e);
        }
        User32.INSTANCE.SetForegroundWindow(hWnd);
    }
}
