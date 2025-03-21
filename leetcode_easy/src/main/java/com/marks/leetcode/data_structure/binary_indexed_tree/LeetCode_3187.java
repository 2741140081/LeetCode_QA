package com.marks.leetcode.data_structure.binary_indexed_tree;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/19 14:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3187 {
    /**
     * @Description:
     * 数组 arr 中 大于 前面和后面相邻元素的元素被称为 峰值 元素。
     *
     * 给你一个整数数组 nums 和一个二维整数数组 queries 。
     *
     * 你需要处理以下两种类型的操作：
     *
     * queries[i] = [1, li, ri] ，求出子数组 nums[li..ri] 中 峰值 元素的数目。
     * queries[i] = [2, indexi, vali] ，将 nums[indexi] 变为 vali 。
     * 请你返回一个数组 answer ，它依次包含每一个第一种操作的答案。
     *
     * 注意：
     * 子数组中 第一个 和 最后一个 元素都 不是 峰值元素。
     * @param nums 
     * @param queries
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/3/19 14:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> countOfPeaks(int[] nums, int[][] queries) {
        List<Integer> result;
        result = method_01(nums, queries);
        return result;
    }

    /**
     * @Description:
     * 输入：nums = [4,1,4,2,1,5], queries = [[2,2,4],[1,0,2],[1,0,4]]
     *
     * 好像有点难, 后续 wait todo
     * @param nums
     * @param queries
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/3/19 14:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int[] nums, int[][] queries) {
        return new ArrayList<>();
    }
}
