package com.marks.leetcode.backtrack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/15 17:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2044 {

    /**
     * @Description:
     * 给你一个整数数组 nums ，请你找出 nums 子集 按位或 可能得到的 最大值 ，并返回按位或能得到最大值的 不同非空子集的数目 。
     *
     * 如果数组 a 可以由数组 b 删除一些元素（或不删除）得到，则认为数组 a 是数组 b 的一个 子集 。如果选中的元素下标位置不一样，则认为两个子集 不同 。
     *
     * 对数组 a 执行 按位或 ，结果等于 a[0] OR a[1] OR ... OR a[a.length - 1]（下标从 0 开始）。
     *
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/8/15 17:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countMaxOrSubsets(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 两个需求, 找到数组的子集中按位或的最大值
     * 2. 最大值的子集数目
     * 3. 设置一个 int max = 0， 记录最大的值， 判断
     * AC: 7ms(79.14%)/40.11MB(95.42%)
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/8/15 17:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int ans;
    private int max;
    private int n;
    private int method_01(int[] nums) {
        n = nums.length;
        max = 0;
        ans = 0;
        dfs(nums, 0, 0);
        return ans;
    }

    private void dfs(int[] nums, int index, int x) {
        if (index == n) {
            if (x > max) {
                max = x;
                ans = 1;
            } else if (x == max) {
                ans++;
            }
            return;
        }
        dfs(nums, index + 1, x | nums[index]);
        dfs(nums, index + 1, x);
    }
}
