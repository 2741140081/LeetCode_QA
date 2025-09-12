package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/9 17:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3370 {

    /**
     * @Description:
     * 给你一个正整数 n。
     *
     * 返回 大于等于 n 且二进制表示仅包含 置位 位的 最小 整数 x 。
     *
     * 置位 位指的是二进制表示中值为 1 的位。
     *
     * tips:
     * 1 <= n <= 1000
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2025/9/9 17:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int smallestNumber(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入： n = 5
     * 输出： 7
     * 1. 5 的二进制表示为 101, 2^3 - 1= 8 - 1 = 7;
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2025/9/9 17:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        // 方法1: 计算最高位, 2^n - 1 来获取最大值, 1 << s.length() 即为1 * 2^(s.length())
        String str = Integer.toBinaryString(n);
        int ans = 1 << str.length();
        ans--;

        // 方法2: 计算前导零的个数
        int leadingZero = Integer.numberOfLeadingZeros(n);
        int length = 32 - leadingZero;
        ans = (1 << length) - 1;
        return ans;
    }

}
