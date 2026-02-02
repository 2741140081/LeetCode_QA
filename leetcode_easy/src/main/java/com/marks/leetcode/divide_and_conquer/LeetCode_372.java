package com.marks.leetcode.divide_and_conquer;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_372 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/28 15:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_372 {

    /**
     * @Description:
     * 你的任务是计算 a^b 对 1337 取模，a 是一个正整数，b 是一个非常大的正整数且会以数组形式给出。
     * tips:
     * 1 <= a <= 2^31 - 1
     * 1 <= b.length <= 2000
     * 0 <= b[i] <= 9
     * b 不含前导 0
     * @param: a
     * @param: b
     * @return int
     * @author marks
     * @CreateDate: 2026/01/28 15:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int superPow(int a, int[] b) {
        int result;
        result = method_01(a, b);
        return result;
    }

    /**
     * @Description:
     * 1. a % 1337 = x => x^b % 1337, 可以递归执行
     * a > 1337, 递归处理 x = 1337, return 0; x < 1337 时, 就需要把指数减少, 把指数扩大
     * (2x)^c * x^d  = x^b, 好像是同余定理吗
     * @param: a
     * @param: b
     * @return int
     * @author marks
     * @CreateDate: 2026/01/28 15:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int a, int[] b) {
        return 0;
    }

}
