package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1849 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/22 16:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1849 {

    /**
     * @Description:
     * 给你一个仅由数字组成的字符串 s 。
     * 请你判断能否将 s 拆分成两个或者多个 非空子字符串 ，使子字符串的 数值 按 降序 排列，且每两个 相邻子字符串 的数值之 差 等于 1 。
     *
     * 例如，字符串 s = "0090089" 可以拆分成 ["0090", "089"] ，数值为 [90,89] 。这些数值满足按降序排列，且相邻值相差 1 ，这种拆分方法可行。
     * 另一个例子中，字符串 s = "001" 可以拆分成 ["0", "01"]、["00", "1"] 或 ["0", "0", "1"] 。然而，所有这些拆分方法都不可行，因为对应数值分别是 [0,1]、[0,1] 和 [0,0,1] ，都不满足按降序排列的要求。
     * 如果可以按要求拆分 s ，返回 true ；否则，返回 false 。
     *
     * 子字符串 是字符串中的一个连续字符序列。
     * tips:
     * 1 <= s.length <= 20
     * s 仅由数字组成
     * @param: s
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/22 16:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean splitString(String s) {
        boolean result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 确定首位数字后, 判断剩余的字符串是否满足要求
     * 2. 枚举首位数字所有可能的值
     * 3. 由于需要降序, 并且相邻数值之差为1, 所以首位数字不能为0
     * 4. 要特别注意处理 0 的情况, 由于最小数为0, 所以当前数为0时, 不需要继续向下查找下一个数, 而是判断最后的数字是否满足要求即可.
     * AC: 1ms/42.35MB
     * @param: s
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/22 16:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s) {
        long firstNum = 0;
        for (int i = 0; i < s.length(); i++) {
            firstNum = firstNum * 10 + s.charAt(i) - '0';
            if (i == s.length() - 1 || firstNum == 0) {
                continue;
            }
            if (checkIsOk(s, i + 1, firstNum - 1)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkIsOk(String s, int start, long targetNum) {
        long currNum = 0;
        for (int i = start; i < s.length(); i++) {
            currNum = currNum * 10 + s.charAt(i) - '0';
            if (i == s.length() - 1) {
                return currNum == targetNum;
            }
            if (currNum > 0 && currNum == targetNum) {
                return checkIsOk(s, i + 1, targetNum - 1);
            }
        }
        return false;
    }

}
