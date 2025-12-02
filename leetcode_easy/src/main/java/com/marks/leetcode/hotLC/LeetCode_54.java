package com.marks.leetcode.hotLC;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_54 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/2 11:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_54 {

    /**
     * @Description:
     * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
     * @param: matrix
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/12/02 11:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result;
        result = method_01(matrix);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * 输出：[1,2,3,6,9,8,7,4,5]
     * AC: 1ms/42.43MB
     * @param: matrix
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/12/02 11:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int dir = 0;
        List<Integer> ans = new ArrayList<>();
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;
        ans.add(matrix[0][0]);
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0], y = cur[1];
            int nextX = x + dirs[dir][0], nextY = y + dirs[dir][1];
            if (nextX < 0 || nextX >= m || nextY < 0 || nextY >= n || visited[nextX][nextY]) {
                dir = (dir + 1) % 4;
                nextX = x + dirs[dir][0];
                nextY = y + dirs[dir][1];
            }

            if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n && !visited[nextX][nextY]) {
                queue.offer(new int[]{nextX, nextY});
                visited[nextX][nextY] = true;
                ans.add(matrix[nextX][nextY]);
            }
        }
        return ans;
    }

}
