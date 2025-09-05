package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/3 16:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_638Test {

    @Test
    void shoppingOffers() {
        // price = [2,5], special = [[3,0,5],[1,2,10]], needs = [3,2]
        LeetCode_638 solution = new LeetCode_638();

        List<Integer> price1 = Arrays.asList(4,3,2,9,8,8);
        List<List<Integer>> special1 = new ArrayList<>();
        special1.add(Arrays.asList(1,5,5,1,4,0,18));
        special1.add(Arrays.asList(3,3,6,6,4,2,32));
        List<Integer> needs1 = Arrays.asList(6,5,5,6,4,1);

        int result = solution.shoppingOffers(price1, special1, needs1);
//        assertEquals(14, result); // 预期结果为14
    }
}