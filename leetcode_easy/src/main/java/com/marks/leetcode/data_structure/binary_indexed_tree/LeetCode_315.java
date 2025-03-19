package com.marks.leetcode.data_structure.binary_indexed_tree;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/19 15:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_315 {
    /**
     * @Description:
     * 给你一个整数数组 nums ，按要求返回一个新数组 counts 。
     * 数组 counts 有该性质： counts[i] 的值是  nums[i] 右侧小于 nums[i] 的元素的数量。
     * @param nums
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/3/19 15:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: 
     * 与 LCR_170 基本一致
     *
     * AC: 93ms/58.02MB
     * @param nums 
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/3/19 15:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            List<Integer> list = new ArrayList<>();
            list.add(0);
            return list;
        }
        int[] nums_copy = Arrays.copyOf(nums, n);
        Arrays.sort(nums_copy);

        Map<Integer, Integer> map = new HashMap<>();
        int index = 1;
        for (int i = n - 1; i >= 0; i--) {
            map.put(nums_copy[i], index++);
        }
        BinaryIndexedTree tree = new BinaryIndexedTree(n);
        tree.add(map.get(nums[n - 1]));
        int count = 1;
        List<Integer> list = new ArrayList<>();
        list.add(0);
        for (int i = n - 2; i >= 0; i--) {
            list.add(count - tree.get(map.get(nums[i])));
            tree.add(map.get(nums[i]));
            count++;
        }
        Collections.reverse(list);
        return list;
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
