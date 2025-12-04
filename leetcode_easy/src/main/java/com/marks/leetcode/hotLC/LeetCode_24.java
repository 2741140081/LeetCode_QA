package com.marks.leetcode.hotLC;

import com.marks.utils.ListNode;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_24 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/4 10:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_24 {

    public ListNode swapPairs(ListNode head) {
        ListNode result;
        result = method_01(head);
        return result;
    }

    /**
     * @Description:
     * 1. prev -> curr -> next
     * curr.next = next.next, prev.next = next, next.next = curr
     * 2. 更新下一个 prev = prev.next; curr = prev.next; next = curr.next;
     * AC: 0ms/42.55MB
     * @param: head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/12/04 10:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode head) {
        // 构建一个虚拟节点
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = head;
        ListNode next;
        while (curr != null && curr.next != null) {
            next = curr.next;
            curr.next = next.next;
            prev.next = next;
            next.next = curr;
            prev = curr;
            curr = curr.next;
            // 不需要更新 next, 因为没有意义
        }
        return dummy.next;
    }

}
