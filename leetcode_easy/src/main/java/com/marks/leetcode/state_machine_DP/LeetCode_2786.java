package com.marks.leetcode.state_machine_DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/4 9:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2786 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始的整数数组 nums 和一个正整数 x 。
     *
     * 你 一开始 在数组的位置 0 处，你可以按照下述规则访问数组中的其他位置：
     *
     * 如果你当前在位置 i ，那么你可以移动到满足 i < j 的 任意 位置 j 。
     * 对于你访问的位置 i ，你可以获得分数 nums[i] 。
     * 如果你从位置 i 移动到位置 j 且 nums[i] 和 nums[j] 的 奇偶性 不同，那么你将失去分数 x 。
     * 请你返回你能得到的 最大 得分之和。
     *
     * 注意 ，你一开始的分数为 nums[0] 。
     *
     * tips；
     * 2 <= nums.length <= 10^5
     * 1 <= nums[i], x <= 10^6
     * ]
     * @param nums
     * @param x
     * @return long
     * @author marks
     * @CreateDate: 2024/9/4 9:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxScore(int[] nums, int x) {
        long result = 0;
        result = method_01(nums, x);
        return result;
    }
    /**
     * @Description: [
     * 直接做: 思路是动态规划
     * 使用dp存储最大得分和
     * 对于位置i, 可能需要一个变量存储前一个位置的
     * temp = nums[0]
     * temp & 1 == nums[i] & 1
     * 奇数 & 1 后结果是 1, 偶数 & 1 后结果是 0.
     * 以上思路存在问题
     * 查看题解后
     * // 分别使用odd(奇数), even(偶数) 来存储前一个是奇数到当前i 还是选择偶数到当前i
     *
     * AC:6ms/58.88MB
     * ]
     * @param nums
     * @param x
     * @return long
     * @author marks
     * @CreateDate: 2024/9/4 9:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int x) {
        long ans = nums[0];
        long odd = (nums[0] & 1) == 1 ? nums[0] : -x;
        long even = (nums[0] & 1) != 1 ? nums[0] : -x;
        for (int i = 1; i < nums.length; i++) {
            if ((nums[i] & 1) == 1) {
                // num[i] 为奇数
                odd = Math.max(odd + nums[i], even + nums[i] - x);
                ans = Math.max(ans, odd);
            }else {
                // num[i] 为偶数
                even = Math.max(even + nums[i], odd + nums[i] - x);
                ans = Math.max(ans, even);
            }
        }
        return ans;
    }
}
