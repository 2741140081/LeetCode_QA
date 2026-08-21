package com.marks.leetcode.array_hard;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3116 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/21 15:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3116 {

    /**
     * @Description:
     * 给你一个整数数组 coins 表示不同面额的硬币，另给你一个整数 k 。
     * 你有无限量的每种面额的硬币。但是，你 不能 组合使用不同面额的硬币。
     * 返回使用这些硬币能制造的 第 kth 小 金额。
     * tips:
     * 1 <= coins.length <= 15
     * 1 <= coins[i] <= 25
     * 1 <= k <= 2 * 10^9
     * coins 包含两两不同的整数。
     * @param: coins
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/08/21 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long findKthSmallest(int[] coins, int k) {
        long result;
        result = method_01(coins, k);
        return result;
    }

    /**
     * @Description:
     * 1. k 是一个特别大的数, 应该使用二分法来处理, 假设coins[i] = 1, 那么有 k 个硬币时
     * 2. 对 coins[] 进行排序操作, 金额小于等于 coins[i] 构成的序列可以组合成一个 kth大小的集合 硬币面值数组
     * 3. 需要剔除重复元素, 相当于是贡献法 目标值
     * @param: coins 返回第k小的组合金额
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/08/21 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
    // 对硬币面值数组进行排序，便于后续处理
     */
    private long method_01(int[] coins, int k) {
        // m 是硬币面值数组的子集数量，使用位掩码表示
        Arrays.sort(coins);
        int n = coins.length;
        // 初始化二分查找的左右边界
        int m = 1 << n;  // 左边界
        // 右边界
        long l = k;
        // bitCount数组用于记录每个子集包含的硬币数量
        long r = (long) coins[0] * k + 1;
        // lcm数组用于记录每个子集的最小公倍数
        int[] bitCount = new int[m];
        // 预处理所有子集的最小公倍数和硬币数量
        long[] lcm = new long[m];
        // 当前子集的最小公倍数
        for (int mask = 1; mask < m; mask++) {
            // 检查当前硬币是否在子集中
            long curLcm = 1;
            // 计算当前子集的最小公倍数
            for (int i = 0; i < n; i++) {
                if ((mask >> i & 1) == 1) {
                    long g = gcd(curLcm, coins[i]);
                    // 检查是否会溢出
                    long tmp = curLcm / g;

                    if (tmp <= r / coins[i]) {
                        curLcm = tmp * coins[i];  // 标记为溢出
                    } else {
                        curLcm = r + 1;
                        break;
                    }
                    // 更新子集的硬币数量
                    bitCount[mask]++;
                }
            }
            lcm[mask] = curLcm;
        }
        // 使用二分查找确定第k小的组合金额
        while (l < r) {
            long x = l + (r - l) / 2; // 中间值
            // 计算小于等于x的组合数量
            if (count(x, m, lcm, bitCount) >= k) {
                r = x;  // 调整右边界
            } else {
                l = x + 1; // 调整左边界
            }
        }
        return l; // 返回第k小的组合金额
    }

    private long count(long x, int m, long[] lcm, int[] bitCount) {
        long res = 0;
        for (int mask = 1; mask < m; mask++) {
            if (lcm[mask] > x) continue;

            if ((bitCount[mask] & 1) == 1) {
                res += x / lcm[mask];
            } else {
                res -= x / lcm[mask];
            }
        }
        return res;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

}
