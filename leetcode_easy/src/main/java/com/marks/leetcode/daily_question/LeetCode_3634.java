package com.marks.leetcode.daily_question;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3634 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/6 15:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3634 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k。
     * 如果一个数组的 最大 元素的值 至多 是其 最小 元素的 k 倍，则该数组被称为是 平衡 的。
     * 你可以从 nums 中移除 任意 数量的元素，但不能使其变为 空 数组。
     * 返回为了使剩余数组平衡，需要移除的元素的 最小 数量。
     * 注意：大小为 1 的数组被认为是平衡的，因为其最大值和最小值相等，且条件总是成立。
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 1 <= k <= 10^5
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/02/06 15:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minRemoval(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 先排序, 升序[n1,n2,..,nx]
     * 2. 移除元素, 只有两种选择(移除最小元素, 或者溢出最大元素), 重复执行
     * 3. 然后遍历数组, 使用滑动窗口, 即窗口中的元素保持平衡, 然后求的最大窗口长度, n - maxWindow 即为删除的最小元素个数
     * AC: 27ms/84.5MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/02/06 15:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int maxLength = 1;
        int left = 0;
        // 排序
        Arrays.sort(nums);
        for (int right = 1; right < n; right++) {
            while (nums[right] > (long)  nums[left] * k) { // nums[left] * k 导致了溢出
                left++;
            }
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return n - maxLength;
    }

}
