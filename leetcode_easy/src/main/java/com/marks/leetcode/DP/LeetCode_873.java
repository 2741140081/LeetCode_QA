package com.marks.leetcode.DP;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_873 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/25 10:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_873 {

    /**
     * @Description:
     * 如果序列 x1, x2, ..., xn 满足下列条件，就说它是 斐波那契式 的：
     * n >= 3
     * 对于所有 i + 2 <= n，都有 xi + xi+1 == xi+2
     * 给定一个 严格递增 的正整数数组形成序列 arr ，找到 arr 中最长的斐波那契式的子序列的长度。如果不存在，返回  0 。
     * 子序列 是通过从另一个序列 arr 中删除任意数量的元素（包括删除 0 个元素）得到的，同时不改变剩余元素顺序。例如，[3, 5, 8] 是 [3, 4, 5, 6, 7, 8] 的子序列。
     * tips:
     * 3 <= arr.length <= 1000
     * 1 <= arr[i] < arr[i + 1] <= 10^9
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/02/25 10:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int lenLongestFibSubseq(int[] arr) {
        int result;
        result = method_01(arr);
        result = method_02(arr);
        return result;
    }

    /**
     * @Description: [方法描述]
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/02/25 10:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] arr) {
        Map<Integer, Integer> indices = new HashMap<Integer, Integer>();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            indices.put(arr[i], i);
        }
        int[][] dp = new int[n][n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i - 1; j >= 0 && arr[j] * 2 > arr[i]; j--) {
                int k = indices.getOrDefault(arr[i] - arr[j], -1);
                if (k >= 0) {
                    dp[j][i] = Math.max(dp[k][j] + 1, 3);
                }
                ans = Math.max(ans, dp[j][i]);
            }
        }
        return ans;
    }

    /**
     * @Description:
     * 1. 两个数字可以递归的求出长度, 例如 x, y 为 arr[i], arr[j], next1 = x + y, next2 = y + next1, 只需要判断nextX 是否在 arr[] 中, 如果在则继续递归求出长度, 否则返回 当前的长度
     * 2. 整个程序的时间复杂度为 O(n^2), 另外一个注意点是arr[] 已经是升序排序的, 使用Set 存储arr[] 的元素
     * AC: 220ms/45.98MB
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/02/25 10:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {
        int n = arr.length;
        int ans = 0;
        Set<Long> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            set.add((long) arr[i]);
        }
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                long x = arr[i], y = arr[j];
                int len = 2;
                while (set.contains(x + y)) {
                    long temp = x + y;
                    x = y;
                    y = temp;
                    len++;
                }
                if (len > 2) {
                    ans = Math.max(ans, len);
                }
            }
        }
        return ans;
    }

}
