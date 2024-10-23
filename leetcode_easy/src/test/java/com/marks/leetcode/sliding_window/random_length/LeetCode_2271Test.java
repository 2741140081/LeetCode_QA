package com.marks.leetcode.sliding_window.random_length;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/22 10:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2271Test {

    @Test
    void maximumWhiteTiles() {
        // tiles = [[1,5],[10,11],[12,18],[20,25],[30,32]], carpetLen = 10
        int[][] tiles = {{1, 5}, {30,32}, {10, 11},  {20, 25}, {12, 18}};
        int carpetLen = 10;
        int result = new LeetCode_2271().maximumWhiteTiles(tiles, carpetLen);
        System.out.println(result);
    }
}