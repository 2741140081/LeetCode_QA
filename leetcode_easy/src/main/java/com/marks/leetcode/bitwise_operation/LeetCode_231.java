package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/12 14:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_231 {

    /**
     * @Description:
     * 给你一个整数 n，请你判断该整数是否是 2 的幂次方。如果是，返回 true ；否则，返回 false 。
     *
     * 如果存在一个整数 x 使得 n == 2^x ，则认为 n 是 2 的幂次方。
     *
     * tips:
     * -2^31 <= n <= 2^31 - 1
     *
     * 2^-1, 2^-2 =
     *
     * AC: 0ms/40.18MB
     * @param n
     * @return boolean
     * @author marks
     * @CreateDate: 2025/9/12 14:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isPowerOfTwo(int n) {
        // 0不是2的幂次方
        if (n <= 0) {
            return false;
        }
        // 判断二进制中是否只有一个1
        return Integer.bitCount(n) == 1;
    }
    
}
