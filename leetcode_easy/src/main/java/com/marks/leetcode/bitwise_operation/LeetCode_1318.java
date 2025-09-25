package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/23 11:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1318 {

    
    /**
     * @Description:
     * 给你三个正整数 a、b 和 c。
     * 你可以对 a 和 b 的二进制表示进行位翻转操作，返回能够使按位或运算   a OR b == c  成立的最小翻转次数。
     * 「位翻转操作」是指将一个数的二进制表示任何单个位上的 1 变成 0 或者 0 变成 1 。
     * @param a 
     * @param b 
     * @param c
     * @return int
     * @author marks
     * @CreateDate: 2025/9/23 11:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minFlips(int a, int b, int c) {
        int result;
        result = method_01(a, b, c);
        return result;
    }

    /**
     * @Description:
     * 1. 假设 c 的末位是1, 那么就需要a, 或者 b 的末位存在一个1
     * 2. 假设 c 的末位是0, 那么就需要a, 和 b 的末位都是0
     * AC: 0ms/39.69MB
     * @param a 
     * @param b 
     * @param c 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/23 11:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int a, int b, int c) {
        int count = 0;
        while (c > 0 || a > 0 || b > 0) {
            int bitC = c & 1; // 判断c的末位是0还是1
            int bitA = a & 1;
            int bitB = b & 1;
            if ((bitA | bitB) != bitC) {
                if (bitC == 1) {
                    count++; // 翻转a或b的末位为1
                } else {
                    // bitC == 0
                    count += (bitA + bitB); // 翻转a和b的末位为0
                }
            }
            c >>= 1;
            a >>= 1;
            b >>= 1;
        }
        return count;
    }

}
