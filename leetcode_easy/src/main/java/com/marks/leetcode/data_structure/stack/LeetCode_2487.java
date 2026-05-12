package com.marks.leetcode.data_structure.stack;

import com.marks.utils.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2487 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/12 14:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2487 {

    /**
     * @Description:
     * 给你一个链表的头节点 head 。
     * 移除每个右侧有一个更大数值的节点。
     * 返回修改后链表的头节点 head 。
     *
     * tips:
     * 给定列表中的节点数目在范围 [1, 10^5] 内
     * 1 <= Node.val <= 10^5
     * @param: head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2026/05/12 14:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode removeNodes(ListNode head) {
        ListNode result;
        result = method_01(head);
        return result;
    }

    /**
     * @Description:
     * 1. 构建一个虚拟节点, dummy.next = head, 赋值 为 Integer.MAX_VALUE
     * 2. 创建一个单调递减栈
     * AC: 23ms/131.39MB
     * @param: head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2026/05/12 14:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode head) {
        ListNode dummy = new ListNode(Integer.MAX_VALUE);
        dummy.next = head;
        ListNode cur = head;
        Deque<ListNode> stack = new ArrayDeque<>();
        stack.push(dummy);
        while (cur != null) {
            while (!stack.isEmpty() && stack.peek().val < cur.val) {
                stack.pop();
                // update ListNode next
                if (stack.peek() != null) {
                    stack.peek().next = cur;
                }
            }
            stack.push(cur);
            cur = cur.next;
        }

        return dummy.next;
    }

}
