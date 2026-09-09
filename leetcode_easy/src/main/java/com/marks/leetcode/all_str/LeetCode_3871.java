package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3871 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/9 10:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3871 {

    /**
     * @Description:
     * 给你一个整数 n。
     * 返回将所有从 [1, n]（包含两端）范围内的整数以 标准 数字格式书写时所用到的 逗号总数。
     * 在 标准 格式中：
     * 从右边开始，每 三位 数字后插入一个逗号。
     * 位数 少于四位 的数字不包含逗号。
     *
     * tips:
     * 1 <= n <= 10^15
     * @param: n
     * @return long
     * @author marks
     * @CreateDate: 2026/09/09 10:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long countCommas(long n) {
        long result;
        result = method_01(n);
        result = method_02(n);
        return result;
    }

    // 贡献法，时间复杂度 O(log n)
    private long method_02(long n) {
        long p = 1000, res = 0;
        while (p <= n) {
            res += n - p + 1;
            p *= 1000;
        }
        return res;
    }

    /**
     * @Description:
     * 1. 10^15 = 1,000,000,000,000,000
     * 2. 分类讨论，根据数字的位数不同，逗号的个数也不同, [1 ~ 999] -> 0, [1,000 ~ 999,999] -> 1, [1,000,000 ~ 999,999,999] -> 2, [1,000,000,000 ~ 999,999,999,999] -> 3, [1,000,000,000,000 ~ 999,999,999,999,999] -> 4
     * [1,000,000,000,000,000] -> 5
     * AC: 1ms/42.43MB
     * @param: n
     * @return long
     * @author marks
     * @CreateDate: 2026/09/09 10:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(long n) {
        int len = 1; // 记录数字的位数
        long[][] cnt = new long[7][2]; // 记录每种位数的逗号个数
        long max = 1_000_000_000_000_000_000L;
        long num = 1; // 记录当前数字
        long prev = 1;
        int sum = 0; // 记录逗号数量
        while (num <= max) {
            int curr = (len - 1) / 3;
            if (curr != sum) {
                cnt[sum][0] = prev;
                cnt[sum][1] = num;
                prev = num;
                sum = curr;
            }
            num *= 10;
            len++;
        }
        long ans = 0;
        for (int i = 0; i < 7; i++) {
            if (n >= cnt[i][1]) {
                ans += (cnt[i][1] - cnt[i][0]) * i;
            } else if (n < cnt[i][1]) {
                ans += (n - cnt[i][0] + 1) * i;
                break;
            }
        }

        return ans;
    }

}
