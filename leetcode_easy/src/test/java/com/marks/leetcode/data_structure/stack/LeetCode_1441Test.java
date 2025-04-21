package com.marks.leetcode.data_structure.stack;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/14 17:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1441Test {

    @Test
    void buildArray() {
        int[] target = new int[] {1, 2};
        int n = 4;

        List<String> result = new LeetCode_1441().buildArray(target, n);
        System.out.println("");
        for (String s : result) {
            System.out.print(s);
        }
        System.out.println("");
    }
}