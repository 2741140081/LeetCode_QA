package com.marks.leetcode.divide_and_conquer;

import com.marks.utils.TreeNode;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_654 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/27 15:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_654 {

    /**
     * @Description:
     * 给定一个不重复的整数数组 nums 。 最大二叉树 可以用下面的算法从 nums 递归地构建:
     *
     * 创建一个根节点，其值为 nums 中的最大值。
     * 递归地在最大值 左边 的 子数组前缀上 构建左子树。
     * 递归地在最大值 右边 的 子数组后缀上 构建右子树。
     * 返回 nums 构建的 最大二叉树 。
     * tips:
     * 1 <= nums.length <= 1000
     * 0 <= nums[i] <= 1000
     * nums 中的所有整数 互不相同
     * @param: nums
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2026/01/27 15:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        TreeNode result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    /**
     * @Description:
     * 官方题解: 单调栈
     * E1:
     * 输入：nums = [3,2,1,6,0,5]
     * left[]  =  [-1, 0, 1, -1, 4, 3]
     * right[] =  [3, 3, 3, -1, 5, -1]
     * @param: nums
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2026/01/27 16:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeNode method_02(int[] nums) {
        int n = nums.length;
        // 创建栈
        Deque<Integer> stack = new ArrayDeque<>();
        int[] left = new int[n]; // 记录当前节点 i 的左侧下标值 left[i] = j, tree[i] 节点是 tree[j] 的左子树节点
        int[] right = new int[n]; // 同上
        Arrays.fill(left, -1);
        Arrays.fill(right, -1);
        TreeNode[] tree = new TreeNode[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new TreeNode(nums[i]);
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                right[stack.poll()] = i;
            }
            if (!stack.isEmpty()) {
                left[i] = stack.peek();
            }
            stack.push(i);
        }
        TreeNode root = null;
        for (int i = 0; i < n; i++) {
            if (left[i] == -1 && right[i] == -1) {
                root = tree[i];
            } else if (right[i] == -1 || (left[i] != -1 && nums[left[i]] < nums[right[i]])) {
                tree[left[i]].right = tree[i];
            } else {
                tree[right[i]].left = tree[i];
            }
        }


        return root;
    }

    /**
     * @Description:
     * E1:
     * 输入：nums = [3,2,1,6,0,5]
     * 输出：[6,3,5,null,2,0,null,null,1]
     * 1. 怎么在O(1) 的时间范围找到[i, j] 中的最大值 及其下标? (先直接暴力遍历, 待优化)
     * 时间复杂度O(n^2)
     * AC: 2ms/46.23MB
     *
     * 查看官方题解, 使用单调栈解决, 见 method_02()
     * @param: nums
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2026/01/27 15:27
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
        // 找到根及其下标
        int index = -1;
        int max = Integer.MIN_VALUE;
        for (int i = left; i <= right; i++) {
            if (nums[i] > max) {
                max = nums[i];
                index = i;
            }
        }
        // 创建根节点
        TreeNode root = new TreeNode(nums[index]);
        root.left = dfs(nums, left, index - 1);
        root.right = dfs(nums, index + 1, right);

        return root;
    }

}
