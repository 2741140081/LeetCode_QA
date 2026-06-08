package com.marks.leetcode.hash_table;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_03 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/4 16:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_03 {

    /**
     * @Description:
     * 力扣团队买了一个可编程机器人，机器人初始位置在原点(0, 0)。
     * 小伙伴事先给机器人输入一串指令command，机器人就会无限循环这条指令的步骤进行移动。指令有两种：
     * U: 向y轴正方向移动一格
     * R: 向x轴正方向移动一格。
     * 不幸的是，在 xy 平面上还有一些障碍物，他们的坐标用obstacles表示。机器人一旦碰到障碍物就会被损毁。
     *
     * 给定终点坐标(x, y)，返回机器人能否完好地到达终点。如果能，返回true；否则返回false。
     *
     * tips:
     * 2 <= command的长度 <= 1000
     * command由U，R构成，且至少有一个U，至少有一个R
     * 0 <= x <= 1e9, 0 <= y <= 1e9
     * 0 <= obstacles的长度 <= 1000
     * obstacles[i]不为原点或者终点
     * @param: command
     * @param: obstacles
     * @param: x
     * @param: y
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/04 16:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean robot(String command, int[][] obstacles, int x, int y) {
        boolean result;
        result = method_01(command, obstacles, x, y);
        return result;
    }

    /**
     * @Description:
     * 1. 统计指令中 U, R 的数量
     * 2. 需要分析各种情况
     * 3. 需要分析障碍物超过终点, 需要分析 index 越界
     * AC: 0ms/42.57MB
     * @param: command
     * @param: obstacles
     * @param: x
     * @param: y
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/04 16:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String command, int[][] obstacles, int x, int y) {
        int n = command.length();
        int[] uCount = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            uCount[i] = uCount[i - 1] + (command.charAt(i - 1) == 'U' ? 1 : 0);
        }
        // 忽略障碍物, 判断是否能到达终点
        if (!checkEqual(uCount, x, y)) {
            return false;
        }
        // 对于每一个障碍物, 判断 robot 行进过程中是否会到达该障碍物
        for (int[] obstacle : obstacles) {
            int obstacleX = obstacle[0];
            int obstacleY = obstacle[1];
            // 判断 obstacle 是否 超过 终点
            if (obstacleX > x || obstacleY > y) {
                continue;
            }
            if (checkEqual(uCount, obstacleX, obstacleY)) {
                return false;
            }

        }

        return true;
    }

    private boolean checkEqual(int[] uCount, int x, int y) {
        int n = uCount.length - 1;
        int sumY = uCount[n];
        int sumX = n - sumY;
        int cntX = x / sumX;
        int cntY = y / sumY;
        int minCnt = Math.min(cntX, cntY);
        int remainX = x - sumX * minCnt;
        int remainY = y - sumY * minCnt;
        int index = remainX + remainY;
        // 判断 index 是否越界
        if (index < 0 || index > n) {
            return false;
        }
        int targetX = sumX * minCnt + (index - uCount[index]);
        int targetY = sumY * minCnt + uCount[index];

        return targetX == x && targetY == y;
    }

}
