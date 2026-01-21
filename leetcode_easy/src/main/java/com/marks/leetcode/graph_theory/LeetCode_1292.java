package com.marks.leetcode.graph_theory;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1292 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/19 14:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1292 {

    /**
     * @Description:
     * 给你一个大小为 m x n 的矩阵 mat 和一个整数阈值 threshold。
     * 请你返回元素总和小于或等于阈值的正方形区域的最大边长；如果没有这样的正方形区域，则返回 0 。
     * tips:
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n <= 300
     * 0 <= mat[i][j] <= 10^4
     * 0 <= threshold <= 10^5
     * @param: mat
     * @param: threshold
     * @return int
     * @author marks
     * @CreateDate: 2026/01/19 14:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxSideLength(int[][] mat, int threshold) {
        int result;
        result = method_01(mat, threshold);
        return result;
    }

    /**
     * @Description:
     * 1. 每一行可以用前缀和来进行优化, 假设正方形的左上角顶点为 (i, j), 可以得到的最大正方形边长是多少?
     * 2. int m, n, 边长是 Math.min(m - i, n - j) 然后用二分法求解当前边长mid 是否满足条件?
     * AC: 67ms/56.45MB
     * @param: mat
     * @param: threshold
     * @return int
     * @author marks
     * @CreateDate: 2026/01/19 14:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] mat, int threshold) {
        int m = mat.length;
        int n = mat[0].length;
        // 前缀和
        int[][] preSum = new int[m][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                preSum[i][j + 1] = preSum[i][j] + mat[i][j];
            }
        }
        // 算了只处理横向的前缀和
        int ans = 0; // 最大边长
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int left = 0;
                int right = Math.min(m - i, n - j); // m = 3, n = 7;
                if (right < ans) {
                    // 剪枝
                    continue;
                }
                while (left <= right) {
                    int mid = left + (right - left) / 2; // mid = 1;
                    int sum = sumArea(preSum, i, j, mid);
                    if (sum <= threshold) {
                        ans = Math.max(ans, mid);
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
        }

        return ans;
    }

    private int sumArea(int[][] preSum, int i, int j, int mid) {
        int sum = 0;
        for (int k = i; k < i + mid; k++) {
            sum += preSum[k][j + mid] - preSum[k][j];
        }
        return sum;
    }

}
