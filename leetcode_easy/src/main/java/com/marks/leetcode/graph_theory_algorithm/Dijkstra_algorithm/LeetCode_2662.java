package com.marks.leetcode.graph_theory_algorithm.Dijkstra_algorithm;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/3 17:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2662 {
    /**
     * @Description: [
     * 给你一个数组 start ，其中 start = [startX, startY] 表示你的初始位置位于二维空间上的 (startX, startY) 。
     * 另给你一个数组 target ，其中 target = [targetX, targetY] 表示你的目标位置 (targetX, targetY) 。
     *
     * 从位置 (x1, y1) 到空间中任一其他位置 (x2, y2) 的 代价 是 |x2 - x1| + |y2 - y1| 。
     *
     * 给你一个二维数组 specialRoads ，表示空间中存在的一些 特殊路径。其中 specialRoads[i] = [x1i, y1i, x2i, y2i, costi] 表示第 i 条特殊路径可以从 (x1i, y1i) 到 (x2i, y2i) ，但成本等于 costi 。你可以使用每条特殊路径任意次数。
     *
     * 返回从 (startX, startY) 到 (targetX, targetY) 所需的 最小 代价。
     *
     * tips:
     * start.length == target.length == 2
     * 1 <= startX <= targetX <= 10^5
     * 1 <= startY <= targetY <= 10^5
     * 1 <= specialRoads.length <= 200
     * specialRoads[i].length == 5
     * startX <= x1i, x2i <= targetX
     * startY <= y1i, y2i <= targetY
     * 1 <= costi <= 10^5
     * ]
     * @param start
     * @param target
     * @param specialRoads
     * @return int
     * @author marks
     * @CreateDate: 2025/1/3 17:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumCost(int[] start, int[] target, int[][] specialRoads) {
        int result;
        result = method_01(start, target, specialRoads);
        return result;
    }

    /**
     * @Description: [
     * 想到一个点
     * 1. 我们可以将 "x_y" 合并为一个key, 相当于节点 0, [sx_sy, x_y, ....xn_yn , tx_ty]
     * ]
     * @param start
     * @param target
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/3 17:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] start, int[] target, int[][] s) {
        int len = s.length;
        // d[i]表示从start出发，到中间这点的最短路
        int[] d = new int[len];
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(a -> d[a]));
        for(int i = 0; i < len; i++){
            d[i] = Math.min(Math.abs(start[0] - s[i][2]) + Math.abs(start[1] - s[i][3]),
                    Math.abs(start[0] - s[i][0]) + Math.abs(start[1] - s[i][1]) + s[i][4]);
            pq.offer(i);
        }
        while(!pq.isEmpty()){
            int c = pq.poll();
            for(int i = 0; i < len; i++){
                int mi = Math.min( Math.abs(s[c][2] - s[i][0]) + Math.abs(s[c][3] - s[i][1]) + s[i][4],
                        Math.abs(s[c][2] - s[i][2]) + Math.abs(s[c][3] - s[i][3]))
                        + d[c];

                if(mi < d[i]){
                    d[i] = mi;
                    pq.offer(i);
                }
            }
        }
        int ret = Math.abs(target[0] - start[0]) + Math.abs(target[1] - start[1]);
        for(int i = 0; i < len; i++){
            ret = Math.min(ret, d[i] + Math.abs(target[0] - s[i][2]) + Math.abs(target[1] - s[i][3]));
        }
        return ret;
    }
}
