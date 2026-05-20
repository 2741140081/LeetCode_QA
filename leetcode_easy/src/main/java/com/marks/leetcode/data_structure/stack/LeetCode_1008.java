package com.marks.leetcode.data_structure.stack;

import com.marks.utils.TreeNode;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1008 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/15 15:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1008 {

    /**
     * @Description:
     * 给定一个整数数组，它表示BST(即 二叉搜索树 )的 先序遍历 ，构造树并返回其根。
     * 保证 对于给定的测试用例，总是有可能找到具有给定需求的二叉搜索树。
     * 二叉搜索树 是一棵二叉树，其中每个节点， Node.left 的任何后代的值 严格小于 Node.val , Node.right 的任何后代的值 严格大于 Node.val。
     * 二叉树的 前序遍历 首先显示节点的值，然后遍历Node.left，最后遍历Node.right。
     * @param: preorder
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2026/05/15 15:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode bstFromPreorder(int[] preorder) {
        TreeNode result;
        result = method_01(preorder);
        return result;
    }

    /**
     * @Description:
     * E1:
     * preorder = [8,5,1,7,10,12]
     * 1. 数组是一个先序遍历得到的数组, 先序遍历的顺序是: 根节点 -> 左子树 -> 右子树, 另外数是一颗二叉搜索数, 所有有 node.val > node.left.val && node.val < node.right.val
     * 2. 这种题目不应该使用分治法来处理吗, 在[1 ~ n - 1] 的范围中找到小 idx, preorder[idx] > preorder[0], idx 即为右子树的根节点, 并且 preorder[]
     * AC: 4ms/43.09MB
     * @param: preorder
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2026/05/15 15:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeNode method_01(int[] preorder) {
        int len = preorder.length;
        Map<Integer, Integer> hashMap = new HashMap<>();

        int[] inorder = new int[len];
        System.arraycopy(preorder, 0, inorder, 0, len);
        Arrays.sort(inorder);

        int index = 0;
        for (Integer value : inorder) {
            hashMap.put(value, index);
            index++;
        }
        return dfs(0, len - 1, 0, len - 1, preorder, hashMap);
    }

    private TreeNode dfs(int preLeft, int preRight, int inLeft, int inRight, int[] preorder, Map<Integer, Integer> hashMap) {
        if (preLeft > preRight || inLeft > inRight) {
            return null;
        }
        int pivot = preorder[preLeft];
        TreeNode root = new TreeNode(pivot);
        int pivotIndex = hashMap.get(pivot);
        root.left = dfs(preLeft + 1, pivotIndex - inLeft + preLeft,
                inLeft, pivotIndex - 1, preorder, hashMap);
        root.right = dfs(pivotIndex - inLeft + preLeft + 1, preRight,
                pivotIndex + 1, inRight, preorder, hashMap);
        return root;
    }

}
