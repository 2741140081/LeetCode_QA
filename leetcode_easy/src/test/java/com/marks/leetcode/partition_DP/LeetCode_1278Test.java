package com.marks.leetcode.partition_DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/8 11:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1278Test {

    @Test
    void palindromePartition() {
        String s = "aabbc";
        int k = 2;
        int result = new LeetCode_1278().palindromePartition(s, k);
        System.out.println(result);
    }
}