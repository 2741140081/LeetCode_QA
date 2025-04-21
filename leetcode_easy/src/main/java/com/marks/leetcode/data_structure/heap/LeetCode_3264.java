package com.marks.leetcode.data_structure.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/7 16:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3264 {
    /**
     * @Description:
     * 给你一个整数数组 nums ，一个整数 k  和一个整数 multiplier 。
     *
     * 你需要对 nums 执行 k 次操作，每次操作中：
     *
     * 找到 nums 中的 最小 值 x ，如果存在多个最小值，选择最 前面 的一个。
     * 将 x 替换为 x * multiplier 。
     * 请你返回执行完 k 次乘运算之后，最终的 nums 数组。
     * @param nums
     * @param k
     * @param multiplier
     * @return int[]
     * @author marks
     * @CreateDate: 2025/2/7 16:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] getFinalState(int[] nums, int k, int multiplier) {
        int[] result;
        result = method_01(nums, k, multiplier);
        return result;
    }

    /**
     * @Description:
     * AC:4ms/44.33MB
     *
     * @param nums
     * @param k
     * @param multiplier
     * @return int[]
     * @author marks
     * @CreateDate: 2025/2/7 16:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums, int k, int multiplier) {
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o1[0] - o2[0];
            } else {
                return o1[1] - o2[1];
            }
        });
        int n = nums.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            queue.offer(new int[]{nums[i], i});
        }
        while (k > 0) {
            int[] array = queue.poll();
            queue.offer(new int[]{array[0] * multiplier, array[1]});
            k--;
        }
        while (!queue.isEmpty()) {
            int[] array = queue.poll();
            ans[array[1]] = array[0];
        }
        return ans;
    }
}
