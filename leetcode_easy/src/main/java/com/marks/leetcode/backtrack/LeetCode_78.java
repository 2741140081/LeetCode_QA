package com.marks.leetcode.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/15 15:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_78 {

    /**
     * @Description:
     * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
     *
     * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
     * @param nums
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/8/15 15:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    /**
     * @Description:
     * 回溯优化空间复杂度, 不需要新建一个 List<Integer> 来保存 add(nums[index])，
     * 通过回溯来删除对应的元素
     * AC: 0ms(100%)/41.89MB(62.45%)
     * @param nums
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/8/15 15:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_02(int[] nums) {
        ans = new ArrayList<>();
        n = nums.length;
        backtrack(nums, 0, new ArrayList<>());
        return ans;
    }

    private void backtrack(int[] nums, int index, ArrayList<Integer> list) {
        if (index == n) {
            ans.add(new ArrayList<>(list));
            return;
        }
        list.add(nums[index]);
        backtrack(nums, index + 1, list);
        // 回溯
        list.remove(list.size() - 1);
        backtrack(nums, index + 1, list);
    }


    /**
     * @Description:
     * E1:
     * 输入：nums = [1,2,3]
     * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     *
     * AC: 0ms(100%)/42.25B(5.28%)
     * @param nums
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/8/15 15:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> ans;
    private int n;
    private List<List<Integer>> method_01(int[] nums) {
        ans = new ArrayList<>();
        n = nums.length;

        dfs(nums, 0, new ArrayList<>());
        return ans;
    }

    private void dfs(int[] nums, int index, ArrayList<Integer> list) {
        if (index == n) {
            ans.add(new ArrayList<>(list));
            return;
        }

        // 添加当前元素
        ArrayList<Integer> newList = new ArrayList<>(list);
        newList.add(nums[index]);
        dfs(nums, index + 1, newList);

        dfs(nums, index + 1, list);
    }
}
