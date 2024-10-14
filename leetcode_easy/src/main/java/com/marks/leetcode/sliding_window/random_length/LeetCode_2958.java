package com.marks.leetcode.sliding_window.random_length;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/14 15:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2958 {
    /**
     * @Description: [
     * 给你一个整数数组 nums 和一个整数 k 。
     * 一个元素 x 在数组中的 频率 指的是它在数组中的出现次数。
     * 如果一个数组中所有元素的频率都 小于等于 k ，那么我们称这个数组是 好 数组。
     * 请你返回 nums 中 最长好 子数组的长度。
     * 子数组 指的是一个数组中一段连续非空的元素序列。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 1 <= k <= nums.length
     * ]
     * @param nums 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 15:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxSubarrayLength(int[] nums, int k) {
        int result = 0;
        result = method_01(nums, k);
        return result;
    }
    /**
     * @Description: [
     * E1:
     * 输入：nums = [1,2,3,1,2,3,1,2], k = 2
     * 输出：6
     * 解释：最长好子数组是 [1,2,3,1,2,3] ，值 1 ，2 和 3 在子数组中的频率都没有超过 k = 2 。[2,3,1,2,3,1] 和 [3,1,2,3,1,2] 也是好子数组。
     * 最长好子数组的长度为 6 。
     * AC:77ms/58.19MB
     * 优化1.将map.put改成map.merge
     * AC:67ms/59.88MB
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int left = 0;
        int ans = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            map.merge(num, 1, Integer::sum);
            // 超过时, 需要将left右移, 使得map.get(num) == k
            while (map.get(num) > k) {
                int temp = nums[left];
                map.merge(temp, -1, Integer::sum);
                left++;
            }
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }
}
