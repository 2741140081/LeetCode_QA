package com.marks.leetcode.backtrack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/18 10:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2397 {

    /**
     * @Description:
     * 给你一个下标从 0 开始、大小为 m x n 的二进制矩阵 matrix ；另给你一个整数 numSelect，表示你必须从 matrix 中选择的 不同 列的数量。
     *
     * 如果一行中所有的 1 都被你选中的列所覆盖，则认为这一行被 覆盖 了。
     *
     * 形式上，假设 s = {c1, c2, ...., cnumSelect} 是你选择的列的集合。对于矩阵中的某一行 row ，如果满足下述条件，则认为这一行被集合 s 覆盖：
     *
     * 对于满足 matrix[row][col] == 1 的每个单元格 matrix[row][col]（0 <= col <= n - 1），col 均存在于 s 中，或者
     * row 中 不存在 值为 1 的单元格。
     * 你需要从矩阵中选出 numSelect 个列，使集合覆盖的行数最大化。
     *
     * 返回一个整数，表示可以由 numSelect 列构成的集合 覆盖 的 最大行数 。
     *
     * @param matrix
     * @param numSelect
     * @return int
     * @author marks
     * @CreateDate: 2025/8/18 10:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumRows(int[][] matrix, int numSelect) {
        int result;
        result = method_01(matrix, numSelect);
        return result;
    }

    /**
     * @Description:
     * 输入：matrix = [[0,0,0],[1,0,1],[0,1,1],[0,0,1]], numSelect = 2
     * 输出：3
     * 1. 差不多看懂了题目, 按照回溯法的思路来看, 就是选取当前 index 列, 或者不选当前列, 然后递归调用, 直到 numSelect == 0
     * 2. 怎么判断某一列是否被覆盖, 通过一个Set set 记录列的索引, 如果选择某一列, 则 set.remove(index), 之后遍历每一行,
     * 并且遍历 set 集合, 判断set 集合的改行是否存在1, 如果存在1，表示未被覆盖, 如果不存在， 则表示覆盖, count++
     * 3. 递归结束条件 index == n, ans = Math.max(ans, count)
     * 4. just do it!
     * AC: 34ms(5.00%)/40.58MB(60.00%)
     * 5. 优化, 将list修改为 boolean[] 数组, 因为 List.contains() 时间复杂度 O(n), boolean[] 时间复杂度 O(1)
     * AC: 3ms(37.86%)/40.43MB(85.71%)
     * @param matrix
     * @param numSelect
     * @return int
     * @author marks
     * @CreateDate: 2025/8/18 10:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int ans;
    private int n;
    private int m;
    private int method_01(int[][] matrix, int numSelect) {
        m = matrix.length;
        n = matrix[0].length;
        ans = 0;
        boolean[] used = new boolean[n];
        backtrack(matrix, numSelect, 0, used);
        return ans;
    }

    private void backtrack(int[][] matrix, int numSelect, int index, boolean[] used) {
        if (index == n || numSelect == 0) {
            int count = 0;
            for (int i = 0; i < m; i++) {
                boolean flag = true;
                for (int j = 0; j < n; j++) {
                    if (!used[j] && matrix[i][j] == 1) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    count++;
                }
            }
            ans = Math.max(ans, count);
            return;
        }
        if (numSelect > 0) {
            used[index] = true;
            backtrack(matrix, numSelect - 1, index + 1, used);
            used[index] = false;
            backtrack(matrix, numSelect, index + 1, used);
        } else {
            backtrack(matrix, numSelect, index + 1, used);
        }
    }
}
