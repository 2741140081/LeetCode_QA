package com.marks.leetcode.hash_table;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_442 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/20 10:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_442 {

    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 nums ，其中 nums 的所有整数都在范围 [1, n] 内，且每个整数出现 最多两次 。
     * 请你找出所有出现 两次 的整数，并以数组形式返回。
     * 你必须设计并实现一个时间复杂度为 O(n) 且仅使用常量额外空间（不包括存储输出所需的空间）的算法解决此问题。
     *
     * tips:
     * n == nums.length
     * 1 <= n <= 10^5
     * 1 <= nums[i] <= n
     * nums 中的每个元素出现 一次 或 两次
     * @param: nums
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/05/20 10:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 如果考虑限制条件, 也就是必须要在 O(1) 复杂度判断 nums[i] 是否是重复? 并且只能使用常量额外空间, 但是有一个有用
     * 的条件是数据范围是[1, n], 也就是可以将 nums[i] 放在 nums[i] - 1 的位置处.
     * 2. 当 nums[i] - 1 != i 时, 则 nums[i] 是重复元素, 出现两次.
     * AC: 5ms/56.48MB
     * @param: nums
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/05/20 10:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                ans.add(nums[i]);
            }
        }
        return ans;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
