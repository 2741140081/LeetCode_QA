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
 * @data 2024/8/12 16:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class MagicDictionaryTest {

    @Test
    void search() {
        MagicDictionary md = new MagicDictionary();
        String[] dictionary = new String[]{"hello", "hallo", "leetcode"};
        md.buildDict(dictionary);

        boolean t1 = md.search("hello");
        System.out.println("input hello, the result is " + t1);

        boolean t2 = md.search("hhllo");
        System.out.println("input hhllo, the result is " + t2);

        boolean t3 = md.search("hell");
        System.out.println("input hell, the result is " + t3);

        boolean t4 = md.search("leetcoded");
        System.out.println("input leetcoded, the result is " + t4);


    }
}