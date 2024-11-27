package com.marks.leetcode.binary_algorithm;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/27 11:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_378 {
    
    /**
     * @Description: [
     * 给你一个 n x n 矩阵 matrix ，其中每行和每列元素均按升序排序，找到矩阵中第 k 小的元素。
     * 请注意，它是 排序后 的第 k 小元素，而不是第 k 个 不同 的元素。
     *
     * 你必须找到一个内存复杂度优于 O(n^2) 的解决方案。
     *
     * tips:
     * n == matrix.length
     * n == matrix[i].length
     * 1 <= n <= 300
     * -10^9 <= matrix[i][j] <= 10^9
     * 题目数据 保证 matrix 中的所有行和列都按 非递减顺序 排列
     * 1 <= k <= n2
     * ]
     * @param matrix 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/27 11:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int kthSmallest(int[][] matrix, int k) {
        int result;
        result = method_01(matrix, k);
//        result = method_02(matrix, k);
        return result;
    }

    /**
     * @Description: [官方题解:归并排序
     * 1, 5, 9
     * 10, 11, 13
     * 12, 13, 15
     *
     * AC:23ms/46.82MB
     * ]
     * @param matrix
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/11/27 15:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] matrix, int k) {
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            queue.offer(new int[]{matrix[i][0], i, 0});
        }
        for (int i = 0; i < k - 1; i++) {
            int[] now = queue.poll();
            if (now[2] != n - 1) {
                queue.offer(new int[]{matrix[now[1]][now[2] + 1], now[1], now[2] + 1});
            }
        }
        return queue.poll()[0];
    }

    /**
     * @Description: [
     * E1:
     * 输入：matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
     * 输出：13
     * 解释：矩阵中的元素为 [1,5,9,10,11,12,13,13,15]，第 8 小元素是 13
     * 1, 5, 9
     * 10, 11, 13
     * 12, 13, 15
     * 1. 需要复杂度低于O(n^2)的算法, 所以选择二分法
     * 2. matrix 是一个n * n的二维数组
     * 3. 找到 排序后 的第 k 小元素
     * 4. 感觉用left = matrix[0][0], right = matrix[n - 1][n - 1] 边界二分不是很方便
     * 5. 是否可以用left = 0, right = 0, leftMax = n - 1, rightMax = n - 1;
     * AC:0ms/49.16MB
     * ]
     * @param matrix
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/11/27 14:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] matrix, int k) {
        int n = matrix.length;
        int left = matrix[0][0];
        int right = matrix[n - 1][n - 1];
        while (left < right) {
            int mid = (right - left) / 2 + left;
            if (checkMidthSmallest(mid, matrix, k, n)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * @Description: [功能描述]
     * @param mid
     * @param matrix
     * @param k
     * @param n
     * @return boolean
     * @author marks
     * @CreateDate: 2024/11/27 15:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean checkMidthSmallest(int mid, int[][] matrix, int k, int n) {
        int i = n - 1, j = 0; // 从左下角开始
        int num = 0;
        while (i >= 0 && j < n) {
            if (matrix[i][j] <= mid) {
                num += i + 1;
                j++;
            } else {
                i--;
            }
        }
        return num >= k;
    }
}
