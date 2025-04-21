package com.marks.leetcode.linked_list;

import com.marks.utils.ListNode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/9 17:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_203 {

    /**
     * @Description: [功能描述]
     * @param head
     * @param val
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/9 17:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode removeElements(ListNode head, int val) {
        ListNode result;
        result = method_01(head, val);
        return result;
    }

    /**
     * @Description:
     * AC: 1ms/44.77MB
     * @param head
     * @param val
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/9 17:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode head, int val) {
        // 创建一个标记头结点, 即不存在的节点
        ListNode dummy = new ListNode(-1); // val >= 0
        dummy.next = head;
        ListNode curr = head;
        if (curr == null) {
            return null;
        }
        ListNode pre = dummy;
        while (curr.next != null) {
            if (curr.val == val) {
                pre.next = curr.next;
            } else {
                pre = curr;
            }
            curr = curr.next;
        }

        if (curr.val == val) {
            pre.next = null;
        }
        return dummy.next;
    }
}
