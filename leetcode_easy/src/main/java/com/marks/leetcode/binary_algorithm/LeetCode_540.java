package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_540 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/27 16:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_540 {

    /**
     * @Description:
     * 给你一个仅由整数组成的有序数组，其中每个元素都会出现两次，唯有一个数只会出现一次。
     * 请你找出并返回只出现一次的那个数。
     * 你设计的解决方案必须满足 O(log n) 时间复杂度和 O(1) 空间复杂度。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/11/27 16:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int singleNonDuplicate(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 每一个数都出现2次, 并且偶数位的数等于奇数位的数 i % 2 == 0, nums[i] = nums[i + 1],
     * 2. 插入一个仅出现一次的数, 会破坏当前的结构, 即变成偶数位 != 奇数位的数, 通过这一条件判断目标数位于哪一个区间
     * 3. 通过二分查找解决, AC: 0ms/52.09MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/11/27 16:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int left = 0, right = n - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (mid % 2 == 0) {
                // mid 当前处于偶数位置
                if (nums[mid] == nums[mid + 1]) {
                    // 目标数在mid的右边
                    left = mid + 2;
                } else {
                    // 目标数在mid的左边
                    right = mid;
                }
            } else {
                // mid 当前处于奇数位置
                if (nums[mid] == nums[mid - 1]) {
                    // 目标数在mid的右边
                    left = mid + 1;
                } else {
                    // 目标数在mid的左边
                    right = mid - 1;
                }
            }

        }
        return nums[left];
    }

}
