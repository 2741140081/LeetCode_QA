package com.marks.leetcode.daily_question;

import com.marks.utils.ListNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2095Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/15 10:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2095Test {

    @Test
    void deleteMiddle() {
        LeetCode_2095 leetCode_2095 = new LeetCode_2095();
        // 输入：head = [2,1]
        ListNode head = new ListNode(2);
        head.next = new ListNode(1);
        ListNode result = leetCode_2095.deleteMiddle(head);
    }
}