package com.marks.leetcode.backtrack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/27 11:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1219 {

    
    /**
     * @Description:
     * 你要开发一座金矿，地质勘测学家已经探明了这座金矿中的资源分布，并用大小为 m * n 的网格 grid 进行了标注。
     * 每个单元格中的整数就表示这一单元格中的黄金数量；如果该单元格是空的，那么就是 0。
     *
     * 为了使收益最大化，矿工需要按以下规则来开采黄金：
     *
     * 每当矿工进入一个单元，就会收集该单元格中的所有黄金。
     * 矿工每次可以从当前位置向上下左右四个方向走。
     * 每个单元格只能被开采（进入）一次。
     * 不得开采（进入）黄金数目为 0 的单元格。
     * 矿工可以从网格中 任意一个 有黄金的单元格出发或者是停止。
     *
     * tips:
     * 1 <= grid.length, grid[i].length <= 15
     * 0 <= grid[i][j] <= 100
     * 最多 25 个单元格中有黄金。
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2025/8/27 11:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int getMaximumGold(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：grid = [[0,6,0],[5,8,7],[0,9,0]]
     * 输出：24
     * 1. 递归的结束条件, 没有下一步可以移动, 判断下一步是否越界, 下一个单元格的 grid[i][j] = 0, 因为为0不能进入该单元格
     * 2. 基于1的规则, 每次经过一个单元格, 就将 grid[i][j] 置为 0, 避免重复进入,
     * 由于需要进行回溯, 所以需要将退回上一个单元格时, 将grid[i][j] 置为原来的值, 需要int temp = grid[i][j];
     * 3. 由于不是固定起点, 所以我们可以从任意 grid[i][j] > 0, 作为我们的起始点, 枚举这些起始点, 获取最大sum
     *
     * AC: 111ms/40.29MB
     * 现在就好像总体指数在不断动荡, 但是新兴技术在不断走高, 传统技术在持续的走低?
     * 什么时候会是一个打破这个平衡，因为就感觉所有的资金或者热钱全部涌向新兴领域, 构成一个泡沫,
     * 当新兴领域的泡沫被戳破时, 市场将会是一场雪崩。但是也有可能, 某些技术会因此得到发展和进步, 可能会维持指数不断地动荡。
     * "Be fearful when others are greedy, and greedy when others are fearful."
     * "别人贪婪时我恐惧，别人恐惧时我贪婪"
     *
     * @param grid 
     * @return int
     * @author marks
     * @CreateDate: 2025/8/27 11:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int ans;
    private int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private int method_01(int[][] grid) {
        ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] > 0) {
                    int sum = grid[i][j];
                    grid[i][j] = 0;
                    backtrack(grid, i, j, sum);
                    grid[i][j] = sum;
                }
            }
        }
        return ans;
    }

    private void backtrack(int[][] grid, int i, int j, int sum) {
        // 判断是否有下一个可以移动的单元格
        if (!validHasNext(grid, i, j)) {
            ans = Math.max(ans, sum);
            return;
        }

        for (int[] dir : dirs) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            if (inArea(grid, newI, newJ) && grid[newI][newJ] > 0) {
                int temp = grid[newI][newJ];
                grid[newI][newJ] = 0;
                backtrack(grid, newI, newJ, sum + temp);
                grid[newI][newJ] = temp;
            }
        }

    }

    private boolean validHasNext(int[][] grid, int i, int j) {
        boolean flag = false;
        for (int[] dir : dirs) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            if (inArea(grid, newI, newJ) && grid[newI][newJ] > 0) {
                flag = true;
            }
        }
        return flag;
    }

    private boolean inArea(int[][] grid, int i, int j) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length) {
            return true;
        }
        return false;
    }
}
