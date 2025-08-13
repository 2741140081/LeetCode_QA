package com.marks.leetcode.binary_tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/11 17:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3331 {

    /**
     * @Description:
     * 给你一棵 n 个节点且根节点为编号 0 的树，节点编号为 0 到 n - 1 。
     * 这棵树用一个长度为 n 的数组 parent 表示，其中 parent[i] 是第 i 个节点的父亲节点的编号。由于节点 0 是根，parent[0] == -1 。
     *
     * 给你一个长度为 n 的字符串 s ，其中 s[i] 是节点 i 对应的字符。
     * 对于节点编号从 1 到 n - 1 的每个节点 x ，我们 同时 执行以下操作 一次 ：
     *
     * 找到距离节点 x 最近 的祖先节点 y ，且 s[x] == s[y] 。
     * 如果节点 y 不存在，那么不做任何修改。
     * 否则，将节点 x 与它父亲节点之间的边 删除 ，在 x 与 y 之间连接一条边，使 y 变为 x 新的父节点。
     * 请你返回一个长度为 n 的数组 answer ，其中 answer[i] 是 最终 树中，节点 i 为根的 子树 的 大小 。
     *
     * tips:
     * n == parent.length == s.length
     * 1 <= n <= 10^5
     * 对于所有的 i >= 1 ，都有 0 <= parent[i] <= n - 1 。
     * parent[0] == -1
     * parent 表示一棵合法的树。
     * s 只包含小写英文字母。
     * @param parent 
     * @param s
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/11 17:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] findSubtreeSizes(int[] parent, String s) {
        int[] result;
        result = method_01(parent, s);
        return result;
    }

    /**
     * @Description:
     * 输入：parent = [-1,0,0,1,1,1], s = "abaabc"
     * 输出：[6,3,1,1,1,1]
     * 1. 之前的理解有问题, 祖先节点: 根节点到目标节点路径上的所有节点, 都称为祖先节点(某些特殊情况下自身节点也是祖先节点, 这个先不管)
     * 2. 从0开始dfs遍历, 返回子节点的 List<Integer>[] lists, 当前节点的 int ch = s[i] - 'a', 相当于是index, 遍历 lists[i],
     * 将 lists[i] 的子节点进行修改, 拷贝一份parent[] 数组, 即 p[], 然后按照新的规则进行修改, 例如i节点原本的父节点是 p[i], 那么新的父节点是 p[x], x为当前节点。
     * 3. 再次进行dfs, int[] ans, ans[i] 为节点 i的子节点个数。
     *
     * 超时了, 需要看看哪里能优化一波
     * @param parent 
     * @param s 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/11 17:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int[] ans;
    private int[] method_01(int[] parent, String s) {
        List<Integer> [] graph = buildGraph(parent);
        int n = s.length();
        ans = new int[n];
        Arrays.fill(ans, 1);

        // 根据规则从新建立树的父子关系, 即更新parent[] 数组
        dfs(0, parent, graph, s);

        // 从新构建二叉树
        graph = buildGraph(parent);
        dfs(0, -1, graph, ans);

        return ans;
    }

    private int dfs(int node, int parent, List<Integer>[] graph, int[] ans) {
        int sum = 1;
        for (int next : graph[node]) {
            if (next != parent) {
                sum += dfs(next, node, graph, ans);
            }
        }
        ans[node] = sum;
        return sum;
    }

    private List<Integer> dfs(int node, int[] parent, List<Integer>[] graph, String s) {
        List<Integer> lists = new ArrayList<>();
        char pChar = s.charAt(node);

        for (int i = 0; i < graph[node].size(); i++) {
            for (int next : graph[node]) {
                List<Integer> childList = dfs(next, parent, graph, s);
                for (int child : childList) {
                    if (s.charAt(child) == pChar) {
                        parent[child] = node;
                    } else {
                        lists.add(child);
                    }
                }
            }
        }
        lists.add(node);
        return lists;
    }

    private List<Integer>[] buildGraph(int[] parent) {
        int n = parent.length;
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            int a = parent[i];
            int b = i;
            graph[a].add(b);
        }

        return graph;
    }
}
