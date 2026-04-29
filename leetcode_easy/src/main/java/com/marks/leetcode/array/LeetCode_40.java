package com.marks.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_40 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/27 15:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_40 {

    /**
     * @Description:
     * 给定一个候选人编号的集合 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     * candidates 中的每个数字在每个组合中只能使用 一次 。
     * 注意：解集不能包含重复的组合。
     *
     * tips:
     * 1 <= candidates.length <= 100
     * 1 <= candidates[i] <= 50
     * 1 <= target <= 30
     * @param: candidates
     * @param: target
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/04/27 15:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result;
        result = method_01(candidates, target);
        result = method_02(candidates, target);
        return result;
    }
    private List<List<Integer>> result;
    private List<int[]> freq = new ArrayList<>();
    private List<Integer> sequence = new ArrayList<>();

    private List<List<Integer>> method_02(int[] candidates, int target) {
        Arrays.sort(candidates);
        result = new ArrayList<>();
        for (int number : candidates) {
            if (number > target) {
                break;
            } else {
                if (freq.isEmpty() || number != freq.get(freq.size() - 1)[0]) {
                    freq.add(new int[]{number, 1});
                } else {
                    freq.get(freq.size() - 1)[1]++;
                }
            }
        }
        dfs(0, target);
        return result;
    }

    private void dfs(int i, int rest) {
        if (rest == 0) {
            result.add(new ArrayList<>(sequence));
            return;
        }
        if (i == freq.size() || rest < freq.get(i)[0]) {
            return;
        }
        dfs(i + 1, rest);
        int most = Math.min(freq.get(i)[1], rest / freq.get(i)[0]);
        for (int j = 1; j <= most; j++) {
            sequence.add(freq.get(i)[0]);
            dfs(i, rest - j * freq.get(i)[0]);
        }
        for (int j = 1; j <= most; j++) {
            sequence.remove(sequence.size() - 1);
        }
    }

    private List<List<Integer>> ans;
    /**
     * @Description:
     * 1. 使用回溯法,
     * 超时: 172/176
     * @param: candidates
     * @param: target
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/04/27 15:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(int[] candidates, int target) {
        // 先清理数据
        List<Integer> candidatesList = new ArrayList<>();
        for (int candidate : candidates) {
            if (candidate <= target) {
                candidatesList.add(candidate);
            }
        }
        // 对列表进行排序
        candidatesList.sort(Integer::compareTo);
        List<Integer> path = new ArrayList<>();
        ans = new ArrayList<>();
        backTracking(candidatesList, target, path, 0, 0);

        return ans;
    }

    private void backTracking(List<Integer> candidatesList, int target, List<Integer> path, int i, int sum) {
        if (sum > target) {
            return;
        }
        if (sum == target) {
            boolean canInsert = true;
            for (List<Integer> list : ans) {
                if (list.equals(path)) {
                    canInsert = false;
                    break;
                }
            }
            if (canInsert) {
                ans.add(new ArrayList<>(path));
            }
        }
        for (int j = i; j < candidatesList.size(); j++) {
            path.add(candidatesList.get(j));
            backTracking(candidatesList, target, path, j + 1, sum + candidatesList.get(j));
            path.remove(path.size() - 1);
        }

    }

}
