package com.marks.leetcode.gird_dfs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/9 10:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class Interview_Qs_1619Test {

    @Test
    void pondSizes() {
        /*
        [
            [0,2,1,0],
            [0,1,0,1],
            [1,1,0,1],
            [0,1,0,1]
        ]
         */
        int[][] land = new int[][] {{0,2,1,0}, {0,1,0,1}, {1,1,0,1}, {0,1,0,1}};
        int[] result = new Interview_Qs_1619().pondSizes(land);
        System.out.println(result);
    }
}