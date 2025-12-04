package com.marks.leetcode.hotLC;

import com.marks.utils.ListNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_142 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/3 15:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_142 {

    /**
     * @Description:
     * 给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
     * 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。
     * 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
     * 不允许修改 链表。
     * @param: head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/12/03 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode detectCycle(ListNode head) {
        ListNode result;
        result = method_01(head);
        result = method_02(head);
        return result;
    }

    /**
     * @Description:
     * 还是用快慢指针
     * 1. 从头节点到第一个环节点入口的距离是a, 第一个环节点到第一次指针相遇的距离是 b, 指针相遇到第一次环节点入口的距离是 c
     * 2. 满指针移动距离是 sLen: a + b; 快指针移动的距离是 fLen: 2 * (a + b) = a + 2b + c; => a == c
     * 4. 并且当前快慢指针都在c处, 所以需要一个指针重置到head处重新出发, 并且将快慢指针速度变为1, 当快慢指针重新相遇点即为环入口点
     *
     * AC: 0ms/45.85MB
     * @param: head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/12/03 16:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_02(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }
        if (fast == null || fast.next == null) {
            return null;
        }
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * @Description:
     * 直接用Set 进行判断, 并且存储节点的下标
     * AC: 8ms/45.71MB
     * @param: head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/12/03 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) {
                return head;
            }
            set.add(head);
            head = head.next;
        }

        return null;
    }

}
