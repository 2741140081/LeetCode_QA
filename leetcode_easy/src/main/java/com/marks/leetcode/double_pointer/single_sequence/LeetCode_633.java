package com.marks.leetcode.double_pointer.single_sequence;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/4 15:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_633 {
    /**
     * @Description: [
     * 给定一个非负整数 c ，你要判断是否存在两个整数 a 和 b，使得 a^2 + b^2 = c 。
     * tips:
     * 0 <= c <= 2^31 - 1
     * ]
     * @param c
     * @return boolean
     * @author marks
     * @CreateDate: 2024/11/4 16:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean judgeSquareSum(int c) {
        boolean result;
        result = method_01(c);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：c = 5
     * 输出：true
     * 解释：1 * 1 + 2 * 2 = 5
     *
     * AC:5ms/39.52MB
     * ]
     * @param c
     * @return boolean
     * @author marks
     * @CreateDate: 2024/11/4 15:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int c) {
        int left = 0;
        int right = (int) Math.sqrt(c);
        while (left <= right) {
            int sum = (int) ( Math.pow(left, 2) + Math.pow(right, 2));
            if (sum == c) {
                return true;
            } else if (sum > c) {
                right--;
            }else {
                left++;
            }
        }
        return false;
    }
}
