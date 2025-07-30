package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/16 17:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2458 {

    private int currVal;
    private Map<TreeNode, Integer> height = new HashMap<>(); // 每棵子树的高度
    private int[] res; // 每个节点的答案
    
    /**
     * @Description:
     * 给你一棵 二叉树 的根节点 root ，树中有 n 个节点。
     * 每个节点都可以被分配一个从 1 到 n 且互不相同的值。另给你一个长度为 m 的数组 queries 。
     *
     * 你必须在树上执行 m 个 独立 的查询，其中第 i 个查询你需要执行以下操作：
     *
     * 从树中 移除 以 queries[i] 的值作为根节点的子树。题目所用测试用例保证 queries[i] 不 等于根节点的值。
     * 返回一个长度为 m 的数组 answer ，其中 answer[i] 是执行第 i 个查询后树的高度。
     *
     * 注意：
     *
     * 查询之间是独立的，所以在每个查询执行后，树会回到其 初始 状态。
     * 树的高度是从根到树中某个节点的 最长简单路径中的边数 。
     *
     * tips:
     * 树中节点的数目是 n
     * 2 <= n <= 10^5
     * 1 <= Node.val <= n
     * 树中的所有值 互不相同
     * m == queries.length
     * 1 <= m <= min(n, 10^4)
     * 1 <= queries[i] <= n
     * queries[i] != root.val
     * @param root 
     * @param queries
     * @return int[]
     * @author marks
     * @CreateDate: 2025/7/16 17:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] treeQueries(TreeNode root, int[] queries) {
        int[] result;
        result = method_01(root, queries);
        result = method_02(root, queries);
        return result;
    }

    /**
     * @Description:
     * 查看题解, 得到一个思路, 只有删除最长边上的节点, 才会影响结果
     * 1. 例如E1 所示案例, 最长边是 5 -> 8 -> 2 -> (4,6)
     * 2. 现在的问题是怎么才能找到这条最长的边?
     * 3.
     * @param root
     * @param queries
     * @return int[]
     * @author marks
     * @CreateDate: 2025/7/18 10:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(TreeNode root, int[] queries) {
        getHeight(root);
        height.put(null, 0); // 简化 dfs 的代码，这样不用写 getOrDefault
        res = new int[height.size()];
        dfs(root, -1, 0);
        for (var i = 0; i < queries.length; i++)
            queries[i] = res[queries[i]];
        return queries;
    }

    private int getHeight(TreeNode node) {
        if (node == null) return 0;
        int h = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        height.put(node, h);
        return h;
    }

    private void dfs(TreeNode node, int depth, int restH) {
        if (node == null) return;
        ++depth;
        res[node.val] = restH;
        dfs(node.left, depth, Math.max(restH, depth + height.get(node.right)));
        dfs(node.right, depth, Math.max(restH, depth + height.get(node.left)));
    }

    /**
     * @Description:
     * E1:
     * 输入：root = [5,8,9,2,1,3,7,4,6], queries = [3,2,4,8]
     * 输出：[3,2,3,2]
     * 解释：执行下述查询：
     * - 移除以 3 为根节点的子树。树的高度变为 3（路径为 5 -> 8 -> 2 -> 4）。
     * - 移除以 2 为根节点的子树。树的高度变为 2（路径为 5 -> 8 -> 1）。
     * - 移除以 4 为根节点的子树。树的高度变为 3（路径为 5 -> 8 -> 2 -> 6）。
     * - 移除以 8 为根节点的子树。树的高度变为 2（路径为 5 -> 9 -> 3）。
     *
     *
     * 1. 新建一个结构体, 额外保存当前节点的子节点数量, 通过递归返回最大节点数
     * 2. 递归的过程中, 如果 node.val == queries[i], 设置此节点数为0
     * 3. 基本思路就是, for i + 遍历二叉树, 计算二叉树的最大节点数目, 时间复杂度大概是 O(m*n)
     *
     * 超时!!!
     *
     * @param root 
     * @param queries 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/7/16 17:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(TreeNode root, int[] queries) {
        int n = queries.length;
        int[] ans = new int[n];

        for (int i = 0; i < queries.length; i++) {
            currVal = queries[i];
            ans[i] = Math.max(dfs(root.left), dfs(root.right));
        }
        return ans;
    }

    private int dfs(TreeNode root) {
        if (root == null || root.val == currVal) {
            return 0;
        }

        int left = dfs(root.left);
        int right = dfs(root.right);

        return Math.max(left, right) + 1;
    }
}
