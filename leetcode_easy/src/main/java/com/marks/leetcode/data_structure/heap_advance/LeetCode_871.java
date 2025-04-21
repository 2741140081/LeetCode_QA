package com.marks.leetcode.data_structure.heap_advance;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/7 11:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_871 {
    /**
     * @Description:
     * 汽车从起点出发驶向目的地，该目的地位于出发位置东面 target 英里处。
     *
     * 沿途有加油站，用数组 stations 表示。其中 stations[i] = [positioni, fueli] 表示第 i 个加油站位于出发位置东面 positioni 英里处，并且有 fueli 升汽油。
     *
     * 假设汽车油箱的容量是无限的，其中最初有 startFuel 升燃料。它每行驶 1 英里就会用掉 1 升汽油。
     * 当汽车到达加油站时，它可能停下来加油，将所有汽油从加油站转移到汽车中。
     *
     * 为了到达目的地，汽车所必要的最低加油次数是多少？如果无法到达目的地，则返回 -1 。
     *
     * 注意：如果汽车到达加油站时剩余燃料为 0，它仍然可以在那里加油。如果汽车到达目的地时剩余燃料为 0，仍然认为它已经到达目的地。
     *
     * tips:
     * 1 <= target, startFuel <= 10^9
     * 0 <= stations.length <= 500
     * 1 <= positioni < positioni+1 < target
     * 1 <= fueli < 10^9
     * @param target 
     * @param startFuel 
     * @param stations 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/7 11:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        int result;
        result = method_01(target, startFuel, stations);
        return result;
    }

    /**
     * @Description:
     * 1. position[0] 是升序排序的, 这个不需要处理
     * 2. startFuel 是已经确定, 我们首先要确定初始能到达那些加油站
     * 3. 所有经过的加油站全部存储的 queue(降序排序),
     *
     * 当前所加的油无法到达终点或者到达下一个加油站, 返回 -1
     *
     * 看看数据范围, 是否会发生溢出
     *
     * AC: 2ms/43.96MB
     * @param target
     * @param startFuel
     * @param stations
     * @return int
     * @author marks
     * @CreateDate: 2025/3/7 11:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int target, int startFuel, int[][] stations) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int ans = 0;
        long sumFuel = startFuel;
        for (int i = 0; i < stations.length; i++) {
            int position = stations[i][0];
            int fuel = stations[i][1];

            if (sumFuel >= position) {
                queue.offer(fuel);
            } else {
                // 需要加油, 当油加到能到达下一个加油站 或者 能到达目的地
                while (!queue.isEmpty() && sumFuel <= Math.min(position, target)) {
                    int curr_fuel = queue.poll();
                    sumFuel += curr_fuel;
                    ans++;
                }
                // 判断能否到达下一个加油站
                if (sumFuel >= position) {
                    queue.offer(fuel);
                } else {
                    break;
                }
            }
        }
        // 可以到达所有的加油站, 再去判断能否到达目的地
        while (!queue.isEmpty() && sumFuel < target) {
            sumFuel += queue.poll();
            ans++;
        }
        return sumFuel >= target ? ans : -1;
    }
}
