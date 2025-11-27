package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1351 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/26 10:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1351 {

    /**
     * @Description:
     * 给你一个 m * n 的矩阵 grid，矩阵中的元素无论是按行还是按列，都以非严格递减顺序排列。 请你统计并返回 grid 中 负数 的数目。
     *
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 100
     * -100 <= grid[i][j] <= 100
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2025/11/26 10:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countNegatives(int[][] grid) {
        int result;
        result = method_01(grid);
        result = method_02(grid);
        return result;
    }

    /**
     * @Description:
     * 倒序遍历, 假设第i行的第j个元素 grid[i][j] < 0, 并且是第一个小于0的元素, 那么同理可得第 i + 1 行的第j个元素 grid[i + 1][j] <= grid[i][j]
     * AC: 0ms/46.05MB
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2025/11/26 10:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int ans = 0;
        int posi = n - 1;
        for (int i = 0; i < m; i++) {
            if (posi == n - 1 && grid[i][posi] >= 0) {
                continue;
            }
            while (posi - 1 >= 0 && grid[i][posi - 1] < 0) {
                posi--;
            }
            // posi - 1 >= 0, posi < 0
            ans += (n - posi);

        }
        return ans;
    }

    /**
     * @Description:
     * 1. 比较好想到的方式是计算每一行中有多少个负数
     * AC: 0ms/46.10MB
     *
     * 继续优化, 因为还有一个条件可以使用, 每一列都是一个非严格递减的数组, 如何使用这个条件优化代码?
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2025/11/26 10:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int ans = 0;
        for (int[] ints : grid) {
            ans += binarySearch(ints, 0, n);
        }
        return ans;
    }

    private int binarySearch(int[] nums, int target, int n) {
        int left = 0;
        int right = n - 1;
        if (nums[right] >= target) {
            return 0;
        }
        while (left < right) {
            int mid = (right - left) / 2 + left;
            if (nums[mid] < target) {
                // 找到第一个小于target的元素
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return n - right;
    }

}
