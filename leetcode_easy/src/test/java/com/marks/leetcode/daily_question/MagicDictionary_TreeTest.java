package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/13 10:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class MagicDictionary_TreeTest {

    @Test
    void search() {
        String[] dictionary = new String[]{"hello", "hallo", "leetcode"};
        MagicDictionary_Tree md_tree = new MagicDictionary_Tree();
        md_tree.buildDict(dictionary);

        boolean res1 = md_tree.search("hello");
        System.out.println(res1);
    }
}