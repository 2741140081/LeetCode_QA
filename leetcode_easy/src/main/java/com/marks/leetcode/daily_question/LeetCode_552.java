package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: 学生出勤记录 II </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/19 16:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_552 {
    /**
     * @Description: [
     * 可以用字符串表示一个学生的出勤记录，其中的每个字符用来标记当天的出勤情况（缺勤、迟到、到场）。记录中只含下面三种字符：
     * 'A'：Absent，缺勤
     * 'L'：Late，迟到
     * 'P'：Present，到场
     * 如果学生能够 同时 满足下面两个条件，则可以获得出勤奖励：
     *
     * 按 总出勤 计，学生缺勤（'A'）严格 少于两天。
     * 学生 不会 存在 连续 3 天或 连续 3 天以上的迟到（'L'）记录。
     * 给你一个整数 n ，表示出勤记录的长度（次数）。请你返回记录长度为 n 时，可能获得出勤奖励的记录情况 数量 。答案可能很大，所以返回对 10^9 + 7 取余 的结果。
     * tips:
     * 1 <= n <= 105
     * ]
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2024/8/19 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int checkRecord(int n) {
        int result = 0;
        result = method_01(n);
        return result;
    }

    /**
     * @Description: [
     * 解法感觉是dp
     * 动态规划
     * AA LLL 这两个是失败情况
     * 定义 dp = new int[i][j][k]
     * ]
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2024/8/19 17:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        return 0;
    }
}
