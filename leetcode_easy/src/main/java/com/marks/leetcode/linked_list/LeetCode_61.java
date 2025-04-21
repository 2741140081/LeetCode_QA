package com.marks.leetcode.linked_list;

import com.marks.utils.ListNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/14 14:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_61 {

    /**
     * @Description:
     *
     * @param head
     * @param k
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/14 14:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode rotateRight(ListNode head, int k) {
        ListNode result;
        result = method_01(head, k);
        return result;
    }

    /**
     * @Description:
     * 1. 计算整个 List 中节点个数 为 n
     * 2. 计算  k = k % n, k 对 n 取余, 并且只需要右移余数就行, 因为右移 n 次, 相当于原来的 List
     * 3. 右移 k 次 相当于将 末尾的 k 个节点反转到开始的位置 (这里有问题, 不是添加在头节点, 而是添加到头结点前)
     * 4. 那我还反转个屁, 直接将最后一段给截取到 dummy 节点后, 然后将尾节点进行修改
     *
     * AC: 0ms/41.56MB
     * @param head
     * @param k
     * @return com.marks.utils.ListNode
     * @author marks
     * @CreateDate: 2025/4/14 14:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode method_01(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode count = dummy; // 计数节点
        int n = 0;
        while (count.next != null) {
            count = count.next;
            n++;
        }

        if (n != 0) {
            k = k % n;
        }

        if (n <= 1 || k == 0) {
            return head;
        }

        int num = n - k; // num 表示这些节点不需要进行反转操作

        // 开始进行 List 的反转
        ListNode curr = head;
        ListNode prev = dummy;

        ListNode start = head; // 需要截取的一段节点的的开始节点

        while (curr.next != null) {
            if (num > 0) {
                prev = curr;
                start = curr.next;
                curr = curr.next;
                num--;
            } else {
                curr = curr.next;
            }
        }
        prev.next = null;
        dummy.next = start;
        curr.next = head;

        return dummy.next;
    }
}
