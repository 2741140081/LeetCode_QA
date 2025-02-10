package com.marks.leetcode.data_structure.heap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/10 11:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2406Test {

    @Test
    void minGroups() {
        int[][] intervals = {{441459,446342},{801308,840640},{871890,963447},{228525,336985},{807945,946787},{479815,507766},{693292,944029},{751962,821744}};
        int result = new LeetCode_2406().minGroups(intervals);
        System.out.println(result);
    }
}