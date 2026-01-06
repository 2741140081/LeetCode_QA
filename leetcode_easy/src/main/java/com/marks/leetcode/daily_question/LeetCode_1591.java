package com.marks.leetcode.daily_question;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1591 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/5 11:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1591 {

    /**
     * @Description:
     * 给你一个奇怪的打印机，它有如下两个特殊的打印规则：
     *
     * 每一次操作时，打印机会用同一种颜色打印一个矩形的形状，每次打印会覆盖矩形对应格子里原本的颜色。
     * 一旦矩形根据上面的规则使用了一种颜色，那么 相同的颜色不能再被使用 。
     * 给你一个初始没有颜色的 m x n 的矩形 targetGrid ，其中 targetGrid[row][col] 是位置 (row, col) 的颜色。
     *
     * 如果你能按照上述规则打印出矩形 targetGrid ，请你返回 true ，否则返回 false 。
     * @param: targetGrid
     * @return boolean
     * @author marks
     * @CreateDate: 2026/01/05 11:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isPrintable(int[][] targetGrid) {
        boolean result;
        result = method_01(targetGrid);
        return result;
    }

    /**
     * @Description:
     * 1. 遍历数组, 确定有多少种颜色
     * 2. 假设有颜色1, 找到上下左右四个点(x1, y1), (x2, y2), (x3, y3), (x4, y4), 这个for循环怎么写
     * 3. 只需要4个数据 x1, x2, y3, y4, 当前是 targetGrid[i][j] = 1, x1 =Math.min(x1, i), x2 =Math.max(x2, i), y3 =Math.min(y3, j), y4 =Math.max(y4, j);
     * 4. 需要使用Map<Integer, int[]> 记录颜色和四个点
     * 5. 怎么确定颜色之间的关系? 即颜色1和颜色2, 是颜色1覆盖颜色2还是颜色2覆盖颜色1?
     * 6. 根据颜色的覆盖范围来确定颜色之间的关系, 如果颜色A的区域种存在颜色B, 那么说明颜色B可以覆盖颜色A, A -> B
     * 7. 然后建图 + 拓扑排序
     * AC: 65ms/47.02MB
     * @param: targetGrid
     * @return boolean
     * @author marks
     * @CreateDate: 2026/01/05 11:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[][] targetGrid) {
        int m = targetGrid.length;
        int n = targetGrid[0].length;
        Map<Integer, int[]> map = new HashMap<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int color = targetGrid[i][j];
                if (!map.containsKey(color)) {
                    map.put(color, new int[]{i, i, j, j});
                } else {
                    int[] arr = map.get(color);
                    arr[0] = Math.min(arr[0], i);
                    arr[1] = Math.max(arr[1], i);
                    arr[2] = Math.min(arr[2], j);
                    arr[3] = Math.max(arr[3], j);
                }
            }
        }
        List<Integer> list = new ArrayList<>(map.keySet());
        list.sort(Comparator.comparingInt(o -> o));
        int size = list.size();
        // 将颜色与 index 进行映射
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < size; i++) {
            indexMap.put(list.get(i), i);
        }
        // 构建邻接表
        List<Integer>[] graph = new ArrayList[size];
        for (int i = 0; i < size; i++) {
            graph[i] = new ArrayList<>();
        }
        // 构建入度表
        int[] inDegree = new int[size];
        // 遍历颜色
        for (int i = 0; i < size; i++) {
            int color = list.get(i);
            int[] arr = map.get(color);
            int x1 = arr[0];
            int x2 = arr[1];
            int y1 = arr[2];
            int y2 = arr[3];
            for (int j = x1; j <= x2; j++) {
                for (int k = y1; k <= y2; k++) {
                    int nextColor = targetGrid[j][k];
                    if (nextColor != color) {
                        graph[i].add(indexMap.get(nextColor));
                        inDegree[indexMap.get(nextColor)]++;
                    }
                }
            }
        }
        // 拓扑排序, 将入度为0的节点加入队列
        Queue<Integer> queue = new ArrayDeque<>();
        int ans = 0;
        for (int i = 0; i < size; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
                ans++;
            }
        }
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            // 给color[][]赋值, 好像没有必要赋值, 因为如果颜色关系正确, ans == size
            // 减少相邻节点的入度
            for (int next : graph[cur]) {
                inDegree[next]--;
                if (inDegree[next] == 0) {
                    queue.offer(next);
                    ans++;
                }
            }
        }
        return ans == size;
    }

}
