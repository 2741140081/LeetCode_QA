package com.marks.leetcode.all_str;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_166 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/17 17:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_166 {

    /**
     * @Description:
     * 给定两个整数，分别表示分数的分子 numerator 和分母 denominator，以 字符串形式返回小数 。
     * 如果小数部分为循环小数，则将循环的部分括在括号内。
     * 如果存在多个答案，只需返回 任意一个 。
     * 对于所有给定的输入，保证 答案字符串的长度小于 10^4 。
     * 注意，如果分数可以表示为有限长度的字符串，则 必须 返回它。
     *
     * tips:
     * -2^31 <= numerator, denominator <= 2^31 - 1
     * denominator != 0
     * @param: numerator
     * @param: denominator
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/17 17:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String fractionToDecimal(int numerator, int denominator) {
        String result;
        result = method_01(numerator, denominator);
        return result;
    }

    /**
     * @Description:
     * 1. 分数形式必定不是无理数, 存在3种情况, 整数, 小数, 循环小数
     * 2. 需要将整数部分与小数部分进行分开运算, 最后组合成最终结果.
     * 3. 如何找到正确的小数循环体? 使用 Set 判断除数是否存在, 如果已经存在, 则小数部分说循环体
     * 4. 需要非常注意细节, Math.abs(-2147483648) = -2147483648, 由于发生溢出, 所以返回值还是一个负数, 需要使用long 处理
     * AC: 2ms/42.29MB
     * @param: numerator
     * @param: denominator
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/17 17:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(int numerator, int denominator) {
        long numeratorLong = (long) numerator;
        long denominatorLong = (long) denominator;
        if (numeratorLong == 0) {
            return "0";
        }
        if (numeratorLong % denominatorLong == 0) {
            return String.valueOf(numeratorLong / denominatorLong);
        }
        StringBuilder result = new StringBuilder();
        if ((numeratorLong > 0 && denominatorLong < 0) || (numeratorLong < 0 && denominatorLong > 0)) {
            result.append("-");
        }
        numeratorLong = Math.abs(numeratorLong);
        denominatorLong = Math.abs(denominatorLong);
        result.append(numeratorLong / denominatorLong);
        result.append(".");
        numeratorLong %= denominatorLong;
        Map<Long, Integer> map = new HashMap<>();
        List<Long> list = new ArrayList<>();
        int idx = 0;
        while (numeratorLong != 0 && !map.containsKey(numeratorLong)) {
            map.put(numeratorLong, idx++);
            numeratorLong *= 10;
            list.add(numeratorLong / denominatorLong);
            numeratorLong %= denominatorLong;
        }
        if (numeratorLong == 0) {
            for (Long num : list) {
                result.append(num);
            }
        } else if (map.containsKey(numeratorLong)) {
            for (int i = 0; i < map.get(numeratorLong); i++) {
                result.append(list.get(i));
            }
            result.append("(");
            for (int i = map.get(numeratorLong); i < list.size(); i++) {
                result.append(list.get(i));
            }
            result.append(")");
        }
        return result.toString();
    }

}
