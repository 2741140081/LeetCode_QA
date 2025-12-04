package com.marks.leetcode.hotLC;

import com.marks.utils.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_141 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/3 14:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_141 {

    /**
     * @Description: [方法描述]
     * @param: head
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/03 14:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean hasCycle(ListNode head) {
        boolean result;
        result = method_01(head);
        result = method_02(head);
        return result;
    }

    /**
     * @Description:
     * 使用快慢指针来判断链表是否成环, slow 每次移动 1 步, fast 每次移动 2 步
     * AC: 0ms/46.10MB
     * @param: head
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/03 14:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_02(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Description:
     * 直接用Set<ListNode> 进行存储各个节点的信息, 对head 遍历, 将节点作为 key 存储并且判断
     * AC: 6ms/45.62MB
     * @param: head
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/03 14:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) {
                return true;
            }
            set.add(head);
            head = head.next;
        }
        return false;
    }

}
