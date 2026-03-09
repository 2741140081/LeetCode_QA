package com.marks.tools.kkplatform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: SpecialHandelTest </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/9 14:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class SpecialHandelTest {

    private WindowSwitcherUtils windowSwitcherUtils;
    private ImageRecognitionAutomation automation;
    private SpecialHandel sp;

    @BeforeEach
    void setUp() throws Exception {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        windowSwitcherUtils = WindowSwitcherUtils.getInstance();
        automation = new ImageRecognitionAutomation();
        sp = new SpecialHandel();
    }
    @Test
    void findAllMatches() {
        // 切换到Excel 界面, 然后截图并且查找模板图片
        windowSwitcherUtils.switchToWindow("test.xlsx - Excel");
        // 延迟 1s
        automation.delay(1000);
        Point templateA = sp.findImage("template_A");
    }

    @Test
    void findTopLeftMatch() {
    }
}