package com.marks.leetcode.hash_table;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3272 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/15 17:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3272 {

    /**
     * @Description:
     * 给你两个 正 整数 n 和 k 。
     * 如果一个整数 x 满足以下条件，那么它被称为 k 回文 整数 。
     * x 是一个 回文整数 。
     * x 能被 k 整除。
     * 如果一个整数的数位重新排列后能得到一个 k 回文整数 ，那么我们称这个整数为 好 整数。
     * 比方说，k = 2 ，那么 2020 可以重新排列得到 2002 ，2002 是一个 k 回文串，所以 2020 是一个好整数。
     * 而 1010 无法重新排列数位得到一个 k 回文整数。
     * 请你返回 n 个数位的整数中，有多少个 好 整数。
     * 注意 ，任何整数在重新排列数位之前或者之后 都不能 有前导 0 。比方说 1010 不能重排列得到 101 。
     *
     * tips:
     * 1 <= n <= 10
     * 1 <= k <= 9
     * @param: n
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/06/15 17:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long countGoodIntegers(int n, int k) {
        long result;
        result = method_01(n, k);
        return result;
    }

    /**
     * @Description:
     * need todo
     * @param: n
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/06/15 17:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int n, int k) {

        return 0;
    }

}
