package com.marks.leetcode.binary_algorithm;

import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/27 10:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_668 {
    /**
     * @Description: [
     * 几乎每一个人都用 乘法表。但是你能在乘法表中快速找到第 k 小的数字吗？
     *
     * 乘法表是大小为 m x n 的一个整数矩阵，其中 mat[i][j] == i * j（下标从 1 开始）。
     *
     * 给你三个整数 m、n 和 k，请你在大小为 m x n 的乘法表中，找出并返回第 k 小的数字。
     *
     * tips:
     * 1 <= m, n <= 3 * 10^4
     * 1 <= k <= m * n
     * ]
     * @param m
     * @param n
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/11/27 10:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findKthNumber(int m, int n, int k) {
        int result;
        result = method_01(m, n, k);
        result = method_02(m, n, k);
        return result;
    }

    /**
     * @Description: [
     * 官方题解:
     * AC:8ms/39.57MB
     * ]
     * @param m
     * @param n
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/11/27 10:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int m, int n, int k) {
        int left = 1, right = m * n;
        while (left < right) {
            int x = left + (right - left) / 2;
            int count = x / n * n;
            for (int i = x / n + 1; i <= m; ++i) {
                count += x / i;
            }
            if (count >= k) {
                right = x;
            } else {
                left = x + 1;
            }
        }
        return left;
    }

    /**
     * @Description: [
     * 找第 k 小的数, 总计有 int sum = m * n 个数,
     * 第 k 小, 相当于 sum - k + 1 大的数,  + 1是因为要包含第 k 个数
     * 超时:59/70!!!
     * 找第 K 大的, 简单/中等题用优先队列, Hard题用二分法
     * ]
     * @param m
     * @param n
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/11/27 10:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int m, int n, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(); // 优先队列存储[k, sum]的数, 其它数[1, k - 1], 总数是sum
        int size = m * n - k + 1; // 设置优先队列大小
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                queue.offer(i * j);
                if (queue.size() > size) {
                    queue.poll(); // 获取并删除队列头部元素, 即queue中最小值
                }
            }
        }
        return queue.peek();
    }
}
