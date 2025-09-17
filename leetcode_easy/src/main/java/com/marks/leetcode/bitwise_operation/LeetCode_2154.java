package com.marks.leetcode.bitwise_operation;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/17 11:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2154 {

    /**
     * @Description:
     *
     * 给你一个整数数组 nums ，另给你一个整数 original ，这是需要在 nums 中搜索的第一个数字。
     *
     * 接下来，你需要按下述步骤操作：
     *
     * 如果在 nums 中找到 original ，将 original 乘以 2 ，得到新 original（即，令 original = 2 * original）。
     * 否则，停止这一过程。
     * 只要能在数组中找到新 original ，就对新 original 继续 重复 这一过程。
     * 返回 original 的 最终 值。
     *
     * tips:
     * 1 <= nums.length <= 1000
     * 1 <= nums[i], original <= 1000
     *
     * @param nums 
     * @param original
     * @return int
     * @author marks
     * @CreateDate: 2025/9/17 11:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findFinalValue(int[] nums, int original) {
        int result;
        result = method_01(nums, original);
        return result;
    }

    
    /**
     * @Description:
     * AC: 4ms/43.23MB
     * @param nums 
     * @param original 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/17 11:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int original) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == original) {
                original <<= 1;
            }
            if (nums[i] > original) {
                return original;
            }
        }
        return original;
    }


}
