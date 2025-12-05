package com.marks.leetcode.hotLC;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_108 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/5 11:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_108 {

    /**
     * @Description:
     * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 平衡 二叉搜索树。
     * 1 <= nums.length <= 10^4
     * -10^4 <= nums[i] <= 10^4
     * nums 按 严格递增 顺序排列
     * @param: nums
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/12/05 11:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        TreeNode result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 相当于是分治法, 递归求解
     * 2. 找到数组中中间节点, 创建根节点, 递归求解左右子树
     * AC: 0ms/44.45MB
     * @param: nums
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/12/05 11:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeNode method_01(int[] nums) {
        int n = nums.length;
        return dfs(nums, 0, n - 1);
    }

    private TreeNode dfs(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int mid = (right - left) / 2 + left;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = dfs(nums, left, mid - 1);
        node.right = dfs(nums, mid + 1, right);
        return node;
    }

}
