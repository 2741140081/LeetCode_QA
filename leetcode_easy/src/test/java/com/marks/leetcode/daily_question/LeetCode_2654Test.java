package com.marks.leetcode.daily_question;

import com.marks.utils.NumberUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2654Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/12 15:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2654Test {

    @Test
    void minOperations() {
        // 需要测试最大公约数怎么计算
        int x = 6, y = 2;
        int gcd = NumberUtil.gcd(x, y);
        System.out.println(gcd);
        int ans = NumberUtil.gcd(gcd, y);
        System.out.println(ans);
    }
}