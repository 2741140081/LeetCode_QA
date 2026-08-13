package com.marks.leetcode.all_str;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1946 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/24 15:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1946 {

    /**
     * @Description:
     * 给你一个字符串 num ，该字符串表示一个大整数。
     * 另给你一个长度为 10 且 下标从 0  开始 的整数数组 change ，该数组将 0-9 中的每个数字映射到另一个数字。
     * 更规范的说法是，数字 d 映射为数字 change[d] 。
     * 你可以选择 突变  num 的任一子字符串。突变 子字符串意味着将每位数字 num[i] 替换为该数字在 change 中的映射（也就是说，将 num[i] 替换为 change[num[i]]）。
     * 请你找出在对 num 的任一子字符串执行突变操作（也可以不执行）后，可能得到的 最大整数 ，并用字符串表示返回。
     * 子字符串 是字符串中的一个连续序列。
     * @param: num
     * @param: change
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/24 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String maximumNumber(String num, int[] change) {
        String result;
        result = method_01(num, change);
        return result;
    }

    /**
     * @Description:
     * 1. 找到第一个突变增加的下标 i, 找到下一个突变变小的下标 j, [i, j - 1] 即为 可变区间
     * 2. 将可变区域进行突变操作, 操作后的结果
     * AC: 21ms/47.27MB
     * @param: num
     * @param: change
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/24 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String num, int[] change) {
        Set<Character> increaseNums = new HashSet<>();
        Set<Character> reduceNums = new HashSet<>();
        for (int i = 0; i < change.length; i++) {
            if (change[i] > i) {
                increaseNums.add((char) (i + '0'));
            } else if (change[i] < i) {
                reduceNums.add((char) (i + '0'));
            }
        }
        int n = num.length();
        int start = -1, end = n;
        for (int i = 0; i < n; i++) {
            if (start == -1 && increaseNums.contains(num.charAt(i))) {
                start = i;
            } else if (start != -1 && reduceNums.contains(num.charAt(i))) {
                end = i;
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (start != -1 && i >= start && i < end) {
                sb.append((char) (change[num.charAt(i) - '0'] + '0'));
            } else {
                sb.append(num.charAt(i));
            }
        }
        return sb.toString();
    }

}
