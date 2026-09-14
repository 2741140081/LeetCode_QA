package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3483 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/11 16:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3483 {

    /**
     * @Description:
     * 给你一个数字数组 digits，你需要从中选择三个数字组成一个三位偶数，你的任务是求出 不同 三位偶数的数量。
     * 注意：每个数字在三位偶数中都只能使用 一次 ，并且 不能 有前导零。
     * tips:
     * 3 <= digits.length <= 10
     * 0 <= digits[i] <= 9
     * @param: digits
     * @return int
     * @author marks
     * @CreateDate: 2026/09/11 16:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int totalNumbers(int[] digits) {
        int result;
        result = method_01(digits);
        return result;
    }

    // AC: 9ms/45.88MB
    private int method_01(int[] digits) {
        int[] cnt = new int[10];
        for (int digit : digits) {
            cnt[digit]++;
        }
        int result = 0;
        for (int i = 100; i < 1000; i += 2) {
            int[] temp = new int[10];
            int a = i / 100;
            int b = (i / 10) % 10;
            int c = i % 10;
            temp[a]++;
            temp[b]++;
            temp[c]++;
            if (temp[a] <= cnt[a] && temp[b] <= cnt[b] && temp[c] <= cnt[c]) {
                result++;
            }
        }
        return result;
    }

}
