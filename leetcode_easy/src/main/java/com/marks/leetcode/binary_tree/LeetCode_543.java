package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/21 14:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_543 {
    private int ans;
    
    /**
     * @Description:
     * 给你一棵二叉树的根节点，返回该树的 直径 。
     *
     * 二叉树的 直径 是指树中任意两个节点之间最长路径的 长度 。这条路径可能经过也可能不经过根节点 root 。
     *
     * 两节点之间路径的 长度 由它们之间边数表示。
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/4/21 14:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int diameterOfBinaryTree(TreeNode root) {
        int result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * 1. 使用额外变量 ans = Math.max(ans, left + right), 其中 left, right 分别是当前节点的左右子树的最长距离
     * 2. 递归对每一个根节点执行1的操作
     * 3. 递归返回值是 Math.max(left, right) + 1
     *
     * AC: 1ms/44.21MB
     * @param root 
     * @return int
     * @author marks
     * @CreateDate: 2025/4/21 14:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        ans = 0;
        dfsGetMaxLenght(root);
        return ans;
    }

    private int dfsGetMaxLenght(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 是否是叶子节点
        if (root.left == null && root.right == null) {
            return 1;
        }
        // 如果是父节点, 判断此父节点的最大直径 与 ans 对比
        int left = dfsGetMaxLenght(root.left);
        int right = dfsGetMaxLenght(root.right);
        ans = Math.max(ans, left + right);

        // 返回本身的最长路径, +1 是因为此节点到父节点的距离为1
        return Math.max(left, right) + 1;
    }
}
