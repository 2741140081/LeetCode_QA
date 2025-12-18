package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1203Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/18 11:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1203Test {

    @Test
    void sortItems() {
        // 输入：n = 8, m = 2, group = [-1,-1,1,0,0,1,0,-1], beforeItems = [[],[6],[5],[6],[3,6],[],[],[]]
        int n = 8;
        int m = 2;
        int[] group = {-1, -1, 1, 0, 0, 1, 0, -1};
        List<List<Integer>> beforeItems = new ArrayList<>();
        beforeItems.add(new ArrayList<>());
        List<Integer> list1 = new ArrayList<>();
        list1.add(6);
        beforeItems.add(list1);
        List<Integer> list2 = new ArrayList<>();
        list2.add(5);
        beforeItems.add(list2);
        List<Integer> list3 = new ArrayList<>();
        list3.add(6);
        beforeItems.add(list3);
        List<Integer> list4 = new ArrayList<>();
        list4.add(3);
        list4.add(6);
        beforeItems.add(list4);
        List<Integer> list5 = new ArrayList<>();
        beforeItems.add(list5);
        List<Integer> list6 = new ArrayList<>();
        beforeItems.add(list6);
        List<Integer> list7 = new ArrayList<>();
        beforeItems.add(list7);
        LeetCode_1203 leetCode_1203 = new LeetCode_1203();
        int[] result = leetCode_1203.sortItems(n, m, group, beforeItems);
    }
}