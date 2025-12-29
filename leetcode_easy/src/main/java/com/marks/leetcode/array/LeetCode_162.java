package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_162 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/26 16:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_162 {

    /**
     * @Description: [方法描述]
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/26 16:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findPeakElement(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * AC: 0ms/43.66MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/26 16:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        // 先判断边界情况
        int n = nums.length;
        if (n == 1 || nums[0] > nums[1]) {
            return 0;
        }
        if (nums[n - 1] > nums[n - 2]) {
            return n - 1;
        }
        for (int i = 1; i < n - 1; i++) {
            if (nums[i] > nums[i - 1] && nums[i] > nums[i + 1]) {
                return i;
            }
        }
        return 0;
    }

}
