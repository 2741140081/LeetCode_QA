package com.marks.leetcode.data_structure.stack;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_581 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/13 10:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_581 {

    /**
     * @Description:
     * 给你一个整数数组 nums ，你需要找出一个 连续子数组 ，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
     * 请你找出符合题意的 最短 子数组，并输出它的长度。
     *
     * tips:
     * 1 <= nums.length <= 10^4
     * -10^5 <= nums[i] <= 10^5
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/13 10:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findUnsortedSubarray(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 要是的无序子数组最短, 需要让其它有序数组最长, 而需要求最长子数组可以使用单调栈, 由于是升序排序, 所以需要单调递增栈
     * 2. 整个数组中有序的部分组合可以有3种: 1. 最左边的子数组 2. 最右边的子数组 3. 最左侧 + 最右侧的子数组
     * 3. [1, 2, 10, 5, 4] 这种, [0,2] 是有序的, 但是 子数组所需要的范围是 [2, 4]
     * 4. 想不到很好的处理方式, 选择使用排序后的数组 sort[] 与  nums[] 进行对比, 然后找到[0, i] 相同的最长序列, 然后找到[j, n - 1] 的最长序列
     * AC: 11ms/47.09MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/13 10:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        // 拷贝一份数组, 然后对拷贝后的数组进行升序排序
        int[] sort = new int[n];
        for (int i = 0; i < n; i++) {
            sort[i] = nums[i];
        }
        Arrays.sort(sort);
        int ans = 0;
        int left = 0;
        while (left < n && nums[left] == sort[left]) {
            left++;
            ans++;
        }
        int right = n - 1;
        while (right > left && nums[right] == sort[right]) {
            right--;
            ans++;
        }

        return n - ans;
    }

}
