package com.marks.leetcode.state_machine_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/3 17:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2708 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始的整数数组 nums ，它表示一个班级中所有学生在一次考试中的成绩。
     * 老师想选出一部分同学组成一个 非空 小组，且这个小组的 实力值 最大，如果这个小组里的学生下标为 i0, i1, i2, ... , ik ，
     * 那么这个小组的实力值定义为 nums[i0] * nums[i1] * nums[i2] * ... * nums[ik] 。
     *
     * 请你返回老师创建的小组能得到的最大实力值为多少。
     * tips:
     * 1 <= nums.length <= 13
     * -9 <= nums[i] <= 9
     * ]
     * @param nums
     * @return long
     * @author marks
     * @CreateDate: 2024/9/3 17:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxStrength(int[] nums) {
        long result = 0;
//        result = method_01(nums);
        result = method_02(nums);

        return result;
    }

    private long method_02(int[] nums) {
        // max 维护[0, i - 1]最大的乘积
        // min 维护[0, i - 1]最小的乘积
        long max = nums[0];
        long min = nums[0];

        for (int i = 1; i < nums.length; i++) {
            long tmp1 = max * nums[i];
            long tmp2 = min * nums[i];

            max = Math.max(max, Math.max(tmp1, tmp2));
            max = Math.max(max, nums[i]);

            min = Math.min(min, Math.min(tmp1, tmp2));
            min = Math.min(min, nums[i]);
        }

        return max;
    }

    /**
     * @Description: [
     * nums = [3,-1,-5,2,5,-9]
     *
     * num[i] > 0
     * ]
     * @param nums
     * @return long
     * @author marks
     * @CreateDate: 2024/9/3 17:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        Arrays.sort(nums);
        long[] positive = new long[n];
        long[] negative = new long[n];
        positive[0] = nums[0] > 0 ? nums[0] : 0;
        negative[0] = nums[0] < 0 ? nums[0] : 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] > 0) {
                positive[i] = positive[i - 1] != 0 ? nums[i] * positive[i - 1] : nums[i];
                negative[i] = negative[i - 1] < 0 ? nums[i] * negative[i - 1] : 0;
            }else if (nums[i] < 0){
                positive[i] = Math.max(positive[i - 1], negative[i - 1] * nums[i]);
                negative[i] = Math.min(positive[i - 1] * nums[i], nums[i]);
            }else {
                // nums[i] == 0
                positive[i] = positive[i - 1];
                negative[i] = negative[i - 1];
            }
        }
        return positive[n - 1];
    }
}
