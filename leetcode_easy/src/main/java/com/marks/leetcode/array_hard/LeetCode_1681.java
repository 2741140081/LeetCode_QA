package com.marks.leetcode.array_hard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1681 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/31 11:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1681 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k 。
     * 你需要将这个数组划分到 k 个相同大小的子集中，使得同一个子集里面没有两个相同的元素。
     * 一个子集的 不兼容性 是该子集里面最大值和最小值的差。
     * 请你返回将数组分成 k 个子集后，各子集 不兼容性 的 和 的 最小值 ，如果无法分成分成 k 个子集，返回 -1 。
     * 子集的定义是数组中一些数字的集合，对数字顺序没有要求。
     *
     * tips:
     * 1 <= k <= nums.length <= 16
     * nums.length 能被 k 整除。
     * 1 <= nums[i] <= nums.length
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/07/31 11:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumIncompatibility(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 查看官方题解, 使用状态压缩 + 动态规划
     * AC: 348ms/48.5MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/07/31 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int k) {
        int n = nums.length, group = n / k, inf = Integer.MAX_VALUE;
        int[] dp = new int[1 << n];
        Arrays.fill(dp, inf);
        dp[0] = 0;
        // 预处理: value 存储 mask -> (max - min) 对应关系
        HashMap<Integer, Integer> values = new HashMap<>();

        for (int mask = 1; mask < (1 << n); mask++) {
            if (Integer.bitCount(mask) != group) {
                continue;
            }
            // 处理1的数量 = group 的情况
            int mn = 20, mx = 0;
            HashSet<Integer> cur = new HashSet<>();
            // 更新在 mask 的情况下, 最大值与最小值的差值
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) > 0) {
                    // 如果包含重复数字, 则跳过
                    if (cur.contains(nums[i])) {
                        break;
                    }
                    cur.add(nums[i]);
                    mn = Math.min(mn, nums[i]);
                    mx = Math.max(mx, nums[i]);
                }
            }
            if (cur.size() == group) {
                values.put(mask, mx - mn);
            }
        }

        for (int mask = 0; mask < (1 << n); mask++) {
            if (dp[mask] == inf) {
                continue;
            }
            HashMap<Integer, Integer> seen = new HashMap<>();
            // 查找所有未使用的nums[i]
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) == 0) {
                    seen.put(nums[i], i);
                }
            }
            // 如果未使用的数字大小小于 group, 即无法构成一个子集, 则跳过
            if (seen.size() < group) {
                continue;
            }
            int sub = 0;
            for (int v : seen.values()) {
                sub |= (1 << v);
            }
            // 下一个子集
            int nxt = sub;
            while (nxt > 0) {
                // 是否是有效分组
                if (values.containsKey(nxt)) {
                    dp[mask | nxt] = Math.min(dp[mask | nxt], dp[mask] + values.get(nxt));
                }
                nxt = (nxt - 1) & sub; // 枚举 sub 的所有非空子集
            }
        }

        return (dp[(1 << n) - 1] < inf) ? dp[(1 << n) - 1] : -1;
    }


    private int maxCapacity;
    private int ans;
    /**
     * @Description:
     * 1. 先排序, 然后计算相同数字的最大数量, 如果数量超过 k, 则返回 -1, 即不满足子集中存在两个相同数字
     * 2. 由于数据量不大, 所以使用回溯 + 枚举的办法, int[][] memo = new int[k][3] 分别存储 k 个子集的最小值, 最大值, 数量
     * 3. 感觉回溯会超时, 时间复杂度太高了
     * 4. 应该使用状态压缩
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/07/31 11:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int maxSame = 0;
        int cnt = 1;
        Arrays.sort(nums);
        for (int i = 1; i < n; i++) {
            if (nums[i] == nums[i - 1]) {
                cnt++;
                maxSame = Math.max(maxSame, cnt);
            } else {
                cnt = 1;
            }
        }
        if (maxSame > k) {
            return -1;
        }
        maxCapacity = n / k; // 每个子集的最大容量
        if (maxCapacity == 1) {
            // 如果子集容量为1, 那么每个子集的不兼容性 = 0, 总和为 0
            return 0;
        }
        int[][] memo = new int[k][3]; // {min, max, cnt}
        ans = Integer.MAX_VALUE / 2;
        backTrack(0, nums, memo);
        return ans;
    }

    private void backTrack(int index, int[] nums, int[][] memo) {
        if (index == nums.length) {
            // 计算此时不兼容性值
            int sum = 0;
            for (int[] ints : memo) {
                sum += (ints[1] - ints[0]);
            }
            ans = Math.min(ans, sum);
            return;
        }
        // 处理 第 i 个数字, 将其分配给 k 个子集中的某一个
        for (int i = 0; i < memo.length; i++) {
            if (memo[i][2] < maxCapacity) {
                int prev = memo[i][1];
                boolean isMin = false;
                // 没有到达上限, 可以继续添加
                if (memo[i][0] != 0) {
                    // 已经存在最小值
                    memo[i][1] = nums[index];
                } else {
                    isMin = true;
                    memo[i][0] = nums[index]; // 设置当前值为最小值
                }
                memo[i][2]++;
                // 递归执行下一个数
                backTrack(index + 1, nums, memo);
                // 执行复原操作
                if (isMin) {
                    // 执行的是 min 的更新
                    memo[i][0] = 0;
                } else {
                    memo[i][1] = prev;
                }
                memo[i][2]--;
            }
        }
    }

}
