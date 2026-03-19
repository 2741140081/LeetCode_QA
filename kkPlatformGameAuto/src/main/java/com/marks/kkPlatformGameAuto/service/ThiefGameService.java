package com.marks.kkPlatformGameAuto.service;

import com.marks.kkPlatformGameAuto.entity.ThiefGameEntity;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ThiefGameService </p>
 * <p>描述: 小偷游戏服务接口 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/19 10:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface ThiefGameService {
    /**
     * 执行小偷游戏完整流程
     * @param config 游戏配置参数
     * @return true 如果执行成功，否则 false
     */
    boolean executeThiefGameFlow(ThiefGameEntity config);

    /**
     * 根据游戏难度选择难度和挑战
     * @param difficulty 难度等级
     * @param gameMode 游戏模式
     * @param challenges 挑战列表
     * @return true 如果选择成功，否则 false
     */
    boolean selectDifficultyAndChallenges(int difficulty, int gameMode, List<Integer> challenges);

    /**
     * 挑战存档 BOSS
     * @param archiverBuildings 存档建筑编号列表
     * @return true 如果挑战成功，否则 false
     */
    boolean challengeArchiverBosses(List<Integer> archiverBuildings);
}
