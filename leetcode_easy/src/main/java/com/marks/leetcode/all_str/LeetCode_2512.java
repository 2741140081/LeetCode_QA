package com.marks.leetcode.all_str;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2512 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/23 10:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2512 {

    /**
     * @Description:
     * 给你两个字符串数组 positive_feedback 和 negative_feedback ，分别包含表示正面的和负面的词汇。不会 有单词同时是正面的和负面的。
     * 一开始，每位学生分数为 0 。每个正面的单词会给学生的分数 加 3 分，每个负面的词会给学生的分数 减  1 分。
     * 给你 n 个学生的评语，用一个下标从 0 开始的字符串数组 report 和一个下标从 0 开始的整数数组 student_id 表示，其中 student_id[i] 表示这名学生的 ID ，这名学生的评语是 report[i] 。每名学生的 ID 互不相同。
     *
     * 给你一个整数 k ，请你返回按照得分 从高到低 最顶尖的 k 名学生。如果有多名学生分数相同，ID 越小排名越前。
     * @param: positive_feedback
     * @param: negative_feedback
     * @param: report
     * @param: student_id
     * @param: k
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/06/23 10:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> topStudents(String[] positive_feedback, String[] negative_feedback, String[] report, int[] student_id, int k) {
        List<Integer> result;
        result = method_01(positive_feedback, negative_feedback, report, student_id, k);
        return result;
    }

    /**
     * @Description:
     * 1. 分别将 positive_feedback 和 negative_feedback 转换成 set, 使用 map 存储学生的分数
     * 2. 对于每个 report[i], 使用空格分割, 对于每一个单词, 判断是否在 positive_feedback 或者 negative_feedback 中, 如果在, 则 score + 3 或者 score - 1
     * AC: 83ms/60.81MB
     * @param: positive_feedback
     * @param: negative_feedback
     * @param: report
     * @param: student_id
     * @param: k
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/06/23 10:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(String[] positive_feedback, String[] negative_feedback, String[] report, int[] student_id, int k) {
        Set<String> pSet = new HashSet<>(Arrays.asList(positive_feedback));
        Set<String> nSet = new HashSet<>(Arrays.asList(negative_feedback));
        Map<Integer, Integer> scoreMap = new HashMap<>();
        int n = report.length;
        for (int i = 0; i < n; i++) {
            String[] words = report[i].split(" ");
            int score = 0;
            for (String word : words) {
                if (pSet.contains(word)) {
                    score += 3;
                } else if (nSet.contains(word)) {
                    score -= 1;
                }
            }
            // 不能使用 score != 0 进行过滤, 否则map 中学生数量少于 k.
            scoreMap.merge(student_id[i], score, Integer::sum);
        }
        // 将 map 的结果转换成 list
        List<int[]> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : scoreMap.entrySet()) {
            list.add(new int[]{entry.getKey(), entry.getValue()});
        }
        list.sort((a, b) -> {
            if (a[1] != b[1]) {
                return b[1] - a[1];
            } else {
                return a[0] - b[0];
            }
        });
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            result.add(list.get(i)[0]);
        }

        return result;
    }

}
