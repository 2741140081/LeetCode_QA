package com.marks.leetcode.hotLC;

import com.marks.utils.ListNode;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_25 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/5 9:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_25 {

    /**
     * @Description:
     * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
     * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
     * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
     * @param: head
     * @param: k
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/12/05 9:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode result;
        result = method_01(head, k);
        return result;
    }

    /**
     * @Description:
     * 1. 每k个节点进行翻转, 需要记录当前节点的下表 int index;
     * 2. 需要用prev标记翻转前的头节点 ListNode prev;
     * AC: 1ms/45.23MB
     * @param: head
     * @param: k
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/12/05 9:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode head, int k) {
        if (k == 1) {
            return head;
        }
        // 构建一个虚拟节点
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        int index = 0;
        ListNode curr = head;
        while (curr != null) {
            index++;
            if (index % k == 0) {
                ListNode end = curr.next;
                prev = reverse(prev, end);
                curr = prev;
            }
            curr = curr.next;
        }
        return dummy.next;
    }

    /**
     * @Description:
     * prev = dummy; curr = N1; prev.next = N1; next = N2;
     * prev.next = next;
     * @param: prev
     * @param: next
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/12/05 9:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode reverse(ListNode prev, ListNode end) {
        ListNode curr = prev.next;
        ListNode tail = curr;
        while (curr != end) {
            ListNode temp = curr;
            curr = curr.next;
            temp.next = prev.next;
            prev.next = temp;
        }
        tail.next = end;
        return tail;
    }

}
