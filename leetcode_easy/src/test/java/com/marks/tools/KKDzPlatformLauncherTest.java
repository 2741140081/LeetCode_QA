package com.marks.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/16 16:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class KKDzPlatformLauncherTest {
    @Test
    void launcherPlatform() {
        CmdUtils cmdUtils = new CmdUtils();
//        cmdUtils.launchProgram("notepad++.exe");
        cmdUtils.shutDownWindows(30);
    }
}