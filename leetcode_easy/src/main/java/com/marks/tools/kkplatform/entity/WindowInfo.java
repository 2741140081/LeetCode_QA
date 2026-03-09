package com.marks.tools.kkplatform.entity;

import com.sun.jna.platform.win32.WinDef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: WindowInfo </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/9 10:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
@AllArgsConstructor
@ToString
public class WindowInfo {
    private String title;
    private int processId;
    private WinDef.HWND hwnd;
    private String processName;
}
