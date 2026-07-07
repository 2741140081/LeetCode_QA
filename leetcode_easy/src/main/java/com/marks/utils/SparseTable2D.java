package com.marks.utils;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: SparseTable2D </p>
 * <p>描述: ST表: 稀疏表 </p>
 * ST表（Sparse Table）是一种基于倍增思想的数据结构，主要用于解决静态区间最值查询（RMQ）问题。
 * 对于二维矩阵的子矩阵最大值查询，其核心思想是将一维的倍增扩展到二维，通过预处理将任意子矩阵分解为四个有重叠但能完全覆盖该区域的子矩阵，
 * 从而在O(1)O(1) 时间内完成查询。
 * @author marks
 * @version v1.0
 * @date 2026/7/7 11:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class SparseTable2D {
    private int[][][][] st;
    private int[] logTable;
    private int n, m;

    public SparseTable2D(int[][] matrix) {
        this.n = matrix.length - 1;
        this.m = matrix[0].length - 1;

        int maxLogN = (int)(Math.log(n) / Math.log(2)) + 1;
        int maxLogM = (int)(Math.log(m) / Math.log(2)) + 1;
        st = new int[n + 1][m + 1][maxLogN][maxLogM];

        int maxVal = Math.max(n, m);
        logTable = new int[maxVal + 1];
        for (int i = 2; i <= maxVal; i++) {
            logTable[i] = logTable[i >> 1] + 1;
        }

        build(matrix);
    }

    private void build(int[][] matrix) {
        // 初始化 k=0, h=0
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                st[i][j][0][0] = matrix[i][j];
            }
        }

        int maxK = logTable[n];
        int maxH = logTable[m];

        // 行方向倍增
        for (int k = 1; k <= maxK; k++) {
            for (int i = 1; i + (1 << k) - 1 <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    st[i][j][k][0] = Math.max(
                            st[i][j][k - 1][0],
                            st[i + (1 << (k - 1))][j][k - 1][0]
                    );
                }
            }
        }

        // 列方向倍增
        for (int h = 1; h <= maxH; h++) {
            for (int k = 0; k <= maxK; k++) {
                for (int i = 1; i + (1 << k) - 1 <= n; i++) {
                    for (int j = 1; j + (1 << h) - 1 <= m; j++) {
                        st[i][j][k][h] = Math.max(
                                st[i][j][k][h - 1],
                                st[i][j + (1 << (h - 1))][k][h - 1]
                        );
                    }
                }
            }
        }
    }

    public int queryMax(int x1, int y1, int x2, int y2) {
        int k = logTable[x2 - x1 + 1];
        int h = logTable[y2 - y1 + 1];

        int v1 = st[x1][y1][k][h];
        int v2 = st[x1][y2 - (1 << h) + 1][k][h];
        int v3 = st[x2 - (1 << k) + 1][y1][k][h];
        int v4 = st[x2 - (1 << k) + 1][y2 - (1 << h) + 1][k][h];

        return Math.max(Math.max(v1, v2), Math.max(v3, v4));
    }
}
