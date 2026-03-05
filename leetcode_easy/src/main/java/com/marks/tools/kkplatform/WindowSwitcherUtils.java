package com.marks.tools.kkplatform;

import com.marks.utils.LogUtil;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: WindowSwitcherUtils </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/4 16:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class WindowSwitcherUtils {
    private static final int PROCESS_QUERY_INFORMATION = 0x0400;
    private static final int PROCESS_VM_READ = 0x0010;

    private static WindowSwitcherUtils instance;

    public WindowSwitcherUtils() {}

    public static synchronized WindowSwitcherUtils getInstance() {
        if (instance == null) {
            instance = new WindowSwitcherUtils();
        }
        return instance;
    }

    /**
     * 根据窗口标题切换到指定的应用程序窗口
     * @param windowTitle 窗口标题（支持模糊匹配）
     * @return true 如果切换成功，否则 false
     */
    public boolean switchToWindow(String windowTitle) {
        User32.HWND targetWindow = findWindow(windowTitle);
        if (targetWindow != null) {
            bringWindowToFront(targetWindow);
            return true;
        }
        return false;
    }

    /**
     * 根据进程名切换到指定的应用程序窗口
     * @param processName 进程名（例如："chrome.exe", "outlook.exe"）
     * @return true 如果切换成功，否则 false
     */
    public boolean switchToWindowByProcess(String processName) {
        User32.HWND targetWindow = findWindowByProcess(processName);
        if (targetWindow != null) {
            bringWindowToFront(targetWindow);
            return true;
        }
        return false;
    }

    /**
     * 获取所有打开的窗口列表
     * @return 窗口标题列表
     */
    public List<String> getAllOpenWindows() {
        List<String> windows = new ArrayList<>();

        User32.INSTANCE.EnumWindows(new User32.WNDENUMPROC() {
            @Override
            public boolean callback(User32.HWND hWnd, Pointer pointer) {
                if (User32.INSTANCE.IsWindowVisible(hWnd)) {
                    char[] buffer = new char[512];
                    User32.INSTANCE.GetWindowText(hWnd, buffer, 512);
                    String title = Native.toString(buffer).trim();
                    if (!title.isEmpty()) {
                        windows.add(title);
                    }
                }
                return true;
            }
        }, null);

        return windows;
    }

    /**
     * 根据窗口标题查找窗口
     * @param windowTitle 窗口标题（支持模糊匹配）
     * @return 窗口句柄
     */
    private User32.HWND findWindow(String windowTitle) {
        final User32.HWND[] foundWindow = {null};
        final String searchTitle = windowTitle.toLowerCase();

        User32.INSTANCE.EnumWindows(new User32.WNDENUMPROC() {
            @Override
            public boolean callback(User32.HWND hWnd, Pointer pointer) {
                if (User32.INSTANCE.IsWindowVisible(hWnd)) {
                    char[] buffer = new char[512];
                    User32.INSTANCE.GetWindowText(hWnd, buffer, 512);
                    String title = Native.toString(buffer).trim();
                    if (title.toLowerCase().contains(searchTitle)) {
                        foundWindow[0] = hWnd;
                        return false; // 找到后停止枚举
                    }
                }
                return true;
            }
        }, null);

        return foundWindow[0];
    }

    /**
     * 根据进程名查找窗口
     * @param processName 进程名
     * @return 窗口句柄
     */
    private User32.HWND findWindowByProcess(String processName) {
        final User32.HWND[] foundWindow = {null};
        final String targetProcess = processName.toLowerCase();

        User32.INSTANCE.EnumWindows(new User32.WNDENUMPROC() {
            @Override
            public boolean callback(User32.HWND hWnd, Pointer pointer) {
                if (User32.INSTANCE.IsWindowVisible(hWnd)) {
                    IntByReference pid = new IntByReference();
                    User32.INSTANCE.GetWindowThreadProcessId(hWnd, pid);

                    WinNT.HANDLE hProcess = Kernel32.INSTANCE.OpenProcess(
                            PROCESS_QUERY_INFORMATION | PROCESS_VM_READ,
                            false,
                            pid.getValue()
                    );

                    if (hProcess != null) {
                        char[] processNameBuffer = new char[512];
                        if (Kernel32.INSTANCE.QueryFullProcessImageName(hProcess, 0, processNameBuffer, new IntByReference(processNameBuffer.length))) {
                            String currentProcess = Native.toString(processNameBuffer).toLowerCase();
                            if (currentProcess.endsWith(targetProcess)) {
                                foundWindow[0] = hWnd;
                                Kernel32.INSTANCE.CloseHandle(hProcess);
                                return false;
                            }
                        }
                        Kernel32.INSTANCE.CloseHandle(hProcess);
                    }
                }
                return true;
            }
        }, null);

        return foundWindow[0];
    }

    /**
     * 将窗口带到前台并激活
     * @param hWnd 窗口句柄
     */
    private void bringWindowToFront(User32.HWND hWnd) {
        // 获取窗口放置信息
        User32.WINDOWPLACEMENT wp = new User32.WINDOWPLACEMENT();
        wp.length = wp.size();

        // 如果窗口最小化，先恢复
        if (User32.INSTANCE.GetWindowPlacement(hWnd, wp).booleanValue()) {
            if ((wp.showCmd & User32.SW_SHOWMINIMIZED) != 0 ||
                    (wp.showCmd & User32.SW_MINIMIZE) != 0) {
                User32.INSTANCE.ShowWindow(hWnd, User32.SW_RESTORE);
            }
        }

        // 设置前台窗口
        User32.INSTANCE.SetForegroundWindow(hWnd);

        // 尝试再次设置以确保窗口获得焦点
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        User32.INSTANCE.SetForegroundWindow(hWnd);
    }

    /**
     * 打印所有打开的窗口信息（用于调试）
     */
    public void printAllWindowsInfo() {
        LogUtil.info("=== 当前打开的所有窗口 ===");
        User32.INSTANCE.EnumWindows(new User32.WNDENUMPROC() {
            @Override
            public boolean callback(User32.HWND hWnd, Pointer pointer) {
                if (User32.INSTANCE.IsWindowVisible(hWnd)) {
                    char[] buffer = new char[512];
                    User32.INSTANCE.GetWindowText(hWnd, buffer, 512);
                    String title = Native.toString(buffer).trim();

                    IntByReference pid = new IntByReference();
                    User32.INSTANCE.GetWindowThreadProcessId(hWnd, pid);

                    if (!title.isEmpty()) {
                        LogUtil.info("窗口标题：%s, 进程 ID: %d%n", title, pid.getValue());
                    }
                }
                return true;
            }
        }, null);
        LogUtil.info("========================");
    }
}
