package com.marks.leetcode.backtrack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/26 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2850 {


    /**
     * @Description:
     * 给你一个大小为 3 * 3 ，下标从 0 开始的二维整数矩阵 grid ，分别表示每一个格子里石头的数目。
     * 网格图中总共恰好有 9 个石头，一个格子里可能会有 多个 石头。
     *
     * 每一次操作中，你可以将一个石头从它当前所在格子移动到一个至少有一条公共边的相邻格子。
     * 请你返回每个格子恰好有一个石头的 最少移动次数 。
     *
     * tips:
     * grid.length == grid[i].length == 3
     * 0 <= grid[i][j] <= 9
     * grid 中元素之和为 9 。
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2025/8/26 10:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumMoves(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }


    /**
     * @Description:
     * E1:
     * 输入：grid = [
     * [1,3,0],
     * [1,0,0],
     * [1,0,3]]
     * 输出：4
     * 1. 递归的结束条件是, 当任意一个 grid[i][j] = 1 时, 递归结束; 由于4个格子就可以构成一个环, 所以需要一个 boolean[] visited 来记录格子是否被访问过, 防止成环。
     * 2. 移动的步骤是什么? 从那个起点开始移动, 如何移动, 移动过程中怎么计算移动次数? 起始移动点时一个还是多个, 如果当前石头数量过多, 如何分配每个方向的移动数目
     * 3. 分情况讨论, a. 位于4个角落的点, 可以移动的步数是 0 ~ 4 步; 位于边的中点, 可以移动的距离 0 ~ 3 步, 位于中间的点; 可以移动的距离 0 ~ 2 步
     * 4. 虽然有点绕, 但是还有有一点思路的, 需要两个List<int[]> list; 存储石头数目大于1的格子, 并且将数目等于0的节点也存储, list2.
     * 5. 不太想用list存储, 直接用原数组存储吧, 看情况存储吧, 还有一个问题是, 我每次不是只能移动一块石头, 我可以将多个石头放在一起移动
     * 写不出来! need todo!
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2025/8/26 10:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        return 0;
    }
}
