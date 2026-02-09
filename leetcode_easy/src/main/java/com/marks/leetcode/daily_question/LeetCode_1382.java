package com.marks.leetcode.daily_question;

import com.marks.utils.TreeNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1382 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/9 14:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1382 {

    /**
     * @Description:
     * 给你一棵二叉搜索树，请你返回一棵 平衡后 的二叉搜索树，新生成的树应该与原来的树有着相同的节点值。如果有多种构造方法，请你返回任意一种。
     * 如果一棵二叉搜索树中，每个节点的两棵子树高度差不超过 1 ，我们就称这棵二叉搜索树是 平衡的 。
     *
     * @param: root
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2026/02/09 14:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode balanceBST(TreeNode root) {
        TreeNode result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * 1. 先进行递归, 得到左右子树的最大深度 leftDepth, rightDepth, sub = leftDepth - rightDepth 的深度差, 如果大于1，则进行平衡
     * 2. 遍历树, 将节点存储到List中, 然后进行排序
     * 3. 然后在进行归并树, 递归调用
     * AC: 13ms/47.63MB
     * 不需要进行排序, 进行中序遍历得到的list是一个升序的
     * AC: 2ms/47.21MB
     * @param: root
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2026/02/09 14:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeNode method_01(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        dfs(root, list);
        // 构建树, 分治
        int n = list.size();
        TreeNode ans = buildTree(list, 0, n - 1);
        return ans;
    }

    private TreeNode buildTree(List<TreeNode> list, int left, int right) {
        if (left <= right) {
            int mid = left + (right - left) / 2;
            TreeNode root = list.get(mid);
            root.left = buildTree(list, left, mid - 1);
            root.right = buildTree(list, mid + 1, right);
            return root;
        }
        return null;
    }

    private void dfs(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }
        // 二叉搜索树以及是有序的, 进行中序遍历即可
        dfs(root.left, list);
        list.add(root);
        dfs(root.right, list);
    }

}
