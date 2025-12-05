package com.marks.leetcode.daily_question;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3432 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/5 15:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3432 {

    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 nums 。
     *
     * 分区 是指将数组按照下标 i （0 <= i < n - 1）划分成两个 非空 子数组，其中：
     *
     * 左子数组包含区间 [0, i] 内的所有下标。
     * 右子数组包含区间 [i + 1, n - 1] 内的所有下标。
     * 对左子数组和右子数组先求元素 和 再做 差 ，统计并返回差值为 偶数 的 分区 方案数。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/05 15:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countPartitions(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }


    /**
     * @Description:
     * AC: 4ms/43.25MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/05 15:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 != 0) {
            return 0;
        }
        return n - 1;
    }

}
