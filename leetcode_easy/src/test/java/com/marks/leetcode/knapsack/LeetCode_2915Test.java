package com.marks.leetcode.knapsack;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/7/29 17:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2915Test {

    @Test
    void lengthOfLongestSubsequence() {
        List<Integer> nums = new ArrayList<>();
        // 1,3,3,8 target = 7
        nums.add(1);
        nums.add(3);
        nums.add(3);
        nums.add(8);
        // 1,2,3,4,5 target = 9
//        nums.add(1);
//        nums.add(2);
//        nums.add(3);
//        nums.add(4);
//        nums.add(5);
        int result = new LeetCode_2915().lengthOfLongestSubsequence(nums, 7);
//        int result = new LeetCode_2915().lengthOfLongestSubsequence(nums, 9);
        System.out.println(result);
    }
}