package com.marks.leetcode.data_structure.union_find;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/11 10:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_721Test {

    @Test
    void accountsMerge() {
        List<List<String>> accounts = new ArrayList<>();
        // accounts = [["John", "johnsmith@mail.com", "john00@mail.com"],
        // ["John", "johnnybravo@mail.com"],
        // ["John", "johnsmith@mail.com", "john_newyork@mail.com"],
        // ["Mary", "mary@mail.com"]]
        List<String> account_1 = new ArrayList<>();
        account_1.add("John");
        account_1.add("johnsmith@mail.com");
        account_1.add("john00@mail.com");
        List<String> account_2 = new ArrayList<>();
        account_2.add("John");
        account_2.add("johnnybravo@mail.com");
        List<String> account_3 = new ArrayList<>();
        account_3.add("John");
        account_3.add("johnsmith@mail.com");
        account_3.add("john_newyork@mail.com");
        List<String> account_4 = new ArrayList<>();
        account_4.add("Mary");
        account_4.add("mary@mail.com");
        accounts.add(account_1);
        accounts.add(account_2);
        accounts.add(account_3);
        accounts.add(account_4);
        List<List<String>> result = new LeetCode_721().accountsMerge(accounts);
    }
}