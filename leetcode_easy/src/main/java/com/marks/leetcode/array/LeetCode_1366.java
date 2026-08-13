package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1366 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/28 14:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1366 {

    /**
     * @Description:
     * 现在有一个特殊的排名系统，依据参赛团队在投票人心中的次序进行排名，
     * 每个投票者都需要按从高到低的顺序对参与排名的所有团队进行排位。
     * 排名规则如下：
     * 参赛团队的排名次序依照其所获「排位第一」的票的多少决定。如果存在多个团队并列的情况，将继续考虑其「排位第二」的票的数量。以此类推，直到不再存在并列的情况。
     * 如果在考虑完所有投票情况后仍然出现并列现象，则根据团队字母的字母顺序进行排名。
     * 给你一个字符串数组 votes 代表全体投票者给出的排位情况，请你根据上述排名规则对所有参赛团队进行排名。
     * 请你返回能表示按排名系统 排序后 的所有团队排名的字符串。
     *
     * tips:
     * 1 <= votes.length <= 1000
     * 1 <= votes[i].length <= 26
     * votes[i].length == votes[j].length for 0 <= i, j < votes.length
     * votes[i][j] 是英文 大写 字母
     * votes[i] 中的所有字母都是唯一的
     * votes[0] 中出现的所有字母 同样也 出现在 votes[j] 中，其中 1 <= j < votes.length
     * @param: votes
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/07/28 14:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String rankTeams(String[] votes) {
        String result;
        result = method_01(votes);
        return result;
    }

    /**
     * @Description:
     * 1. 统计
     * 2. 对 int[][] cnt 数组进行排序, 例如cnt[0] = {3,2,1} cnt[1] = {3,2,1} 那么 cnt[0] > cnt[1], 如果 cnt[2] = {3,3,1} 则cnt[2] > cnt[0]
     * AC: 5ms/44.79MB
     * @param: votes
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/07/28 14:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String[] votes) {
        int n = votes[0].length();
        int[][] cnt = new int[26][26];
        for (String vote : votes) {
            for (int i = 0; i < n; i++) {
                cnt[vote.charAt(i) - 'A'][i]++;
            }
        }

        // 创建索引数组
        Integer[] idx = new Integer[26];
        for (int i = 0; i < 26; i++) {
            idx[i] = i;
        }
        Arrays.sort(idx, (a, b) -> {
            int[] arr1 = cnt[a];
            int[] arr2 = cnt[b];
            for (int i = 0; i < arr1.length; i++) {
                if (arr1[i] != arr2[i]) {
                    return Integer.compare(arr2[i], arr1[i]);
                }
            }
            return Integer.compare(a, b);
        });
        // 构建排序结果
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char ch = (char) (idx[i] + 'A');
            sb.append(ch);
        }

        return sb.toString();
    }


}
