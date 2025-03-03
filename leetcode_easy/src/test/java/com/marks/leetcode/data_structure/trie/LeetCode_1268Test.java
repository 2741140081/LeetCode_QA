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
 * @date 2025/3/3 17:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1268Test {

    @Test
    void suggestedProducts() {
        // products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
        String[] products = {"mobile","mouse","moneypot","monitor","mousepad"};
        String searchWord = "mouse";
        List<List<String>> lists = new LeetCode_1268().suggestedProducts(products, searchWord);
    }
}