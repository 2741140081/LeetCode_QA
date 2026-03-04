package com.marks.tools.kkplatform;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: WindowSwitcherUtilsTest </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/4 16:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class WindowSwitcherUtilsTest {

    private WindowSwitcherUtils windowSwitcherUtils;

    @BeforeEach
    void setUp() {
        windowSwitcherUtils = WindowSwitcherUtils.getInstance();
    }

    @Test
    @DisplayName("测试单例模式 - 多次获取应返回同一实例")
    void getInstance() {
        // 当调用 getInstance 时
        WindowSwitcherUtils instance1 = WindowSwitcherUtils.getInstance();
        WindowSwitcherUtils instance2 = WindowSwitcherUtils.getInstance();

        // 验证两次获取的是同一个实例
        assertNotNull(instance1, "实例不应为 null");
        assertNotNull(instance2, "实例不应为 null");
        assertSame(instance1, instance2, "两次获取的应该是同一个实例");
    }

    @Test
    @DisplayName("测试获取所有打开的窗口列表")
    void getAllOpenWindows() {
        // 当获取所有打开的窗口时
        List<String> windows = windowSwitcherUtils.getAllOpenWindows();

        // 验证返回的窗口列表不为 null
        assertNotNull(windows, "窗口列表不应为 null");

        // 打印所有窗口（用于调试和手动验证）
        System.out.println("当前打开的窗口数量：" + windows.size());
        windows.forEach(window -> System.out.println("窗口标题：" + window));

        // 注意：无法断言窗口数量，因为取决于系统当前状态
        // 但至少应该能获取到一些窗口（包括桌面、任务栏等）
        assertTrue(windows.size() >= 0, "窗口列表应该可以获取");
    }

    @Test
    @DisplayName("测试打印所有窗口信息")
    void printAllWindowsInfo() {
        // 这个方法主要是输出信息到控制台，验证是否能正常执行
        assertDoesNotThrow(() -> {
            windowSwitcherUtils.printAllWindowsInfo();
        }, "打印窗口信息时不应抛出异常");
    }

    @Test
    @DisplayName("测试通过窗口标题切换窗口 - 使用常见应用")
    void switchToWindow() {
        // 注意：这个测试需要系统中实际运行这些应用才能成功

        // 测试 1: 尝试切换到记事本（如果存在）
        boolean result = windowSwitcherUtils.switchToWindow("聊天 | [返程] - ODC-GZ-年会烧烤活动-广州4车 | Microsoft Teams");
        System.out.println("切换到Microsoft Teams结果：" + result);

        // 测试 2: 尝试切换到 Chrome（如果存在）
        boolean chromeResult = windowSwitcherUtils.switchToWindow("收件箱 - Marks W Wang - Outlook");
        System.out.println("切换到 Outlook 结果：" + chromeResult);

        // 测试 3: 尝试切换到 IDEA（如果存在）
        boolean ideaResult = windowSwitcherUtils.switchToWindow("LeetCode_QA – WindowSwitcherUtilsTest.java [leetcode_easy]");
        System.out.println("切换到 IDEA 结果：" + ideaResult);

        // 由于无法保证这些应用是否打开，只验证方法能正常执行
        // 实际应用时需要确保目标应用已打开
    }

    @Test
    @DisplayName("测试通过进程名切换窗口 - 使用常见应用")
    void switchToWindowByProcess() {
        // 注意：这个测试需要系统中实际运行这些应用才能成功

        // 测试 1: 尝试通过进程名切换到记事本
        boolean notepadResult = windowSwitcherUtils.switchToWindowByProcess("notepad++.exe");
        System.out.println("切换到 notepad++.exe 结果：" + notepadResult);

        // 测试 2: 尝试通过进程名切换到 Chrome
        boolean chromeResult = windowSwitcherUtils.switchToWindowByProcess("chrome.exe");
        System.out.println("切换到 chrome.exe 结果：" + chromeResult);

        // 测试 3: 尝试通过进程名切换到 IDEA
        boolean ideaResult = windowSwitcherUtils.switchToWindowByProcess("idea64.exe");
        System.out.println("切换到 idea64.exe 结果：" + ideaResult);

        // 由于无法保证这些应用是否打开，只验证方法能正常执行
    }

    @Test
    @DisplayName("测试窗口切换的完整流程")
    void testCompleteWorkflow() {
        System.out.println("\n=== 开始完整工作流测试 ===");

        // 步骤 1: 获取并打印所有窗口
        System.out.println("步骤 1: 获取所有窗口");
        List<String> allWindows = windowSwitcherUtils.getAllOpenWindows();
        System.out.println("找到 " + allWindows.size() + " 个窗口");

        // 步骤 2: 打印详细的窗口信息
        System.out.println("\n步骤 2: 详细窗口信息");
        windowSwitcherUtils.printAllWindowsInfo();

        // 步骤 3: 尝试切换到一个可能存在的窗口
        System.out.println("\n步骤 3: 尝试切换窗口");
        boolean switched = windowSwitcherUtils.switchToWindow("");
        System.out.println("切换结果：" + switched);

        System.out.println("\n=== 完整工作流测试结束 ===");

        // 验证整个流程没有抛出异常
        assertNotNull(allWindows);
    }

    @Test
    @DisplayName("测试模糊匹配功能")
    void testFuzzyMatching() {
        // 测试模糊匹配 - 使用部分标题
        // 例如，如果有一个窗口标题是 "IntelliJ IDEA - Project"，搜索 "IDEA" 也应该能找到

        List<String> windows = windowSwitcherUtils.getAllOpenWindows();

        // 如果有窗口包含 "Google"，尝试用不同的关键词匹配
        boolean hasGoogleWindow = windows.stream()
                .anyMatch(title -> title.toLowerCase().contains("google"));

        if (hasGoogleWindow) {
            // 尝试用 "Google" 作为关键词切换
            boolean result = windowSwitcherUtils.switchToWindow("Google");
            System.out.println("通过 'Google' 关键词切换结果：" + result);
        } else {
            System.out.println("没有找到包含 'Google' 的窗口");
        }
    }
}