package com.marks.leetcode.data_structure.binary_indexed_tree;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/19 16:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_493 {

    /**
     * @Description:
     * 给定一个数组 nums ，如果 i < j 且 nums[i] > 2*nums[j] 我们就将 (i, j) 称作一个重要翻转对。
     *
     * 你需要返回给定数组中的重要翻转对的数量。
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/3/19 16:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int reversePairs(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 我们需要的条件是: nums[i] <= 2 * nums[j]
     * 2. 先把基础框架先搭起来
     * 3. 我们可能需要 在 nums_copy 中使用二分法找到  2 * nums[j] 的index 位置, index: 插入的位置 >= nums_copy[i]
     *
     * 找到问题所在了
     * E1: int[] nums_1 = {2,4,3,5,1};
     * E2: int[] nums_2 = {1,2,1,2,1};
     *
     * E1中需要的是 i = 4, nums_1[4] = 1, 1 * 2, 找这个的val 的 index, 但是 getIndexByBinarySearch()
     *
     * 4. 可能需要重新整理一下思路
     * a. 条件: nums[i] > 2*nums[j], i > j
     * b. 这就相当于在 LCR_170的基础上变更
     * c. 这个nums_copy[] 应该不是这样的, 应该用一个list来替代
     * d. 但是我直接用 二分法查找val 的index, 然后得到count, 之后添加 nums[j]
     *
     * 虽然这种很慢, 但是还是 AC: 544ms/55.07MB
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/3/19 16:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return 0;
        }
        long[] numbers = new long[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = nums[i];
        }

        List<Long> nums_copy = new ArrayList<>();
        nums_copy.add(numbers[0]);

        int ans = 0;
        for (int i = 1; i < n; i++) {
            long val = 2 * (long)nums[i];

            int index = getIndexByBinarySearch(nums_copy, val);
            if (index != -1) {
                // val >= all in nums_copy[i], 1, 2, 3, 4; val = 2, index = 2
                ans += (nums_copy.size() - index);
            }
            // 这个添加需要二分法找到index, 然后添加, 以便让 nums_copy 仍然是一个有序List
            int insert_index = getIndexByBinarySearch(nums_copy, numbers[i]);

            nums_copy.add(insert_index == -1 ? nums_copy.size() : insert_index, numbers[i]);
        }
        return ans;
    }

    private int getIndexByBinarySearch(List<Long> nums_copy, long val) {
        int left = 0;
        int right = nums_copy.size() - 1;
        int index = -1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (nums_copy.get(mid) > val) {
                index = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return index;
    }

    private static class BinaryIndexedTree {
        private int[] tree;

        public BinaryIndexedTree(int n) {
            tree = new int[n + 1];
        }

        public void add(long i) {
            while (i < tree.length) {
                tree[(int) i]++;
                i += i & -i;
            }
        }

        public int get(long i) {
            int sum = 0;
            while (i > 0) {
                sum += tree[(int) i];
                i -= i & -i;
            }
            return sum;
        }
    }
}
