package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3012 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/13 16:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3012 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums ，它只包含 正 整数。
     * 你的任务是通过进行以下操作 任意次 （可以是 0 次） 最小化 nums 的长度：
     * 在 nums 中选择 两个不同 的下标 i 和 j ，满足 nums[i] > 0 且 nums[j] > 0 。
     * 将结果 nums[i] % nums[j] 插入 nums 的结尾。
     * 将 nums 中下标为 i 和 j 的元素删除。
     * 请你返回一个整数，它表示进行任意次操作以后 nums 的 最小长度 。
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/13 16:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumArrayLength(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 先对 nums 数组进行升序排序
     * 2. 使用双指针left 和 right 分别指向nums 的首尾元素, 对于 nums[left] 和 nums[right] 进行分类讨论
     * 2.1 nums[left] < nums[right], 则当进行 nums[left] % nums[right] 进行取余操作后, 只会剩余 nums[left], nums[right] 删除, 则 right--
     * 2.2 nums[left] == nums[right], 由于是一个升序数组, 所以此时剩余的数组元素全部是相同的, 此时数组的长度是 int len = right - left + 1;
     * 则剩余的 0 的个数是 int ans = (len + 1) / 2;
     * 3. 错误了, case = {5,2,2,2,9,10}, 由于 9 % 2 = 1, 所以最后只剩余一个元素, 需要在 while 循环中添加 nums[right] % nums[left] 取余的判断
     * 3.1 如果 nums[right] % nums[left] == 0, 则 right--, 否则直接返回 1
     * AC: 16ms/101.36MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/13 16:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        // 排序
        Arrays.sort(nums);
        int left = 0, right = n - 1;
        while (nums[left] != nums[right]) {
            // 判断 nums[left] 和 nums[right]
            if (nums[right] % nums[left] == 0) {
                right--;
            } else {
                return 1;
            }

        }
        int len = right - left + 1;

        return (len + 1) / 2;
    }

}
