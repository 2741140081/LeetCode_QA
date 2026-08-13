package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3428 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/29 16:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3428 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个正整数 k，返回所有长度最多为 k 的 子序列 中 最大值 与 最小值 之和的总和。
     * 非空子序列 是指从另一个数组中删除一些或不删除任何元素（且不改变剩余元素的顺序）得到的数组。
     * 由于答案可能非常大，请返回对 10^9 + 7 取余数的结果。
     * tips:
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^9
     * 1 <= k <= min(100, nums.length)
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/06/29 16:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minMaxSums(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }


    private static final int MOD = 1_000_000_007;
    private long[] fact;     // 阶乘数组
    private long[] invFact;  // 逆阶乘数组

    private void initFact(int maxN) {
        fact = new long[maxN + 1];
        invFact = new long[maxN + 1];

        fact[0] = 1;
        for (int i = 1; i <= maxN; i++) {
            fact[i] = fact[i - 1] * i % MOD;
        }

        // 利用费马小定理计算最大阶乘的逆元: fact[maxN]^(MOD-2)
        invFact[maxN] = pow(fact[maxN], MOD - 2);

        // 递推计算逆阶乘: invFact[i-1] = invFact[i] * i % MOD
        for (int i = maxN; i > 0; i--) {
            invFact[i - 1] = invFact[i] * i % MOD;
        }
    }

    /**
     * 快速幂计算 base^exp % mod
     */
    private long pow(long base, long exp) {
        long result = 1;
        base %= MOD;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % MOD;
            }
            base = (base * base) % MOD;
            exp >>= 1;
        }
        return result;
    }

    /**
     * 计算组合数 C(n, k) % MOD
     * 如果 k < 0 或 k > n，返回 0
     */
    public long comb(int n, int k) {
        if (k < 0 || k > n) {
            return 0;
        }
        // C(n, k) = fact[n] * invFact[k] % MOD * invFact[n-k] % MOD
        return fact[n] * invFact[k] % MOD * invFact[n - k] % MOD;
    }

    /**
     * @Description:
     * 1. 区间 [i, j] 中的最大值和最小值分别是 a, b;
     * 2. 既然求序列, 说明与顺序无关, 可以先对 nums[] 数组进行排序操作.
     * 3. 当处理 i 时, 存在 n - i - 1个元素, 从这些元素中取 k - 1 个元素, 组成一个 nums[i] 为最小值的子序列,
     * 有多少种可能性? C(n - i - 1, k - 1)
     * 4. 理解错误, 不是固定的 k 大小, 而是最大可以有 k 个元素, 最小有1个元素
     * AC: 264ms/73.17MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/06/29 16:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        initFact(n);
        Arrays.sort(nums);
        long ans = 0;
        for (int i = 0; i < n; i++) {
            int remainingElements = n - i - 1;
            int needToChoose = k - 1;
            long cnt = 0;
            for (int j = 0; j <= needToChoose; j++) {
                cnt = (cnt + comb(remainingElements, j)) % MOD;
            }
            ans = (ans + cnt * nums[i]) % MOD;
        }
        // 倒序处理最大值
        for (int i = n - 1; i >= 0; i--) {
            int needToChoose = k - 1;
            long cnt = 0;
            for (int j = 0; j <= needToChoose; j++) {
                cnt = (cnt + comb(i, j)) % MOD;
            }
            ans = (ans + cnt * nums[i]) % MOD;
        }

        return (int) (ans % MOD);
    }

}
