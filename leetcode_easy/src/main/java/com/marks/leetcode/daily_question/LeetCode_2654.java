package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2654 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/12 15:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2654 {

    
    /**
     * @Description:
     * 给你一个下标从 0 开始的 正 整数数组 nums 。你可以对数组执行以下操作 任意 次：
     * 选择一个满足 0 <= i < n - 1 的下标 i ，将 nums[i] 或者 nums[i+1] 两者之一替换成它们的最大公约数。
     * 请你返回使数组 nums 中所有元素都等于 1 的 最少 操作次数。如果无法让数组全部变成 1 ，请你返回 -1 。
     *
     * 两个正整数的最大公约数指的是能整除这两个数的最大正整数。
     * tips:
     * 2 <= nums.length <= 50
     * 1 <= nums[i] <= 10^6
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/11/12 15:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minOperations(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：nums = [2,6,3,4]
     * 输出：4
     * 1. 差不多有思路了, 需要从数组中找到将 nums[i] 变成 1 的最小操作次数 count
     * 2. 分情况,
     *  a. 数组中不存在nums[i] == 1, ans = count + nums.length - 1;
     *  b. 存在nums[i] == 1, count = 0, 不为1的元素个数 sum, ans = sum;
     * 3. 如果数组中所有元素的gcd > 1, ans = -1;
     * 4. 遍历数组, 找到最小的子数组, 使得gcd = 1, 返回sum + n - 2;
     * AC: 2ms/43.16MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/11/12 15:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int count_zero = 0;
        int gcd = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                count_zero++;
            }
            gcd = gcd(gcd, nums[i]);
        }
        if (count_zero > 0) {
            return n - count_zero;
        }
        if (gcd > 1) {
            return -1;
        }
        int minLength = n;
        for (int i = 0; i < n; i++) {
            int temp = nums[i];
            for (int j = i + 1; j < n; j++) {
                temp = gcd(temp, nums[j]);
                if (temp == 1) {
                    minLength = Math.min(minLength, j - i + 1);
                    break;
                }
            }
        }

        return minLength + n - 2;
    }

    private int gcd(int x, int y) {
        return y != 0 ? gcd(y, x % y) : x;
    }
}
