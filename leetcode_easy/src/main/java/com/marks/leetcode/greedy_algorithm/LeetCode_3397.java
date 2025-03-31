package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/31 16:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3397 {
    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k。
     *
     * 你可以对数组中的每个元素 最多 执行 一次 以下操作：
     *
     * 将一个在范围 [-k, k] 内的整数加到该元素上。
     * 返回执行这些操作后，nums 中可能拥有的不同元素的 最大 数量。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 0 <= k <= 10^9
     * @param nums 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/3/31 16:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxDistinctElements(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 排序 + 贪心
     * 即让小的 - k, 让大的 + k
     *
     * AC: 38ms/59.54MB
     * @param nums 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/31 16:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int max_value = nums[n - 1] + k;
        Set<Integer> set = new HashSet<>();
        int left = nums[0] - k;
        set.add(left);
        left++;
        for (int i = 1; i < n && left <= max_value; i++) {
            int min = nums[i] - k;
            int max = nums[i] + k;
            if (min >= left) {
                set.add(min);
                left = min + 1;
            } else if (left >= min && left <= max) {
                set.add(left);
                left++;
            } else {
                continue;
            }

        }
        return set.size();
    }
}
