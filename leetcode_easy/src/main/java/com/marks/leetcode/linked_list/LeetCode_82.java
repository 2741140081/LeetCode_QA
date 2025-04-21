package com.marks.leetcode.linked_list;

import com.marks.utils.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/11 10:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_82 {
    /**
     * @Description:
     * 给定一个已排序的链表的头 head ， 删除原始链表中所有重复数字的节点，只留下不同的数字 。返回 已排序的链表 。
     *
     * tips:
     * 链表中节点数目在范围 [0, 300] 内
     * -100 <= Node.val <= 100
     * 题目数据保证链表已经按升序 排列
     * @param head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/11 10:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode result;
        result = method_01(head);
        result = method_02(head);
        return result;
    }

    /**
     * @Description: 尝试一次遍历, 删除所有符合条件的节点
     * 1. pre_num: 上一个不同数字的结束节点, 因为我们要删除一段重复的节点
     * 2. curr_start: 当前数字开始节点, 如果不删除, 需要重新连接到 pre_num 上
     *
     * AC: 0ms/42.39MB
     * @param head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/11 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_02(ListNode head) {
        ListNode dummy = new ListNode(-101);
        dummy.next = head;
        ListNode pre_num = new ListNode(-102); // 这个不能设置为dummy, 否则会与 pre_num.next = prev; 产生自循环。
        ListNode prev= dummy;
        ListNode curr = head;
        int count = 1;
        while (curr != null) {
            if (curr.val == prev.val) {
                count++;
            } else {
                // new number
                if (count > 1) {
                    // duplicate number, need deleted
                    pre_num.next = curr;
                } else {
                    // connect ListNode prev
                    pre_num.next = prev;
                    pre_num = pre_num.next; // set pre_num is latest
                }
                count = 1; // reset count
            }
            prev = curr;
            curr = curr.next;
        }
        // 判断最后一组节点的count
        if (count > 1) {
            pre_num.next = null;
        }
        return dummy.next;
    }

    /**
     * @Description:
     * AC: 3ms/43.25MB
     * @param head 
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/11 10:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode head) {
        Set<Integer> set = new HashSet<>();
        Set<Integer> remove = new HashSet<>();
        ListNode curr = head;
        while (curr != null) {
            if (!remove.contains(curr.val)) {
                if (set.contains(curr.val)) {
                    set.remove(curr.val);
                    remove.add(curr.val);
                } else {
                    set.add(curr.val);
                }
            }
            curr = curr.next;
        }
        ListNode dummy = new ListNode(-101);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode currNode = head;

        while (currNode != null) {
            if (remove.contains(currNode.val)) {
                prev.next = currNode.next;
            } else {
                prev = currNode;
            }
            currNode = currNode.next;
        }
        return dummy.next;
    }
}
