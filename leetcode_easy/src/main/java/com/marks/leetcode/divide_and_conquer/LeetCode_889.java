package com.marks.leetcode.divide_and_conquer;

import com.marks.utils.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_889 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/26 16:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_889 {

    /**
     * @Description:
     * 给定两个整数数组，preorder 和 postorder ，
     * 其中 preorder 是一个具有 无重复 值的二叉树的前序遍历，postorder 是同一棵树的后序遍历，重构并返回二叉树。
     * 如果存在多个答案，您可以返回其中 任何 一个。
     * tips:
     * 1 <= preorder.length <= 30
     * 1 <= preorder[i] <= preorder.length
     * preorder 中所有值都 不同
     * postorder.length == preorder.length
     * 1 <= postorder[i] <= postorder.length
     * postorder 中所有值都 不同
     * 保证 preorder 和 postorder 是同一棵二叉树的前序遍历和后序遍历
     * @param: preorder
     * @param: postorder
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2026/01/26 16:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        TreeNode result;
        result = method_01(preorder, postorder);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：preorder = [1,2,4,5,3,6,7], postorder = [4,5,2,6,7,3,1]
     * 输出：[1,2,3,4,5,6,7]
     * 1. 前序: 根节点 -> 左子树 -> 右子树, 后序: 左子树 -> 右子树 -> 根节点
     * 2. 倒数第二个节点为右子树的根节点, 判断在 preorder中的位置, leftPre, rightPre
     * AC: 2ms/44.66MB
     * @param: preorder
     * @param: postorder
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2026/01/26 16:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int n;
    private Map<Integer, Integer> map;
    private TreeNode method_01(int[] preorder, int[] postorder) {
        this.n = preorder.length;
        map = new HashMap<>();
        // 遍历 preorder, 构建 map
        for (int i = 0; i < n; i++) {
            map.put(preorder[i], i);
        }
        return dfs(preorder, postorder, 0, n - 1, 0, n - 1);
    }

    private TreeNode dfs(int[] preorder, int[] postorder, int leftPre, int rightPre, int leftPost, int rightPost) {
        // 判断下标是否合法
        if (leftPre > rightPre || leftPost > rightPost) {
            return null;
        }
        // 构建根节点
        TreeNode root = new TreeNode(preorder[leftPre]);
        if (leftPre == rightPre || leftPost == rightPost) {
            return root;
        }
        if (rightPost > 0) {
            int index = map.get(postorder[rightPost - 1]); // 右子树的根节点
            // 分别计算左右子树的大小(节点数)
            int leftSize = index - leftPre - 1;
            int rightSize = rightPre - index + 1;
            // 构建左子树, 并且判断下标是否合法
            if (leftSize > 0) {
                root.left = dfs(preorder, postorder, leftPre + 1, index - 1, leftPost, leftPost + leftSize - 1);
            }
            // 构建右子树
            if (rightSize > 0) {
                root.right = dfs(preorder, postorder, index, rightPre, leftPost + leftSize, rightPost - 1);
            }
        }
        return root;
    }

}
