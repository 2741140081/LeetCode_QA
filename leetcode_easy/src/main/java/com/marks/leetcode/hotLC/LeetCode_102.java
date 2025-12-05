package com.marks.leetcode.hotLC;

import com.marks.utils.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_102 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/5 11:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_102 {

    /**
     * @Description:
     * 给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
     * @param: root
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/12/05 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * 广度优先搜索
     * AC: 2ms/46.0MB
     * @param: root
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/12/05 11:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(TreeNode root) {
        if (root == null) {
            return new LinkedList<>();
        }
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> ans = new ArrayList<>();
        queue.add(root);
        List<Integer> temp = new ArrayList<>();
        temp.add(root.val);
        ans.add(temp);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node == null) {
                    continue;
                }
                if (node.left != null) {
                    queue.add(node.left);
                    list.add(node.left.val);
                }
                if (node.right != null) {
                    queue.add(node.right);
                    list.add(node.right.val);
                }
            }
            if (!list.isEmpty()) {
                ans.add(list);
            }
        }
        return ans;
    }

}
