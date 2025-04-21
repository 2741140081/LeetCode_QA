package com.marks.leetcode.data_structure.common_enum_technique;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/7 16:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1512 {
    /**
     * @Description: [
     * 给你一个整数数组 nums 。
     *
     * 如果一组数字 (i,j) 满足 nums[i] == nums[j] 且 i < j ，就可以认为这是一组 好数对 。
     *
     * 返回好数对的数目。
     * ]
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/7 16:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numIdenticalPairs(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: [
     * AC:1ms/40.32MB
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/1/7 16:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    ++ans;
                }
            }
        }
        return ans;
    }
}
