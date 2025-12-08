package com.marks.leetcode.hotLC;

import com.marks.utils.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_105 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/8 11:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_105 {

    /**
     * @Description:
     * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
     * @param: preorder
     * @param: inorder
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/12/08 11:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        TreeNode result;
        result = method_01(preorder, inorder);
        return result;
    }

    private Map<Integer, Integer> map;
    /**
     * @Description:
     * 输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
     * 输出: [3,9,20,null,null,15,7]
     * preorder = [3,9,20,15,7],
     * inorder = [9,3,15,20,7]
     *
     * AC: 2ms/45.87MB
     * @param: preorder
     * @param: inorder
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/12/08 11:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeNode method_01(int[] preorder, int[] inorder) {
        int n = preorder.length;
        if (n == 0) {
            return null;
        }
        map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(inorder[i], i);
        }
        return buildingTree(preorder, inorder, 0, n - 1, 0);
    }

    private TreeNode buildingTree(int[] preorder, int[] inorder, int pre_left, int pre_right, int in_left) {
        if (pre_left > pre_right) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[pre_left]);
        int index = map.get(root.val);
        int size_left_subtree = index - in_left;
        root.left = buildingTree(preorder, inorder, pre_left + 1, pre_left + size_left_subtree, in_left);
        root.right = buildingTree(preorder, inorder, pre_left + size_left_subtree + 1, pre_right, index + 1);
        return root;
    }

}
