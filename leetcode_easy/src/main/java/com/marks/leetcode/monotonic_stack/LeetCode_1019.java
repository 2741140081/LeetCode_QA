package com.marks.leetcode.monotonic_stack;

import com.marks.utils.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/28 11:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1019 {
    /**
     * @Description: [功能描述]
     * @param head
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/28 11:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] nextLargerNodes(ListNode head) {
        int[] result;
        result = method_01(head);
        return result;
    }

    /**
     * @Description: [
     * 给定一个长度为 n 的链表 head
     *
     * 对于列表中的每个节点，查找下一个 更大节点 的值。也就是说，对于每个节点，找到它旁边的第一个节点的值，这个节点的值 严格大于 它的值。
     *
     * 返回一个整数数组 answer ，其中 answer[i] 是第 i 个节点( 从1开始 )的下一个更大的节点的值。
     * 如果第 i 个节点没有下一个更大的节点，设置 answer[i] = 0 。
     *
     * AC:13ms/46.04MB
     * ]
     * @param head
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/28 11:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(ListNode head) {
        int n = getListNodeLength(head);
        int[] ans = new int[n];
        int[] array = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        ListNode curr = head;
        for (int i = 0; i < n; i++) {
            int num = head.val;
            array[i] = num;
            while (!stack.isEmpty() && array[stack.peek()] < num) {
                Integer preIndex = stack.poll();
                ans[preIndex] = num;
            }
            stack.push(i);
            head = head.next;
        }
        return ans;
    }

    private int getListNodeLength(ListNode head) {
        ListNode currNode = head;
        int len = 0;
        while (currNode != null) {
            len++;
            currNode = currNode.next;
        }
        return len;
    }
}
