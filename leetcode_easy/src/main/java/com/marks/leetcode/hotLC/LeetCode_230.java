package com.marks.leetcode.hotLC;

import com.marks.utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.druid.sql.ast.SQLPartitionValue.Operator.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_230 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/8 9:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_230 {

    /**
     * @Description:
     * 给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 小的元素（从 1 开始计数）。
     * tips:
     * 树中的节点数为 n 。
     * 1 <= k <= n <= 10^4
     * 0 <= Node.val <= 10^4
     * @param: root
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2025/12/08 9:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int kthSmallest(TreeNode root, int k) {
        int result;
        result = method_01(root, k);
        return result;
    }

    private int ans = -1;
    /**
     * @Description:
     * 1. 用二叉搜索树的中序遍历顺序，找到第k个节点; 左子树 -> 根节点 -> 右子树
     * AC: 0ms/46.04MB
     * @param: root
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2025/12/08 9:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root, int k) {
        dfsOrderTraversal(root, new ArrayList<Integer>(), k);
        return ans;
    }

    private void dfsOrderTraversal(TreeNode root, List<Integer> list, int k) {
        if (root == null || ans != -1) {
            return;
        }
        dfsOrderTraversal(root.left, list, k);
        list.add(root.val);
        dfsOrderTraversal(root.right, list, k);
        if (list.size() >= k) {
            ans = list.get(k - 1);
        }
    }

}
