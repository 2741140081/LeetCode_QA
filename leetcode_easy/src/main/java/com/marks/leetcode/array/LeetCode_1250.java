package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1250 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/28 11:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1250 {

    /**
     * @Description:
     * 给你一个正整数数组 nums，你需要从中任选一些子集，然后将子集中每一个数乘以一个 任意整数，并求出他们的和。
     * 假如该和结果为 1，那么原数组就是一个「好数组」，则返回 True；否则请返回 False。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param: nums
     * @return boolean
     * @author marks
     * @CreateDate: 2026/07/28 11:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isGoodArray(int[] nums) {
        boolean result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 最值结果必定是 a - b = 1, 则 a 是奇数或者 b 是奇数, 也就是说, 如果 nums 中不存在奇数, 则返回 false.
     * 2. a - b = 1 成立的条件是 a * x - b * y = 1, 成立条件是 gcd(a, b) == 1.
     * 3. 由于数据范围是 10^9, 所以不能使用筛法, 并且 n = 10^5, 所有 O(n^2) 会超时
     * 4. 应该还存在某些东西我不知道, 看看题解吧, 直接统计所有数的gcd 即可
     * AC: 2ms/60.73MB
     * @param: nums
     * @return boolean
     * @author marks
     * @CreateDate: 2026/07/28 11:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums) {
        int divisor = nums[0];
        for (int num : nums) {
            divisor = gcd(divisor, num);
            if (divisor == 1) {
                return true;
            }
        }
        return false;
    }

    private int gcd(int divisor, int num) {
        return divisor == 0 ? num : gcd(num % divisor, divisor);
    }

}
