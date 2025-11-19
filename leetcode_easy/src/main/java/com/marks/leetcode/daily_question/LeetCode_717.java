package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_717 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/18 14:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_717 {

    /**
     * @Description:
     * 有两种特殊字符：
     *
     * 第一种字符可以用一比特 0 表示
     * 第二种字符可以用两比特（10 或 11）表示
     * 给你一个以 0 结尾的二进制数组 bits ，如果最后一个字符必须是一个一比特字符，则返回 true 。
     * @param: bits
     * @return boolean
     * @author marks
     * @CreateDate: 2025/11/18 14:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isOneBitCharacter(int[] bits) {
        boolean result;
        result = method_01(bits);
        return result;
    }

    /**
     * @Description:
     * 输入: bits = [1, 0, 0]
     * 输出: true
     * 输入：bits = [1,1,1,0]
     * 输出：false
     * 1. bits[i] = 0时, i + 1, bits[i + 1] = 1 时, i + 2; 并且 i 的取值为 0 到 bits.length - 2
     * 2. 最后返回 index = i, 如果 index = bits.length - 2, 则返回 true, 否则返回 false
     * AC: 0ms/43.69MB
     * @param: bits
     * @return boolean
     * @author marks
     * @CreateDate: 2025/11/18 14:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] bits) {
        int n = bits.length;
        int index = 0;
        int last = 0;
        while (index < n) {
            if (bits[index] == 0) {
                index++;
                last = 1;
            } else {
                index += 2;
                last = 2;
            }
        }
        return last == 1;
    }
}
