package com.marks.leetcode.graph_theory_algorithm.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/23 14:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2101 {
    private int[] pre;
    /**
     * @Description: [
     * <p>给你一个炸弹列表。一个炸弹的 爆炸范围 定义为以炸弹为圆心的一个圆。
     * <p>炸弹用一个下标从 0 开始的二维整数数组 bombs 表示，其中 bombs[i] = [xi, yi, ri] 。xi 和 yi 表示第 i 个炸弹的 X 和 Y 坐标，ri 表示爆炸范围的 半径 。
     * <p>你需要选择引爆 一个 炸弹。当这个炸弹被引爆时，所有 在它爆炸范围内的炸弹都会被引爆，这些炸弹会进一步将它们爆炸范围内的其他炸弹引爆。
     * <p>给你数组 bombs ，请你返回在引爆 一个 炸弹的前提下，最多 能引爆的炸弹数目。
     * <p>tips:
     * 1 <= bombs.length <= 100
     * bombs[i].length == 3
     * 1 <= xi, yi, ri <= 10^5
     * ]
     * @param bombs
     * @return int
     * @author marks
     * @CreateDate: 2024/12/23 14:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumDetonation(int[][] bombs) {
        int result;
        result = method_01(bombs);
        return result;
    }

    /**
     * @Description: [
     * 一个易错点, 计算过程中会发生int类型溢出, 判断半径时, 使用long来替代int
     * AC:96ms/44.19MB
     * DFS中加一个剪枝
     * if (pre[j] == 0) {
     *     count += DFSMaxArea(j, list);
     * }
     * AC:84ms/44.27MB
     * ]
     * @param bombs
     * @return int
     * @author marks
     * @CreateDate: 2024/12/23 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] bombs) {
        int n = bombs.length;
        List<Integer>[] list = new ArrayList[n];

        // 将炸弹转换为二维数组, 相当于无向图
        for (int i = 0; i < n; i++) {
            list[i] = new ArrayList<>();
            long x = bombs[i][0], y = bombs[i][1], r = bombs[i][2];
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                int curr_x = bombs[j][0], curr_y = bombs[j][1];

                // 欧几里得距离公式 Math.sqrt((x2 - x1)^2 + (y2 - y1)^2)
                // 发生错误的案例[[1,1,100000],[100000,100000,1]], 范围溢出, 使用 long 替代int
                long distance = (curr_x - x) * (curr_x - x) + (curr_y - y) * (curr_y - y);
                if (distance <= r * r) {
                    list[i].add(j);
                }
            }
        }
        int ans = 0;
        // 用DFS求最大炸弹数目
        for (int i = 0; i < n; i++) {
            pre = new int[n];
            int sum = DFSMaxArea(i, list);
            ans = Math.max(ans, sum);
        }

        return ans;
    }

    private int DFSMaxArea(int i, List<Integer>[] list) {
        if (pre[i] == 1) {
            return 0;
        }
        pre[i] = 1;
        int count = 1;
        for (int j : list[i]) {
            if (pre[j] == 0) {
                count += DFSMaxArea(j, list);
            }
        }
        return count;
    }
}
