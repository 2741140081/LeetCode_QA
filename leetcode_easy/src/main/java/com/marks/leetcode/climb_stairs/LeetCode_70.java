package com.marks.leetcode.climb_stairs;

public class LeetCode_70 {
    /**
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * E1:
     * 输入：n = 2
     * 输出：2
     * 解释：有两种方法可以爬到楼顶。
     * 1. 1 阶 + 1 阶
     * 2. 2 阶
     *
     * E2:
     * 输入：n = 3
     * 输出：3
     * 解释：有三种方法可以爬到楼顶。
     * 1. 1 阶 + 1 阶 + 1 阶
     * 2. 1 阶 + 2 阶
     * 3. 2 阶 + 1 阶
     *
     * E3:
     * 输入：n = 4
     * 输出：5
     * 1. 1 + 1 + 1 + 1
     * 2. 1 + 2 + 1
     * 3. 1 + 1 + 2
     * 4. 2 + 1 + 1
     * 5. 2 + 2
     * @param n
     * @return int
     */
    public int climbStairs(int n) {
        int result = 0;
        int[] nums = new int[n + 1];
//        result = method_01(n, nums);
//        result = method_02(n);
        result = method_04(n);
        return result;
    }




    /**
     * f1 = 1
     * f2 = f1 + 1 = 2
     * f3 = f2 + f1 = 3
     * f4 = f3 + f2 = 5
     * f5 = f4 + f3 = 8
     * 递归
     *
     * 假设 n=9。
     * 我们要解决的问题是从 0 爬到 9 有多少种不同的方法。
     * 分类讨论：
     * 如果最后一步爬了 1 个台阶，那么我们得先爬到 8，要解决的问题缩小成：从 0 爬到 8 有多少种不同的方法。
     * 如果最后一步爬了 2 个台阶，那么我们得先爬到 7，要解决的问题缩小成：从 0 爬到 7 有多少种不同的方法
     *
     * @param n
     * @return
     */
    private int method_01(int n, int[] nums) {
        if (n == 1 || n == 0) {
            return 1;
        }
        if (nums[n] != 0) {
            return nums[n];
        }
        return nums[n] = method_01(n - 1, nums) + method_01(n - 2, nums);
    }

    private int method_02(int n) {
        if (n <= 1) {
            return 1;
        }
        return method_02(n - 1) + method_02(n - 2);
    }

    private int method_03(int n) {
        int[] nums = new int[n + 1];
        nums[0] = 1;
        nums[1] = 1;
        for (int i = 2; i < nums.length; i++) {
            nums[i] = nums[i - 1] + nums[i - 2];
        }

        return nums[n];
    }

    /**
     * 如果每次可以爬 1 或 2 或 3 个台阶
     * 求解次数
     * f0 = 1
     * f1 = 1
     * f2 = 2
     * f3 = 4
     * @param n
     * @return
     */
    private int method_04(int n) {
        if (n <= 1) {
            return 1;
        }
        int[] nums = new int[n+1];
        nums[0] = 1;
        nums[1] = 1;
        nums[2] = 2;
        for (int i = 3; i <= n; i++) {
            nums[i] = nums[i-1] + nums[i-2] + nums[i-3];
        }
        return nums[n];
    }
}
