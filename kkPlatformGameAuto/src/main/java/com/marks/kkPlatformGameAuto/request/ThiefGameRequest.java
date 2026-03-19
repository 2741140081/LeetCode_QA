package com.marks.kkPlatformGameAuto.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 小偷游戏请求参数实体类
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/19
 */
@Data
public class ThiefGameRequest {
    /**
     * 游戏名称
     * 例如："小偷偷金"
     */
    @NotNull(message = "游戏名称不能为空")
    private String gameName;

    /**
     * 游戏难度
     * 范围：1-30 (或其他合理范围)
     */
    @NotNull(message = "游戏难度不能为空")
    @Min(value = 1, message = "游戏难度最小为 1")
    @Max(value = 100, message = "游戏难度最大为 100")
    private Integer difficulty;

    /**
     * 游戏模式
     * 前端单选框选项：[模式 0, 模式 1, 模式 2, 模式 3, 模式 4]
     * 值范围：0-4
     */
    @NotNull(message = "游戏模式不能为空")
    @Min(value = 0, message = "模式值最小为 0")
    @Max(value = 4, message = "模式值最大为 4")
    private Integer gameMode;

    /**
     * 开启的挑战类型
     * 前端多选框选项：[挑战 0, 挑战 1, 挑战 2, 挑战 3, 挑战 4, 挑战 5, 挑战 6]
     * 值范围：0-6，其中 0 表示不开启任何挑战
     * 如果包含 0，则忽略其他值
     */
    @NotEmpty(message = "挑战类型列表不能为空")
    private List<Integer> challenges;

    /**
     * 选择要挑战的存档建筑
     * 前端多选框选项：[存档建筑 0, 存档建筑 1, 存档建筑 2, 存档建筑 3, 存档建筑 4, 存档建筑 5, 存档建筑 6]
     * 值范围：0-6，其中 0 表示不挑战任何存档建筑
     * 如果包含 0，则忽略其他值
     */
    @NotEmpty(message = "存档建筑列表不能为空")
    private List<Integer> archiverBuildings;

    /**
     * 执行游戏次数
     * 必须大于 0
     */
    @NotNull(message = "执行次数不能为空")
    @Min(value = 1, message = "执行次数最小为 1")
    private Integer runCount;

    /**
     * 运行完成后是否关闭计算机
     * true: 关机，false: 不关机
     */
    @NotNull(message = "关机设置不能为空")
    private Boolean shutdownAfterRun;
}
