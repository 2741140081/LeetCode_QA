package com.marks.leetcode.hotLC;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_41 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/2 10:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_41 {

    /**
     * @Description:
     * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
     * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/02 10:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int firstMissingPositive(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 有什么思路吗, 首先需要对数组进行排序, 然后通过一次遍历判断 int target = 1;
     * 2. 当我们遍历到下标 nums[i] = target时, target++, 继续遍历,
     * 3. nums[i + 1] < target时, i++; nums[i + 1] == target时, i++, target++; nums[i+1] > target时, return target;
     * AC: 15ms/72.25MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/02 10:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        // 排序
        Arrays.sort(nums);
        int ans = 1;
        // 优化一下, 使用二分法找到1的位置
        int left = 0;
        int right = n - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= 1) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        for (int i = left; i < n; i++) {
            if (nums[i] == ans) {
                ans++;
            } else if (nums[i] > ans) {
                return ans;
            }
        }

        return ans;
    }

}
