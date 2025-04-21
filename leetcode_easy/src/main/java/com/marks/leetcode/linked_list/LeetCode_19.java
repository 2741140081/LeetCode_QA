package com.marks.leetcode.linked_list;

import com.marks.utils.ListNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/14 14:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_19 {

    /**
     * @Description:
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     *
     * tips:
     * 链表中结点的数目为 sz
     * 1 <= sz <= 30
     * 0 <= Node.val <= 100
     * 1 <= n <= sz
     * @param head
     * @param n
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/14 14:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode result;
        result = method_01(head, n);
        return result;
    }

    /**
     * @Description:
     * end 标记最后一个节点
     * curr 标记待删除的节点
     * prev 待删除节点的前一个节点
     *
     * index 记录end 走过的多少步, 当 index = n 时, curr, prev 开始与end 一起行动
     *
     * AC: 0ms/40.83MB
     * @param head
     * @param n
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/14 14:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode head, int n) {
        int index = 1;
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode prev = dummy;
        ListNode curr = head;
        ListNode end = head;

        while (end.next != null) {
            if (index < n) {
                end = end.next;
                index++;
            } else {
                end = end.next;
                prev = curr;
                curr = curr.next;
            }
        }
        prev.next = curr.next;
        return dummy.next;
    }
}
