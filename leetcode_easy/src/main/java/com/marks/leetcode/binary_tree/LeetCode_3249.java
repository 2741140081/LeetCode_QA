package com.marks.leetcode.binary_tree;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/6 10:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3249 {

    /**
     * @param edges
     * @return int
     * @Description: 现有一棵 无向 树，树中包含 n 个节点，按从 0 到 n - 1 标记。树的根节点是节点 0 。
     * 给你一个长度为 n - 1 的二维整数数组 edges，其中 edges[i] = [ai, bi] 表示树中节点 ai 与节点 bi 之间存在一条边。
     * 如果一个节点的所有子节点为根的 子树 包含的节点数相同，则认为该节点是一个 好节点。
     * <p>
     * 返回给定树中 好节点 的数量。
     * <p>
     * 子树 指的是一个节点以及它所有后代节点构成的一棵树。
     * <p>
     * tips:
     * 2 <= n <= 10^5
     * edges.length == n - 1
     * edges[i].length == 2
     * 0 <= ai, bi < n
     * 输入确保 edges 总表示一棵有效的树。
     * @author marks
     * @CreateDate: 2025/8/6 10:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public int countGoodNodes(int[][] edges) {
        int result;
        result = method_01(edges);
        System.out.println("======>分割<=======");
//        int result2 = method_02(edges);
        return result;
    }

    private List<Integer>[] g;
    private int res;

    private int method_02(int[][] edges) {
        g = buildGraph(edges);
        dfs(0, -1);
        return res;
    }

    public int dfs(int node, int parent) {
        boolean valid = true;
        int treeSize = 0;
        int subTreeSize = 0;
        for (int child : g[node]) {
            if (child != parent) {
                int size = dfs(child, node);
                if (subTreeSize == 0) {
                    subTreeSize = size;
                } else if (size != subTreeSize) {
                    valid = false;
                }
                treeSize += size;
            }
        }
        if (valid) {
            res++;
            System.out.print(node + ", ");
        }
        return treeSize + 1;
    }


    /**
     * @Description: E1:
     * 输入：edges = [[0,1],[0,2],[1,3],[1,4],[2,5],[2,6]]
     * 输出：7
     * 提示：
     * 1. 构建邻接表
     * 2. 判断节点是否是一个好节点, 节点的子树包含的节点数相同, 需要自底向上进行遍历
     * 3. 采用的方式是深度优先搜索 DFS? 可以采用递归的方式, 判断节点的所有子节点的个数
     * 4. 用一个类的成员变量来存储好节点的数目, 另外还有一个需要注意的地方是, 树的叶子节点是不是一个好节点?
     * 5. 所有的叶子节点都是好节点. 从叶子节点返回节点数量, 判断当前节点的所有子节点, 是否数量一致, 如果数量一致, 则 ans++。
     * 6. testCountGoodNodesLargeTree() 方法测试报错, 测试的结果中缺少了 385, 需要修改代码对这个 385 进行debug操作
     * 7. 找到错误点了 counts.get(i) != counts.get(0), 这个比较的逻辑错误, 比较的List<Integer>, 也就是Integer 对象的比较, 超过128的值, 会导致Integer对象比较失败
     * 所以需要将比较方式修改为 equals() 方法, 或者使用Integer.valueOf() 方法进行比较
     * AC: 176ms(17.14%)/112.46MB(91.43%)
     * @param edges
     * @return int
     * @author marks
     * @CreateDate: 2025/8/6 10:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int ans = 0;

    private int method_01(int[][] edges) {
        ans = 0; // 重置计数器
        List<Integer>[] graph = buildGraph(edges);

        // 由于是一个无向图, 所以需要添加一个boolean[] visited 进行判断节点是否已经处理了
        boolean[] visited = new boolean[edges.length + 2]; // 增加数组大小以避免越界

        // 从节点0 开始进行深度优先遍历, 即获取节点0的所有子节点的个数, 判断是否一致
        dfs(graph, 0, visited);
        return ans;
    }

    private int dfs(List<Integer>[] graph, int root, boolean[] visited) {
        if (visited[root]) return 0;

        visited[root] = true;
        int count = 1;

        // 记录节点的所有子节点的个数
        List<Integer> counts = new ArrayList<>();
        for (int next : graph[root]) {
            if (!visited[next]) { // 只处理未访问的邻居节点
                int dfsResult = dfs(graph, next, visited);
                count += dfsResult;
                counts.add(dfsResult);
            }
        }

        // 判断是否为好节点：所有子树大小相同（包括没有子树的情况）
        boolean isGoodNode = true;
        for (int i = 1; i < counts.size(); i++) {
            if (!counts.get(i).equals(counts.get(0))) {
                isGoodNode = false;
                break;
            }
        }

        if (isGoodNode) {
            ans++;
        }
        return count;
    }

    private List<Integer>[] buildGraph(int[][] edges) {
        int n = edges.length + 1; // 对于树来说，节点数 = 边数 + 1
        List<Integer>[] graph = new List[n];

        // 初始化每个节点的邻接列表
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // 根据边构建邻接表
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
        }

        return graph;
    }
}