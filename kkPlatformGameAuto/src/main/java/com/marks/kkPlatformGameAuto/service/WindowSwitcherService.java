package com.marks.kkPlatformGameAuto.service;

import com.marks.kkPlatformGameAuto.entity.WindowInfo;
import com.sun.jna.platform.win32.User32;

import java.util.List;

/**
 * <p>项目名称：LeetCode_QA </p>
 * <p>文件名称：WindowSwitcherService </p>
 * <p>描述：窗口切换服务接口 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17
 * @update [序号][日期 YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface WindowSwitcherService {

    /**
     * 切换到平台窗口
     * @return true 如果切换成功，否则 false
     */
    boolean switchToPlatform();

    /**
     * 切换到准备房间窗口
     * @return true 如果切换成功，否则 false
     */
    boolean switchToReadyRoom();

    /**
     * 切换到修改器窗口
     * @return true 如果切换成功，否则 false
     */
    boolean switchToModifier();

    /**
     * 切换到游戏主体窗口
     * @return true 如果切换成功，否则 false
     */
    boolean switchToGame();
}
