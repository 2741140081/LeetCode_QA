package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/13 14:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_744 {
    /**
     * @Description: [
     * 给你一个字符数组 letters，该数组按非递减顺序排序，以及一个字符 target。letters 里至少有两个不同的字符。
     *
     * 返回 letters 中大于 target 的最小的字符。如果不存在这样的字符，则返回 letters 的第一个字符。
     * ]
     * @param letters 
     * @param target
     * @return char
     * @author marks
     * @CreateDate: 2024/11/13 14:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public char nextGreatestLetter(char[] letters, char target) {
        char result;
        result = method_01(letters, target);
        return result;
    }

    /**
     * @Description: [
     * letter = ["c","f","j"], target = "d"
     * left = 0, right = 2, mid = 1, letters[1] = 'f'
     * 'f' > target, right = 0, left = 0, mid = 0, letters[0] = 'c'
     * 'c' < target, left = 1, right = 0, break;
     *
     * AC:0ms/43.25MB
     * ]
     * @param letters
     * @param target
     * @return char
     * @author marks
     * @CreateDate: 2024/11/13 14:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private char method_01(char[] letters, char target) {
        // 特殊情况1, letters[0] > target
        // 特殊情况2, letters[len - 1] <= target
        if (letters[0] > target || letters[letters.length - 1] <= target) {
            return letters[0];
        }
        // 特殊情况3, letters[0] <= target < letters[len - 1]
        int left = 0;
        int right = letters.length - 1;
        int last = -1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (letters[mid] == target) {
                last = mid;
                left = mid + 1;
            } else if (letters[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        if (last == -1) {
            // 没有找到
            return letters[left];
        } else {
            // 找到最后一个
            return letters[last + 1];
        }
    }
}
