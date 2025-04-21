package com.marks.leetcode.graph_theory_algorithm.dfs;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/20 16:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2192 {

    /**
     * @Description: [
     * 给你一个正整数 n ，它表示一个 有向无环图 中节点的数目，节点编号为 0 到 n - 1 （包括两者）。
     *
     * 给你一个二维整数数组 edges ，其中 edges[i] = [fromi, toi] 表示图中一条从 fromi 到 toi 的单向边。
     *
     * 请你返回一个数组 answer，其中 answer[i]是第 i 个节点的所有 祖先 ，这些祖先节点 升序 排序。
     *
     * 如果 u 通过一系列边，能够到达 v ，那么我们称节点 u 是节点 v 的 祖先 节点。
     * ]
     * @param n
     * @param edges
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2024/12/20 16:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        List<List<Integer>> result;
        result = method_01(n, edges);
        return result;
    }

    /**
     * @Description: [
     * <p>1. 因为是DAG, 所以必定存在入度为0的节点, 将入度为0的节点存放到栈中, 使用额外空间int[] inDegree 记录节点的入度数
     * <p>2. 既然没有环, 就不需要pre[] 标记, 而且会存在多个节点到同一个节点的情况, such as: 0 -> 1, 2 -> 1, 3 -> 1
     * <p>3. 刚刚想到问题, 因为每一次都是存储0 -> 1 -> 2, 而还有一条线路是3 -> 1 -> 2, 那么节点2的祖先就是0, 1, 3, 如果
     * 使用1, 2那么就需要去重, 排序等一系列操作, 放弃这个方法, 逆向DFS
     * <p>4. 例如0 -> 1 -> 2, 而还有一条线路是3 -> 1 -> 2, 遍历0 ~ n - 1, 找a[i] 的祖先节点, 2 <- 1 <- 0, 每次遍历用pre[]来存储状态
     * <p>5. 为什么感觉用BFS来逆序找会更好
     * <p>6. 查看了题解, 使用的是拓扑排序, 试着修改一下
     *
     * AC:99ms/112.26MB
     * ]
     * @param n
     * @param edges
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2024/12/20 16:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(int n, int[][] edges) {
        int[] inDegree = new int[n]; // 入度表
        List<Integer>[] list = new ArrayList[n];
        Set<Integer>[] set = new HashSet[n]; // 存储节点的祖先
        for (int i = 0; i < n; ++i) {
            set[i] = new HashSet<Integer>();
            list[i] = new ArrayList<Integer>();
        }

        for (int[] ints : edges) {
            int x = ints[0], y = ints[1];
            inDegree[y]++;
            list[x].add(y);
        }
        // 广度优先搜索求解拓扑排序
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int curr_i = queue.poll();
            for (Integer j : list[curr_i]) {
                // 更新子节点的祖先哈希表
                set[j].add(curr_i); // 相当于删除当前节点, 将它的子节点全部添加上curr_i
                // 需要将curr_i 节点的祖先, 因为当前节点可能存在祖先, 需要将祖先传承给下一代, 添加到 set[j]
                for (int k : set[curr_i]) {
                    set[j].add(k);
                }

                // 相当于删除节点curr_i, 并且与curr_i相关联的节点的入度全部 -1, 并且将入度为0的节点入队
                inDegree[j]--;
                if (inDegree[j] == 0) {
                    queue.offer(j);
                }
            }
        }


        List<List<Integer>> ans = new ArrayList();
        for (int i = 0; i < n; i++) {
            ans.add(new ArrayList<Integer>());
            for (Integer j : set[i]) {
                ans.get(i).add(j);
            }
            Collections.sort(ans.get(i));
        }

        return ans;
    }
}
