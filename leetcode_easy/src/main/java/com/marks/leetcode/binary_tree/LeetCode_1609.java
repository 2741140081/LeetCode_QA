package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/7 17:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1609 {
    private boolean flag = true;
    
    /**
     * @Description:
     * 如果一棵二叉树满足下述几个条件，则可以称为 奇偶树 ：
     *
     * 二叉树根节点所在层下标为 0 ，根的子节点所在层下标为 1 ，根的孙节点所在层下标为 2 ，依此类推。
     * 偶数下标 层上的所有节点的值都是 奇 整数，从左到右按顺序 严格递增
     * 奇数下标 层上的所有节点的值都是 偶 整数，从左到右按顺序 严格递减
     * 给你二叉树的根节点，如果二叉树为 奇偶树 ，则返回 true ，否则返回 false 。
     *
     * @param root
     * @return boolean
     * @author marks
     * @CreateDate: 2025/5/7 17:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isEvenOddTree(TreeNode root) {
        boolean result;
        result = method_01(root);
        result = method_02(root);
        return result;
    }

    private boolean method_02(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
        queue.offer(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            int prev = level % 2 == 0 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                int value = node.val;
                if (level % 2 == value % 2) {
                    return false;
                }
                if ((level % 2 == 0 && value <= prev) || (level % 2 == 1 && value >= prev)) {
                    return false;
                }
                prev = value;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            level++;
        }
        return true;
    }

    /**
     * @Description:
     *
     * AC: 38ms/64.75MB
     * @param root 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/5/7 17:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(TreeNode root) {
        int nextLevel = 1;
        int id = 0;
        int lastValue = Integer.MIN_VALUE;
        PriorityQueue<Pair> queue = new PriorityQueue<>((o1, o2) -> {
            if (o1.level == o2.level) {
                return o1.id - o2.id; // 升序排序
            } else {
                return o1.level - o2.level;
            }
        });

        if (root.val % 2 == 0) {
            // 根节点所在层为第0层, 所以root.val 必须为偶数, 否则返回 false
            return false;
        }
        queue.add(new Pair(0, id, root));
        while (!queue.isEmpty()) {
            Pair p = queue.poll();
            int level = p.level;
            TreeNode curr_node = p.node;
            if (level == nextLevel) {
                // 下一层
                nextLevel++;
                id = 0;
            } else {
                // 当前这一层 后续按照顺序取出的值
                if (level % 2 == 0) {
                    // 偶数层 递增
                    if ( lastValue >= curr_node.val) {
                        return false;
                    }
                } else {
                    // 奇数层 递减
                    if ( lastValue <= curr_node.val) {
                        return false;
                    }
                }

            }
            lastValue = curr_node.val; // 更新前一个值
            id = addChildNodeToQueue(nextLevel, id, queue, curr_node);
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    private int addChildNodeToQueue(int nextLevel, int id, PriorityQueue<Pair> queue, TreeNode curr_node) {

        if (curr_node.left != null) {
            if (nextLevel % 2 == 0) {
                // 偶数层, 值全部为奇数
                if (curr_node.left.val % 2 == 0) {
                    flag = false;
                }
            } else {
                // 奇数层, 值全部为偶数
                if (curr_node.left.val % 2 != 0) {
                    flag = false;
                }
            }
            queue.offer(new Pair(nextLevel, id++, curr_node.left));
        }

        if (curr_node.right != null) {
            if (nextLevel % 2 == 0) {
                // 偶数层, 值全部为奇数
                if (curr_node.right.val % 2 == 0) {
                    flag = false;
                }
            } else {
                // 奇数层, 值全部为偶数
                if (curr_node.right.val % 2 != 0) {
                    flag = false;
                }
            }
            queue.offer(new Pair(nextLevel, id++, curr_node.right));
        }
        return id;
    }

    class Pair {
        int level;
        int id;
        TreeNode node;

        public Pair(int level, int id, TreeNode node) {
            this.level = level;
            this.id = id;
            this.node = node;
        }
    }
}


