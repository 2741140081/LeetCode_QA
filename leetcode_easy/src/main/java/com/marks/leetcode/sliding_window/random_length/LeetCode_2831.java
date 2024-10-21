package com.marks.leetcode.sliding_window.random_length;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/18 9:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2831 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始的整数数组 nums 和一个整数 k 。
     * 如果子数组中所有元素都相等，则认为子数组是一个 等值子数组 。注意，空数组是 等值子数组 。
     * 从 nums 中删除最多 k 个元素后，返回可能的最长等值子数组的长度。
     * 子数组 是数组中一个连续且可能为空的元素序列。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= nums.length
     * 0 <= k <= nums.length
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/18 9:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestEqualSubarray(List<Integer> nums, int k) {
        int result = 0;
        result = method_01(nums, k);
        return result;
    }
    /**
     * @Description: [
     * E1:
     * 输入：nums = [1,3,2,3,1,3], k = 3
     * 输出：3
     * 解释：最优的方案是删除下标 2 和下标 4 的元素。
     * 删除后，nums 等于 [1, 3, 3, 3] 。
     * 最长等值子数组从 i = 1 开始到 j = 3 结束，长度等于 3 。
     * 可以证明无法创建更长的等值子数组。
     *
     * Code Tips 1:
     * For each indicesx, find i, j such that (indicesx[j] - indicesx[i]) - (j - i) <= k and j - i + 1 is maximized.
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/18 9:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<Integer> nums, int k) {
        int n = nums.size();
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(nums.get(i), x->new ArrayList<>()).add(i);

        }
        return 0;
    }
}
