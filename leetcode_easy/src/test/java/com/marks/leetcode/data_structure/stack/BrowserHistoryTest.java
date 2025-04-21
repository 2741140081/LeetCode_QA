package com.marks.leetcode.data_structure.stack;

import org.junit.jupiter.api.Test;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/15 10:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class BrowserHistoryTest {
    @Test
    void testBrowserHistory() {
        BrowserHistory obj = new BrowserHistory("leetcode.com");
        obj.visit("google.com");
        obj.visit("facebook.com");
        obj.visit("youtube.com");

        System.out.println(obj.back(1));
        System.out.println(obj.back(1));
        System.out.println(obj.forward(1));

        obj.visit("linkedin.com");

        System.out.println(obj.forward(2));
        System.out.println(obj.back(2));
        System.out.println(obj.back(7));
    }
}