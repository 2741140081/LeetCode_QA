package com.marks.leetcode.double_pointer.single_sequence;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/1 16:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_344 {
    /**
     * @Description: [
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
     *
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     * ]
     * @param s
     * @return void
     * @author marks
     * @CreateDate: 2024/11/1 16:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void reverseString(char[] s) {
        method_01(s);
    }

    /**
     * @Description: [
     * E1:
     * 输入：s = ["h","e","l","l","o"]
     * 输出：["o","l","l","e","h"]
     *
     * 双指针
     * AC:0ms/44.43MB
     * ]
     * @param s
     * @return void
     * @author marks
     * @CreateDate: 2024/11/1 16:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_01(char[] s) {
        int left = 0;
        int right = s.length;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }
}
