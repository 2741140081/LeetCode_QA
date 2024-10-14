package com.marks.leetcode.sliding_window.random_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/14 17:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1004 {
    /**
     * @Description: [
     * 给定一个二进制数组 nums 和一个整数 k，如果可以翻转最多 k 个 0 ，则返回 数组中连续 1 的最大个数 。
     * tips:
     * 1 <= nums.length <= 10^5
     * nums[i] 不是 0 就是 1
     * 0 <= k <= nums.length
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 17:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestOnes(int[] nums, int k) {
        int result = 0;
        result = method_01(nums, k);
        return result;
    }
    /**
     * @Description: [
     * 输入：nums = [1,1,1,0,0,0,1,1,1,1,0], K = 2
     * 输出：6
     * 解释：[1,1,1,0,0,1,1,1,1,1,1]
     * 粗体数字从 0 翻转到 1，最长的子数组长度为 6。
     *
     * AC:3ms/45.62MB
     * ]
     * @param nums 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 17:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int left = 0;
        int count = 0;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            count = count + (nums[i] == 0 ? 1 : 0);
            while (count > k) {
                if (nums[left++] == 0) {
                    count--;
                }
            }
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }
}
