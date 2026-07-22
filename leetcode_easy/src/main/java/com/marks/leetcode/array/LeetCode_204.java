package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_204 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/22 14:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_204 {

    /**
     * @Description:
     * 给定整数 n ，返回 所有小于非负整数 n 的质数的数量 。
     *
     * tips:
     * 0 <= n <= 5 * 10^6
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/07/22 14:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countPrimes(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 1. 使用 埃拉托斯特尼筛法 来得到范围内的质数数量.
     * AC: 106ms/50.12MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/07/22 14:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        if (n < 2) {
            return 0;
        }
        // 构建boolean[]
        boolean[] isPrime = new boolean[n];
        Arrays.fill(isPrime, true);
        for (int i = 2; i * i < n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i])
                count++;
        }

        return count;
    }

}
