package com.marks.leetcode.binary_tree;

import com.marks.utils.ListNode;
import com.marks.utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/6/4 10:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_109 {

    /**
     * @Description:
     * 给定一个单链表的头节点  head ，其中的元素 按升序排序 ，将其转换为 平衡 二叉搜索树。
     * 平衡二叉树 是指该树所有节点的左右子树的高度相差不超过 1。
     *
     * tips:
     * head 中的节点数在[0, 2 * 10^4] 范围内
     * -10^5 <= Node.val <= 10^5
     * @param head
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/6/4 10:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode sortedListToBST(ListNode head) {
        TreeNode result;
        result = method_01(head);
        return result;
    }



    /**
     * @Description:
     * 二叉搜索树:
     * 有序性‌
     * 对于任意节点：
     *
     * 左子树中所有节点的值 ‌小于‌ 该节点的值
     * 右子树中所有节点的值 ‌大于‌ 该节点的值
     *
     * 按照我的思路来
     * 1. 遍历head, 求出head.size 的大小
     * 2. 根据size是奇数还是偶数判断, 如果是偶数: size / 2 - 1 , 奇数 (size - 1) / 2
     * 3. 根据head.size 求出二叉树的根节点, 即链表的中间节点size / 2
     *
     * AC: 1ms/44.05MB
     * @param head
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/6/4 10:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeNode method_01(ListNode head) {

        if (head == null) {
            return null;
        }
        ListNode pre = head;
        List<Integer> list = new ArrayList<>();
        while (pre != null) {
            list.add(pre.val);
            pre = pre.next;
        }

        int right = list.size() - 1;
        int left = 0;

        TreeNode root = dfs(left, right, list);

        return root;
    }

    private TreeNode dfs(int left, int right, List<Integer> list) {
        if (left > right) {
            return null;
        }

        int mid = (right - left) / 2 + left;
        TreeNode root = new TreeNode(list.get(mid));

        root.left = dfs(left, mid - 1, list);
        root.right = dfs(mid + 1, right, list);

        return root;
    }
}
