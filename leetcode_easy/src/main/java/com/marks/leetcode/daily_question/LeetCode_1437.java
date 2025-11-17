package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1437 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/17 15:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1437 {

    /***
     * @Description:
     * 给你一个由若干 0 和 1 组成的数组 nums 以及整数 k。如果所有 1 都至少相隔 k 个元素，则返回 true ；否则，返回 false 。
     * @param: nums
     * @param: k
     * @return boolean
     * @author marks
     * @CreateDate: 2025/11/17 15:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean kLengthApart(int[] nums, int k) {
        boolean result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * AC: 1ms/64.24MB
     * @param: nums
     * @param: k
     * @return boolean
     * @author marks
     * @CreateDate: 2025/11/17 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums, int k) {
        boolean ans = true;
        int count = k;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                if (count < k) {
                    ans = false;
                    break;
                }
                count = 0;
            } else {
                // nums[i] = 0
                count++;
            }
        }
        return ans;
    }

}
