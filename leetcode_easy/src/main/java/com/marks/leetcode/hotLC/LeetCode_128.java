package com.marks.leetcode.hotLC;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_128 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/1 11:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_128 {

    /**
     * @Description:
     * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
     *
     * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/01 11:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestConsecutive(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     *
     * AC: 17ms/76.14MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/01 11:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        Arrays.sort(nums);
        int ans = 0;
        int count = 1;
        int prev = nums[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] == prev){
                continue;
            }
            if (nums[i] == prev + 1) {
                count++;
                ans = Math.max(ans, count);
            } else {
                count = 1;
            }
            prev = nums[i];
        }
        return ans;
    }

}
