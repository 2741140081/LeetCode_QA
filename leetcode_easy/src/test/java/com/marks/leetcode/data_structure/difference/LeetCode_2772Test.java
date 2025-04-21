package com.marks.leetcode.data_structure.difference;

import com.marks.leetcode.state_machine_DP.LeetCode_2771;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/19 11:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2772Test {

    @Test
    void checkArray() {
        // nums = [2,2,3,1,1,0], k = 3
        int[] nums = {2,2,3,1,1,0};
        int k = 3;
        boolean result = new LeetCode_2772().checkArray(nums, k);
        System.out.println(result);
    }
}