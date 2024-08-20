package com.marks.leetcode.classic_linear_DP.LIS;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>项目名称: 使得数组K递增的最少操作次数 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/19 17:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2111 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始包含 n 个正整数的数组 arr ，和一个正整数 k 。
     *
     * 如果对于每个满足 k <= i <= n-1 的下标 i ，都有 arr[i-k] <= arr[i] ，那么我们称 arr 是 K 递增 的。
     *
     * 比方说，arr = [4, 1, 5, 2, 6, 2] 对于 k = 2 是 K 递增的，因为：
     * arr[0] <= arr[2] (4 <= 5)
     * arr[1] <= arr[3] (1 <= 2)
     * arr[2] <= arr[4] (5 <= 6)
     * arr[3] <= arr[5] (2 <= 2)
     * 但是，相同的数组 arr 对于 k = 1 不是 K 递增的（因为 arr[0] > arr[1]），对于 k = 3 也不是 K 递增的（因为 arr[0] > arr[3] ）。
     * 每一次 操作 中，你可以选择一个下标 i 并将 arr[i] 改成任意 正整数。
     *
     * 请你返回对于给定的 k ，使数组变成 K 递增的 最少操作次数 。
     *
     * tips:
     * 1 <= arr.length <= 10^5
     * 1 <= arr[i], k <= arr.length
     * ]
     * @param arr
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/8/19 17:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int kIncreasing(int[] arr, int k) {
        int result = 0;
//        result = method_01(arr, k);
        result = method_02(arr, k);
        return result;
    }

    private int method_02(int[] arr, int k) {
        int n = arr.length;
        int sum = 0;
        for (int i = 0; i < k; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            int temp = i;
            // 相当于method_01的二维数组分层, i % k
            while(temp < arr.length) {
                list.add(arr[temp]);
                temp += k;
            }
            int[] nums = list.stream().mapToInt(Integer::intValue).toArray();
            sum += lengthOfLIS(nums);
        }

        return n - sum;
    }

    /**
     * @Description: [
     * 已经想到方法, 是对原数组进行分层, 通过i % k 取余分成k层, 存在在dp[n][k]中
     * 循环遍历k
     * 得到数组dp[i][0]
     * ....以及
     * dp[i][k - 1]共计k层
     *
     * 分别判断这k层是否满足条件，对新数组进行求最长递增序列，再用size - value_LIS
     * 结论: 该方法超出内存限制!!!
     * 优化:减少空间复杂度, 去除二维数组存储分层， 见Method_02
     * ]
     * @param arr
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/8/19 17:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr, int k) {
        int n = arr.length;
        int[][] level = new int[n][k];
        for (int i = 0; i < n; i++) {
            // 分层
            level[i][i % k] = arr[i];
        }
        int sum = 0;
        for (int i = 0; i < k; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (level[j][i] > 0) {
                    list.add(level[j][i]);
                }
            }
            int[] nums = list.stream().mapToInt(Integer::intValue).toArray();
            sum += lengthOfLIS(nums);
        }

        return n - sum;
    }

    private int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int len = 1;
        if (n == 0) {
            return 0;
        }
        int[] dp = new int[n + 1];
        dp[len] = nums[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] >= dp[len]) {
                len++;
                dp[len] = nums[i];
            }else {
                // 如果找不到说明所有的数都比 nums[i] 大，此时要更新 d[1]，所以这里将 pos 设为 0
                int l = 1, r = len, pos = 0;
                while (l <= r) {
                    int mid = (l + r) >> 1; // (l + r) / 2
                    if (dp[mid] <= nums[i]) {
                        l = mid + 1;
                        pos = mid;
                    }else {
                        r = mid - 1;
                    }
                }
                dp[pos + 1] = nums[i];
            }
        }
        return len;
    }
}
