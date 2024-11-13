package com.marks.leetcode.double_pointer.single_sequence;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/11 16:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_925 {
    /**
     * @Description: [
     * 你的朋友正在使用键盘输入他的名字 name。偶尔，在键入字符 c 时，按键可能会被长按，而字符可能被输入 1 次或多次。
     *
     * 你将会检查键盘输入的字符 typed。如果它对应的可能是你的朋友的名字（其中一些字符可能被长按），那么就返回 True。
     *
     * tips:
     * 1 <= name.length, typed.length <= 1000
     * name 和 typed 的字符都是小写字母
     * ]
     * @param name
     * @param typed
     * @return boolean
     * @author marks
     * @CreateDate: 2024/11/11 16:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isLongPressedName(String name, String typed) {
        boolean result;
        result = method_01(name, typed);
        return result;
    }

    /**
     * @Description: [
     * 输入：name = "saeed", typed = "ssaaedd"
     * 输出：false
     * 解释：'e' 一定需要被键入两次，但在 typed 的输出中不是这样。
     *
     * AC:1ms/40.57MB
     * ]
     * @param name
     * @param typed
     * @return boolean
     * @author marks
     * @CreateDate: 2024/11/11 16:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String name, String typed) {
        int i = 0, j = 0;
        while (j < typed.length()) {
            if (i < name.length() && name.charAt(i) == typed.charAt(j)) {
                i++;
                j++;
            } else if (j > 0 && typed.charAt(j) == typed.charAt(j - 1)) {
                j++;
            } else {
                return false;
            }
        }
        return i == name.length();
    }
}
