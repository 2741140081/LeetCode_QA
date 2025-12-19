package com.marks.leetcode.bfs_algorithm;

import com.marks.utils.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_103 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/19 10:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_103 {

    /**
     * @Description:
     * 给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。
     * （即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
     * @param: root
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/12/19 10:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * 1. 广度优先搜索, 遍历每一层的节点
     * 2. 使用 int level 记录当前层数, if level & 1 == 0, 则直接将当前层结果添加到ans中, 否则将当前层结果进行反转之后添加到ans中
     * 3. AC: 1ms/42.91MB
     * @param: root
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/12/19 10:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(TreeNode root) {
        // 创建结果集
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        // 队列
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;
        while (!queue.isEmpty()) {
            List<Integer> currList = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode curr = queue.poll();
                currList.add(curr.val);
                if (curr.left != null) {
                    queue.add(curr.left);
                }
                if (curr.right != null) {
                    queue.add(curr.right);
                }
            }
            if (level % 2 == 0) {
                ans.add(currList);
            } else {
                // 反转
                reversalList(currList);
                ans.add(currList);
            }
            level++;
        }

        return ans;
    }

    private void reversalList(List<Integer> currList) {
        int left = 0;
        int right = currList.size() - 1;
        while (left < right) {
            int temp = currList.get(left);
            currList.set(left, currList.get(right));
            currList.set(right, temp);
            left++;
            right--;
        }
    }

}
