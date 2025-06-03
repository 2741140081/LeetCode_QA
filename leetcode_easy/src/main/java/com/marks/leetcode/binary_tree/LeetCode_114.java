package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/6/3 14:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_114 {

    /**
     * @Description:
     * 给你二叉树的根结点 root ，请你将它展开为一个单链表：
     *
     * 展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
     * 展开后的单链表应该与二叉树 先序遍历 顺序相同。
     *
     * @param root
     * @return void
     * @author marks
     * @CreateDate: 2025/6/3 14:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void flatten(TreeNode root) {
        method_01(root);
    }

    /**
     * @Description:
     *
     * AC: 1ms/41.45MB
     * @param root
     * @return void
     * @author marks
     * @CreateDate: 2025/6/3 14:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_01(TreeNode root) {
        if (root == null) {
            return;
        }
        List<Integer> list = new ArrayList<>();
        dfs(list, root.left);
        dfs(list, root.right);
        root.right = null;
        root.left = null;
        TreeNode head = root;

        for (Integer value : list) {
            head.right = new TreeNode(value);
            head = head.right;
        }
    }

    private void dfs(List<Integer> list, TreeNode root) {
        if (root == null) {
            return;
        }

        list.add(root.val);
        if (root.left != null) {
            dfs(list, root.left);
        }

        if (root.right != null) {
            dfs(list, root.right);
        }
    }
}
