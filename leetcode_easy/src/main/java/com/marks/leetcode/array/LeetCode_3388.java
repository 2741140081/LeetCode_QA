package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3388 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/2 14:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3388 {

    /**
     * @Description:
     * 给你一个整数数组 nums 。
     * 如果数组 nums 的一个分割满足以下条件，我们称它是一个 美丽 分割：
     * 数组 nums 分为三段 非空子数组：nums1 ，nums2 和 nums3 ，
     * 三个数组 nums1 ，nums2 和 nums3 按顺序连接可以得到 nums 。
     * 子数组 nums1 是子数组 nums2 的 前缀 或者 nums2 是 nums3 的 前缀。
     * 请你返回满足以上条件的分割 数目 。
     * 子数组 指的是一个数组里一段连续 非空 的元素。
     * 前缀 指的是一个数组从头开始到中间某个元素结束的子数组。
     * tips:
     * 1 <= nums.length <= 5000
     * 0 <= nums[i] <= 50
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/02 14:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int beautifulSplits(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 要让 nums1 是 nums2 的前缀 或者 nums2 是 nums3 的前缀, 需要存在一段相邻一组数组[i, j] 长度是 n, n 为偶数
     * 2. 暴力不可取, 暴力时间复杂度是 O(n^3), 思考使用动态规划来解决.
     * 3. 使用 LCP 算法, 即最长前缀匹配算法
     * need todo, 需要后续自行理解, 当前是 CV 题解.
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/02 14:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int[][] lcp = new int[n + 1][n + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= i; j--) {
                if (nums[i] == nums[j]) {
                    lcp[i][j] = lcp[i + 1][j + 1] + 1;
                }
            }
        }
        int ans = 0;
        for (int i = 1; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (i <= j - 1 && lcp[0][i] >= i || lcp[i][j] >= j - i) {
                    ans++;
                }
            }
        }

        return ans;
    }

}
