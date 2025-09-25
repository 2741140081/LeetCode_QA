package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/25 15:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3133 {

    
    /**
     * @Description:
     * 给你两个整数 n 和 x 。
     * 你需要构造一个长度为 n 的 正整数 数组 nums ，对于所有 0 <= i < n - 1 ，满足 nums[i + 1] 大于 nums[i] ，并且数组 nums 中所有元素的按位 AND 运算结果为 x 。
     *
     * 返回 nums[n - 1] 可能的 最小 值。
     * tips:
     * 1 <= n, x <= 10^8
     * @param n 
     * @param x
     * @return long
     * @author marks
     * @CreateDate: 2025/9/25 15:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minEnd(int n, int x) {
        long result;
        result = method_01(n, x);
        result = method_02(n, x);
        return result;
    }


    /**
     * @Description:
     * 官方题解
     * @param n
     * @param x
     * @return long
     * @author marks
     * @CreateDate: 2025/9/25 16:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_02(int n, int x) {
        int bitCount = 128 - Long.numberOfLeadingZeros(n) - Long.numberOfLeadingZeros(x);
        long res = x;
        long m = n - 1;
        int j = 0;
        for (int i = 0; i < bitCount; ++i) {
            // 如果当前x的第i位为0, 尝试将nums[j]的i位设置为1
            if (((res >> i) & 1) == 0) {
                if (((m >> j) & 1) == 1) {
                    res |= (1L << i);
                }
                j++;
            }
        }
        return res;
    }


    /**
     * @Description:
     * E1:
     * 输入：n = 2, x = 7
     * 输出：15
     * E2:
     * 输入：n = 3, x = 4
     * 输出：6
     * 1. int[] nums; nums[0] & ... & nums[n - 1] = x; 并且nums[] 递增
     * 2. 推断出一个事实, nums[0] >= x; 可以让 nums[0] = x, 可以使得 nums[n - 1] 最小
     * 3. nums[0] = 7, 0111; 下一个就是 1111 = 15, 在下一个 10111,
     * 4. nums[0] = 4, 0100; 0101; 0110;
     * 5. int temp = x;temp = (temp + 1) | x, long prev = nums[0]; 记录或运算前缀和
     * 6. i = 1; nums[1] = (prev + 1) | x; prev |= nums[1]; 0101
     * 7. i = 2, prev = 0101; nums[2] = 0110 | 0100 = 0110; prev |= nums[2] = 0111;
     * 8. i = 3, prev = 0111; nums[3] = 1000 | 0100 = 1100; prev |= nums[3] = 1111;
     * 9. i = 4, prev = 1111; nums[4] = 10000 | 0100 = 10100; prev |= nums[4] = 11111;
     * 10. i = n - 1,
     * AC: 1802ms/40.1MB
     * @param n 
     * @param x 
     * @return long
     * @author marks
     * @CreateDate: 2025/9/25 15:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int n, int x) {
        long curr = x;
        for (int i = 1; i < n; i++) {
            curr = (curr + 1) | x;
        }
        return curr;
    }

}
