package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/12 15:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_342 {

    
    /**
     * @Description:
     * 给定一个整数，写一个函数来判断它是否是 4 的幂次方。如果是，返回 true ；否则，返回 false 。
     * 整数 n 是 4 的幂次方需满足：存在整数 x 使得 n == 4^x
     *
     * 0x55555555 是一个十六进制数，转换为二进制是： 01010101010101010101010101010101
     * 作用原理
     * 这个掩码用于检查数字中为1的位是否都位于奇数位置（从右往左数，最低位为第0位）：
     * 在32位整数中，4的幂次方（1, 4, 16, 64, 256...）的二进制表示中，唯一的那个1总是出现在奇数位置上
     * @param n
     * @return boolean
     * @author marks
     * @CreateDate: 2025/9/12 15:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isPowerOfFour(int n) {
        if (n <= 0) {
            return false;
        }
        return (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
    }
}
