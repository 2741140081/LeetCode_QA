package com.marks.leetcode.double_pointer.single_sequence;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/8 14:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_80 {
    public int removeDuplicates(int[] nums) {
        int result = 0;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [0,0,1,1,1,1,2,3,3]
     * 输出：7, nums = [0,0,1,1,2,3,3]
     *
     * AC:0ms/43.52MB
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/11/8 14:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        if (n <= 2) {
            return n;
        }
        int left = 1;
        int right = 1;
        int count = 1;
        while (right < n) {
            if (nums[right] == nums[right - 1]) {
                if (count == 1) {
                    nums[left] = nums[right];
                    left++;
                    right++;
                    count++;
                }else {
                    // count == 2
                    right++;
                }
            }else {
                // nums[right] != nums[right - 1]
                count = 1;
                nums[left] = nums[right];
                left++;
                right++;
            }
        }
        return left;
    }
}
