package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_Interview_1_5 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/25 14:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_Interview_1_5 {

    /**
     * @Description:
     * 字符串有三种编辑操作:插入一个英文字符、删除一个英文字符或者替换一个英文字符。 给定两个字符串，编写一个函数判定它们是否只需要一次(或者零次)编辑。
     * @param: first
     * @param: second
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/25 14:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean oneEditAway(String first, String second) {
        boolean result;
        result = method_01(first, second);
        return result;
    }

    /**
     * @Description:
     * 1. 先判断 first 和 second 的长度差, 如果长度相等, 则当遇到不同字符时, 使用替换而不是删除.
     * 2. 如果长度差值为1时, 使用插入/删除, 而不使用替换.
     * AC: 1ms/43.71MB
     * @param: first
     * @param: second
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/25 14:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String first, String second) {
        int m = first.length(), n = second.length();
        if (Math.abs(m - n) > 1) {
            return false;
        }
        int sub = m - n; // 1, 0, -1
        int left = 0, right = 0; // 使用两个指针, 移动短字符串
        int cnt = 0; // 统计编辑次数
        while (left < m && right < n) {
            if (first.charAt(left) != second.charAt(right)) {
                if (cnt > 0) {
                    return false;
                }
                if (sub == 1) {
                    // f > s, 删除当前s 中的字符, 即将right - 1
                    right--;
                } else if (sub == -1) {
                    // f < s, 删除当前f 中的字符, 即将left - 1
                    left--;
                }
                cnt++;
            }
            left++;
            right++;
        }

        return true;
    }

}
