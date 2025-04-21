package com.marks.leetcode.data_structure.heap_advance;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/14 14:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_984 {
    
    /**
     * @Description:
     * 给定两个整数 a 和 b ，返回 任意 字符串 s ，要求满足：
     *
     * s 的长度为 a + b，且正好包含 a 个 'a' 字母与 b 个 'b' 字母；
     * 子串 'aaa' 没有出现在 s 中；
     * 子串 'bbb' 没有出现在 s 中。
     * @param a 
     * @param b 
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/2/14 14:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String strWithout3a3b(int a, int b) {
        String result;
        result = method_01(a, b);
        return result;
    }
    
    /**
     * @Description:
     * b > a
     * bababa
     * b - 1 > a
     * b > a + 1
     * AC: 0ms/40.13MB
     * @param a 
     * @param b 
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/2/14 14:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(int a, int b) {
        StringBuilder sb = new StringBuilder();

        while (a > b && b > 0) { sb.append("aab"); --a; --a; --b; }
        while (b > a && a > 0) { sb.append("bba"); --b; --b; --a; }
        while (a > 0 && b > 0) { sb.append("ab"); --a; --b; }
        while (a > 0) { sb.append('a'); --a; }
        while (b > 0) { sb.append('b'); --b; }
        return sb.toString();
    }
}
