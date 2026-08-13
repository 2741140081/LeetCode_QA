package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1864 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/24 14:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1864 {

    /**
     * @Description:
     * 给你一个二进制字符串 s ，现需要将其转化为一个 交替字符串 。请你计算并返回转化所需的 最小 字符交换次数，如果无法完成转化，返回 -1 。
     * 交替字符串 是指：相邻字符之间不存在相等情况的字符串。例如，字符串 "010" 和 "1010" 属于交替字符串，但 "0100" 不是。
     * 任意两个字符都可以进行交换，不必相邻 。
     * tips:
     * 1 <= s.length <= 1000
     * s[i] 的值为 '0' 或 '1'
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/06/24 14:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minSwaps(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 统计字符串中0的个数，统计字符串中1的个数, 如果差值绝对值大于1，则无法完成转化，返回-1
     * AC: 1ms/42.2MB
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/06/24 14:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int zeroCount = 0;
        int oneCount = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                zeroCount++;
            } else {
                oneCount++;
            }
        }
        if (Math.abs(zeroCount - oneCount) > 1) {
            return -1;
        }

        int ans = n;
        if (zeroCount == oneCount) {
            ans = Math.min(ans, buildSwapStr('0', s, n));
            ans = Math.min(ans, buildSwapStr('1', s, n));
        } else if (zeroCount > oneCount) {
            ans = Math.min(ans, buildSwapStr('0', s, n));
        } else {
            ans = Math.min(ans, buildSwapStr('1', s, n));
        }

        return ans;
    }

    private int buildSwapStr(char startValue, String s, int n) {
        int cnt = 0;
        for (int i = 0; i < n; i += 2) {
            if (s.charAt(i) != startValue) {
                cnt++;
            }
        }
        return cnt;
    }

}
