package com.marks.leetcode.array_hard;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1340 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/10 10:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1340 {

    /**
     * @Description:
     * 给你一个整数数组 arr 和一个整数 d 。每一步你可以从下标 i 跳到：
     * i + x ，其中 i + x < arr.length 且 0 < x <= d 。
     * i - x ，其中 i - x >= 0 且 0 < x <= d 。
     * 除此以外，你从下标 i 跳到下标 j 需要满足：arr[i] > arr[j] 且 arr[i] > arr[k] ，
     * 其中下标 k 是所有 i 到 j 之间的数字（更正式的，min(i, j) < k < max(i, j)）。
     * 你可以选择数组的任意下标开始跳跃。请你返回你 最多 可以访问多少个下标。
     * 请注意，任何时刻你都不能跳到数组的外面。
     *
     * tips:
     * 1 <= arr.length <= 1000
     * 1 <= arr[i] <= 10^5
     * 1 <= d <= arr.length
     * @param: arr
     * @param: d
     * @return int
     * @author marks
     * @CreateDate: 2026/08/10 10:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxJumps(int[] arr, int d) {
        int result;
        result = method_01(arr, d);
        result = method_02(arr, d);
        return result;
    }

    /**
     * @Description:
     * 1. 优化, 完全不需要使用线段树, 因为 可以从 i 节点, 向两边分别进行扩散,
     * 当 arr[j] > arr[i] 时, 结束当前侧遍历即可
     * AC: 9ms/45.75MB
     * @param: arr
     * @param: d
     * @return int
     * @author marks
     * @CreateDate: 2026/08/10 11:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] arr, int d) {
        int n = arr.length;
        dp = new int[n];
        Arrays.fill(dp, -1);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i] == -1) {
                dp[i] = dfsGetMax(arr, d, i);
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    /**
     * @Description: [方法描述]
     * @param: arr
     * @param: d
     * @param: start
     * @return int
     * @author marks
     * @CreateDate: 2026/08/10 11:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int dfsGetMax(int[] arr, int d, int start) {
        if (dp[start] != -1) {
            return dp[start];
        }
        int ans = 1;
        int left = Math.max(0, start - d);
        for (int i = start - 1; i >= left && arr[i] < arr[start]; i--) {
            ans = Math.max(ans, dfsGetMax(arr, d, i) + 1);
        }
        int right = Math.min(arr.length - 1, start + d);
        for (int i = start + 1; i <= right && arr[i] < arr[start]; i++) {
            ans = Math.max(ans, dfsGetMax(arr, d, i) + 1);
        }
        dp[start] = ans;
        return ans;
    }

    private int[] dp;
    private SegmentTree sTree;

    /**
     * @Description:
     * 1. 每次从 i 跳到 j 需要满足两个条件,
     * a. 1 < Math.abs(i - j) <= d, i, j。
     * b. arr[i] > arr[j] && arr[i] > arr[k]
     * 2. 应该是一个动态规划, 对于节点 i 出发, 可以跳跃的最远距离 int[] dp.
     * 并且定义最低点的 dp[min] = 0, 当到达 min 下标时, 无法继续跳跃
     * 3. 遍历任意 dp[i] == -1, 开始执行遍历, 深度优先搜索 dfs
     * 4. 条件 b, 当不存在 k 时, 也可以执行跳跃, 只需要 arr[i] > arr[j],
     * 如果存在 k, 则必须要保证所有 i ~ j 之间的值都要小于 arr[i]
     * @param: arr
     * @param: d
     * @return int
     * @author marks
     * @CreateDate: 2026/08/10 10:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr, int d) {
        int n = arr.length;
        dp = new int[n];
        // 构建线段树用于查询区间最大值
        sTree = new SegmentTree(arr);
        Arrays.fill(dp, -1);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i] == -1) {
                dp[i] = dfs(arr, d, i);
            }
            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }

    /**
     * @Description:
     * 1. 由于需要从 i 跳跃到 j, 假设 i > j, 需要快速得到 [j + 1, i - i] 这个区间的最大值
     * 2. 针对区间最大值问题, 并且是一个静态不变的区间, 使用线段树提前处理
     * AC: 2285ms/45.6MB
     * @param: arr
     * @param: d
     * @param: start
     * @return int
     * @author marks
     * @CreateDate: 2026/08/10 10:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int dfs(int[] arr, int d, int start) {
        if (dp[start] != -1) {
            return dp[start];
        }

        int ans = 1; // 当前节点也算一个下标
        for (int i = Math.max(start - d, 0); i <= Math.min(start + d, arr.length - 1); i++) {
            if (i != start && arr[i] < arr[start]) {
                int dist = Math.abs(i - start);
                if (dist <= 1) {
                    // 可以进行跳跃
                    ans = Math.max(ans, dfs(arr, d, i) + 1);
                } else {
                    // > 1, 存在 k
                    int left = i > start ? start + 1 : i + 1;
                    int right = i > start ? i - 1 : start - 1;
                    int rangeMax = sTree.queryMax(left, right); // arr[k] 的最大值
                    if (rangeMax < arr[start]) {
                        // 执行跳跃
                        ans = Math.max(ans, dfs(arr, d, i) + 1);
                    }
                }
            }
        }
        dp[start] = ans;
        return ans;
    }


    static class SegmentTree {
        private int n;
        private int[] tree;

        public SegmentTree(int[] nums) {
            this.n = nums.length;
            this.tree = new int[4 * n];

            buildTree(nums, 0, 0, n - 1);
        }

        private void buildTree(int[] nums, int treeIndex, int start, int end) {
            if (start == end) {
                tree[treeIndex] = nums[start];
            } else {
                int mid = (end - start) / 2 + start;
                int leftChildIndex = 2 * treeIndex + 1;
                int rightChildIndex = 2 * treeIndex + 2;

                buildTree(nums, leftChildIndex, start, mid);
                buildTree(nums, rightChildIndex, mid + 1, end);

                // 如果是求区间最大值/最小值，则相应地修改为取max/min操作
                tree[treeIndex] = Math.max(tree[leftChildIndex], tree[rightChildIndex]);
            }
        }

        public int queryMax(int queryStart, int queryEnd) {
            return query(0, 0, n - 1, queryStart, queryEnd);
        }


        public int query(int treeIndex, int start, int end, int queryStart, int queryEnd) {
            if (queryEnd < start || queryStart > end) {
                // 当前查询不在
                return Integer.MIN_VALUE;
            }
            if (queryStart <= start && end <= queryEnd) { // 整个数组
                return tree[treeIndex];
            }

            // 部分重叠，递归查询左右子树
            int mid = (end - start) / 2 + start;
            int leftChildIndex = 2 * treeIndex + 1;
            int rightChildIndex = 2 * treeIndex + 2;

            int leftMax = query(leftChildIndex, start, mid, queryStart, queryEnd);
            int rightMax = query(rightChildIndex, mid + 1, end, queryStart, queryEnd);

            return Math.max(leftMax, rightMax);
        }
    }


}
