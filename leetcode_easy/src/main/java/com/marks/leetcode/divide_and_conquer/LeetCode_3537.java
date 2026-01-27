package com.marks.leetcode.divide_and_conquer;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3537 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/27 10:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3537 {

    /**
     * @Description:
     * 给你一个非负整数 N，表示一个 2^N x 2^N 的网格。你需要用从 0 到 (2^2N) - 1 的整数填充网格，使其成为一个 特殊 网格。
     * 一个网格当且仅当满足以下 所有 条件时，才能称之为 特殊 网格：
     *
     * 右上角象限中的所有数字都小于右下角象限中的所有数字。
     * 右下角象限中的所有数字都小于左下角象限中的所有数字。
     * 左下角象限中的所有数字都小于左上角象限中的所有数字。
     * 每个象限也都是一个特殊网格。
     * 返回一个 2^N x 2^N 的特殊网格。
     *
     * 注意：任何 1x1 的网格都是特殊网格。
     * tips:
     * 0 <= N <= 10
     * @param: n
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/01/27 10:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] specialGrid(int n) {
        int[][] result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 1. 有一个数组 int[] nums = new int[2^2N]; 存储从 0 到 (2^2N) - 1 的整数
     * 2. 将整个数组分成均匀的4份, 分别存储在右上角, 右下角, 左下角, 左上角, 怎么计算分配的下标?
     * 3. 假设 n = 3, 那么数组长度为 2^6 = 64
     * AC: 7ms/106.98MB
     * @param: n
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/01/27 10:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int value = 0; // 值从0开始递增
    private int[][] method_01(int n) {
        int[][] ans = new int[1 << n][1 << n];
        dfs(ans, 0, 0, 1 << n);
        return ans;
    }

    private void dfs(int[][] ans, int up, int left, int right) {
        if (right - left == 1) {
            ans[up][left] = value++;
            return;
        }
        int mid = (right - left) / 2; // 可以被整除
        // 右上角
        dfs(ans, up, left + mid, right);
        // 右下角
        dfs(ans, up + mid, left + mid, right);
        // 左下角
        dfs(ans, up + mid, left, left + mid);
        // 左上角
        dfs(ans, up, left, left + mid);
    }

}
