package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/18 16:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2429 {


    /**
     * @Description:
     * 给你两个正整数 num1 和 num2 ，找出满足下述条件的正整数 x ：
     *
     * x 的置位数和 num2 相同，且
     * x XOR num1 的值 最小
     * 注意 XOR 是按位异或运算。
     *
     * 返回整数 x 。题目保证，对于生成的测试用例， x 是 唯一确定 的。
     *
     * 整数的 置位数 是其二进制表示中 1 的数目。
     * tips:
     * 1 <= num1, num2 <= 10^9
     * @param num1
     * @param num2
     * @return int
     * @author marks
     * @CreateDate: 2025/9/18 16:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimizeXor(int num1, int num2) {
        int result;
        result = method_01(num1, num2);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：num1 = 3, num2 = 5
     * 输出：3
     * 解释：
     * num1 和 num2 的二进制表示分别是 0011 和 0101 。
     * 整数 3 的置位数与 num2 相同，且 3 XOR 3 = 0 是最小的。
     * 1. 尽可能将 x XOR num1 的值最小。 需要将num1 中高位的1替换为0
     * 2. 需要分情况讨论, int a = Integer.bitCount(num1), b = Integer.bitCount(num2);
     * 3. a >= b, 只需要将num1 中高位的1替换为0, 怎么把高位的1替换为0, num1 ^ Integer.highestOneBit(num1)
     * 4. a < b, 即将所有的 num1 的1全部替换为0 后, 仍然存在有多的1, 这些1需要填充到 num1 中低位的0处
     * AC: 0ms/39.82MB
     * @param num1
     * @param num2
     * @return int
     * @author marks
     * @CreateDate: 2025/9/18 16:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int num1, int num2) {
        int a = Integer.bitCount(num1), b = Integer.bitCount(num2);
        int original = num1;
        int ans = 0;
        if (a >= b) {
            while (b > 0) {
                num1 ^= Integer.highestOneBit(num1);
                b--;
            }
            ans = num1 ^ original;
        } else {
            int c = b - a;
            StringBuilder sb = new StringBuilder();
            while (c > 0) {
                while ((num1 & 1) == 1) {
                    sb.append('0');
                    num1 >>= 1;
                }
                sb.append('1');
                num1 >>= 1;
                c--;
            }
            sb.reverse();
            ans = Integer.parseInt(sb.toString(), 2) ^ original;
        }

        return ans;
    }

}
