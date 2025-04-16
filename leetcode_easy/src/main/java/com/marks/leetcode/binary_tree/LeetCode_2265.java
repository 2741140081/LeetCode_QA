package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/15 14:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2265 {

    private int ans = 0;
    /**
     * @Description:
     * 给你一棵二叉树的根节点 root ，找出并返回满足要求的节点数，要求节点的值等于其 子树 中值的 平均值 。
     *
     * 注意：
     * n 个元素的平均值可以由 n 个元素 求和 然后再除以 n ，并 向下舍入 到最近的整数。
     * root 的 子树 由 root 和它的所有后代组成。
     *
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/4/15 14:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int averageOfSubtree(TreeNode root) {
        int result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * AC: 1ms/43.42MB
     * @param root 
     * @return int
     * @author marks
     * @CreateDate: 2025/4/15 14:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        dfs(root);
        return ans;
    }

    private int[] dfs(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }
        int[] left = dfs(root.left);
        int[] right = dfs(root.right);
        int sum = root.val + left[0] + right[0];
        int count = 1 + left[1] + right[1];

        int avg = sum / count;
        // 判断是否符合要求
        if (avg == root.val) {
            ans++;
        }

        return new int[]{sum, count};
    }
}
