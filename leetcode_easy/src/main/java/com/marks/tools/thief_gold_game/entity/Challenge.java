package com.marks.tools.thief_gold_game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>项目名称：LeetCode_QA </p>
 * <p>文件名称：Challenge </p>
 * <p>描述：挑战信息实体类，用于优先队列管理挑战 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/14
 * @update [序号][日期 YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Challenge implements Comparable<Challenge> {
    private int priority;        // 优先级（数字越小优先级越高）
    private int challengeNumber; // 挑战编号 (1-6)
    private String imageName;    // 图片名称
    private int remainingCount;  // 剩余可执行次数

    @Override
    public int compareTo(Challenge other) {
        // 大根堆替换, 先点击挑战数字更大的, 因为竖向排布, 从下往上点击, 消除鼠标箭头的影响
        return Integer.compare(other.priority, this.priority);
    }
}
