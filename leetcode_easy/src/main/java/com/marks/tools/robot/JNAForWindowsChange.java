package com.marks.tools.robot;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/6/3 15:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class JNAForWindowsChange {
    private final User32 user32 = User32.INSTANCE;
    private final Map<String, WinDef.HWND> windowMap = new HashMap<>();
    private List<String> appNames;

    public JNAForWindowsChange(List<String> appNames) {
        this.appNames = appNames;
    }

    // 初始化窗口句柄映射
    public void initWindowHandles() {
        user32.EnumWindows((hwnd, data) -> {
            char[] title = new char[512];
            user32.GetWindowText(hwnd, title, title.length);
            String windowTitle = new String(title).trim();

            appNames.forEach(appName -> {
                if(windowTitle.contains(appName)) {
                    windowMap.put(windowTitle, hwnd);
                }
            });
            return true;
        }, null);
    }

    // 强制获取窗口焦点并验证
    public boolean activateWindow(String appName) {
        Optional<Map.Entry<String, WinDef.HWND>> target = windowMap.entrySet().stream()
                .filter(entry -> entry.getKey().contains(appName))
                .findFirst();

        if(!target.isPresent()) return false;

        WinDef.HWND targetHwnd = target.get().getValue();
        user32.SetForegroundWindow(targetHwnd);
        user32.ShowWindow(targetHwnd, User32.SW_RESTORE);

        // 验证焦点状态
        WinDef.HWND activeHwnd = user32.GetForegroundWindow();
        return activeHwnd.equals(targetHwnd);
    }
}
