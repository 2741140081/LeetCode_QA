package com.marks.leetcode.graph_theory_algorithm.topological_sorting;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/30 10:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_310 {
    /**
     * @Description: [
     * 树是一个无向图，其中任何两个顶点只通过一条路径连接。 换句话说，任何一个没有简单环路的连通图都是一棵树。
     * 给你一棵包含 n 个节点的树，标记为 0 到 n - 1 。给定数字 n 和一个有 n - 1 条无向边的 edges 列表（每一个边都是一对标签），其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 之间存在一条无向边。
     * 可选择树中任何一个节点作为根。当选择节点 x 作为根节点时，设结果树的高度为 h 。在所有可能的树中，具有最小高度的树（即，min(h)）被称为 最小高度树 。
     * 请你找到所有的 最小高度树 并按 任意顺序 返回它们的根节点标签列表。
     * 树的 高度 是指根节点和叶子节点之间最长向下路径上边的数量。
     *
     * tips:
     * 1 <= n <= 2 * 10^4
     * edges.length == n - 1
     * 0 <= ai, bi < n
     * ai != bi
     * 所有 (ai, bi) 互不相同
     * 给定的输入 保证 是一棵树，并且 不会有重复的边
     * ]
     * @param n
     * @param edges
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2024/12/30 10:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> result;
        result = method_01(n, edges);
        result = method_02(n, edges);
        return result;
    }

    /**
     * @Description: [
     * 查看官解后进行如下优化
     * a. delNodeList不在需要, 因为如果需要后续for n 和 delNodeList.contains(i) 的复杂度是O(n^2), 或许这是超时的一个原因
     *
     * AC:12ms/55.70MB
     * ]
     * @param n 
     * @param edges
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2024/12/30 14:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_02(int n, int[][] edges) {
        List<Integer> ans = new ArrayList<>();
        if (n <= 2) {
            for (int i = 0; i < n; i++) {
                ans.add(i);
            }
            return ans;
        }
        int[] inDegree = new int[n];
        List<Integer>[] lists = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];
            lists[start].add(end);
            lists[end].add(start);
            inDegree[start]++;
            inDegree[end]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 1) {
                queue.offer(i);
            }
        }
        int count = n;
        while (count > 2) {
            int size = queue.size();
            count -= size;
            for (int i = 0; i < size; i++) {
                int curr_i = queue.poll();
                for (int j : lists[curr_i]) {
                    inDegree[j]--;
                    if (inDegree[j] == 1) {
                        queue.offer(j);
                    }
                }
            }
        }
        while (!queue.isEmpty()){
            ans.add(queue.poll());
        }
        return ans;
    }

    /**
     * @Description: [
     * 我好像想到了
     * 1. 因为最小高度的树(n > 2), 根节点不可能是入度为1的节点, 我们需要一步一步的删除入度为1的节点
     * 2. 使用count = n, 一旦删除一个节点后, 将count--, 但是我们需要按照 deep 慢慢删除
     * 3. 即int size = queue.size(), 先一步取出来, 待删除完这些size之后, deep相当于自增1
     * 4. 因为是无向图, 所以每一个都需要两个[start, end] 和 [end, start] 都要添加到lists 邻接表中, 然后找入度为1的节点
     * 5. 如果我们要删除节点之间的连线,
     *
     * not AC, 超时!!! 70/71
     * 思路没错, 但是需要优化, 见method_02
     * a. 查看官解够修改List.contains(i), 这个的时间复杂度是O(n), 使用Set<Integer> set, set.contains(i)的复杂度是O(1),
     * AC:18ms/54.46MB
     * ]
     * @param n
     * @param edges
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2024/12/30 10:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int n, int[][] edges) {
        List<Integer> ans = new ArrayList<>();
        Set<Integer> delNodeList = new HashSet<>(); // 存储删除的节点
        if (n <= 2) {
            for (int i = 0; i < n; i++) {
                ans.add(i);
            }
            return ans;
        }
        int[] inDegree = new int[n];
        List<Integer>[] lists = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];
            lists[start].add(end);
            lists[end].add(start);
            inDegree[start]++;
            inDegree[end]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 1) {
                queue.offer(i);
                delNodeList.add(i);
            }
        }

        while (!queue.isEmpty()) {
            if (delNodeList.size() >= n - 2) {
                break;
            }
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int curr_i = queue.poll();
                for (int j : lists[curr_i]) {
                    inDegree[j]--;
                    if (inDegree[j] == 1 && !delNodeList.contains(j)) {
                        queue.offer(j);
                        delNodeList.add(j);
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (!delNodeList.contains(i)) {
                ans.add(i);
            }
        }
        return ans;
    }
}
