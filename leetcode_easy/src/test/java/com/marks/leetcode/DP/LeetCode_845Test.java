package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_845Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/11 16:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_845Test {

    @Test
    void longestMountain() {
        // 输入：arr = [2,1,4,7,3,2,5]
        //输出：5
        int[] arr = {2,1,4,7,3,2,5};
        LeetCode_845 leetCode_845 = new LeetCode_845();
        int result = leetCode_845.longestMountain(arr);
        System.out.println(result);
    }
}