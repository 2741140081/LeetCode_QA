package com.marks.leetcode.hotLC;

import com.marks.utils.ListNode;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/3 16:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2 {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result;
        result = method_01(l1, l2);
        return result;
    }

    /**
     * @Description:
     * 1. 对每个节点的数字进行相加, 并且加上前一位的进位值, 更新新的进位值, 将当前相加结果的个位值存储到新的链表中
     * 2. 遍历完成l1 和 l2 后, 仍然需要判断进位值是否不为0, 如果不为0, 则将新的进位值存储到新的链表中
     * AC: 1ms/45.89MB
     * @param: l1
     * @param: l2
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/12/03 16:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode l1, ListNode l2) {
        ListNode head = null, tail = null;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int n1 = l1 != null ? l1.val : 0;
            int n2 = l2 != null ? l2.val : 0;
            int sum = n1 + n2 + carry;
            if (head == null) {
                head = tail = new ListNode(sum % 10);
            } else {
                tail.next = new ListNode(sum % 10);
                tail = tail.next;
            }
            carry = sum / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carry > 0) {
            tail.next = new ListNode(carry);
        }
        return head;
    }

}
