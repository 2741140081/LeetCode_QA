package com.marks.leetcode.gridDP;

import java.util.ArrayList;

/**
 * <p>项目名称: LeetCode </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/7/24 17:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2267 {
    private int m;
    private int n;
    /**
     * @Description: [一个括号字符串是一个 非空 且只包含 '(' 和 ')' 的字符串。
     * 如果下面 任意 条件为 真 ，那么这个括号字符串就是 合法的 。
     * 字符串是 () 。
     * 字符串可以表示为 AB（A 连接 B），A 和 B 都是合法括号序列。
     * 字符串可以表示为 (A) ，其中 A 是合法括号序列。
     * 给你一个 m x n 的括号网格图矩阵 grid 。网格图中一个 合法括号路径 是满足以下所有条件的一条路径：
     *
     * 路径开始于左上角格子 (0, 0) 。
     * 路径结束于右下角格子 (m - 1, n - 1) 。
     * 路径每次只会向 下 或者向 右 移动。
     * 路径经过的格子组成的括号字符串是 合法 的。
     * 如果网格图中存在一条 合法括号路径 ，请返回 true ，否则返回 false 。]
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 100
     * grid[i][j] 要么是 '(' ，要么是 ')' 。
     *
     * @param grid
     * @return boolean
     * @author marks
     * @CreateDate: 2024/7/24 17:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean hasValidPath(char[][] grid) {
        boolean result = false;
        result = method_01(grid);
        return result;
    }

    /**
     * 1    1   1   1   1
     * 1    2   3   4   5
     * 1    3   6   10  15
     * 1    4   10  20  35
     * @Description: [功能描述: 暴力破解, 找出全部可能性]
     * @param grid
     * @return boolean
     * @author marks
     * @CreateDate: 2024/7/24 17:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     *
     * tips:
     * 1. 用一个变量 c 表示括号字符串的平衡度：遇到左括号就 +1，遇到右括号就 −1。那么合法字符串等价于任意时刻 c≥0 且最后 c=0。
     * 2. 从起点到终点，往下走的次数是固定的，即 m−1 次，往右走的次数也是固定的，即 n−1 次，
     * 因此路径长度（字符串长度）是一个定值，即 (m−1)+(n−1)+1=m+n−1。
     * 极限情况下合法的字符串左半均为左括号，右半均为右括号，因此 c 最大为 c = (m + n -1) / 2;
     *
     */
    private char[][] grid;

    private boolean[][][] via;

    private boolean method_01(char[][] grid) {
        m = grid.length;
        n = grid[0].length;
        int len = m + n - 1;
        this.grid = grid;
        this.via = new boolean[m][n][(m + n + 1)/2];
        // 先判断最外层是否符合条件
        if (grid[0][0] == ')' || grid[m-1][n-1] == '(' || len % 2 != 0) {
            return false;
        }
        boolean res = dfs(0, 0, 0);

        return res;
    }

    /**
     * @Description: [功能描述]
     * @param i
     * @param j
     * @param c 遇到左括号就 +1，遇到右括号就 −1
     * @return boolean
     * @author marks
     * @CreateDate: 2024/7/26 9:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean dfs(int i, int j, int c) {
        // 判断当前 c 是否符合要求 c >= 0
        // 相当于将 "())" 这种状态以数值的方式展示, 此时 c = -1

        // 判断后续剩余的数量是否足够抵消当前的c值, 例如当前c = 3, 即前面存在4个未抵消的"((((",
        // 如果后续只剩余2个格子, 即使这两个格子内全都是"))" 也是不合法的情况
        // 这种情况如何计算剩余的格子数量
        // 1. 格子的总数m + n -1, 当前格子的坐标为i, j, 所以已走过的格子数x + y(当前格子不包含在内)
        if (c > m + n -1 - (i + j)) {
            return false;
        }
        // 如果是最后一个格子
        if (i == m - 1 && j == n - 1) {
            return c == 1;
        }
        // 判断节点是否已被访问, 节点的默认值为false
        if (via[i][j][c]) {
            return false;
        }
        via[i][j][c] = true;
        c += grid[i][j] == '(' ? 1 : -1;
        if (c < 0) {
            return false;
        }
        return ((i < m-1 && dfs(i+1, j, c)) ||  (j < n - 1 && dfs(i, j+1, c)));
    }
}
