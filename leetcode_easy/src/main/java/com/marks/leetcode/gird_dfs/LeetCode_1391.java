package com.marks.leetcode.gird_dfs;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/10 14:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1391 {
    private int[][] pre;
    private int m;
    private int n;
    private final String UP = "Up";
    private final String DOWN = "Down";
    private final String LEFT = "Left";
    private final String RIGHT = "Right";

    /**
     * @Description: [
     * 给你一个 m x n 的网格 grid。网格里的每个单元都代表一条街道。grid[i][j] 的街道可以是：
     *
     * 1 表示连接左单元格和右单元格的街道。
     * 2 表示连接上单元格和下单元格的街道。
     * 3 表示连接左单元格和下单元格的街道。
     * 4 表示连接右单元格和下单元格的街道。
     * 5 表示连接左单元格和上单元格的街道。
     * 6 表示连接右单元格和上单元格的街道。
     *
     * 你最开始从左上角的单元格 (0,0) 开始出发，网格中的「有效路径」是指从左上方的单元格 (0,0) 开始、一直到右下方的 (m-1,n-1) 结束的路径。
     * 该路径必须只沿着街道走。注意：你 不能 变更街道。
     * 如果网格中存在有效的路径，则返回 true，否则返回 false 。
     *
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 300
     * 1 <= grid[i][j] <= 6
     * ]
     * @param grid
     * @return boolean
     * @author marks
     * @CreateDate: 2024/12/10 14:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean hasValidPath(int[][] grid) {
        boolean result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description: [
     * 1. 如果(0, 0)是 s5, 那么不可能往下继续走, 并且同理可知(m - 1, n - 1) 是s4也无法到达
     * 2. 可能存在循环{4, 3, .... ,n - 1}, {6, 5, ...,n - 1}, 这种会导致死循环, 所以需要额外数组记录已访问
     * 3. 对于进入的网格会有一个方向, 除了(0, 0)是s4时存在两种
     * 不容易, 感觉枚举好麻烦啊, 情况复杂, 条件判断众多
     * 一个易错点, case 4: 时第一次运行会影响第二次运行, 由于使用了相同的pre[][], 需要新建一个pre[][]
     *
     * AC:22ms/66.97MB
     *
     * 感觉需要新建一个pre[][], 只需要把pre[0][0] 重置为0即可, 除了pre[m - 1][n - 1]两种到达都可以
     * 如果4中一条线路1无法到达, 那么另外一条线路2如果与线路1, 存在可以节点pre[i][j], 可以由线路1 和线路2都可以访问到, 那么必定是一个循环,
     * 即线路1无法到达pre[m - 1][n - 1], 线路2也必定无法到达.
     * 如果线路1, 2不存在重复部分, 那么线路2就是一条新的线路, 根据新线路判断是否可达
     * AC:15ms/66.92MB
     * ]
     * @param grid
     * @return boolean
     * @author marks
     * @CreateDate: 2024/12/10 14:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[][] grid) {
        m = grid.length;
        n= grid[0].length;

        if (grid[0][0] == 5 || grid[m - 1][n - 1] == 4) {
            return false;
        }
        pre = new int[m][n];
        boolean ans;
        switch (grid[0][0]) {
            case 1:
            case 6:
                ans = dfsMaxArea(grid, 0, 0, RIGHT);
                break;
            case 2:
            case 3:
                ans = dfsMaxArea(grid, 0, 0, DOWN);
                break;
            case 4:
                boolean checkRight = dfsMaxArea(grid, 0, 0, RIGHT);
                pre = new int[m][n];
                boolean checkDown = dfsMaxArea(grid, 0, 0, DOWN);
                ans = checkRight || checkDown;
                break;
            default:
                return false;
        }
        return ans;
    }

    private boolean dfsMaxArea(int[][] grid, int i, int j, String direction) {
        if (i == m - 1 && j == n - 1) {
            return true;
        }
        if (!inArea(grid, i, j) || pre[i][j] != 0) {
            return false;
        }
        pre[i][j] = 1; // 标记访问
        switch (direction) {
            case UP:
                if (i - 1 >= 0 && (grid[i - 1][j] == 2 || grid[i - 1][j] == 3 || grid[i - 1][j] == 4)) {
                    if (grid[i - 1][j] == 2) {
                        return dfsMaxArea(grid, i - 1, j, UP);
                    } else if (grid[i - 1][j] == 3) {
                        return dfsMaxArea(grid, i - 1, j, LEFT);
                    } else {
                        return dfsMaxArea(grid, i - 1, j, RIGHT);
                    }
                }else {
                    return false;
                }
            case DOWN:
                if (i + 1 < m && (grid[i + 1][j] == 2 || grid[i + 1][j] == 5 || grid[i + 1][j] == 6)) {
                    if (grid[i + 1][j] == 2) {
                        return dfsMaxArea(grid, i + 1, j, DOWN);
                    } else if (grid[i + 1][j] == 5) {
                        return dfsMaxArea(grid, i + 1, j, LEFT);
                    } else {
                        return dfsMaxArea(grid, i + 1, j, RIGHT);
                    }
                }else {
                    return false;
                }
            case LEFT:
                if (j - 1 >= 0 && (grid[i][j - 1] == 1 || grid[i][j - 1] == 4 || grid[i][j - 1] == 6)) {
                    if (grid[i][j - 1] == 1) {
                        return dfsMaxArea(grid, i, j - 1, LEFT);
                    } else if (grid[i][j - 1] == 4) {
                        return dfsMaxArea(grid, i, j - 1, DOWN);
                    } else {
                        return dfsMaxArea(grid, i, j - 1, UP);
                    }
                }else {
                    return false;
                }
            case RIGHT:
                if (j + 1 < n && (grid[i][j + 1] == 1 || grid[i][j + 1] == 3 || grid[i][j + 1] == 5)) {
                    if (grid[i][j + 1] == 1) {
                        return dfsMaxArea(grid, i, j + 1, RIGHT);
                    } else if (grid[i][j + 1] == 3) {
                        return dfsMaxArea(grid, i, j + 1, DOWN);
                    } else {
                        return dfsMaxArea(grid, i, j + 1, UP);
                    }
                }else {
                    return false;
                }

        }
        return false;
    }

    private boolean inArea(int[][] grid, int i, int j) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length) {
            return true;
        }
        return false;
    }
}
