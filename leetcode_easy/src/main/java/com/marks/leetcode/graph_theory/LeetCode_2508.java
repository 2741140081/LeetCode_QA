package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2508 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/19 10:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2508 {

    /**
     * @Description:
     * 给你一个有 n 个节点的 无向 图，节点编号为 1 到 n 。
     * 再给你整数 n 和一个二维整数数组 edges ，其中 edges[i] = [ai, bi] 表示节点 ai 和 bi 之间有一条边。图不一定连通。
     * 你可以给图中添加 至多 两条额外的边（也可以一条边都不添加），使得图中没有重边也没有自环。
     * 如果添加额外的边后，可以使得图中所有点的度数都是偶数，返回 true ，否则返回 false 。
     * 点的度数是连接一个点的边的数目。
     * tips:
     * 3 <= n <= 10^5
     * 2 <= edges.length <= 10^5
     * edges[i].length == 2
     * 1 <= ai, bi <= n
     * ai != bi
     * 图中不会有重边
     * @param: n
     * @param: edges
     * @return boolean
     * @author marks
     * @CreateDate: 2026/01/19 10:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isPossible(int n, List<List<Integer>> edges) {
        boolean result;
        result = method_01(n, edges);
        return result;
    }

    /**
     * @Description:
     * 1. 创建一个数组，记录每个节点的度数
     * 2. 额外添加2条边, 最多能使得4个点的度数由奇数变为偶数, 并且奇数节点是成对存在的, 即不存在不存在奇数个入度为奇数的节点
     * 3. 如果一条边的两端的节点都为奇数节点, 那么需要两条边才能使得度数变为偶数
     * 4. 可以推断出一个结论, 如果图中入度为奇数的节点数 >= 6, 直接返回 false.
     * 5. 如果图中入度为奇数节点数 <= 2, 那么必定可以添加额外的边, 使得度数变为偶数, 返回 true
     * 6. 我们主要讨论的是奇数节点数 == 4, 这么一种情况. 当且仅当其他3个奇数节点与另外一个奇数节点直连时返回false, 否则返回 true
     * 7. 情况2: 奇数节点数 == 2, 需要讨论一下. [1,2],[2,3],[2,4],[3,4], 返回 false. 剔除这种特殊情况, 即奇数节点的入度 == n - 1,
     * 返回false, 否则返回 true
     * 8. 对 奇数节点数 == 4 的情况, 假设4个节点分别是 a, b, c, d. 那么怎么样的情况下返回 true: a - b, c - d 相连. 枚举所有的可能性, 判断
     * 是否存在一种使得 a, b, c, d 节点两两直接连接
     * AC: 30ms/138.17MB
     * @param: n
     * @param: edges
     * @return boolean
     * @author marks
     * @CreateDate: 2026/01/19 10:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int n, List<List<Integer>> edges) {
        int count = 0; // 奇数节点个数
        int[] degree = new int[n]; // 将节点编号从0开始
        for (List<Integer> edge : edges) {
            int x = edge.get(0) - 1;
            int y = edge.get(1) - 1;
            degree[x]++;
            degree[y]++;
        }
        Map<Integer, Integer> map = new HashMap<>(); // 存储所有奇数节点
        for (int i = 0; i < n; i++) {
            if (degree[i] % 2 == 1) {
                map.put(i, count++);
            }
        }
        if (count >= 6) {
            return false;
        } else if (count <= 2) {
            if (count == 0) {
                return true;
            }
            // [1,2],[2,3],[2,4],[3,4], 返回 false. 剔除这种特殊情况
            int[] index = new int[count];
            // 对map遍历
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                index[entry.getValue()] = entry.getKey();
            }
            int a = index[0];
            int b = index[1];
            // 检查 a, b 能否直连, 如果不能直连(edges 中存在直连), 找到一个点不与a, b 有连接的点
            boolean[][] isConnect = new boolean[n][count];
            boolean flag = true; // 标记 a, b 是否直连
            // 遍历 edges
            for (List<Integer> edge : edges) {
                int x = edge.get(0) - 1;
                int y = edge.get(1) - 1;
                if (x == a && y == b || x == b && y == a) {
                    flag = false;
                }
                if (x == a || x == b) {
                    isConnect[y][map.get(y)] = true;
                }
                if (y == a || y == b) {
                    isConnect[x][map.get(y)] = true;
                }
            }
            int sum = 0;
            for (int i = 0; i < n; i++) {
                if (!isConnect[i][map.get(a)] && !isConnect[i][map.get(b)]) {
                    sum++;
                }
            }
            return flag ? flag : sum > 0;
        } else if (count == 4) {
            // 再次对 edges 进行遍历, 判断奇数节点是否与其他3个奇数节点直连
            // 遍历map
            int[] index = new int[count];
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                index[entry.getValue()] = entry.getKey();
            }
            for (int i = 0; i < count; i++) {
                for (int j = i + 1; j < count; j++) {
                    // check i, j 能否直接连接
                    if (check(index, i, j, edges)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private boolean check(int[] index, int i, int j, List<List<Integer>> edges) {
        int a = index[i];
        int b = index[j];
        // c 和 d 怎么找
        int c = -1, d = -1;
        for (int value : index) {
            if (value != a && value != b) {
                if (c == -1) {
                    c = value;
                } else {
                    d = value;
                }
            }
        }
        int count = 0; // 记录a - b, c - d 相连, 如果a - b 或者 c - d 能相连 count++;
        for (List<Integer> edge : edges) {
            int x = edge.get(0) - 1;
            int y = edge.get(1) - 1;
            if (x == a && y == b || x == b && y == a) {
                count++;
            }
            if (x == c && y == d || x == d && y == c) {
                count++;
            }
        }
        return count == 0;
    }

}
