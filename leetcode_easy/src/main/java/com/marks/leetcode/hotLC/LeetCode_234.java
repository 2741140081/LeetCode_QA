package com.marks.leetcode.hotLC;

import com.marks.utils.ListNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_234 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/3 11:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_234 {

    /**
     * @Description:
     * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
     * @param: head
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/03 11:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isPalindrome(ListNode head) {
        boolean result;
        result = method_01(head);
        return result;
    }

    /**
     * @Description:
     * 直接把链表转为List，然后判断List是否为回文
     * AC: 8ms/99.14MB
     * @param: head
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/03 11:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        int left = 0, right = list.size() - 1;
        while (left < right) {
            if (!Objects.equals(list.get(left), list.get(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

}
