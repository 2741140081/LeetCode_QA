package com.marks.leetcode.hotLC;

import com.marks.utils.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_160 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/3 10:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_160 {

    /**
     * @Description:
     * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
     * @param: headA
     * @param: headB
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/12/03 10:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode result;
        result = method_01(headA, headB);
        result = method_02(headA, headB);
        return result;
    }

    /**
     * @Description:
     * 怎么用时间复杂度O(m + n), 空间复杂度O(1) 来解决
     * 1. 假设存在相交点, 两个链表相交长度为c, A的长度:a + c, B的长度:b + c, a != b
     * 2. 假设 a < b; 当pA到达null时, pB节点没有到达, pB剩下的长度为 b - a;
     * 3. 然后交换pA, pB, 相当于pA 从headB 开始, pB 从headA 开始; 当 pA == pB, 说明pA和pB相交, 返回pA;
     * AC: 1ms/46.98MB
     * @param: headA
     * @param: headB
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/12/03 11:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_02(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode pA = headA;
        ListNode pB = headB;
        while (pA != pB) {
            // 两者行走的距离为 a + b + c
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }

    /**
     * @Description:
     * 1. 直接用 HashSet<ListNode> 进行存储各个节点的信息, 对headA 遍历, 将节点作为 key 存储
     * AC: 7ms/48.60MB
     * @param: headA
     * @param: headB
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/12/03 10:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<>();
        while (headA != null) {
            set.add(headA);
            headA = headA.next;
        }
        ListNode curr = null;
        while (headB != null) {
            if (set.contains(headB)) {
                curr = headB;
                break;
            }
            headB = headB.next;
        }
        return curr;
    }

}
