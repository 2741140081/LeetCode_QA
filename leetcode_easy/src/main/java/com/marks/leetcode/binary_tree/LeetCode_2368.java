package com.marks.leetcode.binary_tree;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/23 17:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2368 {
    
    /**
     * @Description:
     * 现有一棵由 n 个节点组成的无向树，节点编号从 0 到 n - 1 ，共有 n - 1 条边。
     *
     * 给你一个二维整数数组 edges ，长度为 n - 1 ，其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 之间存在一条边。另给你一个整数数组 restricted 表示 受限 节点。
     *
     * 在不访问受限节点的前提下，返回你可以从节点 0 到达的 最多 节点数目。
     *
     * 注意，节点 0 不 会标记为受限节点。
     *
     * tips:
     * 2 <= n <= 10^5
     * edges.length == n - 1
     * edges[i].length == 2
     * 0 <= ai, bi < n
     * ai != bi
     * edges 表示一棵有效的树
     * 1 <= restricted.length < n
     * 1 <= restricted[i] < n
     * restricted 中的所有值 互不相同
     * @param n 
     * @param edges 
     * @param restricted 
     * @return int
     * @author marks
     * @CreateDate: 2025/7/23 17:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int reachableNodes(int n, int[][] edges, int[] restricted) {
        int result;
        result = method_01(n, edges, restricted);
        return result;
    }

    /**
     * @Description:
     * 1. 判断 edge[i] 的start, end 是否是 restricted[] 所含节点, 如果包含, 则跳过这条无向边
     * 2. 将所有的无向边组成一个邻接表, 通过 bfs 广度优先搜索方式, 从0出发到达下一个节点, 并且数不可能存在环
     * 3. 需要添加额外的Set<Integer> usedNode 判断下一个添加的 nextNum 是否已经被使用, 防止成环
     * AC: 92ms(25.97%)/96.49MB(92.21%)
     * @param n 
     * @param edges 
     * @param restricted 
     * @return int
     * @author marks
     * @CreateDate: 2025/7/23 17:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges, int[] restricted) {
        // 将数组转为set集合, 使用stream api, stream() 转为stream 流, boxed(), 将int 装箱 Integer对象, collect() 收集为Set集合
        Set<Integer> unableSet = Arrays.stream(restricted).boxed().collect(Collectors.toSet());
        // 已经使用的节点
        Set<Integer> usedNode = new HashSet<>();
        // 邻接表
        List<Integer>[] list = new List[n];
        for (int i = 0; i < n; i++) {
            list[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];

            if (!unableSet.contains(start) && !unableSet.contains(end)) {
                list[start].add(end);
                list[end].add(start);
            }
        }
        int ans = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        usedNode.add(0);
        ans++;
        while (!queue.isEmpty()) {
            int currNum = queue.poll();
            for (int nextNum : list[currNum]) {
                if (!usedNode.contains(nextNum)) {
                    queue.add(nextNum);
                    usedNode.add(nextNum);
                    ans++;
                }
            }
        }

        return ans;
    }
}
