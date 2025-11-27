package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_33 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/26 16:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_33 {

    /**
     * @Description:
     * 整数数组 nums 按升序排列，数组中的值 互不相同 。
     * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 向左旋转，
     * 使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
     * 例如， [0,1,2,4,5,6,7] 下标 3 上向左旋转后可能变为 [4,5,6,7,0,1,2] 。
     * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
     * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
     * @param: nums
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2025/11/26 16:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int search(int[] nums, int target) {
        int result;
        result = method_01(nums, target);
        return result;
    }

    /**
     * @Description:
     * 1. 如何确定这个k? 不太好确定, 直接进行二分吧
     * left = 0, right = n - 1; target;
     * int mid = (left + right) / 2;
     * 2. nums[mid] == target, return mid;
     * 3. nums[mid] > target,
     * AC: 0ms/43.11MB
     *
     * @param: nums
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2025/11/26 16:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int target) {
        int n = nums.length;
        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] >= nums[0]) {
                if (nums[0] <= target && nums[mid] > target) {
                    // target 在 [0, mid] 中
                    right = mid - 1;
                } else {
                    // target 在 [mid + 1, n - 1] 中
                    left = mid + 1;
                }
            } else {
                // nums[mid] < nums[0]
                if (target <= nums[n - 1] && nums[mid] < target) {
                    // target 在 [mid + 1, n - 1] 中
                    left = mid + 1;
                } else {
                    // target 在 [0, mid] 中
                    right = mid - 1;
                }
            }
        }

        return -1;
    }

}
