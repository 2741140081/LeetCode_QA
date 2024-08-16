package com.marks.leetcode.classic_linear_DP.LIS;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/16 16:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2826Test {

    @Test
    void minimumOperations() {
        List<Integer> nums = Arrays.asList(2,1,3,2,1);
//        List<Integer> nums = Arrays.asList(2);
        System.out.println(new LeetCode_2826().minimumOperations(nums));
    }
}