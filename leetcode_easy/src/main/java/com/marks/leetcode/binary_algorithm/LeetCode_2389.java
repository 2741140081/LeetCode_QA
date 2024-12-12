package com.marks.leetcode.binary_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/15 11:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2389 {
    /**
     * @Description: [
     * 给你一个长度为 n 的整数数组 nums ，和一个长度为 m 的整数数组 queries 。
     *
     * 返回一个长度为 m 的数组 answer ，其中 answer[i] 是 nums 中 元素之和小于等于 queries[i] 的 子序列 的 最大 长度  。
     * 子序列 是由一个数组删除某些元素（也可以不删除）但不改变剩余元素顺序得到的一个数组。
     *
     * tips:
     * n == nums.length
     * m == queries.length
     * 1 <= n, m <= 1000
     * 1 <= nums[i], queries[i] <= 10^6
     * ]
     * @param nums
     * @param queries
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/15 11:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] answerQueries(int[] nums, int[] queries) {
        int[] result;
        result = method_01(nums, queries);
        return result;
    }

    /**
     * @Description: [
     * AC:4ms/44.10MB
     * ]
     * @param nums
     * @param queries
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/15 11:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums, int[] queries) {
        int n = nums.length;
        int m = queries.length;
        int[] ans = new int[m];

        // 先对nums进行排序, pre 存储前缀和
        Arrays.sort(nums);
        long[] pre = new long[n];
        pre[0] = nums[0];
        for (int i = 1; i < n; i++) {
            pre[i] = pre[i - 1] + nums[i];
        }

        for (int i = 0; i < m; i++) {
            ans[i] = binarySearch(pre, queries[i]);
        }
        return ans;
    }

    /**
     * @Description: [
     * E1:nums = [4,5,2,1], queries = [3,10,21]
     * int[] pre = {1, 3, 7, 12}
     * target = 3,
     * left = 0, right = 3, mid = 1, pre[1] = 3 == 3
     * left = 2, right = 3, mid = 2, pre[2] = 7 > 3
     * left = 2, right = 1, break
     *
     * target = 10,
     * left = 0, right = 3, mid = 1, pre[1] = 3 < 10
     * left = 2, right = 3, mid = 2, pre[2] = 7 < 10
     * left = 3, right = 3, mid = 3, pre[3] = 12 > 10
     * left = 3, right = 2, break
     *
     * target = 21,
     * left = 0, right = 3, mid = 1, pre[1] = 3 < 21
     * left = 2, right = 3, mid = 2, pre[2] = 7 < 21
     * left = 3, right = 3, mid = 3, pre[3] = 12 < 21
     * left = 4, right = 3, break
     * ]
     * @param pre
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/11/15 11:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int binarySearch(long[] pre, int target) {
        int left = 0;
        int right = pre.length - 1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (pre[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
