package com.marks.leetcode.DP;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/24 16:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_337 {

    /**
     * @Description:
     * 小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为 root 。
     *
     * 除了 root 之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。
     * 如果 两个直接相连的房子在同一天晚上被打劫 ，房屋将自动报警。
     *
     * 给定二叉树的 root 。返回 在不触动警报的情况下 ，小偷能够盗取的最高金额 。
     * tips:
     * 树的节点数在 [1, 10^4] 范围内
     * 0 <= Node.val <= 10^4
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/10/24 16:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int rob(TreeNode root) {
        int result;
        result = method_01(root);
        return result;
    }

    
    /**
     * @Description:
     * 1. 定义两个转态, 0 表示不偷当前节点, 1 表示偷当前节点
     * dp[i][0] = Math.max(dp[i - 1][1], dp[i - 1][0]);
     * dp[i][1] = dp[i - 1][0] + root.val;
     * AC: 0ms/43.49MB
     * @param root 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/24 16:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        int[] ans = dfs(root);
        return Math.max(ans[0], ans[1]);
    }

    private int[] dfs(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }
        int[] left = dfs(root.left);
        int[] right = dfs(root.right);
        int[] ans = new int[2];
        ans[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        ans[1] = left[0] + right[0] + root.val;
        return ans;
    }

}
