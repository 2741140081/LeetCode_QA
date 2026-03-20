package com.marks.kkPlatformGameAuto.entity;

import com.marks.kkPlatformGameAuto.request.ThiefGameRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 小偷游戏执行参数
 * 用于 Service 层内部业务处理
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/19
 */
@Data
@Builder
public class ThiefGameEntity {
    /**
     * 游戏难度 (1-100)
     */
    private Integer difficulty;

    /**
     * 游戏模式 (0-4)
     */
    private Integer gameMode;

    /**
     * 开启的挑战类型列表
     * 例如：[1, 2, 6] 表示开启挑战 1、挑战 2、挑战 6
     * 特殊值：[0] 表示不开启任何挑战
     */
    private List<Integer> challenges;

    /**
     * 要挑战的存档建筑列表
     * 例如：[1, 3, 5] 表示挑战存档建筑 1、3、5
     * 特殊值：[0] 表示不挑战任何存档建筑
     */
    private List<Integer> archiverBuildings;

    /**
     * 从请求对象转换为配置对象
     * @param request 请求参数
     * @return 配置对象
     */
    public static ThiefGameEntity fromRequest(ThiefGameRequest request) {
        return ThiefGameEntity.builder()
                .difficulty(request.getDifficulty())
                .gameMode(request.getGameMode())
                .challenges(request.getChallenges())
                .archiverBuildings(request.getArchiverBuildings())
                .build();
    }
}
