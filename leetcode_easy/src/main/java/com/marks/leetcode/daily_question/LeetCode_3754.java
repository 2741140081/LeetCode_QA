package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3754 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/7 9:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3754 {

    /**
     * @Description:
     * 给你一个整数 n。
     * 将 n 中所有的 非零数字 按照它们的原始顺序连接起来，形成一个新的整数 x。如果不存在 非零数字 ，则 x = 0。
     * sum 为 x 中所有数字的 数字和 。
     * 返回一个整数，表示 x * sum 的值。
     * @param: n
     * @return long
     * @author marks
     * @CreateDate: 2026/07/07 9:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long sumAndMultiply(int n) {
        long result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 1. 将 n 转换为字符串, StringBuilder 存储非零数字的原始顺序, long sum 存储非零数字的数字和
     * AC: 2ms/41.98MB
     * @param: n
     * @return long
     * @author marks
     * @CreateDate: 2026/07/07 9:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int n) {
        StringBuilder sb = new StringBuilder();
        String str = String.valueOf(n);
        long sum = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != '0') {
                sb.append(c);
                sum += c - '0';
            }
        }
        long x = 0;
        if (!sb.isEmpty()) {
            x = Long.parseLong(sb.toString());
        }
        return x * sum;
    }

}
