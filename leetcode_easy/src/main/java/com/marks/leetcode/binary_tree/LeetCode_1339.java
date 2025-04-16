package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/15 16:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1339 {

    private final int MOD = (int) (1e9 + 7);
    private int sum = 0;
    private int bestSplitSum = 0;
    /**
     * @Description:
     * 给你一棵二叉树，它的根为 root 。请你删除 1 条边，使二叉树分裂成两棵子树，且它们子树和的乘积尽可能大。
     *
     * 由于答案可能会很大，请你将结果对 10^9 + 7 取模后再返回。
     *
     * tips:
     * 每棵树最多有 50000 个节点，且至少有 2 个节点。
     * 每个节点的值在 [1, 10000] 之间。
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/4/15 16:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxProduct(TreeNode root) {
        int result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * 1. 5 * 10^8 是最大值, 即 5亿
     * 2. 我想让 乘积最大化, 因为和是一个定值 sum, 那么让abs = Math.abs(sumLeft - sumRight)
     * abs的差值越小, 乘积 ans 越大
     * 3. 假设 sumLeft >= sumRight, 也有可能反过来, 不重要
     * sumLeft = sum - sumRight
     * abs = Math.abs(sum - 2 * sumRight)
     *
     * AC: 7ms/54.12MB
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/4/15 16:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        sum = getSum(root);
        getSum(root);
        int product = (int) ((long) bestSplitSum * (sum - bestSplitSum) % MOD);
        return product;
    }

    public int getSum(TreeNode node) {
        int curSum = node.val;
        TreeNode left = node.left, right = node.right;
        if (left != null) {
            curSum += getSum(node.left);
        }
        if (right != null) {
            curSum += getSum(node.right);
        }
        if (sum > 0 && Math.abs(curSum * 2 - sum) < Math.abs(bestSplitSum * 2 - sum)) {
            bestSplitSum = curSum;
        }
        return curSum;
    }
}
