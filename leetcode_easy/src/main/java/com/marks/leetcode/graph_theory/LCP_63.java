package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_63 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/21 11:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_63 {

    /**
     * @Description:
     * 欢迎各位来到「力扣嘉年华」，接下来将为各位介绍在活动中广受好评的弹珠游戏。
     * N*M 大小的弹珠盘的初始状态信息记录于一维字符串型数组 plate 中，数组中的每个元素为仅由 "O"、"W"、"E"、"." 组成的字符串。其中：
     *
     * "O" 表示弹珠洞（弹珠到达后会落入洞中，并停止前进）；
     * "W" 表示逆时针转向器（弹珠经过时方向将逆时针旋转 90 度）；
     * "E" 表示顺时针转向器（弹珠经过时方向将顺时针旋转 90 度）；
     * "." 表示空白区域（弹珠可通行）。
     * 游戏规则要求仅能在边缘位置的 空白区域 处（弹珠盘的四角除外）沿 与边缘垂直 的方向打入弹珠，并且打入后的每颗弹珠最多能 前进 num 步。
     * 请返回符合上述要求且可以使弹珠最终入洞的所有打入位置。你可以 按任意顺序 返回答案。
     *
     * 注意：
     * 若弹珠已到达弹珠盘边缘并且仍沿着出界方向继续前进，则将直接出界。
     * tips:
     * 1 <= num <= 10^6
     * 1 <= plate.length, plate[i].length <= 1000
     * plate[i][j] 仅包含 "O"、"W"、"E"、"."
     * @param: num
     * @param: plate
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/01/21 11:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] ballGame(int num, String[] plate) {
        int[][] result;
        result = method_01(num, plate);
        return result;
    }

    /**
     * @Description:
     * 1. 无法在4个角落处输入弹珠, 并且弹珠是垂直落入边缘区域。
     * 2. 从洞口出发能否到达边界位置(不含角落)? 由于从洞口出发能否在 num 步数到达边缘区域,
     * 并且方向需要相反, 对于转向, 也需要反向, 例如 W, 进入时是逆时针, 进入方向 ^, 进行逆时针旋转后, 方向为 <.
     * 如果从洞口出发 >, 遇到 'W', 需要顺时针旋转(反向), 进入方向 >, 旋转后, 为 v.
     * 3. 用 '^, v, <, >' 表示方向. 如果从顶部边缘出去, 则单元格为 '^'
     * 4. 有可能包含有多个洞口. 并且每一个洞都最多存在4条路径可以到达洞口.
     * 对于每个方向, 如果能够在num步数到达边缘位置(特定方向, 不包含角落), 这是一个有效路径.(记录到达的位置信息[i, j]),
     * 针对这种到达可能性的判断, 使用深度优先搜索(只能沿着特定方向不断前进, 并且存在 num 限制)
     * 5. 步数的定义: 两个相邻单元格之间移动 / 距离 算 1 步.
     * AC: 539ms/104.25MB
     * @param: num
     * @param: plate
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/01/21 11:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] dirs;
    private int maxStep;
    private int m;
    private int n;
    private int[][] method_01(int num, String[] plate) {
        this.m = plate.length;
        this.n = plate[0].length();
        dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        maxStep = num;
        List<int[]> zeros = new ArrayList<>(); // 存储所有洞口位置
        char[][] board = new char[m][n];
        for (int i = 0; i < m; i++) {
            board[i] = plate[i].toCharArray();
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    zeros.add(new int[]{i, j});
                }
            }
        }
        if (zeros.isEmpty()) {
            return new int[][]{}; // 洞口不存在
        }
        List<int[]> ans = new ArrayList<>();
        for (int[] zero : zeros) {
            // 按照方向来看: 东, 南, 西, 北 对于下标dirs[i]{0, 1, 2, 3}
            for (int dir = 0; dir < 4; dir++) {
                int x = zero[0];
                int y = zero[1];
                int step = 0;
                // DFS
                dfsFindPath(x, y, dir, board, step, ans);
            }
        }

        // 将 ans 转为 int[][] 数组
        return ans.toArray(new int[ans.size()][]);
    }

    private void dfsFindPath(int x, int y, int dir, char[][] board, int step, List<int[]> ans) {
        // 判断步数是否超过限制
        if (step > maxStep) {
            return;
        }
        // 判断下一个位置
        int nx = x + dirs[dir][0];
        int ny = y + dirs[dir][1];
        if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
            // 边界位置, 去除4个角落
            if ((x == 0 && y == 0) || (x == 0 && y == n - 1) || (x == m - 1 && y == 0) || (x == m - 1 && y == n - 1)) {
                return;
            }
            if (board[x][y] == '.') {
                ans.add(new int[]{x, y});
            }
            return;
        }
        char c = board[nx][ny]; // 获取下一个位置的字符
        if (c == '.') {
            // 空白区域, 可以沿着当前方向继续前进
            dfsFindPath(nx, ny, dir, board, step + 1, ans);
        } else if (c == 'W') {
            // 顺时针旋转
            int nDir =(dir + 1) % 4;
            dfsFindPath(nx, ny, nDir, board, step + 1, ans);
        } else if (c == 'E') {
            // 逆时针旋转
            int nDir = (dir + 3) % 4;
            dfsFindPath(nx, ny, nDir, board, step + 1, ans);
        }
        // c == 'O', 洞口, 停止移动
    }

}
