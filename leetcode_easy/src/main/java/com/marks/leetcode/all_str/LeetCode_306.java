package com.marks.leetcode.all_str;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_306 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/18 10:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_306 {

    /**
     * @Description:
     * 累加数 是一个字符串，组成它的数字可以形成累加序列。
     * 一个有效的 累加序列 必须 至少 包含 3 个数。除了最开始的两个数以外，序列中的每个后续数字必须是它之前两个数字之和。
     * 给你一个只包含数字 '0'-'9' 的字符串，编写一个算法来判断给定输入是否是 累加数 。如果是，返回 true ；否则，返回 false 。
     * 说明：累加序列里的数，除数字 0 之外，不会 以 0 开头，所以不会出现 1, 2, 03 或者 1, 02, 3 的情况。
     *
     * tips:
     * 1 <= num.length <= 35
     * num 仅由数字（0 - 9）组成
     * @param: num
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/18 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isAdditiveNumber(String num) {
        boolean result;
        result = method_01(num);
        return result;
    }

    /**
     * @Description:
     * 1. 枚举前两个数, 分别用 String a, b 表示, 判断 a, b 自身是否是有效数, 即没有前导零.
     * 2. 计算 a + b, 用 String c 存储, 判断 c 是否是后一个数, 递归执行, 将 a = b, b = c, 然后继续判断 c 是否是后续的字母排列.
     * 3. c 的最小长度是 Math.min(a.length(), b.length());
     * 4. 可以a = 0 或者 b = 0. int remaindLen = n - j 是剩余的长度
     * AC: 1ms/41.94MB
     * @param: num
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/18 10:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String num) {
        int n = num.length();
        if (n < 3) {
            return false;
        }
        StringBuilder sa = new StringBuilder();
        for (int i = 0; i < n / 2; i++) { // 枚举 a 的长度
            sa.append(num.charAt(i));
            StringBuilder sb = new StringBuilder();
            // 剩余给 c 的长度, a = i + 1, b = j - i, c >= Math.max(a, b)
            for (int j = i + 1; j < n - Math.max(sa.length(), sb.length()); j++) {
                sb.append(num.charAt(j));
                if (checkIsValid(num, sa, sb, j + 1)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkIsValid(String num, StringBuilder sa, StringBuilder sb, int startC) {
        // 检查 sa, sb 是否有前导零
        if (sa.charAt(0) == '0' && sa.length() > 1) {
            return false;
        }
        if (sb.charAt(0) == '0' && sb.length() > 1) {
            return false;
        }
        // 从后往前计算 sc, sc = sa + sb, 使用双指针加进位
        StringBuilder sc = new StringBuilder();
        int left = sa.length() - 1;
        int right = sb.length() - 1;
        int carry = 0;
        while (left >= 0 || right >= 0 || carry != 0) {
            int a = left >= 0 ? sa.charAt(left) - '0' : 0;
            int b = right >= 0 ? sb.charAt(right) - '0' : 0;
            int c = a + b + carry;
            sc.append(c % 10);
            carry = c / 10;
            left--;
            right--;
        }
        sc.reverse(); // 翻转操作
        // 判断 num 从 startC 开始是否是 sc 的排列
        if (startC + sc.length() <= num.length()) {
            for (int i = 0; i < sc.length(); i++) {
                if (num.charAt(startC + i) != sc.charAt(i)) {
                    return false;
                }
            }
            // 递归判断 sc 是否是后续的数, 判断是否后后续
            if (startC + sc.length() == num.length()) {
                return true;
            } else {
                return checkIsValid(num, sb, sc, startC + sc.length());
            }
        }
        return false;
    }

}
