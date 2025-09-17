package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/15 11:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_338 {

    /**
     * @Description:
     * 给你一个整数 n ，对于 0 <= i <= n 中的每个 i ，计算其二进制表示中 1 的个数 ，返回一个长度为 n + 1 的数组 ans 作为答案。
     * @param n
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/15 11:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] countBits(int n) {
        int[] result;
        result = method_01(n);
        result = method_02(n);
        return result;
    }

    private int[] method_02(int n) {
        int[] ans = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            ans[i] = Integer.bitCount(i);
        }
        return ans;
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 8
     * i = 0 ~ 8, [0000, 0001, 0010, 0011, 0100, 0101, 0110, 0111, 1000]
     * 输出：[0,1,1,2,1,2,2,3,1]
     * @param n
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/15 11:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n) {
        int[] result = new int[n + 1];
        result[0] = 0;
        for (int i = 1; i <= n; i++) {
            result[i] = result[i >> 1] + (i & 1);
        }
        return result;
    }

}
