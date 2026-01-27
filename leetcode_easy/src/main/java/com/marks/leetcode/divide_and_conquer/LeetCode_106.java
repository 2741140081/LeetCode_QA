package com.marks.leetcode.divide_and_conquer;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_106 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/26 15:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_106 {

    /**
     * @Description:
     * 给定两个整数数组 inorder 和 postorder ，
     * 其中 inorder 是二叉树的中序遍历， postorder 是同一棵树的后序遍历，请你构造并返回这颗 二叉树 。
     *
     * tips:
     * 1 <= inorder.length <= 3000
     * postorder.length == inorder.length
     * -3000 <= inorder[i], postorder[i] <= 3000
     * inorder 和 postorder 都由 不同 的值组成
     * postorder 中每一个值都在 inorder 中
     * inorder 保证是树的中序遍历
     * postorder 保证是树的后序遍历
     * @param: inorder
     * @param: postorder
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2026/01/26 15:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        TreeNode result;
        result = method_01(inorder, postorder);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
     * 输出：[3,9,20,null,null,15,7]
     * 1. 中序: 左子树 -> 根节点 -> 右子树, 后序: 左子树 -> 右子树 -> 根节点
     * 2. 对于inorder[0] == postorder[0], 那么可以推断 index = 0 出是左子树, in[0] != p[0],
     * AC: 10ms/44.78MB
     * 查看官方题解, 可以通过建立哈希表存储中序数组的下标值, 不需要遍历 中序数组
     * @param: inorder
     * @param: postorder
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2026/01/26 15:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeNode method_01(int[] inorder, int[] postorder) {
        int n = inorder.length;
        return dfs(inorder, postorder, 0, n - 1, 0, n - 1);
    }

    private TreeNode dfs(int[] inorder, int[] postorder, int leftIn, int rightIn, int leftPost, int rightPost) {
        // 判断是否下标是否越界
        if (leftIn > rightIn || leftPost > rightPost) {
            return null;
        }
        int n = inorder.length;
        // 先创建根节点(后序遍历的最后一个值)
        TreeNode root = new TreeNode(postorder[rightPost]);
        // 然后遍历 inorder, 分隔左子树和右子树
        for (int i = leftIn; i <= rightIn; i++) {
            if (inorder[i] == postorder[rightPost]) {
                int leftSize = i - leftIn;
                // 通过size 来分割 postorder
                // 左子树
                root.left = dfs(inorder, postorder, leftIn, i - 1, leftPost, leftPost + leftSize - 1);
                // 右子树
                root.right = dfs(inorder, postorder, i + 1, rightIn, leftPost + leftSize, rightPost - 1);
            }
        }
        return root;
    }

}
