package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_003 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/9 11:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_003 {

    /**
     * @Description:
     * 给定一个非负整数 n ，请计算 0 到 n 之间的每个数字的二进制表示中 1 的个数，并输出一个数组。
     * @param: n
     * @return int[]
     * @author marks
     * @CreateDate: 2026/02/09 11:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] countBits(int n) {
        int[] result;
        result = method_01(n);
        result = method_02(n);
        return result;
    }

    /**
     * @Description:
     * 查看官解的 dp 思路
     * @param: n
     * @return int[]
     * @author marks
     * @CreateDate: 2026/02/09 11:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int n) {
        int[] dp = new int[n + 1];
        int highBit = 0;
        for (int i = 1; i <= n; i++) {
            if ((i & (i - 1)) == 0) {
                highBit = i;
            }
            dp[i] = dp[i - highBit] + 1;
        }
        return dp;
    }

    /**
     * @Description:
     * 直接调Api
     * @param: n
     * @return int[]
     * @author marks
     * @CreateDate: 2026/02/09 11:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n) {
        int[] ans = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            ans[i] = Integer.bitCount(i);
        }
        return ans;
    }

}
