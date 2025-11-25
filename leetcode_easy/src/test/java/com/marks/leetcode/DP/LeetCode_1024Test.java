package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1024Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/25 14:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1024Test {

    @Test
    void videoStitching() {
        // 输入：clips = [[0,2],[4,6],[8,10],[1,9],[1,5],[5,9]], time = 10
        // 输出：3
        int[][] clips = {{0,2},{4,6},{8,10},{1,9},{1,5},{5,9}};
        int time = 10;
        int result = new LeetCode_1024().videoStitching(clips, time);
        System.out.println(result);
    }
}