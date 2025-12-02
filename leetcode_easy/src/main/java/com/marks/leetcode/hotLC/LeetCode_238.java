package com.marks.leetcode.hotLC;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_238 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/2 9:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_238 {

    /**
     * @Description:
     * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
     * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
     * 请 不要使用除法，且在 O(n) 时间复杂度内完成此题。
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2025/12/02 9:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] productExceptSelf(int[] nums) {
        int[] result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    /**
     * @Description:
     * 优化method_01, 优化空间复杂度, 使得空间复杂度为O(1)
     * 1. 思路还是后缀乘积
     * AC: 3ms/70.76MB
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2025/12/02 10:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        // 处理后缀
        ans[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            ans[i] = ans[i + 1] * nums[i];
        }
        int res = 1;
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                ans[i] = ans[i + 1];
            } else if (i == n - 1) {
                ans[i] = res;
            } else {
                ans[i] = res * ans[i + 1];
            }
            res *= nums[i];
        }
        return ans;
    }


    /**
     * @Description:
     * 1. 如果不使用除法, 只能使用前缀和 和 后缀和 来预处理数组中数据的乘积(希望不会发生溢出)
     * AC: 2ms/63.67MB
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2025/12/02 9:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n];
        prefix[0] = nums[0];
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] * nums[i];
        }
        int[] suffix = new int[n];
        suffix[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffix[i] = suffix[i + 1] * nums[i];
        }
        int[] result = new int[n];
        result[0] = suffix[1];
        result[n - 1] = prefix[n - 2];
        for (int i = 1; i < n - 1; i++) {
            result[i] = prefix[i - 1] * suffix[i + 1];
        }
        return result;
    }

}
