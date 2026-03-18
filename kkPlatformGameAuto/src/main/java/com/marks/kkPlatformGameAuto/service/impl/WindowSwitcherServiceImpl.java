package com.marks.kkPlatformGameAuto.service.impl;

import com.marks.kkPlatformGameAuto.config.PlatformConfig;
import com.marks.kkPlatformGameAuto.config.PrepareRoomConfig;
import com.marks.kkPlatformGameAuto.entity.WindowInfo;
import com.marks.kkPlatformGameAuto.service.ImageRecognitionService;
import com.marks.kkPlatformGameAuto.service.WindowSwitcherService;

import com.marks.kkPlatformGameAuto.config.WindowConfig;
import com.marks.kkPlatformGameAuto.util.ImagePathUtils;
import com.marks.kkPlatformGameAuto.util.WindowPositionManager;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称：LeetCode_QA </p>
 * <p>文件名称：WindowSwitcherServiceImpl </p>
 * <p>描述：窗口切换服务实现类 </p>
 * <p>创建日期：2026/3/17: 当前4个主要窗口功能基本实现 </p>
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

    @Autowired
    private PrepareRoomConfig prepareRoomConfig;

    @Autowired
    private PlatformConfig platformConfig;

    @Autowired
    private ImagePathUtils imagePathUtils;

    @Autowired
    private ImageRecognitionService imageRecognitionService;

    @Autowired
    private WindowPositionManager windowPositionManager;

    @Override
    public boolean switchToPlatform() {
        log.debug("切换到平台窗口");
        // 获取窗口名称
        String windowTitle = windowConfig.getPlatformAndRoomName();
        // 得到所有同名窗口
        List<User32.HWND> sameNameWindows = findSameNameWindowByTitle(windowTitle);
        // 从配置文件得到平台logo 和 创建房间按钮图片
        String platformLogoImage = platformConfig.getPlatformLogoImage();
        String createRoomButtonImage = prepareRoomConfig.getStartGameButtonImage();
        // 平台目录用于读取图片
        String imageDir = platformConfig.getImageDir();
        // 获取完整目录
        String platformLogoImagePath = imagePathUtils.buildImagePathWithExtension(imageDir, platformLogoImage);
        String createRoomButtonImagePath = imagePathUtils.buildImagePathWithExtension(imageDir, createRoomButtonImage);

        // 遍历同名窗口
        for (User32.HWND window : sameNameWindows) {
            // 通过句柄获取窗口焦点
            bringWindowToFront(window);
            // 通过识别平台logo图片 和 创建房间按钮图片 判断是否匹配
            if (imageRecognitionService.findImage(platformLogoImagePath) &&
                imageRecognitionService.findImage(createRoomButtonImagePath)) {
                // 该窗口是平台窗口
                return true;
            }
        }
        log.warn("未找到平台窗口");
        return false;
    }

    @Override
    public boolean switchToPrepareRoom() {
        log.debug("切换到准备房间窗口");
        // 获取窗口名称
        String windowTitle = windowConfig.getPlatformAndRoomName();
        // 得到所有同名窗口
        List<User32.HWND> sameNameWindows = findSameNameWindowByTitle(windowTitle);
        // 从准备房间配置文件获取房间号图片名称
        String roomNumberLabelImage = prepareRoomConfig.getRoomNumberLabelImage();
        // 获取图片目录
        String imageDir = prepareRoomConfig.getImageDir();
        // 拼接完整目录
        String roomNumberLabelImagePath = imagePathUtils.buildImagePathWithExtension(imageDir, roomNumberLabelImage);
        // 遍历同名窗口
        for (User32.HWND window : sameNameWindows) {
            // 通过句柄获取窗口焦点
            bringWindowToFront(window);
            if (imageRecognitionService.findImage(roomNumberLabelImagePath)) {
                // 该窗口是准备房间窗口
                return true;
            }
        }
        log.warn("未找到准备房间窗口");
        return false;
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
     * 根据窗口标题查找并返回所有同名的窗口
     * @param windowTitle 窗口标题
     * @return 窗口句柄列表
     */
    private List<User32.HWND> findSameNameWindowByTitle(String windowTitle) {
        List<User32.HWND> foundWindows = new ArrayList<>();
        final String searchTitle = windowTitle.toLowerCase();

        User32.INSTANCE.EnumWindows((hWnd, pointer) -> {
            if (User32.INSTANCE.IsWindowVisible(hWnd)) {
                char[] buffer = new char[512];
                User32.INSTANCE.GetWindowText(hWnd, buffer, 512);
                String title = Native.toString(buffer).trim();
                if (!title.isEmpty() && title.toLowerCase().contains(searchTitle)) {
                    foundWindows.add(hWnd);
                }
            }
            return true;
        }, null);
        return foundWindows;
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

    /**
     * 获取所有打开的窗口列表
     * 1. 用于在测试类中运行, 获取所有打开的窗口列表, 方便查找所需窗口名称
     * @return 窗口标题列表
     */
    public List<WindowInfo> getAllOpenWindows() {
        List<WindowInfo> windows = new ArrayList<>();

        User32.INSTANCE.EnumWindows((hWnd, pointer) -> {
            if (User32.INSTANCE.IsWindowVisible(hWnd)) {
                char[] buffer = new char[512];
                User32.INSTANCE.GetWindowText(hWnd, buffer, 512);
                String title = Native.toString(buffer).trim();
                if (!title.isEmpty()) {
                    IntByReference pidRef = new IntByReference();
                    User32.INSTANCE.GetWindowThreadProcessId(hWnd, pidRef);
                    int processId = pidRef.getValue();

                    String processName = getProcessNameById(processId);

                    windows.add(new WindowInfo(title, processId, hWnd, processName));
                }
            }
            return true;
        }, null);

        return windows;
    }

    /**
     * 根据进程 ID 获取进程名
     * @param processId 进程 ID
     * @return 进程名
     */
    private String getProcessNameById(int processId) {
        WinNT.HANDLE hProcess = Kernel32.INSTANCE.OpenProcess(
                PROCESS_QUERY_INFORMATION | PROCESS_VM_READ,
                false,
                processId
        );

        if (hProcess != null) {
            try {
                char[] processNameBuffer = new char[512];
                if (Kernel32.INSTANCE.QueryFullProcessImageName(hProcess, 0, processNameBuffer, new IntByReference(processNameBuffer.length))) {
                    return Native.toString(processNameBuffer);
                }
            } finally {
                Kernel32.INSTANCE.CloseHandle(hProcess);
            }
        }
        return "Unknown";
    }


    /**
     * 固定窗口的位置和大小
     * @param hWnd 窗口句柄
     * @param windowType 窗口类型 (platform, modifier, game)
     */
    private void fixWindowPosition(User32.HWND hWnd, String windowType) {
        if (!windowConfig.getEnablePositionFix()) {
            log.debug("窗口位置固定功能未启用");
            return;
        }

        WindowConfig.WindowPositionConfig positionConfig = getWindowPositionConfig(windowType);
        if (positionConfig == null) {
            log.warn("未找到{}窗口的位置配置", windowType);
            return;
        }

        log.info("正在固定{}窗口位置：x={}, y={}, width={}, height={}",
                windowType,
                positionConfig.getX(),
                positionConfig.getY(),
                positionConfig.getWidth(),
                positionConfig.getHeight());

        boolean success = windowPositionManager.setWindowPosition(
                hWnd,
                positionConfig.getX(),
                positionConfig.getY(),
                positionConfig.getWidth(),
                positionConfig.getHeight()
        );

        if (success) {
            log.info("{}窗口位置固定成功", windowType);
        } else {
            log.error("{}窗口位置固定失败", windowType);
        }
    }

    /**
     * 根据窗口类型获取位置配置
     * @param windowType 窗口类型 (platform, modifier, game)
     * @return 窗口位置配置
     */
    private WindowConfig.WindowPositionConfig getWindowPositionConfig(String windowType) {
        return switch (windowType.toLowerCase()) {
            case "platform" -> windowConfig.getPlatform();
            case "modifier" -> windowConfig.getModifier();
            case "game" -> windowConfig.getGame();
            default -> null;
        };
    }
}
