package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/17 10:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2595 {

    
    /**
     * @Description:
     * 给你一个 正 整数 n 。
     * 用 even 表示在 n 的二进制形式（下标从 0 开始）中值为 1 的偶数下标的个数。
     * 用 odd 表示在 n 的二进制形式（下标从 0 开始）中值为 1 的奇数下标的个数。
     * 请注意，在数字的二进制表示中，位下标的顺序 从右到左。
     * 返回整数数组 answer ，其中 answer = [even, odd] 。
     * @param n
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/17 10:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] evenOddBit(int n) {
        int[] result;
        result = method_01(n);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入：n = 17
     * 输出：[2,0]
     * 1. 10001
     * AC: 2ms/41.69MB
     * @param n 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/17 10:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n) {
        int[] result = new int[2];
        int index = 0;
        while (n != 0) {
            if ((n & 1) == 1) {
                result[index]++;
            }
            index = (index + 1) % 2;
            n = n >> 1;
        }
        return result;
    }

}
