package com.marks.leetcode.graph_theory;

import com.marks.utils.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_863 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/15 14:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_863 {

    /**
     * @Description:
     * 给定一个二叉树（具有根结点 root）， 一个目标结点 target ，和一个整数值 k ，返回到目标结点 target 距离为 k 的所有结点的值的数组。
     * 答案可以以 任何顺序 返回。
     * tips:
     * 节点数在 [1, 500] 范围内
     * 0 <= Node.val <= 500
     * Node.val 中所有值 不同
     * 目标结点 target 是树上的结点。
     * 0 <= k <= 1000
     * @param: root
     * @param: target
     * @param: k
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/12/15 14:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        List<Integer> result;
        result = method_01(root, target, k);
        return result;
    }

    /**
     * @Description:
     * 1. 把树转为邻接矩阵, 然后广度优先搜索, 找到距离为k的所有节点
     * 2. AC: 14ms/43.62MB
     * @param: root
     * @param: target
     * @param: k
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/12/15 14:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(TreeNode root, TreeNode target, int k) {
        int maxValue = dfsMaxValue(root);
        List<Integer>[] matrix = new List[maxValue + 1];
        for (int i = 0; i < maxValue + 1; i++) {
            matrix[i] = new ArrayList<>();
        }
        // 使用广度优先搜索构建邻接矩阵
        Queue<TreeNode> queueNode = new ArrayDeque<>();
        queueNode.offer(root);
        while (!queueNode.isEmpty()) {
            TreeNode curr = queueNode.poll();
            if (curr.left != null) {
                matrix[curr.val].add(curr.left.val);
                matrix[curr.left.val].add(curr.val);
                queueNode.offer(curr.left);
            }
            if (curr.right != null) {
                matrix[curr.val].add(curr.right.val);
                matrix[curr.right.val].add(curr.val);
                queueNode.offer(curr.right);
            }
        }
        // 再次广度优先搜索, 找到距离为k的所有节点
        List<Integer> ans = new ArrayList<>();
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(target.val);
        boolean[] visited = new boolean[maxValue + 1];
        visited[target.val] = true;
        int depth = 0;
        while (!queue.isEmpty() && depth <= k) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int curr = queue.poll();
                if (depth == k) {
                    ans.add(curr);
                }
                for (int next : matrix[curr]) {
                    if (!visited[next]) {
                        visited[next] = true;
                        queue.offer(next);
                    }
                }
            }
            depth++;
        }

        return ans;
    }

    private int dfsMaxValue(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int result = root.val;
        result = Math.max(result, dfsMaxValue(root.left));
        result = Math.max(result, dfsMaxValue(root.right));
        return result;
    }

}
