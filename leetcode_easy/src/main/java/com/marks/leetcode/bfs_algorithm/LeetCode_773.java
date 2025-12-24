package com.marks.leetcode.bfs_algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_773 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/24 10:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_773 {

    /**
     * @Description:
     * 在一个 2 x 3 的板上（board）有 5 块砖瓦，用数字 1~5 来表示, 以及一块空缺用 0 来表示。
     * 一次 移动 定义为选择 0 与一个相邻的数字（上下左右）进行交换.
     * 最终当板 board 的结果是 [[1,2,3],[4,5,0]] 谜板被解开。
     * 给出一个谜板的初始状态 board ，返回最少可以通过多少次移动解开谜板，如果不能解开谜板，则返回 -1 。
     * @param: board
     * @return int
     * @author marks
     * @CreateDate: 2025/12/24 10:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int slidingPuzzle(int[][] board) {
        int result;
        result = method_01(board);
        return result;
    }

    private int[][] neighbors = {{1, 3}, {0, 2, 4}, {1, 5}, {0, 4}, {1, 3, 5}, {2, 4}}; // 节点的相邻节点

    /**
     * @Description:
     * 1. 移动是只能与数字0相邻的节点直接进行移动.
     * AC: 6ms/44MB
     * @param: board
     * @return int
     * @author marks
     * @CreateDate: 2025/12/24 10:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] board) {
        // 将二维数组转为为String
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                sb.append(board[i][j]);
            }
        }
        String initial = sb.toString();
        String target = "123450";
        if (initial.equals(target)) {
            return 0;
        }
        int step = 0;
        // Set 存储状态
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(initial);
        visited.add(initial);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                if (curr.equals(target)) {
                    return step;
                }
                int index = curr.indexOf('0');
                for (int next : neighbors[index]) {
                    String nextState = swap(curr, index, next);
                    if (!visited.contains(nextState)) {
                        queue.offer(nextState);
                        visited.add(nextState);
                    }
                }
            }
            step++;
        }
        return -1;
    }

    private String swap(String curr, int index, int next) {
        char[] chars = curr.toCharArray();
        char temp = chars[index];
        chars[index] = chars[next];
        chars[next] = temp;
        return new String(chars);
    }

}
