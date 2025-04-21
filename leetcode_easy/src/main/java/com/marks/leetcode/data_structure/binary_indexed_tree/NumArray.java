package com.marks.leetcode.data_structure.binary_indexed_tree;

/**
 * <p>项目名称: 区域和检索_数组可修改 </p>
 * <p>文件名称: LeetCode_307 </p>
 * <p>描述:
 * 给你一个数组 nums ，请你完成两类查询。
 * 其中一类查询要求 更新 数组 nums 下标对应的值
 * 另一类查询要求返回数组 nums 中索引 left 和索引 right 之间（ 包含 ）的nums元素的 和 ，其中 left <= right
 * 实现 NumArray 类：
 *
 * NumArray(int[] nums) 用整数数组 nums 初始化对象
 * void update(int index, int val) 将 nums[index] 的值 更新 为 val
 * int sumRange(int left, int right) 返回数组 nums 中索引 left 和索引 right 之间（ 包含 ）的nums元素的 和 （即，nums[left] + nums[left + 1], ..., nums[right]）
 * </p>
 *
 * AC: 99ms/74.55MB
 * @author marks
 * @version v1.0
 * @date 2025/3/17 15:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class NumArray {
    private int[] tree;
    private int[] nums;

    public NumArray(int[] nums) {
        int n = nums.length;
        this.nums = new int[n];
        tree = new int[n + 1];
        for (int i = 0; i < n; i++) {
            update(i, nums[i]);
        }
    }

    public void update(int index, int val) {
        int delta = val - nums[index];
        nums[index] = val;
        for (int i = index + 1; i < tree.length; i += i & -i) {
            tree[i] += delta;
        }
    }

    /**
     * @Description: 求前缀和
     * @param i
     * @return int
     * @author marks
     * @CreateDate: 2025/3/17 15:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int pre(int i) {
        int res = 0;
        for (; i > 0; i -= i & -i) {
            res += tree[i];
        }
        return res;
    }

    public int sumRange(int left, int right) {
        if (right < left) {
            return 0;
        }
        return pre(right + 1) - pre(left);
    }
}
