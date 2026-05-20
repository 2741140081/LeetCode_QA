package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_12 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/19 10:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_12 {

    /**
     * @Description:
     * 七个不同的符号代表罗马数字，其值如下：
     * I	1
     * V	5
     * X	10
     * L	50
     * C	100
     * D	500
     * M	1000
     * 罗马数字是通过添加从最高到最低的小数位值的转换而形成的。将小数位值转换为罗马数字有以下规则：
     *
     * 如果该值不是以 4 或 9 开头，请选择可以从输入中减去的最大值的符号，将该符号附加到结果，减去其值，然后将其余部分转换为罗马数字。
     * 如果该值以 4 或 9 开头，使用 减法形式，表示从以下符号中减去一个符号，例如 4 是 5 (V) 减 1 (I): IV ，9 是 10 (X) 减 1 (I)：IX。仅使用以下减法形式：4 (IV)，9 (IX)，40 (XL)，90 (XC)，400 (CD) 和 900 (CM)。
     * 只有 10 的次方（I, X, C, M）最多可以连续附加 3 次以代表 10 的倍数。你不能多次附加 5 (V)，50 (L) 或 500 (D)。如果需要将符号附加4次，请使用 减法形式。
     * 给定一个整数，将其转换为罗马数字。
     *
     * tips:
     * 1 <= num <= 3999
     * @param: num
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/05/19 10:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String intToRoman(int num) {
        String result;
        result = method_01(num);
        return result;
    }

    /**
     * @Description:
     * 1. 先用map 存储值和符号
     * 2. 转换为罗马数字是从高位到低位进行处理, 先处理高位数字, 然后处理低位数字.
     * 3. 通过除法操作和取余操作得到每一位的数字
     * 4. temp = num / count; 需要分几种情况讨论 1. temp = 0; 2. 1 <= temp <= 3; 3. temp = 4; 4. 5 <= temp <= 8; 5. temp = 9;
     * AC: 5ms/45.75MB
     * @param: num
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/05/19 10:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(int num) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "I");
        map.put(5, "V");
        map.put(10, "X");
        map.put(50, "L");
        map.put(100, "C");
        map.put(500, "D");
        map.put(1000, "M");
        StringBuilder result = new StringBuilder();
        int count = 1000;
        while (count > 0) {
            int temp = num / count;
            if (1 <= temp && temp <= 3) {
                for (int i = 0; i < temp; i++) {
                    result.append(map.get(count));
                }
            } else if (temp == 4) {
                // 4 需要使用减法, 通过 5 减去 1 得到
                result.append(map.get(count)).append(map.get(count * 5));
            } else if (5 <= temp && temp <= 8) {
                result.append(map.get(count * 5));
                for (int i = 5; i < temp; i++) {
                    result.append(map.get(count));
                }
            } else if (temp == 9) {
                // 9 需要使用减法, 通过 10 减去 1 获取
                result.append(map.get(count)).append(map.get(count * 10));
            }
            num = num % count;
            count = count / 10;
        }

        return result.toString();
    }

}
