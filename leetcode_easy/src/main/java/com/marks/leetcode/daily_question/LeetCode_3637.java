package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3637 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/3 11:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3637 {

    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 nums。
     * 如果存在索引 0 < p < q < n − 1，使得数组满足以下条件，则称其为 三段式数组（trionic）：
     * nums[0...p] 严格 递增，
     * nums[p...q] 严格 递减，
     * nums[q...n − 1] 严格 递增。
     * 如果 nums 是三段式数组，返回 true；否则，返回 false。
     *
     * @param: nums
     * @return boolean
     * @author marks
     * @CreateDate: 2026/02/03 11:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isTrionic(int[] nums) {
        boolean result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 需要数组是一个倒置的 'S' 型数组, 0 ~ p 递增, p ~ q 递减, q ~ n - 1 递增(并且都是严格的)
     * [2,1,3]
     * AC: 1ms/44.09MB
     * @param: nums
     * @return boolean
     * @author marks
     * @CreateDate: 2026/02/03 11:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums) {
        int flag = 0; // 0: 递增, 1: 递减, 2: 递增, 当遇到递减时 flag++, 判断需要递增时, flag % 2 == 0
        int n = nums.length;
        // 判断初始是否递增
        if (nums[0] >= nums[1]) {
            return false;
        }
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                // 递增, 判断 flag
                if (flag % 2 != 0) {
                    // flag 是递减的, 需要改变为递增
                    flag++;
                }
            } else if (nums[i] < nums[i - 1]) {
                // 递减
                if (flag % 2 == 0) {
                    // flag 是递增的, 需要改变为递减
                    flag++;
                }
            } else {
                // 不是严格的, 返回false
                return false;
            }
        }

        return flag == 2;
    }

}
