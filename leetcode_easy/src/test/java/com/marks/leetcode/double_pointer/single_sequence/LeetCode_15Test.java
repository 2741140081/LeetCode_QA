package com.marks.leetcode.double_pointer.single_sequence;

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
 * @data 2024/11/5 10:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_15Test {

    @Test
    void threeSum() {
        int[] nums = {-1,0,1,2,-1,-4};
        List<List<Integer>> lists = new LeetCode_15().threeSum(nums);
    }
}