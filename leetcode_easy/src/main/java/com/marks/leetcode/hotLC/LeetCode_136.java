package com.marks.leetcode.hotLC;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_136 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/11 10:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_136 {

    /**
     * @Description:
     * 给你一个 非空 整数数组 nums ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * 你必须设计并实现线性时间复杂度的算法来解决此问题，且该算法只使用常量额外空间。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/11 10:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int singleNumber(int[] nums) {
        int result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    /**
     * @Description:
     * 直接将所有数进行异或操作, 所得结果即为单个元素
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/11 10:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    /**
     * @Description:
     * 直接二分查找, 时间复杂度O(nlogn)
     * 关键点: nums[mid] == nums[mid ^ 1], 由于已经排序, 可以使用二分查找
     * mid 为偶数时, mid ^ 1 相当于 mid + 1, nums[mid] == nums[mid + 1], 可以判断单个元素在左侧还是右侧
     * mid 为奇数时, mid ^ 1 相当于 mid - 1, nums[mid] == nums[mid - 1], 可以判断单个元素在左侧还是右侧
     * mid ^ 1 可以减少mid的 奇偶性判断。
     * AC: 9ms/46.29MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/11 10:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        // 需要排序
        Arrays.sort(nums);
        int left = 0, right = n - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == nums[mid ^ 1]) {
                left = mid + 1; // 在右侧查找
            } else {
                right = mid; // 在左侧查找
            }
        }
        return nums[left];
    }

}
