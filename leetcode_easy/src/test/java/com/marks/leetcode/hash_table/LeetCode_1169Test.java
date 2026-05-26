package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1169Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/26 14:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1169Test {

    @Test
    void invalidTransactions() {
        // 输入：transactions = ["alice,20,800,mtv","alice,50,100,beijing"]
        String[] transactions = {"alice,20,800,mtv","alice,50,100,beijing"};
        LeetCode_1169 leetCode_1169 = new LeetCode_1169();
        List<String> invalidTransactions = leetCode_1169.invalidTransactions(transactions);
        for (String invalidTransaction : invalidTransactions) {
            System.out.println(invalidTransaction);
        }
    }
}