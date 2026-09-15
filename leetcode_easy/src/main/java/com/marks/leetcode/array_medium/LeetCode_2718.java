package com.marks.leetcode.array_medium;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2718 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/14 15:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2718 {

    /**
     * @Description:
     * 给你一个整数 n 和一个下标从 0 开始的 二维数组 queries ，其中 queries[i] = [typei, indexi, vali] 。
     * 一开始，给你一个下标从 0 开始的 n x n 矩阵，所有元素均为 0 。每一个查询，你需要执行以下操作之一：
     * 如果 typei == 0 ，将第 indexi 行的元素全部修改为 vali ，覆盖任何之前的值。
     * 如果 typei == 1 ，将第 indexi 列的元素全部修改为 vali ，覆盖任何之前的值。
     * 请你执行完所有查询以后，返回矩阵中所有整数的和。
     * @param: n
     * @param: queries
     * @return long
     * @author marks
     * @CreateDate: 2026/09/14 15:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long matrixSumQueries(int n, int[][] queries) {
        long result;
        result = method_01(n, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 既然是覆盖型赋值操作, 那么就需要从后往前遍历
     * 2. 处理第 i 个查询时, 如果 type == 0, 只需要判断 boolean[] rows, rows[i] 是否为 true,
     * 如果为 true, 则表示 i 已经处理了, 跳过, 如果为 false, 则更新 rows[i] = true, 并且将 val_i * n 添加到结果中
     * 3. 需要额外两个 boolean[] 数组, 分别表示行和列是否已经处理过
     * 4. 存在问题, 即需要得到还有多少个未处理的行和列, 以便计算 val_i * n 的时候, 能够正确计算
     * AC: 4ms/102.3MB
     * @param: n
     * @param: queries
     * @return long
     * @author marks
     * @CreateDate: 2026/09/14 15:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int n, int[][] queries) {
        int m = queries.length;
        long ans = 0;
        boolean[] rows = new boolean[n];
        boolean[] cols = new boolean[n];
        int rowUnprocessed = n;
        int colUnprocessed = n;
        for (int i = m - 1; i >= 0; i--) {
            int type = queries[i][0];
            int index = queries[i][1];
            int val = queries[i][2];
            if (type == 0) {
                if (!rows[index]) {
                    rows[index] = true;
                    ans += (long) val * colUnprocessed;
                    rowUnprocessed--;
                }
            } else {
                if (!cols[index]) {
                    cols[index] = true;
                    ans += (long) val * rowUnprocessed;
                    colUnprocessed--;
                }
            }
        }
        return ans;
    }

}
