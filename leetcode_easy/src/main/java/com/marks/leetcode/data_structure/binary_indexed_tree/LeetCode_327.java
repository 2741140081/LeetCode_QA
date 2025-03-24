package com.marks.leetcode.data_structure.binary_indexed_tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/21 14:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_327 {

    /**
     * @Description:
     * 给你一个整数数组 nums 以及两个整数 lower 和 upper 。
     * 求数组中，值位于范围 [lower, upper] （包含 lower 和 upper）之内的 区间和的个数 。
     *
     * 区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * -2^31 <= nums[i] <= 2^31 - 1
     * -10^5 <= lower <= upper <= 10^5
     * 题目数据保证答案是一个 32 位 的整数
     * @param nums
     * @param lower
     * @param upper
     * @return int
     * @author marks
     * @CreateDate: 2025/3/21 14:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countRangeSum(int[] nums, int lower, int upper) {
        int result;
        result = method_01(nums, lower, upper);
        result = method_02(nums, lower, upper);
        return result;
    }

    /**
     * @Description:
     * 归并排序:
     *
     * @param nums
     * @param lower
     * @param upper
     * @return int
     * @author marks
     * @CreateDate: 2025/3/24 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int lower, int upper) {
        return 0;
    }

    /**
     * @Description:
     * E1:
     * 输入：nums = [-2,5,-1], lower = -2, upper = 2
     * -2, 3, 2, 5, 4, -1
     * [-2, 3, 2]
     *
     * i = 0 时, 只考虑 nums[0], 判断是否在 between 之间
     * i = 1, nums[1] = 5, between [3, 7], tree = {-2, 3}
     *
     * 等下, 我这需要逆序 的前缀和
     *
     * wait, 好像又想到一个思路
     * nums -> forr, 倒序
     * -1 放入 tree中, 判断 -1 是否符合要求
     * 然后进行单点更新 -1
     *
     * 算了直接看官解吧, 实在想不到如何处理
     *
     * 假设 nums[] 的前缀和为: pre[]
     * pre[j] - pre[i - 1] (i <= j), 计算 nums[i, j] 之间的和
     *
     * lower <= pre[j] - pre[i - 1] <= upper
     * pre[j] - upper <= pre[i - 1] <= pre[j] - lower
     *
     * AC: 645ms/108.51MB
     * @param nums
     * @param lower
     * @param upper
     * @return int
     * @author marks
     * @CreateDate: 2025/3/21 14:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int lower, int upper) {
        int n = nums.length;
        long[] pres = new long[n + 1];
        for (int i = 0; i < n; i++) {
            pres[i + 1] = pres[i] + nums[i];
        }
        Set<Long> set = new TreeSet<Long>();
        for (long pre : pres) {
            set.add(pre);
            set.add(pre - lower);
            set.add(pre - upper);
        }
        Map<Long, Integer> maps = new HashMap<>();
        int index_init = 1;
        for (Long value : set) {
            maps.put(value, index_init++);
        }

        BinaryIndexedTree tree = new BinaryIndexedTree(maps.size());
        int ans = 0;
        for (int i = 0; i < pres.length; i++) {
            int left = maps.get(pres[i] - upper), right = maps.get(pres[i] - lower);
            ans += tree.get(right) - tree.get(left - 1);
            tree.add(maps.get(pres[i]), 1);
        }

        return ans;
    }

    private static class BinaryIndexedTree {
        private int[] tree;

        public BinaryIndexedTree(int n) {
            tree = new int[n + 1];
        }

        public void add(int i, int val) {
            while (i < tree.length) {
                tree[i] += val;
                i += i & -i;
            }
        }

        public int get(int i) {
            int sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= i & -i;
            }
            return sum;
        }
    }
}
