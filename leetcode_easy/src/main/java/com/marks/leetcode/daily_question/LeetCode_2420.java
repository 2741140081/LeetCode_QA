package com.marks.leetcode.daily_question;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2420 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/27 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2420 {

    /**
     * @Description:
     * 给你一个大小为 n 下标从 0 开始的整数数组 nums 和一个正整数 k 。
     * 对于 k <= i < n - k 之间的一个下标 i ，如果它满足以下条件，我们就称它为一个 好 下标：
     * 下标 i 之前 的 k 个元素是 非递增的 。
     * 下标 i 之后 的 k 个元素是 非递减的 。
     * 按 升序 返回所有好下标。
     *
     * tips:
     * n == nums.length
     * 3 <= n <= 10^5
     * 1 <= nums[i] <= 10^6
     * 1 <= k <= n / 2
     * @param: nums
     * @param: k
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/03/27 15:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> goodIndices(int[] nums, int k) {
        List<Integer> result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * todo
     * @param: nums
     * @param: k
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/03/27 15:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int[] nums, int k) {

        return null;
    }

}
