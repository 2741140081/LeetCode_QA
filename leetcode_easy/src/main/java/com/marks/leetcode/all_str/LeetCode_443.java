package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_443 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/22 15:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_443 {

    /**
     * @Description:
     * 给你一个字符数组 chars ，请使用下述算法压缩：
     * 从一个空字符串 s 开始。对于 chars 中的每组 连续重复字符 ：
     * 如果这一组长度为 1 ，则将字符追加到 s 中。
     * 否则，需要向 s 追加字符，后跟这一组的长度。
     * 压缩后得到的字符串 s 不应该直接返回 ，需要转储到字符数组 chars 中。
     * 需要注意的是，如果组长度为 10 或 10 以上，则在 chars 数组中会被拆分为多个字符。
     * 请在 修改完输入数组后 ，返回该数组的新长度。
     * 你必须设计并实现一个只使用常量额外空间的算法来解决此问题。
     * 注意：数组中超出返回长度的字符无关紧要，应予忽略。
     * tips:
     * 1 <= chars.length <= 2000
     * chars[i] 可以是小写英文字母、大写英文字母、数字或符号
     * @param: chars
     * @return int
     * @author marks
     * @CreateDate: 2026/06/22 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int compress(char[] chars) {
        int result;
        result = method_01(chars);
        return result;
    }

    /**
     * @Description:
     * 1. 通过模拟压缩字符, char prev 记录前一个字符, int cnt 记录当前字符出现的次数
     * 2. 当得到一个 x + num 的结果之后, 需要将这个结果存入到 chars 中, 并且 ans 增加修改的长度.
     * AC: 1ms/44.98MB
     * @param: chars
     * @return int
     * @author marks
     * @CreateDate: 2026/06/22 15:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(char[] chars) {
        char prev = chars[0];
        int next = 0;
        int cnt = 1;
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == prev) {
                cnt++;
            } else {
                chars[next++] = prev;
                if (cnt > 1) {
                    for (char c : String.valueOf(cnt).toCharArray()) {
                        chars[next++] = c;
                    }
                }
                prev = chars[i];
                cnt = 1;
            }
        }
        // 添加最后一个字符
        chars[next++] = prev;
        if (cnt > 1) {
            for (char c : String.valueOf(cnt).toCharArray()) {
                chars[next++] = c;
            }
        }

        return next;
    }

}
