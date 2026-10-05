package com.marks.leetcode.array_medium;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1760 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/22 11:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1760 {

    /**
     * @Description:
     * 给你一个整数数组 nums ，其中 nums[i] 表示第 i 个袋子里球的数目。同时给你一个整数 maxOperations 。
     * 你可以进行如下操作至多 maxOperations 次：
     * 选择任意一个袋子，并将袋子里的球分到 2 个新的袋子中，每个袋子里都有 正整数 个球。
     * 比方说，一个袋子里有 5 个球，你可以把它们分到两个新袋子里，分别有 1 个和 4 个球，或者分别有 2 个和 3 个球。
     * 你的开销是单个袋子里球数目的 最大值 ，你想要 最小化 开销。
     * 请你返回进行上述操作后的最小开销。
     * @param: nums
     * @param: maxOperations
     * @return int
     * @author marks
     * @CreateDate: 2026/09/22 11:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumSize(int[] nums, int maxOperations) {
        int result;
        result = method_01(nums, maxOperations);
        return result;
    }

    /**
     * @Description:
     * 1. 开销定义为操作之后, 袋子中球数目的最大值, 现在需要最小化该值, 那么就需要最大值最小化,
     * 2. 假设采用二分查找的思想, 找到在[1, max(nums)] 中 mid 作为目标值, 判断是否满足条件
     * 3. 对 nums 进行降序排序, 对于 nums[i], 如果 nums[i] > mid, 则需要执行x次操作, 每
     * 操作一次, 会使得 nums[i] 减少 mid, 要使得剩余的元素小于等于mid, reminding = nums[i] - x * mid <= mid, 解的
     * x >= (nums[i] - mid) / mid, 并且 x 需要向上取整, (nums[i] + (mid - 1) - mid) / mid => x = (nums[i] - 1) / mid
     * 4. 然后不断减少 maxOperations, 直到 maxOperations <= 0, 此时如果后续还有元素大于 mid, 则返回 false, 否则返回 true
     * 5. 最后得到最小的 mid 即为所求
     * 6. 时间复杂度: O(nlogn), 空间复杂度: O(1)
     * AC: 48ms/68.39MB
     * @param: nums
     * @param: maxOperations
     * @return int
     * @author marks
     * @CreateDate: 2026/09/22 11:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int maxOperations) {
        // 对 nums 进行升序排序
        Arrays.sort(nums);
        int n = nums.length;
        int left = 1, right = nums[n - 1];
        int ans = right;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check(nums, mid, maxOperations)) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    private boolean check(int[] nums, int target, int maxOperations) {
        // 倒序遍历 nums
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] > target) {
                int operations = (nums[i] - 1) / target;
                maxOperations -= operations;
                if (maxOperations < 0) {
                    return false;
                }
            } else {
                break; // 剪枝优化
            }
        }
        return true;
    }

}
