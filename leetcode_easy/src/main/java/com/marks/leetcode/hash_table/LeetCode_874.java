package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_874 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/20 17:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_874 {

    /**
     * @Description:
     * 机器人在一个无限大小的 XY 网格平面上行走，从点 (0, 0) 处开始出发，面向北方。该机器人可以接收以下三种类型的命令 commands ：
     * -2 ：向左转 90 度
     * -1 ：向右转 90 度
     * 1 <= x <= 9 ：向前移动 x 个单位长度
     * 在网格上有一些格子被视为障碍物 obstacles 。第 i 个障碍物位于网格点  obstacles[i] = (xi, yi) 。
     * 机器人无法走到障碍物上，它将会停留在障碍物的前一个网格方块上，并继续执行下一个命令。
     * 返回机器人距离原点的 最大欧式距离 的 平方 。（即，如果距离为 5 ，则返回 25 ）
     * @param: commands
     * @param: obstacles
     * @return int
     * @author marks
     * @CreateDate: 2026/05/20 17:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int robotSim(int[] commands, int[][] obstacles) {
        int result;
        result = method_01(commands, obstacles);
        return result;
    }

    /**
     * @Description:
     * 1. 模拟, 并且将障碍物存入 map 集合中
     * 2. 理解错题目了, 以为返回的是最终结果, 但是实际需要的是过程中的最大距离
     * AC: 16ms/54.1MB
     * @param: commands
     * @param: obstacles
     * @return int
     * @author marks
     * @CreateDate: 2026/05/20 17:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] commands, int[][] obstacles) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int[] obstacle : obstacles) {
            // 使用lambda表达式
            map.computeIfAbsent(obstacle[0], k -> new HashSet<>()).add(obstacle[1]);
        }
        // 开始执行模拟, 构建dirs, 方向是 上 右 下 左
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        // 初始位置和index
        int x = 0, y = 0, index = 0, len = 4;
        int ans = 0; // 最大距离
        // -2 向左旋转, 即 (index - 1 + len) % len, -1 向右旋转, 即 (index + 1) % len
        for (int command : commands) {
            if (command == -2) {
                index = (index - 1 + len) % len;
            } else if (command == -1) {
                index = (index + 1) % len;
            } else {
                // 处理移动, 即 command > 0
                while (command-- > 0) {
                    int nx = x + dirs[index][0];
                    int ny = y + dirs[index][1];
                    // 判断 [nx, ny] 是否是障碍物
                    if (map.containsKey(nx) && map.get(nx).contains(ny)) {
                        break;
                    }
                    // 如果[nx, ny] 不是障碍物
                    x = nx;
                    y = ny;
                    ans = Math.max(ans, x * x + y * y);
                }

            }
        }

        return ans;
    }

}
