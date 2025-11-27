package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_153 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/26 17:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_153 {

    /**
     * @Description:
     * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,2,4,5,6,7] 在变化后可能得到：
     * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,2]
     * 若旋转 7 次，则可以得到 [0,1,2,4,5,6,7]
     * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
     *
     * 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
     *
     * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/11/26 17:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findMin(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 说是旋转1~n 次, 其实就是选择节点k, 然后进行旋转
     * 2. 怎么才算是找到了最小值? nums[left] > nums[left + 1];
     *
     * AC: 0ms/43.07MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/11/26 17:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;

        // 判断是否进行旋转
        if (nums[0] < nums[n - 1] || n == 1) {
            return nums[0];
        }

        int left = 0;
        int right = n - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[right]) {
                right = mid;
            } else {
                // nums[mid] < nums[left], 最小值在 [0 ~ mid]
                left = mid + 1;
            }
        }
        return nums[left];
    }

}
