package com.marks.leetcode.binary_tree;

import com.marks.utils.Node;
import com.marks.utils.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_173 </p>
 * <p>文件名称:  </p>
 * <p>描述:
 *
 * 实现一个二叉搜索树迭代器类BSTIterator ，表示一个按中序遍历二叉搜索树（BST）的迭代器：
 *
 * BSTIterator(TreeNode root) 初始化 BSTIterator 类的一个对象。BST 的根节点 root 会作为构造函数的一部分给出。
 * 指针应初始化为一个不存在于 BST 中的数字，且该数字小于 BST 中的任何元素。
 *
 * boolean hasNext() 如果向指针右侧遍历存在数字，则返回 true ；否则返回 false 。
 * int next()将指针向右移动，然后返回指针处的数字。
 * 注意，指针初始化为一个不存在于 BST 中的数字，所以对 next() 的首次调用将返回 BST 中的最小元素。
 *
 * 你可以假设 next() 调用总是有效的，也就是说，当调用 next() 时，BST 的中序遍历中至少存在一个下一个数字。
 *
 * </p>
 *
 * 1. 需要遍历整个二叉树, 按照中序遍历(左子树, 根节点, 右子树)
 * 2. 构建一个链表, 直接用队列吧,
 * AC: 20ms/47.29MB
 *
 * 可以继续优化
 * 你可以设计一个满足下述条件的解决方案吗？next() 和 hasNext() 操作均摊时间复杂度为 O(1) ，并使用 O(h) 内存。其中 h 是树的高度。
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/9 16:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class BSTIterator {
    private Queue<Integer> queue;
    /**
     * @Description:
     * 初始化 BSTIterator 类的一个对象。BST 的根节点 root 会作为构造函数的一部分给出。
     * 指针应初始化为一个不存在于 BST 中的数字，且该数字小于 BST 中的任何元素。
     *
     * @param root
     * @return
     * @author marks
     * @CreateDate: 2025/7/9 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public BSTIterator(TreeNode root) {
        queue = new LinkedList<>();
        dfs(root);
    }

    private void dfs(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            dfs(root.left);
        }
        queue.add(root.val);
        if (root.right != null) {
            dfs(root.right);
        }
    }

    /**
     * @Description:
     * int next()将指针向右移动，然后返回指针处的数字。
     * @return int
     * @author marks
     * @CreateDate: 2025/7/9 16:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int next() {
        return queue.poll();
    }


    /**
     * @Description:
     * boolean hasNext() 如果向指针右侧遍历存在数字，则返回 true ；否则返回 false 。
     * @return boolean
     * @author marks
     * @CreateDate: 2025/7/9 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean hasNext() {
        return !queue.isEmpty();
    }
}
