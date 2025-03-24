package com.marks.leetcode.data_structure.binary_indexed_tree;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/24 17:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2426 {
    
    /**
     * @Description:
     * 给你两个下标从 0 开始的整数数组 nums1 和 nums2 ，两个数组的大小都为 n ，同时给你一个整数 diff ，统计满足以下条件的 数对 (i, j) ：
     *
     * 0 <= i < j <= n - 1 且
     * nums1[i] - nums1[j] <= nums2[i] - nums2[j] + diff.
     * 请你返回满足条件的 数对数目 。
     *
     * tips:
     * n == nums1.length == nums2.length
     * 2 <= n <= 10^5
     * -10^4 <= nums1[i], nums2[i] <= 10^4
     * -10^4 <= diff <= 10^4
     * @param nums1 
     * @param nums2 
     * @param diff
     * @return long
     * @author marks
     * @CreateDate: 2025/3/24 17:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long numberOfPairs(int[] nums1, int[] nums2, int diff) {
        long result;
        result = method_01(nums1, nums2, diff);
        return result;
    }

    /**
     * @Description:
     * nums1[i] - nums1[j] <= nums2[i] - nums2[j] + diff, 0 <= i < j <= n - 1
     * nums1[i] - nums2[i] <= nums1[j] - nums2[j] + diff
     *
     * nums1[i] - nums2[i] - diff <= nums1[j] - nums2[j]
     *
     * AC: 100ms/60.33MB
     * @param nums1 
     * @param nums2 
     * @param diff 
     * @return long
     * @author marks
     * @CreateDate: 2025/3/24 17:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums1, int[] nums2, int diff) {
        int n = nums1.length;
        TreeSet<Long> set = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            set.add((long) (nums1[i] - nums2[i]));
            set.add((long) (nums1[i] - nums2[i] - diff));
        }
        Map<Long, Integer> map = new HashMap<>();
        int index_init = 1;

        for (Long value : set) {
            map.put(value, index_init++);
        }

        BinaryIndexedTree tree = new BinaryIndexedTree(map.size());
        long ans = 0;
        for (int i = 0; i < n; i++) {
            int index = map.get((long)(nums1[i] - nums2[i]));
            ans += tree.query(index);
            tree.add(map.get((long)(nums1[i] - nums2[i] - diff)));
        }
        return ans;
    }

    private static class BinaryIndexedTree {
        private int[] tree;

        public BinaryIndexedTree(int n) {
            tree = new int[n + 1];
        }

        public void add(int i) {
            while (i < tree.length) {
                tree[i]++;
                i += i & -i;
            }
        }

        public long query(int i) {
            long sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= i & -i;
            }
            return sum;
        }
    }
}
