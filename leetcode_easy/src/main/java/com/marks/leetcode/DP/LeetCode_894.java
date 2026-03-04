package com.marks.leetcode.DP;

import com.marks.utils.TreeNode;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_894 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/2 16:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_894 {

    /**
     * @Description:
     * 给你一个整数 n ，请你找出所有可能含 n 个节点的 真二叉树 ，并以列表形式返回。答案中每棵树的每个节点都必须符合 Node.val == 0 。
     * 答案的每个元素都是一棵真二叉树的根节点。你可以按 任意顺序 返回最终的真二叉树列表。
     * 真二叉树 是一类二叉树，树中每个节点恰好有 0 或 2 个子节点。
     * tips:
     * 1 <= n <= 20
     * @param: n
     * @return java.util.List<com.marks.utils.TreeNode>
     * @author marks
     * @CreateDate: 2026/03/02 16:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<TreeNode> allPossibleFBT(int n) {
        List<TreeNode> result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description: [方法描述]
     * @param: n
     * @return java.util.List<com.marks.utils.TreeNode>
     * @author marks
     * @CreateDate: 2026/03/02 16:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<TreeNode> method_01(int n) {

        return null;
    }

}
