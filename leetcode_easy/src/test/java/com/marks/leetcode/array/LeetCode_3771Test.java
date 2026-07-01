package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3771Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/1 10:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3771Test {

    @Test
    void totalScore() {
        // 输入： hp = 11, damage = [3,6,7], requirement = [4,2,5]
        int hp = 11;
        int[] damage = {3,6,7};
        int[] requirement = {4,2,5};
        long result = new LeetCode_3771().totalScore(hp, damage, requirement);
        System.out.println(result);
    }
}