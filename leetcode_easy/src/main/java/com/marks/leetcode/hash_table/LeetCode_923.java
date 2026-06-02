package com.marks.leetcode.hash_table;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_923 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/1 14:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_923 {

    /**
     * @Description:
     * 给定一个整数数组 arr ，以及一个整数 target 作为目标值，
     * 返回满足 i < j < k 且 arr[i] + arr[j] + arr[k] == target 的元组 i, j, k 的数量。
     * 由于结果会非常大，请返回 10^9 + 7 的模。
     *
     * tips:
     * 3 <= arr.length <= 3000
     * 0 <= arr[i] <= 100
     * 0 <= target <= 300
     * @param: arr
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/06/01 14:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int threeSumMulti(int[] arr, int target) {
        int result;
        result = method_01(arr, target);
        return result;
    }

    /**
     * @Description:
     * 1. 每个数的顺序对于结果没有影响, 所以可以统计每一个数出现的频次 int[] count = new int[101];
     * 2. 然后找到新的三个下标 i, j, k, 使得 count[i] + count[j] + count[k] = target, 其中 i <= j <= k, 并且计算组合数。
     * 3. 讨论关系 i = j = k, c * (c - 1) * (c - 2) / 6
     * AC: 3ms/44.83MB
     * @param: arr
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/06/01 14:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr, int target) {
        int MOD = 1000000007;
        long[] count = new long[101];
        for (int num : arr) {
            count[num]++;
        }
        long ans = 0;
        for (int i = 0; i <= Math.min(100, target); i++) {
            if (count[i] == 0) { // 当前个数为 0
                continue;
            }
            for (int j = i; j <= Math.min(100, target - i); j++) {
                if (count[j] == 0) {
                    continue;
                }

                int k = target - i - j;
                if (k < j || k > 100 || count[k] == 0) {
                    continue;
                }
                if (i == j) {
                    if (j == k) {
                        ans += (count[i] * (count[j] - 1) * (count[k] - 2) / 6) % MOD;
                    } else {
                        ans += (count[i] * (count[j] - 1) * count[k] / 2) % MOD;
                    }
                } else {
                    if (j == k) {
                        ans += (count[i] * count[j] * (count[k] - 1) / 2) % MOD;
                    } else {
                        ans += (count[i] * count[j] * count[k]) % MOD;
                    }
                }


            }
        }

        return (int) ans % MOD;
    }

}
