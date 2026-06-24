package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1041 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/23 16:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1041 {

    /**
     * @Description:
     * 在无限的平面上，机器人最初位于 (0, 0) 处，面朝北方。注意:
     *
     * 北方向 是y轴的正方向。
     * 南方向 是y轴的负方向。
     * 东方向 是x轴的正方向。
     * 西方向 是x轴的负方向。
     * 机器人可以接受下列三条指令之一：
     *
     * "G"：直走 1 个单位
     * "L"：左转 90 度
     * "R"：右转 90 度
     * 机器人按顺序执行指令 instructions，并一直重复它们。
     *
     * 只有在平面中存在环使得机器人永远无法离开时，返回 true。否则，返回 false。
     *
     * tips:
     * 1 <= instructions.length <= 100
     * instructions[i] 仅包含 'G', 'L', 'R'
     * @param: instructions
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/23 16:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isRobotBounded(String instructions) {
        boolean result;
        result = method_01(instructions);
        return result;
    }

    /**
     * @Description:
     * 1. 模拟
     * 2. 如果遍历玩一次后, 如果最后的方向不是 N, 则必定成环;
     * 3. 如果返回原地, 则成环.
     * @param: instructions
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/23 16:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String instructions) {
        int[][] direc = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int direcIndex = 0;
        int x = 0, y = 0;
        int n = instructions.length();
        for (int idx = 0; idx < n; idx++) {
            char instruction = instructions.charAt(idx);
            if (instruction == 'G') {
                x += direc[direcIndex][0];
                y += direc[direcIndex][1];
            } else if (instruction == 'L') {
                direcIndex += 3;
                direcIndex %= 4;
            } else {
                direcIndex++;
                direcIndex %= 4;
            }
        }
        return direcIndex != 0 || (x == 0 && y == 0);
    }

}
