package com.marks.leetcode.greedy_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/8 14:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2561Test {

    @Test
    void minCost() {
        // basket1 = [4,2,2,2], basket2 = [1,4,1,2]
        int[] basket1 = {3697,172,5406,5644,5588,4541,2078,172,6492,6152,4545,5660,3310,4525,1971,6655,6562,1793,5938,2317,3459,6889,5799,5237,2027,4545,203,3681,6587,3031,3710,6152,578,818,3370,5381,88,4525,1971,4157,5439,2078,2590,6712,2786,3681,3618,4396,5268,3459,5570,2916,4396,3525,1085,3618,3525,4934,5406,2707,3995,64,5938,3161,2364,2590,527,1943,6587,2184,6383,5268,6492,922,3697,578,2184,3710,6889,1473,6712,4674,3995};
        int[] basket2 = {2317,3053,2916,6655,6325,3511,4929,3161,5660,2027,2557,2343,2563,5588,6562,5466,5570,5572,314,331,922,6504,2559,1793,6504,6086,2563,818,3031,2559,2975,2557,2454,4721,2143,5572,3511,2143,3549,331,4674,176,2454,5237,6383,1943,527,3370,140,88,176,1085,2364,4541,2975,1473,2707,4721,5439,3053,64,314,5381,5904,6086,3310,3549,4157,166,140,2343,5799,203,4934,44,4929,2786,44,166,5644,6325,5904,5466};
        long result = new LeetCode_2561().minCost(basket1, basket2);
        System.out.println(result);
    }
}