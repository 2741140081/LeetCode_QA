package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1975 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/5 10:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1975 {

    /**
     * @Description:
     * 给你一个 n x n 的整数方阵 matrix 。你可以执行以下操作 任意次 ：
     *
     * 选择 matrix 中 相邻 两个元素，并将它们都 乘以 -1 。
     * 如果两个元素有 公共边 ，那么它们就是 相邻 的。
     *
     * 你的目的是 最大化 方阵元素的和。请你在执行以上操作之后，返回方阵的 最大 和。
     * @param: matrix
     * @return long
     * @author marks
     * @CreateDate: 2026/01/05 10:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxMatrixSum(int[][] matrix) {
        long result;
        result = method_01(matrix);
        return result;
    }

    /**
     * @Description:
     * 1. 可以执行任意次的 * -1 操作, 最终只会剩余0 个 或者 1 个负数(根据贪心, 如果有一个负数, 希望是最小的数变成负数)
     * 2. 判断矩阵中负数的个数, 如果是奇数, 那么最终结果为1个负数, 否则为0个负数
     * AC: 5ms/66.89MB
     * @param: matrix
     * @return long
     * @author marks
     * @CreateDate: 2026/01/05 10:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[][] matrix) {
        int count = 0; // 记录负数的个数
        long sum = 0; // 矩阵元素和
        int min = Integer.MAX_VALUE;
        int n = matrix[0].length;
        for (int[] nums : matrix) {
            for (int j = 0; j < n; j++) {
                if (nums[j] < 0) {
                    count++;
                }
                int curr = Math.abs(nums[j]);
                sum += curr;
                min = Math.min(min, curr);

            }
        }
        return count % 2 == 1 ? sum - 2L * min : sum;
    }

}
