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
 * @date 2025/4/11 10:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3217 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个链表的头节点 head。从链表中移除所有存在于 nums 中的节点后，返回修改后的链表的头节点。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * nums 中的所有元素都是唯一的。
     * 链表中的节点数在 [1, 10^5] 的范围内。
     * 1 <= Node.val <= 10^5
     * 输入保证链表中至少有一个值没有在 nums 中出现过。
     * @param nums
     * @param head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/11 10:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode modifiedList(int[] nums, ListNode head) {
        ListNode result;
        result = method_01(nums, head);
        return result;
    }

    /**
     * @Description:
     * AC: 20ms/65.16MB
     * @param nums
     * @param head
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/11 10:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(int[] nums, ListNode head) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        ListNode dummy = new ListNode(0); // head.val 中最小值为1
        dummy.next = head;
        ListNode prev = dummy; // 前一个未移除的节点, 方便删除节点
        ListNode curr = head; // 当前节点, 用于判断是否符合条件, 以及是否需要删除该节点

        while (curr.next != null) {
            if (set.contains(curr.val)) {
                prev.next = curr.next;
            } else {
                prev = curr;
            }
            curr = curr.next;
        }
        if (set.contains(curr.val)) {
            // 最后一个节点值存在于set集合中, 需要删除最后一个节点
            prev.next = null;
        }
        return dummy.next;
    }
}
