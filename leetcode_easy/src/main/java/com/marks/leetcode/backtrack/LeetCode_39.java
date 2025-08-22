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
 * @date 2025/8/21 10:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_39 {

    
    /**
     * @Description:
     * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，
     * 找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，并以列表形式返回。
     *
     * 你可以按 任意顺序 返回这些组合。
     * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
     *
     * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
     *
     * tips:
     * 1 <= candidates.length <= 30
     * 2 <= candidates[i] <= 40
     * candidates 的所有元素 互不相同
     * 1 <= target <= 40
     * @param candidates 
     * @param target
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/8/21 10:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result;
        result = method_01(candidates, target);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入：candidates = [2,3,6,7], target = 7
     * 输出：[[2,2,3],[7]]
     * 1. 递归结束的添加 sum == target, 添加当前组合到 ans 中; 当 sum > target, 剪枝 直接 return;
     * 2. 使用List<Integer> list 存储当前组合, 回溯时, 使用 list.remove(list.size() - 1) 删除当前组合的最后一个元素;
     * 每次向 ans 中添加 list, 使用 new ArrayList<>(list) 进行添加。
     * 3. 但是有个问题是, 一般的递归是, 取当前 或者 不取, 但是本题, 取当前元素, 或者不取当前元素, 因为可以重复取当前元素, 所以什么时候能取下一个元素?
     * 那么是否可以用一个 for 循环来取下一个元素? 即对整个 candidates 进行 for 循环, 循环的次数为 candidates.length; 这样应该可行!
     * 4. just do it!
     * AC: 3ms(26.75%)/43.94MB(30.72%)
     * @param candidates 
     * @param target 
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/8/21 10:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> ans;
    private List<List<Integer>> method_01(int[] candidates, int target) {
        ans = new ArrayList<>();
        int sum = 0;
        List<Integer> list = new ArrayList<>();
        backTracking(candidates, target, sum, list, 0);
        return ans;
    }

    private void backTracking(int[] candidates, int target, int sum, List<Integer> list, int index) {
        if (sum == target) {
            ans.add(new ArrayList<>(list));
            return;
        } else if (sum > target) {
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            list.add(candidates[i]);
            backTracking(candidates, target, sum + candidates[i], list, i);
            list.remove(list.size() - 1);
        }
    }
}
