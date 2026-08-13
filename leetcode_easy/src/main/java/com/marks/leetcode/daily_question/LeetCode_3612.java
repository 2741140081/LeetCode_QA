package com.marks.leetcode.daily_question;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3612 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/16 10:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3612 {

    /**
     * @Description:
     * 给你一个字符串 s，它由小写英文字母和特殊字符：*、# 和 % 组成。
     * 请根据以下规则从左到右处理 s 中的字符，构造一个新的字符串 result：
     * 如果字符是 小写 英文字母，则将其添加到 result 中。
     * 字符 '*' 会 删除 result 中的最后一个字符（如果存在）。
     * 字符 '#' 会 复制 当前的 result 并 追加 到其自身后面。
     * 字符 '%' 会 反转 当前的 result。
     * 在处理完 s 中的所有字符后，返回最终的字符串 result。
     * tips:
     * 1 <= s.length <= 20
     * s 只包含小写英文字母和特殊字符 *、# 和 %。
     * @param: s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/16 10:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String processStr(String s) {
        String result;
        result = method_01(s);
        result = method_02(s);
        return result;
    }

    // 使用 StringBuilder 处理, AC: 4ms/54.52MB
    private String method_02(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '*':
                    // 删除最后一个字符, 如果存在
                    if (!result.isEmpty()) {
                        result.deleteCharAt(result.length() - 1);
                    }
                    break;
                case '#':
                    // 复制 result 并 追加 到其自身后面
                    result.append(result.toString());
                    break;
                case '%':
                    // 反转 result
                    result.reverse();
                    break;
                default:
                    result.append(c);
            }
        }

        return result.toString();
    }

    /**
     * @Description:
     * 1. 直接模拟, 使用 List<Character> 存储字符
     * AC: 191ms/93.93MB
     * 时间复杂度过高, 是否有优化方法
     * @param: s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/16 10:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        List<Character> result = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '*':
                    // 删除最后一个字符, 如果存在
                    if (!result.isEmpty()) {
                        result.remove(result.size() - 1);
                    }
                    break;
                case '#':
                    // 复制 result 并 追加 到其自身后面
                    if (!result.isEmpty()) {
                        int size = result.size();
                        for (int j = 0; j < size; j++) {
                            result.add(result.get(j));
                        }
                    }
                    break;
                case '%':
                    // 反转 result
                    int size = result.size();
                    int left = 0, right = size - 1;
                    while (left < right) {
                        char temp = result.get(left);
                        result.set(left, result.get(right));
                        result.set(right, temp);
                        left++;
                        right--;
                    }
                    break;
                default:
                    result.add(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Character c : result) {
            sb.append(c);
        }

        return sb.toString();
    }

}
