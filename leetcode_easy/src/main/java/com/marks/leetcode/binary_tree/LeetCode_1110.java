package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/6 15:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1110 {

    private List<TreeNode> ans;
    
    /**
     * @Description:
     * 给出二叉树的根节点 root，树上每个节点都有一个不同的值。
     *
     * 如果节点值在 to_delete 中出现，我们就把该节点从树上删去，最后得到一个森林（一些不相交的树构成的集合）。
     *
     * 返回森林中的每棵树。你可以按任意顺序组织答案。
     * @param root 
     * @param to_delete
     * @return java.util.List<com.marks.utils.TreeNode>
     * @author marks
     * @CreateDate: 2025/5/6 15:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        List<TreeNode> result;
        result = method_01(root, to_delete);
        return result;
    }

    
    /**
     * @Description:
     * 1. 通过一个boolean标记返回, 如果true, 表示该节点是一个待删除节点, 那么就删除
     * 2. 判断当前节点是否是待删除节点, 如果是待删除节点, 需要往list中添加 node.left, node.right,
     * 如果node 的子节点不为空
     *
     * AC: 1ms/44.10MB
     * @param root 
     * @param to_delete 
     * @return java.util.List<com.marks.utils.TreeNode>
     * @author marks
     * @CreateDate: 2025/5/6 15:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<TreeNode> method_01(TreeNode root, int[] to_delete) {
        ans = new ArrayList<>();
        Set<Integer> del = new HashSet<>();
        for (int i : to_delete) {
            del.add(i);
        }

        boolean flag = delNodeAndAddToList(root, del);
        if (!flag) {
            ans.add(root);
        }
        return ans;
    }

    private boolean delNodeAndAddToList(TreeNode root, Set<Integer> set) {
        if (root == null) {
            return false;
        }
        boolean leftFlag = delNodeAndAddToList(root.left, set);
        boolean rightFlag = delNodeAndAddToList(root.right, set);

        if (leftFlag) {
            // 删除节点
            root.left = null;
        }

        if (rightFlag) {
            // 删除节点
            root.right = null;
        }
        boolean flag = false;
        if (set.contains(root.val)) {
            // add TreeNode
            if (root.left != null) {
                ans.add(root.left);
            }
            if (root.right != null) {
                ans.add(root.right);
            }

            flag = true;
        }

        return flag;
    }
}
