package com.marks.leetcode.BasicAlgorithm.merge_sort;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称: 归并排序 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/27 14:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_28 {
    /**
     * @Description: [
     * 给你一个链表数组，每个链表都已经按升序排列。
     *
     * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
     *
     * tips:
     * k == lists.length
     * 0 <= k <= 10^4
     * 0 <= lists[i].length <= 500
     * -10^4 <= lists[i][j] <= 10^4
     * lists[i] 按 升序 排列
     * lists[i].length 的总和不超过 10^4
     * ]
     * @param lists
     * @return com.marks.leetcode.BasicAlgorithm.merge_sort.ListNode
     * @author marks
     * @CreateDate: 2024/11/27 14:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode result;
        result = method_01(lists);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
     * 输出：[1,1,2,3,4,4,5,6]
     * 解释：链表数组如下：
     * [
     *   1->4->5,
     *   1->3->4,
     *   2->6
     * ]
     * 将它们合并到一个有序链表中得到。
     * 1->1->2->3->4->4->5->6
     *
     * AC:8ms/43.64MB
     * ]
     * @param lists 
     * @return com.marks.leetcode.BasicAlgorithm.merge_sort.ListNode
     * @author marks
     * @CreateDate: 2024/11/27 14:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode[] lists) {
//        PriorityQueue<ListNode> queue = new PriorityQueue<>((o1, o2) -> o1.val - o2.val);
        PriorityQueue<ListNode> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.val)); // 与上面等效
        for (ListNode list : lists) {
            if (list != null) {
                queue.offer(list);
            }
        }
        ListNode head = new ListNode(0);
        ListNode tail = head;
        while (!queue.isEmpty()) {
            ListNode now = queue.poll();
            tail.next = now;
            tail = tail.next;
            now = now.next;
            if (now != null) {
                queue.offer(now);
            }
        }
        return head.next;
    }
}
