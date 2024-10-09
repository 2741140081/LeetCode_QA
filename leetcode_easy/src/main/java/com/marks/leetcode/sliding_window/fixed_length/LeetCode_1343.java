package com.marks.leetcode.sliding_window.fixed_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/9 16:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1343 {
    /**
     * @Description: [
     * 给你一个整数数组 arr 和两个整数 k 和 threshold 。
     *
     * 请你返回长度为 k 且平均值大于等于 threshold 的子数组数目。
     * 
     * tips:
     * 1 <= arr.length <= 10^5
     * 1 <= arr[i] <= 10^4
     * 1 <= k <= arr.length
     * 0 <= threshold <= 10^4
     * ]
     * @param arr
     * @param k
     * @param threshold
     * @return int
     * @author marks
     * @CreateDate: 2024/10/9 16:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numOfSubarrays(int[] arr, int k, int threshold) {
        int result = 0;
        result = method_01(arr, k, threshold);
        return result;
    }
    /**
     * @Description: [
     * E1:
     * 输入：arr = [2,2,2,2,5,5,5,8], k = 3, threshold = 4
     * 输出：3
     * 解释：子数组 [2,5,5],[5,5,5] 和 [5,5,8] 的平均值分别为 4，5 和 6 。其他长度为 3 的子数组的平均值都小于 4 （threshold 的值)。
     *
     * AC:1ms/59.36MB
     * ]
     * @param arr
     * @param k
     * @param threshold
     * @return int
     * @author marks
     * @CreateDate: 2024/10/9 16:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr, int k, int threshold) {
        int n = arr.length;
        int ans = 0;
        int sum = 0;
        int minSum = threshold * k;
        for (int i = 0; i < k; i++) {
            sum += arr[i];
        }
        ans = sum >= minSum ? 1 : 0;
        for (int i = k; i < n; i++) {
            sum = sum + arr[i] - arr[i - k];
            if (sum >= minSum) {
                ans++;
            }
        }
        return ans;
    }
}
