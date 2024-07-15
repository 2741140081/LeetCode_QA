package com.marks.leetcode.array;

import com.marks.utils.ListNode;

public class LeetCode_21 {
    public static void main(String[] args) {
        ListNode list1_0 = new ListNode(1);
        ListNode list1_1 = new ListNode(2);
        ListNode list1_2 = new ListNode(4);
        list1_0.next = list1_1;
        list1_1.next = list1_2;
        list1_2.next = null;

        ListNode list2_0 = new ListNode(1);
        ListNode list2_1 = new ListNode(3);
        ListNode list2_2 = new ListNode(4);
        list2_0.next = list2_1;
        list2_1.next = list2_2;
        list2_2.next = null;

        ListNode result = mergeTwoLists(list1_0, list2_0);
        while (result!=null){
            System.out.print(result.val+" ");
            result = result.next;
        }
    }
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode preHead = new ListNode(-1);

        ListNode node = preHead;
        while (list1 != null && list2 != null) {

            if (list1.val<=list2.val) {
               node.next = list1;
               list1 = list1.next;
            }else {
                node.next = list2;
                list2 = list2.next;
            }
            node = node.next;
        }
        // 合并后 l1 和 l2 最多只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
        node.next = list1 == null ? list2 : list1;

        return preHead.next;
    }
}
