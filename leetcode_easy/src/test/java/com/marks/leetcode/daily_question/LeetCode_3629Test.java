package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3629Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/8 11:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3629Test {

    @Test
    void minJumps() {
        // 输入: nums = [893,786,607,137,69,381,790,233,15,42,7,764,890,269,84,262,870,514,514,650,269,485,760,181,489,107,585,428,862,563]
        LeetCode_3629 leetCode_3629 = new LeetCode_3629();
        int[] nums = new int[]{893,786,607,137,69,381,790,233,15,42,7,764,890,269,84,262,870,514,514,650,269,485,760,181,489,107,585,428,862,563};
        int minJumps = leetCode_3629.minJumps(nums);
        System.out.println(minJumps);
    }
}