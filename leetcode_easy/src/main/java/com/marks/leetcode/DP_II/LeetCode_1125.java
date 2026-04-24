package com.marks.leetcode.DP_II;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1125 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/24 14:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1125 {

    /**
     * @Description:
     * 作为项目经理，你规划了一份需求的技能清单 req_skills，并打算从备选人员名单 people 中选出些人组成一个「必要团队」（ 编号为 i 的备选人员 people[i] 含有一份该备选人员掌握的技能列表）。
     * 所谓「必要团队」，就是在这个团队中，对于所需求的技能列表 req_skills 中列出的每项技能，团队中至少有一名成员已经掌握。可以用每个人的编号来表示团队中的成员：
     * 例如，团队 team = [0, 1, 3] 表示掌握技能分别为 people[0]，people[1]，和 people[3] 的备选人员。
     * 请你返回 任一 规模最小的必要团队，团队成员用人员编号表示。你可以按 任意顺序 返回答案，题目数据保证答案存在。
     *
     * tips:
     * 1 <= req_skills.length <= 16
     * 1 <= req_skills[i].length <= 16
     * req_skills[i] 由小写英文字母组成
     * req_skills 中的所有字符串 互不相同
     * 1 <= people.length <= 60
     * 0 <= people[i].length <= 16
     * 1 <= people[i][j].length <= 16
     * people[i][j] 由小写英文字母组成
     * people[i] 中的所有字符串 互不相同
     * people[i] 中的每个技能是 req_skills 中的技能
     * 题目数据保证「必要团队」一定存在
     * @param: req_skills
     * @param: people
     * @return int[]
     * @author marks
     * @CreateDate: 2026/04/24 14:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {
        int[] result;
        result = method_01(req_skills, people);
        result = method_02(req_skills, people);
        return result;
    }

    /**
     * @Description:
     * 1. 改变思路使用动态规划, 并且对于req_skills, 可以使用状态压缩来处理
     * AC: 29ms/49.1MB
     * @param: req_skills
     * @param: people
     * @return int[]
     * @author marks
     * @CreateDate: 2026/04/24 16:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(String[] req_skills, List<List<String>> people) {
        int n = req_skills.length, m = people.size();
        HashMap<String, Integer> skill_index = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            skill_index.put(req_skills[i], i);
        }
        List<Integer>[] dp = new List[1 << n];
        dp[0] = new ArrayList<>();
        for (int i = 0; i < m; ++i) {
            int cur_skill = 0;
            for (String s : people.get(i)) {
                cur_skill |= 1 << skill_index.get(s);
            }
            for (int prev = 0; prev < dp.length; ++prev) {
                if (dp[prev] == null) {
                    continue;
                }
                int comb = prev | cur_skill;
                if (dp[comb] == null || dp[prev].size() + 1 < dp[comb].size()) {
                    dp[comb] = new ArrayList<>(dp[prev]);
                    dp[comb].add(i);
                }
            }
        }
        return dp[(1 << n) - 1].stream().mapToInt(i -> i).toArray();
    }

    private Map<String, Integer> skillMap;
    private List<Integer> smallPeople;

    private int n;
    private int m;
    /**
     * @Description:
     * 1. 使用回溯法来处理, 好像 n 不是很大
     * 2. 使用map<String, Integer> 存储技能和技能编号
     * 可行但是超时: 8/38
     * @param: req_skills
     * @param: people
     * @return int[]
     * @author marks
     * @CreateDate: 2026/04/24 15:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(String[] req_skills, List<List<String>> people) {
        this.n = req_skills.length;
        this.m = people.size();
        skillMap = new HashMap<>();
        smallPeople = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            skillMap.put(req_skills[i], i);
        }

        for (int i = 0; i < m; i++) {
            smallPeople.add(i);
        }
        int[] count = new int[n]; // 记录小组中各类技能的数量
        // 执行回溯
        List<Integer> currPeople = new ArrayList<>();
        backTrack(count, 0, currPeople, people);

        // 将 smallPeople 转换成 int[]
        return smallPeople.stream().mapToInt(Integer::intValue).toArray();
    }

    private void backTrack(int[] count, int currIndex, List<Integer> currPeople, List<List<String>> people) {
        // check count[], 如果所有count[i] > 0, 则更新smallPeople 为更小的集合
        boolean flag = true;
        for (int i = 0; i < n; i++) {
            if (count[i] == 0) {
                flag = false;
                break;
            }
        }
        if (flag) {
            if (smallPeople.size() > currPeople.size()) {
                smallPeople = new ArrayList<>(currPeople);
            }
        }

        if (currIndex == m) {
            // 满足条件
            return;
        }

        List<String> currPeopleSkill = people.get(currIndex);
        if (currPeopleSkill.isEmpty()) {
            // 直接处理下一个
            backTrack(count, currIndex + 1, currPeople, people);
            return;
        }

        // 添加下标 i 到 currPeople, 并且更新 count[]
        currPeople.add(currIndex);
        // 遍历 people[currIndex]
        for (String skill : currPeopleSkill) {
            count[skillMap.get(skill)]++;
        }
        // 递归处理下一个
        backTrack(count, currIndex + 1, currPeople, people);
        // 执行回溯
        currPeople.remove(currPeople.size() - 1);
        for (String skill : currPeopleSkill) {
            count[skillMap.get(skill)]--;
        }
        // 处理下一个
        backTrack(count, currIndex + 1, currPeople, people);
    }

}
