package com.marks.leetcode.linked_list;

import com.marks.utils.ListNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/14 10:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_147 {
    private ListNode dummy = new ListNode(-5001);
    
    /**
     * @Description:
     * 给定单个链表的头 head ，使用 插入排序 对链表进行排序，并返回 排序后链表的头 。
     *
     * 插入排序 算法的步骤:
     *
     * 插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。
     * 每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
     * 重复直到所有输入数据插入完为止。
     * 下面是插入排序算法的一个图形示例。部分排序的列表(黑色)最初只包含列表中的第一个元素。每次迭代时，从输入数据中删除一个元素(红色)，并就地插入已排序的列表中。
     *
     * 对链表进行插入排序。
     *
     * tips:
     * 列表中的节点数在 [1, 5000]范围内
     * -5000 <= Node.val <= 5000
     * @param head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/14 10:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode insertionSortList(ListNode head) {
        ListNode result;
        result = method_01(head);
        return result;
    }

    /**
     * @Description:
     * AC: 2ms/43.59MB
     * @param head 
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/14 10:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode head) {
        dummy.next = head;
        insertSort(dummy.next, head.next);
        return dummy.next;
    }

    private void insertSort(ListNode lastNode, ListNode currNode) {
        // 判断待排序节点是否 null
        if (currNode == null) {
            return;
        }

        // 判断已排序的尾节点 与 下一个待排序节点的 大小关系
        if (lastNode.val <= currNode.val) {
            // 已经按照升序排序, 直接插入
            insertSort(currNode, currNode.next);

        } else {
            // 从 List 中删除待排序节点
            lastNode.next = currNode.next;

            // 需要进行插入操作
            ListNode prev = dummy;
            ListNode curr = dummy.next;
            ListNode temp = currNode; // 备份一份待排序节点

            // 找到待插入的位置
            while (curr != null && curr.val <= currNode.val) {
                prev = curr;
                curr = curr.next;
            }
            // 执行插入操作
            prev.next = temp;
            temp.next = curr;

            // 处理下一个节点
            insertSort(lastNode, lastNode.next);
        }
    }
}
