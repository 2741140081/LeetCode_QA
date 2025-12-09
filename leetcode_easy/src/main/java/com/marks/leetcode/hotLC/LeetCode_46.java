package com.marks.leetcode.hotLC;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_46 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/9 10:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_46 {

    /**
     * @Description:
     * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
     * @param: nums
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/12/09 10:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 回溯 + 标记
     * AC: 1ms/44.91MB
     * @param: nums
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/12/09 10:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> ans;
    private List<List<Integer>> method_01(int[] nums) {
        boolean[] used = new boolean[nums.length];
        ans = new ArrayList<>();
        backTrack(nums, used, new ArrayList<>(), 0);
        return ans;
    }

    private void backTrack(int[] nums, boolean[] used, ArrayList<Integer> list, int count) {
        if (count == nums.length) {
            ans.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            used[i] = true;
            list.add(nums[i]);
            backTrack(nums, used, list, count + 1);
            list.remove(list.size() - 1);
            used[i] = false;
        }
    }

}
