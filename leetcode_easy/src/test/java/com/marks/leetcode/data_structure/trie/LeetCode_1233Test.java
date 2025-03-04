package com.marks.leetcode.data_structure.trie;

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
 * @date 2025/3/4 10:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1233Test {

    @Test
    void removeSubfolders() {
        String[] folder =  {"/ah/al/am","/ah/al"};
        List<String> list = new LeetCode_1233().removeSubfolders(folder);
    }
}