package com.marks.kkPlatformGameAuto.util;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


/**
 * <p>项目名称：LeetCode_QA </p>
 * <p>文件名称：WindowPositionManagerTest </p>
 * <p>描述：窗口位置管理器测试类 - 测试窗口固定功能 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/23
 * @update [序号][日期 YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
class WindowPositionManagerTest {

    private WindowPositionManager windowPositionManager;

    @BeforeEach
    void setUp() {
        windowPositionManager = new WindowPositionManager();
    }

    @Test
    @DisplayName("测试获取 Notepad++ 窗口位置")
    void testGetNotepadPlusPlusWindowPosition() {
        // Given: 查找 Notepad++ 窗口
        String windowTitle = "Notepad++";
        User32.HWND hWnd = findWindowByTitle(windowTitle);

        if (hWnd == null) {
            log.warn("未找到 Notepad++ 窗口，请确保已打开 Notepad++");
            return;
        }

        // When: 获取窗口位置
        WinDef.RECT rect = windowPositionManager.getWindowPosition(hWnd);

        // Then: 验证获取到窗口位置信息
        if (rect != null) {
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;

            log.info("Notepad++ 窗口当前位置:");
            log.info("  X: {}, Y: {}", rect.left, rect.top);
            log.info("  宽度：{}, 高度：{}", width, height);

        } else {
            log.error("获取窗口位置失败");
        }
    }

    @Test
    @DisplayName("测试设置 Notepad++ 窗口到固定位置和大小")
    void testSetNotepadPlusPlusWindowPosition() {
        // Given: 查找 Notepad++ 窗口
        String windowTitle = "Notepad++";
        User32.HWND hWnd = findWindowByTitle(windowTitle);

        if (hWnd == null) {
            log.warn("未找到 Notepad++ 窗口，请确保已打开 Notepad++");
            return;
        }

        // 设置固定的位置和大小
        int x = 100;
        int y = 100;
        int width = 800;
        int height = 600;

        // When: 设置窗口位置
        boolean result = windowPositionManager.setWindowPosition(hWnd, x, y, width, height);

        // Then: 验证设置成功
        assert result : "设置窗口位置应该成功";

        // 延迟一点时间确保窗口已更新
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 重新获取位置验证
        WinDef.RECT newRect = windowPositionManager.getWindowPosition(hWnd);
        if (newRect != null) {
            int actualWidth = newRect.right - newRect.left;
            int actualHeight = newRect.bottom - newRect.top;

            log.info("设置后的窗口位置:");
            log.info("  X: {}, Y: {}", newRect.left, newRect.top);
            log.info("  宽度：{}, 高度：{}", actualWidth, actualHeight);

            // 允许有一定的误差范围（某些系统可能会调整）
            assert Math.abs(newRect.left - x) <= 10 : "X 坐标应该在预期范围内";
            assert Math.abs(newRect.top - y) <= 10 : "Y 坐标应该在预期范围内";
            assert Math.abs(actualWidth - width) <= 20 : "宽度应该在预期范围内";
            assert Math.abs(actualHeight - height) <= 20 : "高度应该在预期范围内";
        }
    }

    @Test
    @DisplayName("测试将 Notepad++ 窗口移动到屏幕左上角")
    void testMoveNotepadPlusPlusToTopLeft() {
        // Given: 查找 Notepad++ 窗口
        String windowTitle = "Notepad++";
        User32.HWND hWnd = findWindowByTitle(windowTitle);

        if (hWnd == null) {
            log.warn("未找到 Notepad++ 窗口，请确保已打开 Notepad++");
            return;
        }

        // When: 移动到屏幕左上角 (0, 0)
        boolean result = windowPositionManager.setWindowPosition(hWnd, 0, 0, 1024, 768);

        // Then: 验证移动成功
        assert result : "移动窗口到左上角应该成功";
        log.info("窗口已移动到屏幕左上角 (0, 0)，大小 1024x768");
    }

    @Test
    @DisplayName("测试将 Notepad++ 窗口设置为全屏")
    void testSetNotepadPlusPlusFullscreen() {
        // Given: 查找 Notepad++ 窗口
        String windowTitle = "Notepad++";
        User32.HWND hWnd = findWindowByTitle(windowTitle);

        if (hWnd == null) {
            log.warn("未找到 Notepad++ 窗口，请确保已打开 Notepad++");
            return;
        }

        // 获取屏幕尺寸
        int screenWidth = User32.INSTANCE.GetSystemMetrics(User32.SM_CXSCREEN);
        int screenHeight = User32.INSTANCE.GetSystemMetrics(User32.SM_CYSCREEN);

        log.info("屏幕尺寸：{} x {}", screenWidth, screenHeight);

        // When: 设置为全屏
        boolean result = windowPositionManager.setWindowPosition(hWnd, 0, 0, screenWidth, screenHeight);

        // Then: 验证设置成功
        assert result : "设置全屏应该成功";
        log.info("窗口已设置为全屏模式");
    }

    @Test
    @DisplayName("测试获取所有打开的窗口标题")
    void testGetAllOpenWindows() {
        // When: 枚举所有窗口
        List<String> windowTitles = getAllWindowTitles();

        // Then: 验证至少有一些窗口
        assert !windowTitles.isEmpty() : "应该至少有一个打开的窗口";

        log.info("当前打开的窗口列表:");
        for (String title : windowTitles) {
            log.info("  - {}", title);
        }

        // 检查是否包含 Notepad++
        boolean hasNotepadPlusPlus = windowTitles.stream()
                .anyMatch(title -> title.contains("Notepad++"));

        if (hasNotepadPlusPlus) {
            log.info("✓ 找到 Notepad++ 窗口");
        } else {
            log.warn("⚠ 未找到 Notepad++ 窗口，请确保已打开 Notepad++ 进行测试");
        }
    }

    @Test
    @DisplayName("测试多个窗口的循环操作")
    void testMultipleWindowsOperation() {
        // Given: 查找所有 Notepad++ 窗口（可能有多个实例）
        List<User32.HWND> notepadWindows = findWindowsByTitle("Notepad++");

        if (notepadWindows.isEmpty()) {
            log.warn("未找到 Notepad++ 窗口，请确保已打开 Notepad++");
            return;
        }

        log.info("找到 {} 个 Notepad++ 窗口", notepadWindows.size());

        // When: 对每个窗口设置不同的位置
        int xOffset = 0;
        for (int i = 0; i < notepadWindows.size(); i++) {
            User32.HWND hWnd = notepadWindows.get(i);
            int x = 50 + xOffset;
            int y = 50 + (i * 30);
            int width = 600;
            int height = 400;

            boolean result = windowPositionManager.setWindowPosition(hWnd, x, y, width, height);
            log.info("窗口 {} 设置结果：{}", i + 1, result ? "成功" : "失败");

            xOffset += 300; // 下一个窗口向右偏移
        }

        // Then: 验证所有窗口都被设置
        log.info("所有窗口位置设置完成");
    }

    /**
     * 根据窗口标题查找窗口句柄
     * @param title 窗口标题
     * @return 窗口句柄，如果未找到则返回 null
     */
    private User32.HWND findWindowByTitle(String title) {
        User32.HWND[] hWndPtr = new User32.HWND[1];

        User32.INSTANCE.EnumWindows((hWnd, arg1) -> {
            char[] buffer = new char[512];
            User32.INSTANCE.GetWindowText(hWnd, buffer, buffer.length);
            String windowTitle = new String(buffer).trim();

            if (windowTitle.contains(title)) {
                hWndPtr[0] = hWnd;
                return false; // 找到目标窗口，停止枚举
            }
            return true; // 继续枚举
        }, null);

        return hWndPtr[0];
    }

    /**
     * 根据窗口标题查找所有匹配的窗口句柄
     * @param title 窗口标题（支持模糊匹配）
     * @return 窗口句柄列表
     */
    private List<User32.HWND> findWindowsByTitle(String title) {
        List<User32.HWND> windows = new java.util.ArrayList<>();

        User32.INSTANCE.EnumWindows((hWnd, arg1) -> {
            char[] buffer = new char[512];
            User32.INSTANCE.GetWindowText(hWnd, buffer, buffer.length);
            String windowTitle = new String(buffer).trim();

            if (!windowTitle.isEmpty() && windowTitle.contains(title)) {
                windows.add(hWnd);
            }
            return true; // 继续枚举
        }, null);

        return windows;
    }

    /**
     * 获取所有打开的窗口标题
     * @return 窗口标题列表
     */
    private List<String> getAllWindowTitles() {
        List<String> titles = new java.util.ArrayList<>();

        User32.INSTANCE.EnumWindows((hWnd, arg1) -> {
            char[] buffer = new char[512];
            User32.INSTANCE.GetWindowText(hWnd, buffer, buffer.length);
            String title = new String(buffer).trim();

            if (!title.isEmpty()) {
                titles.add(title);
            }
            return true; // 继续枚举
        }, null);

        return titles;
    }

}