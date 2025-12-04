package com.marks.leetcode.hotLC;

import com.marks.utils.ListNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_148 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/4 14:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_148 {

    /**
     * @Description:
     * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
     * @param: head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/12/04 14:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode sortList(ListNode head) {
        ListNode result;
//        result = method_01(head);
//        result = method_02(head);
        // 归并排序
        result = method_03(head);
        return result;
    }

    private ListNode method_03(ListNode head) {
        return sortList(head, null);
    }

    /**
     * @Description:
     * 归并排序合并有序链表
     * 使用快慢指针找到中心点
     * AC: 11ms/58.10MB
     * @param: head
     * @param: tail
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/12/04 15:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode sortList(ListNode head, ListNode tail) {
        // 当前节点是null, 直接返回
        if (head == null) {
            return head;
        }
        // 当前节点只有一个节点, 返回
        if (head.next == tail) {
            // 终止节点
            head.next = null;
            return head;
        }
        // 快慢指针找到中心点
        ListNode slow = head, fast = head;
        while (fast != tail && fast.next != tail) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode mid = slow;
        ListNode left = sortList(head, mid);
        ListNode right = sortList(mid, tail);
        ListNode sorted = mergeListNode(left, right);
        return sorted;
    }

    private ListNode mergeListNode(ListNode left, ListNode right) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (left != null && right != null) {
            if (left.val < right.val) {
                curr.next = left;
                left = left.next;
            } else {
                curr.next = right;
                right = right.next;
            }
            curr = curr.next;
        }
        // 合并剩余的节点
        curr.next = left == null ? right : left;
        return dummy.next;
    }

    /**
     * @Description:
     * AC: 16ms/60.15MB
     * @param: head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/12/04 15:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_02(ListNode head) {
        List<Integer> list = new ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            list.add(curr.val);
            curr = curr.next;
        }
        list.sort(Comparator.comparingInt(o -> o));
        curr = head;
        int index = 0;
        while (curr != null) {
            curr.val = list.get(index);
            curr = curr.next;
            index++;
        }
        return head;
    }

    /**
     * @Description:
     * 冒泡排序吧[4,3,2,1] i = 3, j = 0[0,1], j= 1 [1,2],j=2 [2,3]
     * O(n^2) 超时!!! 28/30
     * @param: head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/12/04 14:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode head) {
        int n = 0;
        ListNode curr = head;
        while (curr != null) {
            n++;
            curr = curr.next;
        }
        for (int i = n - 1; i >= 0; i--) {
            curr = head;
            int j = 0;
            while (j < i) {
                ListNode next = curr.next;
                if (next != null && curr.val > next.val) {
                    int temp = curr.val;
                    curr.val = next.val;
                    next.val = temp;
                }
                curr = curr.next;
                j++;
            }
        }
        return head;
    }

}
