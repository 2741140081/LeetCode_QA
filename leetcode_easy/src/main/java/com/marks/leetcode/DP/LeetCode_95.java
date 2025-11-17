package com.marks.leetcode.DP;

import com.marks.utils.TreeNode;
import org.antlr.v4.runtime.tree.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/10 16:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_95 {

    /**
     * @Description: [功能描述]
     * @param n
     * @return java.util.List<com.marks.utils.TreeNode>
     * @author marks
     * @CreateDate: 2025/10/10 16:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> result;
        result = method_01(n);
        return result;
    }

    
    /**
     * @Description:
     * 1. 使用回溯法来解决
     * 2. 怎么拷贝得到的二叉搜索树, 就需要我们遍历二叉树然后拷贝一个新的二叉搜索树
     * 3. boolean[] visited; 来记录访问过的节点
     * @param n 
     * @return java.util.List<com.marks.utils.TreeNode>
     * @author marks
     * @CreateDate: 2025/10/10 16:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<TreeNode> method_01(int n) {
        if (n == 0) {
            return new LinkedList<TreeNode>();
        }
        return generateTrees(1, n);
    }

    public List<TreeNode> generateTrees(int start, int end) {
        List<TreeNode> allTrees = new LinkedList<TreeNode>();
        if (start > end) {
            allTrees.add(null);
            return allTrees;
        }

        // 枚举可行根节点
        for (int i = start; i <= end; i++) {
            // 获得所有可行的左子树集合
            List<TreeNode> leftTrees = generateTrees(start, i - 1);

            // 获得所有可行的右子树集合
            List<TreeNode> rightTrees = generateTrees(i + 1, end);

            // 从左子树集合中选出一棵左子树，从右子树集合中选出一棵右子树，拼接到根节点上
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    TreeNode currTree = new TreeNode(i);
                    currTree.left = left;
                    currTree.right = right;
                    allTrees.add(currTree);
                }
            }
        }
        return allTrees;
    }

}
