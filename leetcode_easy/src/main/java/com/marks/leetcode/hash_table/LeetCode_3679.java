package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3679 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/8 10:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3679 {

    /**
     * @Description:
     * 给你两个整数 w 和 m，以及一个整数数组 arrivals，其中 arrivals[i] 表示第 i 天到达的物品类型（天数从 1 开始编号）。
     * 物品的管理遵循以下规则：
     * 每个到达的物品可以被 保留 或 丢弃 ，物品只能在到达当天被丢弃。
     * 对于每一天 i，考虑天数范围为 [max(1, i - w + 1), i]（也就是直到第 i 天为止最近的 w 天）：
     * 对于 任何 这样的时间窗口，在被保留的到达物品中，每种类型最多只能出现 m 次。
     * 如果在第 i 天保留该到达物品会导致其类型在该窗口中出现次数 超过 m 次，那么该物品必须被丢弃。
     * 返回为满足每个 w 天的窗口中每种类型最多出现 m 次，最少 需要丢弃的物品数量。
     *
     * tips:
     * 1 <= arrivals.length <= 10^5
     * 1 <= arrivals[i] <= 10^5
     * 1 <= w <= arrivals.length
     * 1 <= m <= w
     * @param: arrivals
     * @param: w
     * @param: m
     * @return int
     * @author marks
     * @CreateDate: 2026/06/08 10:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minArrivalsToDiscard(int[] arrivals, int w, int m) {
        int result;
        result = method_01(arrivals, w, m);
        return result;
    }

    /**
     * @Description:
     * 1. 既然是一个时间窗口, 使用滑动窗口来处理, 使用 map 来记录窗口内每种物品出现的次数
     * 2. 出错, 原因是由于重复丢弃, 即在使用 right 时进行判断 arrival[right] > m, 此时已经将 right 进行丢弃,
     * 但是在 left = right 的时候, 由于 之前已经丢弃 preRight, 此时会再次丢弃 left, 导致 index = right 会被丢弃 2 次
     * AC: 38ms/114.18MB
     * @param: arrivals
     * @param: w
     * @param: m
     * @return int
     * @author marks
     * @CreateDate: 2026/06/08 10:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arrivals, int w, int m) {
        int n = arrivals.length;
        Map<Integer, Integer> map = new HashMap<>();
        int ans = 0; // 丢弃物品数量
        // 创建初始窗口
        for (int i = 0; i < w; i++) {
            if (map.getOrDefault(arrivals[i], 0) == m) {
                arrivals[i] = -1; // 修改arrivals[i] 的状态, 方便判别使用已经丢弃
                ans++;
            } else {
                map.merge(arrivals[i], 1, Integer::sum);
            }
        }
        int left = 0;
        for (int right = w; right < n; right++) {
            // delete left
            if (arrivals[left] != -1) {
                // 防止重复丢弃物品
                map.merge(arrivals[left], -1, Integer::sum);
                if (map.get(arrivals[left]) == 0) {
                    map.remove(arrivals[left]);
                }
            }

            if (map.getOrDefault(arrivals[right], 0) == m) {
                arrivals[right] = -1;
                ans++;
            } else {
                map.merge(arrivals[right], 1, Integer::sum);
            }
            left++;
        }


        return ans;
    }

}
