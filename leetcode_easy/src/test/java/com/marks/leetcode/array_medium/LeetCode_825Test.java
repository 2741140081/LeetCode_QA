package com.marks.leetcode.array_medium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_825Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/20 17:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_825Test {

    @Test
    void numFriendRequests() {
        // 输入：ages = [16,16]
        int[] ages = {16,16};
        int result;
//        result = new LeetCode_825().numFriendRequests(ages);
//        System.out.println(result);

        // 输入：ages = [16,17,18]
//        ages = new int[]{16,17,18};
//        result = new LeetCode_825().numFriendRequests(ages);
//        System.out.println(result);

        // 输入：ages = [20,30,100,110,120]
//        ages = new int[]{20,30,100,110,120};
//        result = new LeetCode_825().numFriendRequests(ages);
//        System.out.println(result);

        // 输入: ages = [73,106,39,6,26,15,30,100,71,35,46,112,6,60,110]
        ages = new int[]{73,106,39,6,26,15,30,100,71,35,46,112,6,60,110};
        result = new LeetCode_825().numFriendRequests(ages);
        System.out.println(result);
    }
}