package com.marks.leetcode.array;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_815 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/11 9:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_815 {

    /**
     * @Description:
     * 给你一个数组 routes ，表示一系列公交线路，其中每个 routes[i] 表示一条公交线路，第 i 辆公交车将会在上面循环行驶。
     * 例如，路线 routes[0] = [1, 5, 7] 表示第 0 辆公交车会一直按序列 1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ... 这样的车站路线行驶。
     * 现在从 source 车站出发（初始时不在公交车上），要前往 target 车站。 期间仅可乘坐公交车。
     * 求出 最少乘坐的公交车数量 。如果不可能到达终点车站，返回 -1 。
     *
     * tips:
     * 1 <= routes.length <= 500.
     * 1 <= routes[i].length <= 10^5
     * routes[i] 中的所有值 互不相同
     * sum(routes[i].length) <= 10^5
     * 0 <= routes[i][j] < 10^6
     * 0 <= source, target < 10^6
     * @param: routes
     * @param: source
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/05/11 9:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numBusesToDestination(int[][] routes, int source, int target) {
        int result;
//        result = method_01(routes, source, target);
        result = method_02(routes, source, target);
        return result;
    }

    /**
     * @Description:
     * 1. 查看官方题解
     * 2. AC: 270ms/91.87MB
     * @param: routes
     * @param: source
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/05/11 10:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] routes, int source, int target) {
        if (source == target) {
            return 0;
        }

        int n = routes.length;
        boolean[][] edge = new boolean[n][n];
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < n; i++) {
            for (int site : routes[i]) {
                List<Integer> list = map.getOrDefault(site, new ArrayList<Integer>());
                for (int j : list) {
                    edge[i][j] = edge[j][i] = true;
                }
                list.add(i);
                map.put(site, list);
            }
        }

        int[] dis = new int[n];
        Arrays.fill(dis, -1);
        Queue<Integer> que = new LinkedList<Integer>();
        for (int bus : map.getOrDefault(source, new ArrayList<Integer>())) {
            dis[bus] = 1;
            que.offer(bus);
        }
        while (!que.isEmpty()) {
            int x = que.poll();
            for (int y = 0; y < n; y++) {
                if (edge[x][y] && dis[y] == -1) {
                    dis[y] = dis[x] + 1;
                    que.offer(y);
                }
            }
        }

        int ret = Integer.MAX_VALUE;
        for (int bus : map.getOrDefault(target, new ArrayList<Integer>())) {
            if (dis[bus] != -1) {
                ret = Math.min(ret, dis[bus]);
            }
        }
        return ret == Integer.MAX_VALUE ? -1 : ret;
    }

    /**
     * @Description:
     * 1. 广度优先搜索
     * 2. 构建邻接表
     * 3. 怎么判断站台和路线的关系, 即当通过一系列的站台后, 如何计算公交车的数量?
     * 4. 需要3个站台才能确定这条线路是否由一辆公交车
     * @param: routes
     * @param: source
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/05/11 9:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] routes, int source, int target) {
        if (source == target) {
            return 0;
        }
        int n = routes.length;
        boolean[][] edge = new boolean[n][n];

        return 0;
    }

}
