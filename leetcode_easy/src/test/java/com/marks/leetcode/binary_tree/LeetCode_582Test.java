package com.marks.leetcode.binary_tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/30 10:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_582Test {

    @Test
    void killProcess() {
        int[] pid =  {1, 3, 10, 5};
        int[] ppid = {3, 0, 5, 3};
        int kill = 5;
        int[] result = new LeetCode_582().killProcess(pid, ppid, kill);
    }
}