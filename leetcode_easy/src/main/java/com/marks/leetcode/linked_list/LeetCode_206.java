package com.marks.leetcode.linked_list;

import com.marks.utils.ListNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/14 10:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_206 {

    /**
     * @Description: [功能描述]
     * @param head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/14 10:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode reverseList(ListNode head) {
        ListNode result;
        result = method_01(head);
        result = method_02(head);
        return result;
    }

    /**
     * @Description:
     * 创建一个新的 ListNode 进行添加
     *
     * AC: 0ms/41.68MB
     * @param head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/14 11:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_02(ListNode head) {
        ListNode dummy = new ListNode(0);
        ListNode curr = head;

        while (curr != null) {
            ListNode temp = curr;
            curr = curr.next;
            temp.next = dummy.next;
            dummy.next = temp;
        }

        return dummy.next;
    }

    /**
     * @Description:
     * 原地修改, 只能说真的傻
     * AC: 0ms/41.79MB
     * @param head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/14 10:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode dummy = new ListNode(0); // 创建一个虚拟头节点
        dummy.next = head;
        ListNode firstNode = head;
        ListNode prev = head; // 前一个节点, 相当于尾节点
        ListNode curr = head.next; // 待反转的节点

        while (curr != null) {
            ListNode temp = curr; // 备份一份反转节点
            prev.next = curr.next; // 这才是删除 待反转节点 curr
            curr = curr.next; // 从 List 中删除 待反转节点

            // 在最前面插入节点, 相当于反转
            temp.next = firstNode;
            dummy.next = temp;
            firstNode = temp;
        }
        return dummy.next;
    }
}
