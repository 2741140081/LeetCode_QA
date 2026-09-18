package com.marks.leetcode.array_medium;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_4045 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/18 16:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_4045 {

    /**
     * @Description:
     * 给你一个 严格递增 的整数数组 position，其中 position[i] 是第 i 个机器人（下标从 0 开始）在时间 t = 0 时的初始位置。
     * 另给你一个整数数组 speed，其中 speed[i] 是第 i 个机器人的恒定速度（单位：单位/秒），以及一个整数 distance。
     * 时间是连续的，以秒为单位。速度为 v 的机器人或机器人组在任意 t 秒的时间间隔内向右移动 v * t 个单位。
     * 每当两个机器人或组之间的距离至多为 distance 时，它们就会合并成一个机器人组。
     * 如果多个机器人或机器人组在同一时间满足合并条件，则所有合并 同时 发生。具体而言，任何相邻位置相差至多为 distance 的相连机器人或组都会合并为一个机器人组。
     * 合并后，生成的机器人组将继承该组中 最右侧机器人 的当前位置和速度。一旦合并，机器人将永不分离。
     * 返回在所有可能的合并发生后剩余的组数。
     * 如果数组中的每个元素都严格大于其前一个元素（如果存在），则该数组是 严格递增 的。
     * @param: position
     * @param: speed
     * @param: distance
     * @return int
     * @author marks
     * @CreateDate: 2026/09/18 16:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countGroups(int[] position, int[] speed, int distance) {
        int result;
        result = method_01(position, speed,distance);
        return result;
    }

    /**
     * @Description:
     * 1. 由于都是向右移动, 采用倒序遍历, 只需要记录向右移动过程中的最小 position[i] 和最小的速度 speed[i]
     * AC: 2ms/97.72MB
     * @param: position
     * @param: speed
     * @param: distance
     * @return int
     * @author marks
     * @CreateDate: 2026/09/18 16:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] position, int[] speed, int distance) {
        int n = position.length;
        int ans = 1;
        int minPos = position[n - 1];
        int minSpeed = speed[n - 1];
        for (int i = n - 1; i >= 0; i--) {
            // 先判断 position
            if (minPos - position[i] > distance && minSpeed >= speed[i]) {
                ans++;
                minSpeed = speed[i];
            }
            minPos = position[i];
        }
        return ans;
    }

}
