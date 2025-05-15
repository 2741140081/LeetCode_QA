package com.marks.tools.robot;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinUser;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/15 11:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
// 需添加JNA依赖
public interface Shcore extends Library {
    Shcore INSTANCE = Native.load("shcore", Shcore.class);
    int GetScaleFactorForMonitor(WinUser.HMONITOR hMonitor, int[] scale);
}

