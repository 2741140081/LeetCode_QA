package com.marks.kkPlatformGameAuto.util;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 窗口位置管理工具类
 * 使用 JNA 设置窗口位置和大小
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/18
 */
@Slf4j
@Component
public class WindowPositionManager {

    /**
     * 设置窗口的位置和大小
     *
     * @param hWnd   窗口句柄
     * @param x      X 坐标
     * @param y      Y 坐标
     * @param width  宽度
     * @param height 高度
     * @return true 如果设置成功，否则 false
     */
    public boolean setWindowPosition(User32.HWND hWnd, int x, int y, int width, int height) {
        try {
            // 使用 SetWindowPos 设置窗口位置和大小
            boolean result = User32.INSTANCE.SetWindowPos(
                    hWnd,
                    null,           // 无需改变 Z 序
                    x,              // X 坐标
                    y,              // Y 坐标
                    width,          // 宽度
                    height,         // 高度
                    User32.SWP_NOZORDER | User32.SWP_NOACTIVATE // 标志位
            );

            if (result) {
                log.info("窗口位置和大小已设置：x={}, y={}, width={}, height={}", x, y, width, height);
            } else {
                log.error("设置窗口位置失败");
            }

            return result;
        } catch (Exception e) {
            log.error("设置窗口位置时发生异常", e);
            return false;
        }
    }

    /**
     * 获取窗口的当前位置和大小
     *
     * @param hWnd 窗口句柄
     * @return 窗口矩形信息
     */
    public WinDef.RECT getWindowPosition(User32.HWND hWnd) {
        WinDef.RECT rect = new WinDef.RECT();
        if (User32.INSTANCE.GetWindowRect(hWnd, rect)) {
            log.debug("窗口当前位置：x={}, y={}, width={}, height={}",
                    rect.left, rect.top,
                    rect.right - rect.left,
                    rect.bottom - rect.top);
            return rect;
        }
        return null;
    }
}
