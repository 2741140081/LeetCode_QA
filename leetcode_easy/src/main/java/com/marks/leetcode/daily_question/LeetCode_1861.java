package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1861 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/6 10:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1861 {

    /**
     * @Description:
     * 给你一个 m x n 的字符矩阵 boxGrid ，它表示一个箱子的侧视图。箱子的每一个格子可能为：
     * '#' 表示石头
     * '*' 表示固定的障碍物
     * '.' 表示空位置
     * 这个箱子被 顺时针旋转 90 度 ，由于重力原因，部分石头的位置会发生改变。每个石头会垂直掉落，直到它遇到障碍物，另一个石头或者箱子的底部。
     * 重力 不会 影响障碍物的位置，同时箱子旋转不会产生惯性 ，也就是说石头的水平位置不会发生改变。
     * 题目保证初始时 boxGrid 中的石头要么在一个障碍物上，要么在另一个石头上，要么在箱子的底部。
     * 请你返回一个 n x m 的矩阵，表示按照上述旋转后，箱子内的结果。
     *
     * tips:
     * m == boxGrid.length
     * n == boxGrid[i].length
     * 1 <= m, n <= 500
     * boxGrid[i][j] 只可能是 '#' ，'*' 或者 '.' 。
     * @param: boxGrid
     * @return char[][]
     * @author marks
     * @CreateDate: 2026/05/06 10:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public char[][] rotateTheBox(char[][] boxGrid) {
        char[][] result;
        result = method_01(boxGrid);
        return result;
    }

    /**
     * @Description:
     * 1. 先将矩阵转置，然后对每一行进行处理
     * 2. 处理每一行时, 使用双指针, 一个指针指在底部位置, 另外一个指针从底部向上移动, 移动的指针有3种情况:
     * boxGrid[i][j] = '.' 时: 指针继续向上移动;
     * boxGrid[i][j] = '*' 时: 遇到障碍物, 修改底部指针到障碍物位置; 指针继续向上移动;
     * boxGrid[i][j] = '#' 时: 遇到石头, 底部指针向上移动一个单元, 两个指针所在节点内容, 上面指针继续移动;
     * @param: boxGrid
     * @return char[][]
     * @author marks
     * @CreateDate: 2026/05/06 10:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private char[][] method_01(char[][] boxGrid) {
        int m = boxGrid.length;
        int n = boxGrid[0].length;
        char[][] result = new char[n][m];
        // 顺时针旋转 90 度
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[j][m - i - 1] = boxGrid[i][j];
            }
        }
        // 处理每一列, result 是 n x m 的矩阵
        for (int i = 0; i < m; i++) {
            int low = n; // 底部位置
            for (int j = n - 1; j >= 0; j--) {
                switch (result[j][i]) {
                    case '.':
                        break;
                    case '*':
                        low = j;
                        break;
                    case '#':
                        low--;
                        char temp = result[low][i];
                        result[low][i] = result[j][i];
                        result[j][i] = temp;
                        break;
                }
            }
        }

        return result;
    }

}
