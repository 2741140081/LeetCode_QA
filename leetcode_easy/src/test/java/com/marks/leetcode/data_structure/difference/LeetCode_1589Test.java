package com.marks.leetcode.data_structure.difference;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/24 16:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1589Test {

    @Test
    void maxSumRangeQuery() {
        // nums = {1,2,3,4,5}, requests = {{1,3},{0,1}}
        int[] nums = {1,2,3,4,5};
        int[][] requests = {{1,3},{0,1}};
        int result = new LeetCode_1589().maxSumRangeQuery(nums, requests);
        System.out.println(result);
    }
}