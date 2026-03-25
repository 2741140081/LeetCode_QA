package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1139 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/25 10:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1139 {

    /**
     * @Description:
     * 给你一个由若干 0 和 1 组成的二维网格 grid，
     * 请你找出边界全部由 1 组成的最大 正方形 子网格，并返回该子网格中的元素数量。如果不存在，则返回 0。
     * tips:
     * 1 <= grid.length <= 100
     * 1 <= grid[0].length <= 100
     * grid[i][j] 为 0 或 1
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/03/25 10:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int largest1BorderedSquare(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * 1. 需要找正方形, 即四条边全部相等, 并且边界元素全部由1组成
     * 2. 构建4个二维数组, 存储类似于前缀和, 但是遇到0时, 重置值为0, 将从左往右..., 设置为 left right, up, down 4个二维数组结果
     * 3. 好像不需要4个, 只需要2个就行, 即从左往右, 和从上往下 let, down 两个二维数组
     * 4. 处理 grid[i][j] = 1的情况, 此时 len = left[i][j], 可以得到此时能够构建的正方形边长边界[1, len],
     * 对于这个边界可以使用二分查找来得到最大边长, int l = 1, r = len, mid = (l + r) / 2;
     * 5. 已知了正方形右上角顶点坐标 (i, j), 并且边长为 mid, 可以得到该正方形其它顶点坐标,
     * 右下角顶点坐标: (i + mid - 1, j), 左下角顶点坐标: (i + mid - 1, j - mid + 1), 左上角: (i, j - mid + 1)
     * 6. 得到顶点坐标后判断边长是否相等, 直接判断 down[i + mid - 1][j] >= mid, left[i + mid - 1][j] >= mid, down[i + mid - 1][j - mid + 1] >= mid;
     * 只需要判断相关顶点的前缀和即可判断是否可以组成正方形.
     * 7. 好像不是一个有序的集合,因为3成立, 但是2不一定成立, 不能使用二分查找, 只能for循环判断
     * AC: 4ms/46.28MB
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/03/25 10:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] left = new int[m][n]; // 从左往右的前缀和
        int[][] down = new int[m][n]; // 从上往下的前缀和
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    left[i][j] = (j == 0 ? 0 : left[i][j - 1]) + 1;
                    down[i][j] = (i == 0 ? 0 : down[i - 1][j]) + 1;
                }
            }
        }
        // 然后遍历每一行, 并且从右向左遍历, 记录最大边长,
        // 并且判断剩余所有点能否组成更大的正方形当前点(i,j): j + 1 <= maxLen 跳过当前行, 遍历下一行; m - i <= maxLen, 则跳出循环;
        int maxLen = 0;
        // 遍历每一行, 从右往左遍历每一列
        for (int i = 0; i < m; i++) {
            for (int j = n - 1; j >= 0; j--) {
                if (grid[i][j] == 1) {
                    // 修改, 使用for循环判断
                    for (int len = left[i][j]; len >= 1; len--) {
                        // 添加条件判断是否越界, i + len - 1 < m
                        if (i + len - 1 >= m || j - len + 1 >= n) {
                            continue;
                        }
                        // 判断此时 len 是否满足条件
                        if (down[i + len - 1][j] >= len && left[i + len - 1][j] >= len && down[i + len - 1][j - len + 1] >= len) {
                            maxLen = Math.max(maxLen, len);
                            break;
                        }
                    }
                }
                // 判断是否跳过当前行
                if (j + 1 <= maxLen) {
                    break;
                }
            }
            // 跳出循环
            if (m - i <= maxLen) {
                break;
            }
        }

        return maxLen * maxLen;
    }

}
