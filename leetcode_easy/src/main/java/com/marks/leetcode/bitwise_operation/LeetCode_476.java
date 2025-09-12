package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/11 15:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_476 {

    
    /**
     * @Description:
     * 对整数的二进制表示取反（0 变 1 ，1 变 0）后，再转换为十进制表示，可以得到这个整数的补数。
     *
     * 例如，整数 5 的二进制表示是 "101" ，取反后得到 "010" ，再转回十进制表示得到补数 2 。
     * 给你一个整数 num ，输出它的补数
     * @param num
     * @return int
     * @author marks
     * @CreateDate: 2025/9/11 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findComplement(int num) {
        int result;
        result = method_01(num);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入：num = 5
     * 输出：2
     * 1. 如何将一个数的二进制进行按位取反? 需要将1 => 0, 0 => 1, how to do this?
     * 2. 0101, 如果直接进行取反 1010,
     * 0101 按位取反后, 1010, 需要把最高位置的1变为0, 3 << 1 = 11 << 1, 110 - 1 = 101
     * 101 ^ 111
     * AC: 0ms/39.67MB
     * @param num 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/11 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int num) {
        // 得到 num 的前导零的个数
        int leadingZeroCount = Integer.numberOfLeadingZeros(num);
        int mask = (1 << (32 - leadingZeroCount)) - 1;
        return num ^ mask;
    }

}
