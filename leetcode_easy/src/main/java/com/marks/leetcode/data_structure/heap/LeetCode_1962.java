package com.marks.leetcode.data_structure.heap;

import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/7 17:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1962 {
    
    /**
     * @Description:
     * 给你一个整数数组 piles ，数组 下标从 0 开始 ，其中 piles[i] 表示第 i 堆石子中的石子数量。另给你一个整数 k ，请你执行下述操作 恰好 k 次：
     *
     * 选出任一石子堆 piles[i] ，并从中 移除 floor(piles[i] / 2) 颗石子。
     * 注意：你可以对 同一堆 石子多次执行此操作。
     *
     * 返回执行 k 次操作后，剩下石子的 最小 总数。
     *
     * floor(x) 为 小于 或 等于 x 的 最大 整数。（即，对 x 向下取整）。
     * @param piles 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/2/7 17:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minStoneSum(int[] piles, int k) {
        int result;
        result = method_01(piles, k);
        return result;
    }

    /**
     * @Description:
     * AC:342ms/58.50MB
     * @param piles
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/2/7 17:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] piles, int k) {
        int n = piles.length;
        int ans = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int i = 0; i < n; i++) {
            ans += piles[i];
            queue.offer(piles[i]);
        }

        while (k > 0) {
            int curr_value = queue.poll();
            int value =(int) Math.floor((double) curr_value / 2);
            queue.offer(curr_value - value);
            ans = ans - value;
            k--;
        }
        return ans;
    }
}
