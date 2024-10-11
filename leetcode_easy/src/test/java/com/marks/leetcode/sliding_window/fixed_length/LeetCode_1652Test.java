package com.marks.leetcode.sliding_window.fixed_length;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/11 16:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1652Test {

    @Test
    void decrypt() {
//        int[] code = {5,7,1,4};
//        int k = 3;
        int[] code = {2,4,9,3};
        int k = -2;
        int[] res = new LeetCode_1652().decrypt(code, k);
        Arrays.toString(res);
    }
}