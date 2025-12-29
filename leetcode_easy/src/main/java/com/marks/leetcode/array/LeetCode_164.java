package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_164 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/26 15:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_164 {

    /**
     * @Description:
     * 给定一个无序的数组 nums，返回 数组在排序之后，相邻元素之间最大的差值 。
     * 如果数组元素个数小于 2，则返回 0 。
     *
     * 您必须编写一个在「线性时间」内运行并使用「线性额外空间」的算法。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/26 15:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumGap(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: [方法描述]
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/26 15:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        if (n < 2) {
            return 0;
        }
        int maxGap = nums[1] - nums[0];
        for (int i = 2; i < n; i++) {
            maxGap = Math.max(maxGap, nums[i] - nums[i - 1]);
        }
        return maxGap;
    }

}
