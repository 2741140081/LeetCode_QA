package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/21 10:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_111 {

    /**
     * @Description: [功能描述]
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/4/21 10:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minDepth(TreeNode root) {
        int result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * 1. 如果我想要BFS, 那么我需要就是一个优先队列, 一个额外的类
     * 2. 需要判定root节点是否为空
     *
     * AC: 4ms/61.68MB
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/4/21 10:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        Pair currNode = new Pair(0, root);
        int ans = 0;
        PriorityQueue<Pair> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.depth)); // 升序依据深度排序
        if (root != null) {
            queue.add(currNode);
        }
        while (!queue.isEmpty()) {
            Pair poll = queue.poll();
            int currDepth = poll.depth;
            TreeNode curr = poll.node;

            if (curr.left == null && curr.right == null) {
                // 如果当前节点是叶子节点, 获取叶子节点的深度, 跳出循环
                ans = currDepth;
                break;
            }

            if (curr.left != null) {
                queue.add(new Pair(currDepth + 1, curr.left));
            }

            if (curr.right != null) {
                queue.add(new Pair(currDepth + 1, curr.right));
            }
        }
        return ans;
    }

    class Pair {
        private int depth;
        private TreeNode node;

        public Pair(int depth, TreeNode node) {
            this.depth = depth;
            this.node = node;
        }
    }
}
