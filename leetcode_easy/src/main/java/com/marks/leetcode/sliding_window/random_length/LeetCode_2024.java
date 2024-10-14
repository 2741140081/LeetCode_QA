package com.marks.leetcode.sliding_window.random_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/14 17:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2024 {
    /**
     * @Description: [
     * 一位老师正在出一场由 n 道判断题构成的考试，每道题的答案为 true （用 'T' 表示）或者 false （用 'F' 表示）。
     * 老师想增加学生对自己做出答案的不确定性，方法是 最大化 有 连续相同 结果的题数。（也就是连续出现 true 或者连续出现 false）。
     *
     * 给你一个字符串 answerKey ，其中 answerKey[i] 是第 i 个问题的正确结果。除此以外，还给你一个整数 k ，表示你能进行以下操作的最多次数：
     *
     * 每次操作中，将问题的正确答案改为 'T' 或者 'F' （也就是将 answerKey[i] 改为 'T' 或者 'F' ）。
     * 请你返回在不超过 k 次操作的情况下，最大 连续 'T' 或者 'F' 的数目。
     * 
     * tips:
     * n == answerKey.length
     * 1 <= n <= 5 * 10^4
     * answerKey[i] 要么是 'T' ，要么是 'F'
     * 1 <= k <= n
     * ]
     * @param answerKey
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 17:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxConsecutiveAnswers(String answerKey, int k) {
        int result = 0;
        result = method_01(answerKey, k);
        return result;
    }
    /**
     * @Description: [
     * 输入：answerKey = "TFFT", k = 1
     * 输出：3
     * 解释：我们可以将最前面的 'T' 换成 'F' ，得到 answerKey = "FFFT" 。
     * 或者，我们可以将第二个 'T' 换成 'F' ，得到 answerKey = "TFFF" 。
     * 两种情况下，都有三个连续的 'F' 。
     *
     * AC:17ms/43.73MB
     * ]
     * @param answerKey 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 17:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String answerKey, int k) {
        int n = answerKey.length();
        int ans = 0;
        int left_T = 0;
        int count_T = 0;
        int left_F = 0;
        int count_F = 0;
        for (int i = 0; i < n; i++) {
            char ch = answerKey.charAt(i);
            if (ch == 'T') {
                count_T++;
            }else {
                count_F++;
            }
            if (count_T > k) {
                while (left_F < n && answerKey.charAt(left_F) != 'T') {
                    left_F++;
                }
                left_F++;
                count_T--;
            }
            if (count_F > k) {
                while (left_T < n && answerKey.charAt(left_T) != 'F') {
                    left_T++;
                }
                left_T++;
                count_F--;
            }
            ans = Math.max(ans, i - left_T + 1);
            ans = Math.max(ans, i - left_F + 1);
        }
        return ans;
    }
}
