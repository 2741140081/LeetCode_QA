package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1536 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/2 14:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1536 {

    /**
     * @Description:
     * 给你一个 n x n 的二进制网格 grid，每一次操作中，你可以选择网格的 相邻两行 进行交换。
     * 一个符合要求的网格需要满足主对角线以上的格子全部都是 0 。
     * 请你返回使网格满足要求的最少操作次数，如果无法使网格符合要求，请你返回 -1 。
     * 主对角线指的是从 (1, 1) 到 (n, n) 的这些格子。
     * tips:
     * n == grid.length
     * n == grid[i].length
     * 1 <= n <= 200
     * grid[i][j] 要么是 0 要么是 1 。
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/03/02 14:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minSwaps(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * 1. 需要进行交换的是两行, 对角线以上单元格需要全部为0
     * 2. 对每一行中的从右往左第一个非0的元素, 下标是j, 那么连续0的个数是 n - j - 1
     * 3. 对于第 i 行, 需要至少有n - i - 1 个连续的0, 先判断是否存在满足条件的行.
     * 4. int[] count = new int[n]; 记录相同0行的数量 int[] index = new int[n]; 记录第 i 行中从右往左的0的个数
     * AC: 2ms/48.74MB
     * 贪心 + 模拟
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/03/02 14:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        int n = grid.length;
        int[] count = new int[n];
        int[] index = new int[n];
        // 遍历 grid, 对于每一个 grid[i], 从后往前遍历, 找到第一个非0的元素, 记录下标
        for (int i = 0; i < n; i++) {
            for (int j = n - 1; j >= 0; j--) {
                if (grid[i][j] == 1 || j == 0) {
                    index[i] = n - j - 1;
                    count[index[i]]++;
                    break;
                }
            }
        }
        // 检查是否能够通过交换满足要求, 对count[] 进行后缀和操作, 然后判断 count[i] >= n - i - 1
        for (int i = n - 2; i >= 0; i--) {
            count[i] += count[i + 1];
        }
        for (int i = 0; i < n; i++) {
            // 对于第 i 行, 需要有 n - i - 1 个连续的0
            int need = n - i - 1;
            if (count[need] <= i) {
                return -1;
            }
        }
        // 通过交换可以满足要求, 需要进行模拟操作, 对于第 i 行, 需要与第 j (j > i) 行进行交换
        // 交换操作是 范围是[i + 1, j] index[k] = index[k - 1], 相当于坐标不变, 但是对应的值进行下移操作
        // 使用 int temp = index[j], index[i] = temp; 继续处理下一行
        int ans = 0; // 模拟操作的次数
        for (int i = 0; i < n; i++) {
            int need = n - i - 1;
            // 判断当前行是否满足要求
            if (index[i] >= need) {
                continue;
            }
            // 不满足, 找到最近的 第 j 行, index[j] >= need
            int j = i + 1;
            while (j < n && index[j] < need) {
                j++;
            }
            int temp = index[j];
            // 将 [i + 1, j] 的值进行下移操作
            for (int k = j; k > i; k--) {
                index[k] = index[k - 1];
                ans++;
            }
            // 赋值
            index[i] = temp;
        }

        return ans;
    }

}
