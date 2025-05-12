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
 * @date 2025/5/7 17:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1609 {
    
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
        return result;
    }

    /**
     * @Description: [功能描述]
     * @param root 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/5/7 17:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(TreeNode root) {
        int currLevel = 0;
        int nextLevel = 0;
        int id = 0;
        PriorityQueue<Pair> queue = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if (o1.level == o2.level) {
                    return o1.id - o2.id;
                } else {
                    return 0;
                }

            }
        });

        queue.add(new Pair(currLevel, id, root));
        while (!queue.isEmpty()) {
            Pair p = queue.poll();
            int level = p.level;
            if (level == nextLevel) {
                currLevel = nextLevel;
                nextLevel++;
                id = 0;

            }
        }
        return true;
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


