package com.marks.leetcode.binary_tree;

import com.marks.utils.ListNode;
import com.marks.utils.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/6/4 10:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_109Test {

    @Test
    void sortedListToBST() {
        // head = [-10,-3,0,5,9]
        ListNode head = new ListNode(-10);
        head.next = new ListNode(-3);
        head.next.next = new ListNode(0);
        head.next.next.next = new ListNode(5);
        head.next.next.next.next = new ListNode(9);
        head.next.next.next.next.next = null;

        TreeNode treeNode = new LeetCode_109().sortedListToBST(head);
    }
}