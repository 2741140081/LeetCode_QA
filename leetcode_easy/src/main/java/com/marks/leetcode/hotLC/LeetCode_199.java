package com.marks.leetcode.hotLC;

import com.marks.utils.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_199 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/8 11:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_199 {

    /**
     * @Description: [方法描述]
     * @param: root
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/12/08 11:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * 1. 二叉树的层序遍历, 使用队列实现
     * AC: 1ms/43.16MB
     * @param: root
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/12/08 11:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return new ArrayList<>();
        }
        // 队列
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            int temp = 0;
            for (int i = 0; i < size; i++) {
                TreeNode currNode = queue.poll();
                temp = currNode.val;
                if (currNode.left != null) {
                    queue.offer(currNode.left);
                }
                if (currNode.right != null) {
                    queue.offer(currNode.right);
                }
            }
            ans.add(temp);
        }

        return ans;
    }

}
