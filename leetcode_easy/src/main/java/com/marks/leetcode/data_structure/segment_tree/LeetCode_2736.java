package com.marks.leetcode.data_structure.segment_tree;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2736 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/3 10:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2736 {

    /**
     * @Description:
     * 给你两个长度为 n 、下标从 0 开始的整数数组 nums1 和 nums2 ，另给你一个下标从 1 开始的二维数组 queries ，其中 queries[i] = [xi, yi] 。
     * 对于第 i 个查询，在所有满足 nums1[j] >= xi 且 nums2[j] >= yi 的下标 j (0 <= j < n) 中，找出 nums1[j] + nums2[j] 的 最大值 ，如果不存在满足条件的 j 则返回 -1 。
     * 返回数组 answer ，其中 answer[i] 是第 i 个查询的答案。
     * tips:
     * nums1.length == nums2.length
     * n == nums1.length
     * 1 <= n <= 10^5
     * 1 <= nums1[i], nums2[i] <= 10^9
     * 1 <= queries.length <= 10^5
     * queries[i].length == 2
     * xi == queries[i][1]
     * yi == queries[i][2]
     * 1 <= xi, yi <= 10^9
     * @param: nums1
     * @param: nums2
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/02/03 10:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] maximumSumQueries(int[] nums1, int[] nums2, int[][] queries) {
        int[] result;
        result = method_01(nums1, nums2, queries);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：nums1 = [4,3,1,2], nums2 = [2,4,9,5], queries = [[4,1],[1,3],[2,5]]
     * 输出：[6,10,7]
     * 1. 如果用线段树来处理[4, 2, 6] [3, 4, 7] 这两个怎么进行线段树?  先思考如何暴力处理, 然后考虑用线段树优化
     * 2. 暴力解法: 对于查询 i, 有 xi <= nums1[j] && yi <= nums2[j], 通过 xi 和 yi 找到满足添加的 j1 和 j2 集合吧.
     * 3. 将 nums1 与 nums2 进行结合后排序 int[][] arr = new int[n][2]; {nums1[j], nums2[j]}, 然后对 arr 进行排序
     * @param: nums1
     * @param: nums2
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/02/03 10:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums1, int[] nums2, int[][] queries) {

        return null;
    }

}
