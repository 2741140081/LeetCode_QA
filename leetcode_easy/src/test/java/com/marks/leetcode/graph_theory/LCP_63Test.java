package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_63Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/21 14:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LCP_63Test {

    @Test
    void ballGame() {
        // 输入： num = 5 plate = [".....","..E..",".WO..","....."]
        // 输出：[[0,1],[1,0],[2,4],[3,2]]
        String[] plate = {"..E..OEEO.","..O..EEEOE",".EE.WE..OW","..EEWE.W..","...EE.WEE.","W.E...WEE.","WW.WEEE.WW",".WW...WOOO"};
        int num = 55;
        LCP_63 lcp_63 = new LCP_63();
        int[][] res = lcp_63.ballGame(num, plate);
        System.out.println(res.length);
    }
}