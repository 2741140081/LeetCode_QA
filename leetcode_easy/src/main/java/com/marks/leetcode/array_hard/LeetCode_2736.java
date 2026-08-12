package com.marks.leetcode.array_hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2736 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/7 9:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2736 {

    /**
     * @Description:
     * 给你两个长度为 n 、下标从 0 开始的整数数组 nums1 和 nums2 ，
     * 另给你一个下标从 1 开始的二维数组 queries ，其中 queries[i] = [xi, yi] 。
     * 对于第 i 个查询，在所有满足 nums1[j] >= xi 且 nums2[j] >= yi 的下标 j (0 <= j < n) 中，
     * 找出 nums1[j] + nums2[j] 的 最大值 ，如果不存在满足条件的 j 则返回 -1 。
     * 返回数组 answer ，其中 answer[i] 是第 i 个查询的答案。
     *
     * tips:
     * nums1.length == nums2.length
     * n == nums1.length
     * 1 <= n <= 10^5
     * 1 <= nums1[i], nums2[i] <= 10^9
     * 1 <= queries.length <= 10^5
     * queries[i].length == 2
     * xi == queries[i][1]
     * yi == queries[i][2]
     * 1 <= xi, yi <= 109
     * @param: nums1
     * @param: nums2
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/08/07 9:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] maximumSumQueries(int[] nums1, int[] nums2, int[][] queries) {
        int[] result;
        result = method_01(nums1, nums2, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 对 [0 ~ n] 执行间接索引, 然后根据 nums1[i] 进行升序,
     * 并且使用有序集合存储 nums2[i] 从 0 ~ n - 1 的所有值, 存储在 TreeMap 中
     * 2. 对 queries 执行间接排序, 根据 queries[0] 执行升序排序
     * 3. 当处理第 i 个请求时, int xi = queries[i][0]; yi = queries[i][1],
     * 由于 num1[] 数组是有序的, 所以先找到 nums[j] >= xi, 并且不断移除 [0 ~ j - 1] 的元素从 TreeMap 中,
     * 然后, 由于要取最大值, 所以有序集合 TreeMap 最大值 int two = treeMap.lastKey(); 如果 two < yi, 则 ans[i] = -1,
     * 4. 但是如何找到最大的 nums1[j] + nums2[j], 感觉没可能实现, 这个方案舍弃
     * 5. 新方案是, 修改排序方式, 改成降序排序, TreeMap 存储 nums2[i] 和 sum 值
     * 6. 查看官方题解, 修改方案, 添加单调栈结构用于处理 nums2[] 和 sum
     * AC: 74ms/181.82MB
     * @param: nums1
     * @param: nums2
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/08/07 9:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums1, int[] nums2, int[][] queries) {
        int n = nums1.length, m = queries.length;
        // 创建对 nums1[] 的间接排序索引
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) {
            idx[i] = i;
        }
        Arrays.sort(idx, (i, j) -> nums1[j] - nums1[i]); // 降序排序
        // 创建对 queries[i][0] 的间接排序索引
        Integer[] qIdx = new Integer[m];
        for (int i = 0; i < m; i++) {
            qIdx[i] = i;
        }
        Arrays.sort(qIdx, (i, j) -> queries[j][0] - queries[i][0]); // 降序排序
        // 创建单调递增栈
        List<int[]> stack = new ArrayList<>(); // {nums2[i], sum}

        // 结果集合
        int[] ans = new int[m];
        Arrays.fill(ans, -1);
        // 对qIdx 进行遍历
        int left = 0; // 记录 idx[] 中下标
        for (int i = 0; i < m; i++) {
            int id = qIdx[i];
            int xi = queries[id][0];
            int yi = queries[id][1];
            // 向有序集合添加 >= xi 的元素
            while (left < n && nums1[idx[left]] >= xi) {
                int num1 = nums1[idx[left]];
                int num2 = nums2[idx[left]];
                int sum = num1 + num2;
                // 从 stack 移除 num2* 更小的值
                while (!stack.isEmpty() && stack.get(stack.size() - 1)[1] <= sum) {
                    stack.remove(stack.size() - 1);
                }
                if (stack.isEmpty() || stack.get(stack.size() - 1)[0] < num2) {
                    stack.add(new int[]{num2, sum});
                }
                left++;
            }
            int k = binarySearch(stack, yi);
            if (k < stack.size()) {
                ans[id] = stack.get(k)[1];
            }
        }

        return ans;
    }

    private int binarySearch(List<int[]> list, int target) {
        int low = 0, high = list.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (list.get(mid)[0] >= target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

}
