package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/14 16:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_623 {
    
    /**
     * @Description:
     * 给定一个二叉树的根 root 和两个整数 val 和 depth ，在给定的深度 depth 处添加一个值为 val 的节点行。
     *
     * 注意，根节点 root 位于深度 1 。
     *
     * 加法规则如下:
     *
     * 给定整数 depth，对于深度为 depth - 1 的每个非空树节点 cur ，创建两个值为 val 的树节点作为 cur 的左子树根和右子树根。
     * cur 原来的左子树应该是新的左子树根的左子树。
     * cur 原来的右子树应该是新的右子树根的右子树。
     * 如果 depth == 1 意味着 depth - 1 根本没有深度，那么创建一个树节点，值 val 作为整个原始树的新根，而原始树就是新根的左子树。
     *
     * tips:
     * 节点数在 [1, 10^4] 范围内
     * 树的深度在 [1, 10^4]范围内
     * -100 <= Node.val <= 100
     * -10^5 <= val <= 10^5
     * 1 <= depth <= the depth of tree + 1
     * @param root 
     * @param val 
     * @param depth
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/5/14 17:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        TreeNode result;
        result = method_01(root, val, depth);
        return result;
    }

    /**
     * @Description:
     * 1. 设置虚拟根节点, 方便操作depth = 1 的情况
     * 2. 使用bfs进行遍历操作, 找到depth对应的层级
     * 3. 这个目标depth 应该是depth - 1,
     *
     * AC: 1ms/43.62MB
     * @param root 
     * @param val 
     * @param depth 
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/5/14 17:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeNode method_01(TreeNode root, int val, int depth) {
        TreeNode dummy = new TreeNode(-1);
        dummy.left = root;
        dummy.right = null;

        int index = 1;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(dummy);

        while (!queue.isEmpty() && index <= depth) {
            // 当前层中节点的数量
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode currNode = queue.poll();
                if (index == depth) {
                    // 在目标层添加节点

                    TreeNode nextLeft = currNode.left;
                    TreeNode addLeftNode = new TreeNode(val);
                    currNode.left = addLeftNode;
                    addLeftNode.left = nextLeft;



                    TreeNode nextRight = currNode.right;
                    TreeNode addRightNode = new TreeNode(val);
                    currNode.right = addRightNode;
                    addRightNode.right = nextRight;


                } else {
                    if (currNode.left != null) {
                        queue.offer(currNode.left);
                    }
                    if (currNode.right != null) {
                        queue.offer(currNode.right);
                    }
                }
            }
            index++;
        }
        return dummy.left;
    }
}
