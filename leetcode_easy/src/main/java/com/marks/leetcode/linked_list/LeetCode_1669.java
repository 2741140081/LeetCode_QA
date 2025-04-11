package com.marks.leetcode.linked_list;

import com.marks.utils.ListNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/11 14:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1669 {
    
    /**
     * @Description:
     * 给你两个链表 list1 和 list2 ，它们包含的元素分别为 n 个和 m 个。
     *
     * 请你将 list1 中下标从 a 到 b 的全部节点都删除，并将list2 接在被删除节点的位置。
     *
     * 下图中蓝色边和节点展示了操作后的结果：
     *
     * tips:
     * 3 <= list1.length <= 10^4
     * 1 <= a <= b < list1.length - 1
     * 1 <= list2.length <= 10^4
     * @param list1 
     * @param a 
     * @param b 
     * @param list2
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/11 14:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode result;
        result = method_01(list1, a, b, list2);
        return result;
    }

    /**
     * @Description:
     * 1. list1 中下标是从 0 开始计算
     * 2. 找到 list2的 end 节点, 方便当我们遍历到 b + 1 节点时, 可以直接进行拼接
     *
     * AC: 1ms/45.36MB
     * @param list1 
     * @param a 
     * @param b 
     * @param list2 
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/11 14:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode list1, int a, int b, ListNode list2) {
        ListNode end = list2; // end 为 list2 的不为null的尾节点
        while (end.next != null) {
            end = end.next;
        }

        ListNode dummy = new ListNode(0); // 创建一个盲节点(虚拟节点)
        dummy.next = list1;
        ListNode curr = list1;
        ListNode prev = dummy; // 前一个节点
        int index = 0; // 记录 list1 的下标

        while (index <= b) {
            if (index < a) {
                prev = curr;
            }
            curr = curr.next;
            index++;
        }

        // 拼接 list2
        prev.next = list2;
        end.next = curr;

        return dummy.next;
    }
}
