package com.marks.leetcode.daily_question;

import com.marks.utils.ListNode;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2095 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/15 10:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2095 {

    /**
     * @Description:
     * 给你一个链表的头节点 head 。删除 链表的 中间节点 ，并返回修改后的链表的头节点 head 。
     * 长度为 n 链表的中间节点是从头数起第 ⌊n / 2⌋ 个节点（下标从 0 开始），其中 ⌊x⌋ 表示小于或等于 x 的最大整数。
     * 对于 n = 1、2、3、4 和 5 的情况，中间节点的下标分别是 0、1、1、2 和 2 。
     *
     * tips:
     * 链表中节点的数目在范围 [1, 10^5] 内
     * 1 <= Node.val <= 10^5
     * @param: head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2026/06/15 10:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode deleteMiddle(ListNode head) {
        ListNode result;
        result = method_01(head);
        return result;
    }

    /**
     * @Description:
     * 1. 使用快慢指针
     * AC: 4ms/198.25MB
     * @param: head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2026/06/15 10:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        // 删除slow
        if (prev != null) {
            prev.next = slow.next;
        } else {
            head = null;
        }

        return head;
    }

}
