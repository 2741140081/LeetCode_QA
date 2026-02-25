package com.marks.leetcode.daily_question;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1022 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/24 14:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1022 {

    /**
     * @Description: [方法描述]
     * @param: root
     * @return int
     * @author marks
     * @CreateDate: 2026/02/24 14:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int sumRootToLeaf(TreeNode root) {
        int result;
        result = method_01(root);
        return result;
    }

    private int ans;
    /**
     * @Description:
     * 深度优先搜索 + 位运算
     * AC: 0ms/43.26MB
     * @param: root
     * @return int
     * @author marks
     * @CreateDate: 2026/02/24 14:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        ans = 0;
        // 深度优先搜索
        dfs(root, 0);
        return ans;
    }

    private void dfs(TreeNode root, int sum) {
        // 判断节点是否是叶子节点
        if (root.left == null && root.right == null) {
            sum = (sum << 1) | root.val;
            ans += sum;
            return;
        }
        sum = (sum << 1) | root.val;
        if (root.left != null) {
            dfs(root.left, sum);
        }
        if (root.right != null) {
            dfs(root.right, sum);
        }
    }

}
