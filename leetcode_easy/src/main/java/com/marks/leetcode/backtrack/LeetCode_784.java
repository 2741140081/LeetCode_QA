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
 * @date 2025/8/15 15:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_784 {

    /**
     * @Description:
     * 给定一个字符串 s ，通过将字符串 s 中的每个字母转变大小写，我们可以获得一个新的字符串。
     *
     * 返回 所有可能得到的字符串集合 。以 任意顺序 返回输出。
     * @param s
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/8/15 15:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> letterCasePermutation(String s) {
        List<String> result;
//        result = method_01(s);
        result = method_02(s);
        return result;
    }


    /**
     * @Description:
     * 1. 回溯, 并且将 s 转为 char[] array, 通过修改对应 array[index] 的大小写来进行不同情况的递归
     * 2. new String(array),
     * @param s
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/8/15 16:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_02(String s) {
        ans = new ArrayList<>();
        char[] array = s.toCharArray();
        n = array.length;
        backtrack(array, 0);
        return ans;
    }

    private void backtrack(char[] array, int index) {
        while (index < n && Character.isDigit(array[index])) {
            index++;
        }
        if (index == n) {
            ans.add(new String(array));
            return;
        }

        array[index] = Character.toUpperCase(array[index]);
        backtrack(array, index + 1);
        array[index] = Character.toLowerCase(array[index]);
        backtrack(array, index + 1);
    }

    /**
     * @Description:
     * 输入：s = "a1b2"
     * 输出：["a1b2", "a1B2", "A1b2", "A1B2"]
     * 1. 还是按照之前写的递归加回溯的方法
     * 2. 但是有一点需要注意的是, 如果当前字符是数字, 那么就跳过, 继续递归下一个字符
     * 3. 递归结束的条件是, 当前字符是字符串的末尾
     *
     * AC: 8ms(19.96%)/44.63MB(67.35%)
     * @param s 
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/8/15 15:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> ans;
    private int n;
    private List<String> method_01(String s) {
        n = s.length();
        ans = new ArrayList<>();

        backtrack(s, 0, "");
        return ans;
    }

    private void backtrack(String s, int index, String str) {
        while (index < n && s.charAt(index) >= '0' && s.charAt(index) <= '9') {
            str += s.charAt(index);
            index++;
        }
        if (index == n) {
            ans.add(str);
            return;
        }
        // 当前字符是字母, 将当前字母变成大写
        String upperStr = str + Character.toUpperCase(s.charAt(index));
        backtrack(s, index + 1, upperStr);

        // 当前字符是字母, 将当前字母变成小写
        String lowerStr = str + Character.toLowerCase(s.charAt(index));
        backtrack(s, index + 1, lowerStr);
    }

}
