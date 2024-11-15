package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/15 9:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2529 {
    /**
     * @Description: [
     * 给你一个按 非递减顺序 排列的数组 nums ，返回正整数数目和负整数数目中的最大值。
     *
     * 换句话讲，如果 nums 中正整数的数目是 pos ，而负整数的数目是 neg ，返回 pos 和 neg二者中的最大值。
     * 注意：0 既不是正整数也不是负整数。
     *
     * tips:
     * 1 <= nums.length <= 2000
     * -2000 <= nums[i] <= 2000
     * nums 按 非递减顺序 排列。
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/11/15 9:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumCount(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [-2,-1,-1,1,1,2,3]
     * 输出：3
     * 解释：共有 3 个正整数和 3 个负整数。计数得到的最大值是 3 。
     *
     * AC:0ms/43.91MB
     * ]
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/15 9:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        // target = 1, 最左侧的1
        int pos = binarySearch(nums, 1);

        // target = -1, 最右侧的-1
        // 查看官方题解, 查找最左侧的0是更优秀的一种方式
        int neg = binarySearch(nums, 0);

        int ans = Math.max(nums.length - pos, neg);
        return ans;
    }

    /**
     * @Description: [
     * boolean flag 为标志位
     * true: target > 0, 需要找到最左侧的 1
     * false: target < 0, 需要找到最右侧的 -1
     *
     * 查看官方题解后, 得到优化, 不需要flag标志位,
     * false: target < 0, 需要找到最右侧的 -1, 这种情况相当于找最左侧的0, 与flag = true是一致的方式
     * 因此优化代码
     * ]
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/11/15 10:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;

            if (nums[mid] >= target) {
                right = mid - 1;
            }else {
                // nums[mid] < target
                left = mid + 1;
            }
        }
        return left;
    }
}
