package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1015 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/25 10:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1015 {

    /**
     * @Description:
     * 给定正整数 k ，你需要找出可以被 k 整除的、仅包含数字 1 的最 小 正整数 n 的长度。
     * 返回 n 的长度。如果不存在这样的 n ，就返回-1。
     * 注意： n 可能不符合 64 位带符号整数。
     *
     * tips:
     * 1 <= k <= 10^5
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2025/11/25 10:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int smallestRepunitDivByK(int k) {
        int result;
        result = method_01(k);
        return result;
    }

    /**
     * @Description:
     * 11 % 2 = 1, 1 % 2 = 1, 111 % 2 = 1;
     * 1 % 3 = 1, 11 % 3 = 2, 111 % 3 = 0;
     * 1 % 4 = 1, 11 % 4 = 3, 111 % 4 = 3; 1111 % 4 = 3;
     * 1 % 5 = 1, 11 % 5 = 1, 111 % 5 = 1; 1111 % 5 = 1;
     * 11 % 6 = 5, 111 % 6 =
     * AC: 1ms/41.55MB
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2025/11/25 10:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int k) {
        if (k == 1) {
            return 1;
        }
        if (k % 2 == 0 || k % 5 == 0) {
            return -1;
        }

        int count = 0;
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum = (sum * 10 + 1) % k;
            count++;
            if (sum == 0) {
                return count;
            }
        }

        return -1;
    }
}
