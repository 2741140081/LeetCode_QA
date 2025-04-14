package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/14 16:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1026 {
    private int ans = 0;

    /**
     * @Description:
     * 给定二叉树的根节点 root，找出存在于 不同 节点 A 和 B 之间的最大值 V，其中 V = |A.val - B.val|，且 A 是 B 的祖先。
     *
     * （如果 A 的任何子节点之一为 B，或者 A 的任何子节点是 B 的祖先，那么我们认为 A 是 B 的祖先）
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/4/14 16:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxAncestorDiff(TreeNode root) {
        int result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * AC: 0ms/41.09MB
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/4/14 16:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        dfs(root, root.val, root.val);
        return ans;
    }

    private void dfs(TreeNode root, int minValue, int maxValue) {
        if (root == null) {
            return;
        }
        int value = root.val;
        ans = Math.max(ans, Math.max(Math.abs(value - minValue), Math.abs(value - maxValue)));
        dfs(root.left, Math.min(minValue, value), Math.max(maxValue, value));
        dfs(root.right, Math.min(minValue, value), Math.max(maxValue, value));
    }
}
