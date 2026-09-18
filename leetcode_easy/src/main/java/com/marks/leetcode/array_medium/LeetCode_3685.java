package com.marks.leetcode.array_medium;

import java.util.Arrays;
import java.util.BitSet;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3685 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/18 9:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3685 {

    /**
     * @Description:
     * 给你一个大小为 n 的整数数组 nums 和一个正整数 k。
     * 通过将每个元素 nums[i] 替换为 min(nums[i], x)，可以得到一个由值 x 限制（capped）的数组。
     * 对于从 1 到 n 的每个整数 x，确定是否可以从由 x 限制的数组中选择一个 子序列，使所选元素的和 恰好 为 k。
     * 返回一个下标从 0 开始的布尔数组 answer，其大小为 n，
     * 其中 answer[i] 为 true 表示当 x = i + 1 时可以选出满足要求的子序列；否则为 false。
     * 子序列 是一个从数组中通过删除一些或不删除任何元素（且不改变剩余元素顺序）派生出来的 非空 数组。
     *
     * tips:
     * 1 <= n == nums.length <= 4000
     * 1 <= nums[i] <= n
     * 1 <= k <= 4000
     * @param: nums
     * @param: k
     * @return boolean[]
     * @author marks
     * @CreateDate: 2026/09/18 9:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean[] subsequenceSumAfterCapping(int[] nums, int k) {
        boolean[] result;
//        result = method_01(nums, k);
//        result = method_02(nums, k);
        result = method_03(nums, k);
        return result;
    }

    private boolean[] method_03(int[] nums, int k) {
        Arrays.sort(nums);

        int n = nums.length;
        boolean[] ans = new boolean[n];
        boolean[] f = new boolean[k + 1];
        f[0] = true; // 不选元素，和为 0

        int i = 0;
        for (int x = 1; x <= n; x++) {
            // 增量地考虑所有恰好等于 x 的数
            // 小于 x 的数在之前的循环中已计算完毕，无需重复计算
            while (i < n && nums[i] == x) {
                for (int j = k; j >= nums[i]; j--) { // 倒序遍历 k ~ nums[i]
                    f[j] = f[j] || f[j - nums[i]]; // 0-1 背包：不选 or 选
                }
                i++;
            }

            // 枚举（从大于 x 的数中）选了 j 个 x
            for (int j = 0; j <= Math.min(n - i, k / x); j++) {
                if (f[k - j * x]) {
                    ans[x - 1] = true;
                    break;
                }
            }
        }
        return ans;
    }

    /**
     * @Description:
     * 1. 由于受到 x 的限制数组, 对于小于 x 的元素, 不会变更其值, 对于大于等于 x 的数, 会变成 x
     * 2. 可以先对 nums[] 数组进行升序排序, 并且构建一个前缀和数组 prefixSum[]
     * 3. 如何在一个 int[] 数组种, 找到一个子序列, 使得子序列之和恰好为 k?
     * 4. 使用动态规划解决子序列和问题
     * 超时: 751/794, 需要对 x 进行剪枝操作
     * @param: nums
     * @param: k
     * @return boolean[]
     * @author marks
     * @CreateDate: 2026/09/18 9:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean[] method_01(int[] nums, int k) {
        int n = nums.length;
        boolean[] ans = new boolean[n];
        Arrays.sort(nums);
        for (int x = 1; x <= n; x++) {
            // 构建dp数组
            ans[x - 1] = hasSubsequenceSumBitSet(nums, k, x);

        }
        return ans;
    }

    public boolean hasSubsequenceSumBitSet(int[] nums, int k, int x) {
        // bits[i] == true 表示和 i 可以达到
        BitSet bits = new BitSet(k + 1);
        bits.set(0); // 和为0总是存在

        for (int num : nums) {
            int currNum = Math.min(num, x);
            if (num > k) break; // 升序数组，后续无用
            // 临时存储新增的和，避免并发修改异常或逻辑错误
            BitSet newBits = new BitSet(k + 1);

            // 遍历当前 bits 中所有为 true 的位
            // nextSetBit(i) 返回从索引 i 开始下一个为 true 的位的索引，如果没有则返回 -1
            for (int i = bits.nextSetBit(0); i >= 0; i = bits.nextSetBit(i + 1)) {
                int newSum = i + currNum;
                if (newSum <= k) {
                    newBits.set(newSum);
                } else {
                    break; // 因为 bits 是按顺序遍历的，如果 i+num > k，后续更大的 i 也会超过 k
                }
            }

            // 将新产生的和合并到主集合中 (OR 操作)
            bits.or(newBits);

            // 提前终止
            if (bits.get(k)) {
                return true;
            }
        }

        return bits.get(k);
    }

    /**
     * @Description:
     * 优化思路：
     * 1. 排序后，对于每个 x，数组分为前缀(< x, 原值)和后缀(>= x,  capped为 x)
     * 2. 预计算前缀子集和 DP：dp[i] 表示用 nums[0..i-1] 能凑出的所有和
     * 3. 查询时，后缀全选 x，枚举后缀选取个数 j，检查 k - j*x 是否在前缀 DP 中
     * 时间复杂度: O(n * k/64 + n * H(n))，H(n) 为调和级数
     * AC: 698ms/53.1MB
     * @param: nums
     * @param: k
     * @return boolean[]
     * @author marks
     * @CreateDate: 2026/09/18
     */
    private boolean[] method_02(int[] nums, int k) {
        int n = nums.length;
        boolean[] ans = new boolean[n];
        Arrays.sort(nums);

        BitSet[] dp = new BitSet[n + 1];
        dp[0] = new BitSet(k + 1);
        dp[0].set(0);

        for (int i = 0; i < n; i++) {
            dp[i + 1] = (BitSet) dp[i].clone();
            int num = nums[i];
            if (num <= k) {
                for (int s = dp[i].nextSetBit(0); s >= 0; s = dp[i].nextSetBit(s + 1)) {
                    int ns = s + num;
                    if (ns <= k) {
                        dp[i + 1].set(ns);
                    } else {
                        break;
                    }
                }
            }
        }

        for (int x = 1; x <= n; x++) {
            int split = lowerBound(nums, x);
            int cappedCount = n - split;
            BitSet sums = (BitSet) dp[split].clone();

            boolean found = false;
            for (int j = 0; j <= cappedCount && (long) j * x <= k; j++) {
                if (sums.get(k - j * x)) {
                    found = true;
                    break;
                }
            }
            ans[x - 1] = found;

            if (x > k) {
                while (x <= n) {
                    ans[x - 1] = found;
                    x++;
                }
            }
        }

        return ans;
    }

    private int lowerBound(int[] nums, int target) {
        int lo = 0, hi = nums.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

}
