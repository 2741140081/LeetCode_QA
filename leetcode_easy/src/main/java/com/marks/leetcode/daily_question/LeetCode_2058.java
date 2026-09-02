package com.marks.leetcode.daily_question;

import com.marks.utils.ListNode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2058 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/31 17:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2058 {

    /**
     * @Description:
     * 链表中的 临界点 定义为一个 局部极大值点 或 局部极小值点 。
     * 如果当前节点的值 严格大于 前一个节点和后一个节点，那么这个节点就是一个  局部极大值点 。
     * 如果当前节点的值 严格小于 前一个节点和后一个节点，那么这个节点就是一个  局部极小值点 。
     * 注意：节点只有在同时存在前一个节点和后一个节点的情况下，才能成为一个 局部极大值点 / 极小值点 。
     * 给你一个链表 head ，返回一个长度为 2 的数组 [minDistance, maxDistance] ，
     * 其中 minDistance 是任意两个不同临界点之间的最小距离，maxDistance 是任意两个不同临界点之间的最大距离。
     * 如果临界点少于两个，则返回 [-1，-1] 。
     * @param: head
     * @return int[]
     * @author marks
     * @CreateDate: 2026/08/31 17:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int[] result;
        result = method_01(head);
        return result;
    }

    /**
     * @Description:
     * 1. 首尾不能构成临界点，因为没有前驱或后继节点
     * 2. 使用 List 存储临界点的索引，方便后续计算最小距离和最大距离
     * AC: 12ms/106MB
     * @param: head
     * @return int[]
     * @author marks
     * @CreateDate: 2026/08/31 17:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(ListNode head) {
        int[] ans = {-1, -1};
        if (head == null || head.next == null || head.next.next == null) {
            return ans;
        }
        ListNode cur = head.next;
        ListNode pre = head;
        int index = 0;
        List<Integer> list = new ArrayList<>();

        while (cur != null && cur.next != null) {
            int currVal = cur.val;
            int nextVal = cur.next.val;
            int preVal = pre.val;
            if ((currVal > preVal && currVal > nextVal) || (currVal < preVal && currVal < nextVal)) {
                if (!list.isEmpty()) {
                    int maxDistance = index - list.get(0);
                    ans[1] = maxDistance;
                    int minDistance = index - list.get(list.size() - 1);
                    ans[0] = (ans[0] == -1 ? minDistance : Math.min(ans[0], minDistance));
                }
                list.add(index);
            }
            pre = cur;
            cur = cur.next;
            index++;
        }

        return ans;
    }

}
