package com.marks.leetcode.data_structure.stack;

import com.marks.utils.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_143 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/29 10:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_143 {

    /**
     * @Description:
     * 给定一个单链表 L 的头节点 head ，单链表 L 表示为：
     *
     * L0 → L1 → … → Ln - 1 → Ln
     * 请将其重新排列后变为：
     *
     * L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
     * 不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     * @param: head
     * @return void
     * @author marks
     * @CreateDate: 2026/01/29 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void reorderList(ListNode head) {
        method_01(head);
        method_02(head);
    }

    /**
     * @Description:
     * 在原链表上操作, 减少空间复杂度
     * 1. 快慢指针找到中心的
     * 2. 翻转右侧链表
     * 3. 合并两个链表
     * @param: head
     * @return void
     * @author marks
     * @CreateDate: 2026/01/29 11:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_02(ListNode head) {
        if (head == null) {
            return;
        }
        ListNode mid = middleNode(head);
        ListNode l1 = head;
        ListNode l2 = mid.next;
        mid.next = null;
        l2 = reverseList(l2);
        mergeList(l1, l2);
    }

    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    public void mergeList(ListNode l1, ListNode l2) {
        ListNode l1_tmp;
        ListNode l2_tmp;
        while (l1 != null && l2 != null) {
            l1_tmp = l1.next;
            l2_tmp = l2.next;

            l1.next = l2;
            l1 = l1_tmp;

            l2.next = l1;
            l2 = l2_tmp;
        }
    }

    /**
     * @Description:
     * 1. 使用快慢指针找到链表的中间节点, 当快指针为 null 时, 慢指针即为中间节点
     * 2. 遍历慢指针, 将慢指针的 next 节点指向 null, 并且将节点压入栈中进行存储
     * 3. 从新遍历链表, 向链表插入节点
     * AC: 3ms/49.42MB
     * @param: head
     * @return void
     * @author marks
     * @CreateDate: 2026/01/29 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_01(ListNode head) {
        // 创建快慢指针
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 创建栈, 并且栈底的节点与它的前一个节点的连接并没有断开, 所以有可能 end.next == stack.peek() 是同一个节点(不需要操作)
        Deque<ListNode> stack = new ArrayDeque<>();
        ListNode prev = slow;
        while (slow != null) {
            slow = slow.next;
            prev.next = null;
            stack.push(prev);
            prev = slow;
        }
        ListNode curr = head;
        // 向链表插入节点
        while (!stack.isEmpty()) {
            // 取栈顶元素
            ListNode temp = stack.pop();
            // 判断栈顶元素是否与当前节点相同
            if (curr == temp || curr.next == temp) {
                // 跳出循环
                break;
            }
            // 插入节点
            temp.next = curr.next;
            curr.next = temp;
            curr = temp.next;
        }
    }

}
