package com.marks.leetcode.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/15 10:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_17 {

    /**
     * @Description:
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
     *
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     * 2: abc, 3: def, 4: ghi, 5: jkl, 6: mno, 7: pqrs, 8: tuv, 9: wxyz
     *
     * tips:
     * 0 <= digits.length <= 4
     * digits[i] 是范围 ['2', '9'] 的一个数字。
     * @param digits
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/8/15 10:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> letterCombinations(String digits) {
        List<String> result;
        result = method_01(digits);
        result = method_02(digits);
        return result;
    }

    /**
     * @Description:
     * 1. 用回溯法来写一遍, 看看是否能AC
     * 2. 返回值是void, 输出结果保存在result中
     * 3. 避免了创建大量中间字符串对象，直接在StringBuilder上操作，
     * 4. 在递归返回时进行回溯操作（删除最后一个字符）
     * AC: 0ms/41.19MB
     * @param digits
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/8/15 10:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_02(String digits) {
        List<String> result = new ArrayList<>();
        if (digits.length() == 0) {
            return result;
        }
        String[] map = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backtrack(digits, 0, map, new StringBuilder(), result);
        return result;
    }

    private void backtrack(String digits, int index, String[] map, StringBuilder stringBuilder, List<String> result) {
        if (index == digits.length()) {
            result.add(stringBuilder.toString());
        } else {
            char c = digits.charAt(index);
            for (char c1 : map[c - '2'].toCharArray()) {
                stringBuilder.append(c1);
                backtrack(digits, index + 1, map, stringBuilder, result);
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
        }
    }

    /**
     * @Description:
     * 直接暴力枚举
     * AC: 6ms/41.78MB
     * @param digits 
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/8/15 10:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_01(String digits) {
        if (digits.length() == 0) {
            return new ArrayList<>();
        }
        String[] map = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        List<String> result = new ArrayList<>();
        result.add("");
        for (int i = 0; i < digits.length(); i++) {
            int index = digits.charAt(i) - '2';
            List<String> temp = new ArrayList<>();
            for (String str : result) {
                for (char c : map[index].toCharArray()) {
                    temp.add(str + c);
                }
            }
            result = temp;
        }
        return result;
    }

}
