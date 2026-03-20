package com.marks.kkPlatformGameAuto.service;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: GameCommonService </p>
 * <p>描述: 游戏主体的公共接口服务 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/19 10:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface GameCommonService {
    /**
     * 退出游戏
     */
    void exitGame();

    /**
     * 暂停游戏
     */
    void pauseGame();

    /**
     * 恢复游戏
     */
    void resumeGame();
}
