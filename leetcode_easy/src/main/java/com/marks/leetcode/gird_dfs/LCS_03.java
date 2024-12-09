package com.marks.leetcode.gird_dfs;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/9 14:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCS_03 {

    private boolean flag; // 标记位, 是否与走廊相邻
    /**
     * @Description: [
     * 「以扣会友」线下活动所在场地由若干主题空间与走廊组成，场地的地图记作由一维字符串型数组 grid，字符串中仅包含 "0"～"5" 这 6 个字符。
     * 地图上每一个字符代表面积为 1 的区域，其中 "0" 表示走廊，其他字符表示主题空间。
     * 相同且连续（连续指上、下、左、右四个方向连接）的字符组成同一个主题空间。
     *
     * 假如整个 grid 区域的外侧均为走廊。请问，不与走廊直接相邻的主题空间的最大面积是多少？如果不存在这样的空间请返回 0。
     *
     * tips:
     * 1 <= grid.length <= 500
     * 1 <= grid[i].length <= 500
     * grid[i][j] 仅可能是 "0"～"5"
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/9 14:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int largestArea(String[] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description: [
     * 输入：grid = ["110","231","221"]
     * 输出：1
     *
     * 解释：4 个主题空间中，只有 1 个不与走廊相邻，面积为 1。
     *
     * 1.是否可以优先处理grid, 想了一下, 感觉不可取, 因为题目要求的是主题, 侵染一个块, 相当于侵染一个主题
     * 2.添加一个flag 判断位, 通过判断是否与走廊相邻
     * AC:60ms/45.04MB
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/9 14:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] grid) {
        int m = grid.length;
        int n = grid[0].length();
        char[][] array = new char[m][n];
        for (int i = 0; i < m; i++) {
            array[i] = grid[i].toCharArray();
        }

        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (array[i][j] > '0' && array[i][j] < '6') {
                    flag = true; // 先重置为true, 即不与走廊相邻
                    int sum = dfsMaxArea(array, array[i][j], i, j);
                    if (flag) {
                        ans = Math.max(ans, sum);
                    }
                }
            }
        }
        return ans;
    }

    private int dfsMaxArea(char[][] array, char target, int i, int j) {
        if (!inArea(array, i, j) || array[i][j] == '0') {
            flag = false; // 触碰走廊
            return 0;
        }
        if (array[i][j] != target) {
            return 0;
        } else {
            array[i][j] = '6'; // 标记已遍历
            int count = 1;
            count += dfsMaxArea(array, target, i + 1, j) + dfsMaxArea(array, target, i, j + 1)
                    + dfsMaxArea(array, target, i - 1, j) + dfsMaxArea(array, target, i, j - 1);
            return count;
        }
    }

    private boolean inArea(char[][] array, int i, int j) {
        if (i >= 0 && j >= 0 && i < array.length && j < array[0].length) {
            return true;
        }
        return false;
    }
}
