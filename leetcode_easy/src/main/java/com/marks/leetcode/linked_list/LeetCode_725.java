package com.marks.leetcode.linked_list;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/9 16:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_725 {
    
    /**
     * @Description:
     * 给你一个头结点为 head 的单链表和一个整数 k ，请你设计一个算法将链表分隔为 k 个连续的部分。
     *
     * 每部分的长度应该尽可能的相等：任意两部分的长度差距不能超过 1 。这可能会导致有些部分为 null 。
     *
     * 这 k 个部分应该按照在链表中出现的顺序排列，并且排在前面的部分的长度应该大于或等于排在后面的长度。
     *
     * 返回一个由上述 k 部分组成的数组。
     * @param head 
     * @param k
     * @return com.marks.leetcode.linked_list.LeetCode_725.ListNode[]
     * @author marks
     * @CreateDate: 2025/4/9 16:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ListNode[] splitListToParts(ListNode head, int k) {
        ListNode[] result;
        result = method_01(head, k);
        return result;
    }

    /**
     * @Description:
     * 输入：head = [1,2,3,4,5,6,7,8,9,10], k = 3
     * 输出：[[1,2,3,4],[5,6,7],[8,9,10]]
     * 解释：
     * 输入被分成了几个连续的部分，并且每部分的长度相差不超过 1 。前面部分的长度大于等于后面部分的长度。
     *
     * 1. 只需要保证长度相差为1, 并且长度可以为0,
     * 2. 假设list 的长度为 n, n / k
     * n = 10, k = 3
     * n = 7, k = 2
     * n = 4, k = 1
     *
     * AC: 0ms/42.65MB
     * @param head 
     * @param k 
     * @return com.marks.leetcode.linked_list.LeetCode_725.ListNode[]
     * @author marks
     * @CreateDate: 2025/4/9 16:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private ListNode[] method_01(ListNode head, int k) {
        int n = 0;
        ListNode temp = head;
        while (temp != null) {
            n++;
            temp = temp.next;
        }
        int quotient = n / k, remainder = n % k;

        ListNode[] parts = new ListNode[k];
        ListNode curr = head;
        for (int i = 0; i < k && curr != null; i++) {
            parts[i] = curr;
            int partSize = quotient + (i < remainder ? 1 : 0);
            for (int j = 1; j < partSize; j++) {
                curr = curr.next;
            }
            ListNode next = curr.next;
            curr.next = null;
            curr = next;
        }
        return parts;
    }

    private class ListNode {
        int val;
        ListNode next;
        
        ListNode() {
            
        }
        
        ListNode(int val) { 
            this.val = val; 
        }
        
        ListNode(int val, ListNode next) { 
            this.val = val; 
            this.next = next; 
        }
    }
}
