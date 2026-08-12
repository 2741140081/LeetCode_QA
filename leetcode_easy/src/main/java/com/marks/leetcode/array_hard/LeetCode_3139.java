package com.marks.leetcode.array_hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3139 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/11 15:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3139 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和两个整数 cost1 和 cost2 。你可以执行以下 任一 操作 任意 次：
     * 从 nums 中选择下标 i 并且将 nums[i] 增加 1 ，开销为 cost1。
     * 选择 nums 中两个 不同 下标 i 和 j ，并且将 nums[i] 和 nums[j] 都 增加 1 ，开销为 cost2 。
     * 你的目标是使数组中所有元素都 相等 ，请你返回需要的 最小开销 之和。
     * 由于答案可能会很大，请你将它对 10^9 + 7 取余 后返回。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^6
     * 1 <= cost1 <= 10^6
     * 1 <= cost2 <= 10^6
     * @param: nums
     * @param: cost1
     * @param: cost2
     * @return int
     * @author marks
     * @CreateDate: 2026/08/11 15:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minCostToEqualizeArray(int[] nums, int cost1, int cost2) {
        int result;
        result = method_01(nums, cost1, cost2);
        result = method_02(nums, cost1, cost2);
        return result;
    }

    private int method_02(int[] nums, int c1, int c2) {
        final int MOD = 1_000_000_007;
        long n = nums.length;
        int m = Integer.MAX_VALUE;
        int M = Integer.MIN_VALUE;
        long sum = 0;
        for (int x : nums) {
            m = Math.min(m, x);
            M = Math.max(M, x);
            sum += x;
        }

        long base = n * M - sum;
        if (n <= 2 || c1 * 2 <= c2) {
            return (int) (base * c1 % MOD);
        }

        int i = (int) ((n * M - m * 2 - base + n - 3) / (n - 2));
        long res1 = f(M, base, n, m, M, c1, c2);
        long res2 = f(M + 1, base, n, m, M, c1, c2);
        long res3 = f(i - 1, base, n, m, M, c1, c2);
        long res4 = f(i, base, n, m, M, c1, c2);
        long res5 = f(i + 1, base, n, m, M, c1, c2);
        return (int) (i <= M ? Math.min(res1, res2) % MOD :
                Math.min(Math.min(Math.min(res1, res3), res4), res5) % MOD);
    }

    private long f(int x, long base, long n, int m, int M, int c1, int c2) {
        long s = base + (x - M) * n;
        int d = x - m;
        return Math.max(s / 2 * c2 + s % 2 * c1, (s - d) * c2 + (d * 2 - s) * c1);
    }


    /**
     * @Description:
     * 1. 由于只能增加值, 所以最终元素相等值是 max,
     * 2. 可以计算得到每个数需要提高的数量, 得到一个数组 int[] increase,
     * 然后会有两种方案, 只使用 cost1 和 使用 oost2 + cost1 的组合
     * 3. 然后需要将 increase 数组分割成两组, 使得两组的差值最小, 这应该算是一个分组 dp,
     * 这个不就类似于最高广告牌那题, 但是这个复杂度
     * todo
     * @param: nums
     * @param: cost1
     * @param: cost2
     * @return int
     * @author marks
     * @CreateDate: 2026/08/11 15:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int cost1, int cost2) {
        int n = nums.length;
        int MOD = (int) 1e9 + 7;
        int max = Arrays.stream(nums).max().getAsInt();
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            int sub = max - num;
            if (sub > 0) {
                list.add(sub);
            }
        }
        // 对 list 进行排序
        list.sort(Integer::compareTo);
        // 如果 cost2 >= 2 * cost1
        long ans = 0;
        if (cost2 >= 2 * cost1) {
            // 全部采用 cost1执行
            for (int i = 0; i < list.size(); i++) {
                ans = (ans + (long) list.get(i) * cost1) % MOD;
            }
        } else {
            // 使用双指针, 分别指向 left 和 right, 在 left != right 时, 使用 cost2 方式
            int left = 0, right = list.size() - 1;
            while (left < right) {
                int c1 = list.get(left);
                int c2 = list.get(right);
                if (c1 < c2) {
                    ans = (ans + (long) c1 * cost2) % MOD;
                    c2 -= c1;
                    // 赋值给 right
                    list.set(right, c2);
                    left++;
                } else if (c1 > c2) {
                    ans = (ans + (long) c2 * cost2) % MOD;
                    c1 -= c2;
                    list.set(left, c1);
                    right--;
                } else {
                    ans = (ans + (long) c1 * cost2) % MOD;
                    left++;
                    right--;
                }
            }
            // 处理剩余的left
            if (list.get(left) != 0) {
                int cnt = list.get(left);
                // 此时使用 cost1
                long s1 =(long) cnt * cost1;
                // 或者使用 cost2, 并且提高 max, 即 max 每提高1, 需要消耗 n - 1, cnt - (n - 1)
                int k = cnt / (n - 1); // 提高到 max + k.
                long s2 = 0;
                s2 = (s2 + (long) (n - 1) * k * cost2) % MOD;
                cnt = cnt % (n - 1) + k; // 需要再次提高 k 的数量
            }
        }

        return (int) (ans % MOD);
    }

}
