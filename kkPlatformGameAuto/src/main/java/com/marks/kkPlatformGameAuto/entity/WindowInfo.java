package com.marks.kkPlatformGameAuto.entity;

import com.sun.jna.platform.win32.User32;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>项目名称：LeetCode_QA </p>
 * <p>文件名称：WindowInfo </p>
 * <p>描述：窗口信息实体类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17
 * @update [序号][日期 YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WindowInfo {

    /**
     * 窗口标题
     */
    private String title;

    /**
     * 进程 ID
     */
    private int processId;

    /**
     * 窗口句柄
     */
    private User32.HWND hWnd;

    /**
     * 进程名称
     */
    private String processName;
}
