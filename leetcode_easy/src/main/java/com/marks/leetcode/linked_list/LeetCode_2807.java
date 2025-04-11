package com.marks.leetcode.linked_list;

import com.marks.utils.ListNode;
import com.marks.utils.NumberUtil;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/11 15:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2807 {

    /**
     * @Description:
     * 给你一个链表的头 head ，每个结点包含一个整数值。
     * 在相邻结点之间，请你插入一个新的结点，结点值为这两个相邻结点值的 最大公约数 。
     * 请你返回插入之后的链表。
     * 两个数的 最大公约数 是可以被两个数字整除的最大正整数。
     *
     * @param head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/11 15:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode insertGreatestCommonDivisors(ListNode head) {
        ListNode result;
        result = method_01(head);
        return result;
    }

    /**
     * @Description:
     * AC: 2ms/44.12MB
     * @param head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/11 15:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode head) {
        if (head.next == null) {
            // 返回只包含一个节点的 head
            return head;
        }

        ListNode pre = head;
        int pre_num = head.val;
        ListNode curr = head.next;
        while (curr != null) {
            // add gcd node
            int gcd = NumberUtil.gcd(Math.min(pre_num, curr.val), Math.max(pre_num, curr.val));
            ListNode node = new ListNode(gcd);
            pre.next = node;
            node.next = curr;

            pre = curr;
            pre_num = curr.val;
            curr = curr.next;
        }

        return head;
    }
}
