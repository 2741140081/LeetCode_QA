package com.marks.leetcode.data_structure.difference;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/18 11:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_56Test {

    @Test
    void merge() {
        int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
        int[][] merge = new LeetCode_56().merge(intervals);
    }
}