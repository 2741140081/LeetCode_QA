package com.marks.leetcode.graph_theory_algorithm.topological_sorting;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/30 14:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2392 {
    /**
     * @Description: [
     * 给你一个 正 整数 k ，同时给你：
     *
     * 一个大小为 n 的二维整数数组 rowConditions ，其中 rowConditions[i] = [abovei, belowi] 和
     * 一个大小为 m 的二维整数数组 colConditions ，其中 colConditions[i] = [lefti, righti] 。
     * 两个数组里的整数都是 1 到 k 之间的数字。
     *
     * 你需要构造一个 k x k 的矩阵，1 到 k 每个数字需要 恰好出现一次 。剩余的数字都是 0 。
     *
     * 矩阵还需要满足以下条件：
     *
     * 对于所有 0 到 n - 1 之间的下标 i ，数字 abovei 所在的 行 必须在数字 belowi 所在行的上面。
     * 对于所有 0 到 m - 1 之间的下标 i ，数字 lefti 所在的 列 必须在数字 righti 所在列的左边。
     * 返回满足上述要求的 任意 矩阵。如果不存在答案，返回一个空的矩阵。
     * ]
     * @param k
     * @param rowConditions
     * @param colConditions
     * @return int[][]
     * @author marks
     * @CreateDate: 2024/12/30 14:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] buildMatrix(int k, int[][] rowConditions, int[][] colConditions) {
        int[][] result;
        result = method_01(k, rowConditions, colConditions);
        return result;
    }

    /**
     * @Description: [
     * 这题类似于数独的填写,
     * 将1 ~ k 分别依据row/col 关系进行排列
     * 关系规则如下:
     * rowConditions[i] = [above_i, below_i]
     * colConditions[i] = [left_i, right_i]
     *
     * 1. 一旦有向图中存在环, 那么就无法构建成功, 返回一个空矩阵, 查看tips, 不存在自环
     *
     * AC:9ms/54.43MB
     *
     * 有一个可以完善的点是将col有int[] 转为 HashMap<Integer, Integer>, 其中value为key, index为值,
     * 这种或许可以将复杂度O(n^2) 减少到 O(n)
     *
     * 然而并未有用, 还是O(n^2)的复杂度, 并且引入额外Map, 导致空间复杂度加了一点
     * AC:9ms/54.52MB
     * ]
     * @param k
     * @param rowConditions
     * @param colConditions
     * @return int[][]
     * @author marks
     * @CreateDate: 2024/12/30 14:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int k, int[][] rowConditions, int[][] colConditions) {
        int[] row = getArrayByConditions(k, rowConditions);
        int[] col = getArrayByConditions(k, colConditions);
        if (row.length == 0 || col.length == 0) {
            return new int[0][];
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < col.length; i++) {
            map.put(col[i], i);
        }
        int[][] ans = new int[k][k];
        for (int i = 0; i < k; i++) {
            int value = row[i];
            int j = map.get(value);
            ans[i][j] = value;
        }
        return ans;
    }

    private int[] getArrayByConditions(int k, int[][] conditions) {
        int[] array = new int[k];
        int[] inDegree = new int[k];
        List<Integer>[] lists = new ArrayList[k];
        for (int i = 0; i < k; i++) {
            lists[i] = new ArrayList<>();
        }
        for (int[] condition : conditions) {
            int start = condition[0] - 1;
            int end = condition[1] - 1;
            inDegree[end]++;
            lists[start].add(end);
        }
        int index = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
                array[index++] = i + 1;
            }
        }
        while (!queue.isEmpty()) {
            int curr_i = queue.poll();
            for (int j : lists[curr_i]) {
                inDegree[j]--;
                if (inDegree[j] == 0) {
                    queue.offer(j);
                    array[index++] = j + 1;
                }
            }
        }

        return index == k ? array : new int[0];
    }
}
