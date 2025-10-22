package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/21 10:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1987 {

    private final int MOD = (int) 1e9 + 7;


    /**
     * @Description:
     * 给你一个二进制字符串 binary 。 binary 的一个 子序列 如果是 非空 的且没有 前导 0 （除非数字是 "0" 本身），那么它就是一个 好 的子序列。
     *
     * 请你找到 binary 不同好子序列 的数目。
     *
     * 比方说，如果 binary = "001" ，那么所有 好 子序列为 ["0", "0", "1"] ，所以 不同 的好子序列为 "0" 和 "1" 。 注意，子序列 "00" ，"01" 和 "001" 不是好的，因为它们有前导 0 。
     * 请你返回 binary 中 不同好子序列 的数目。由于答案可能很大，请将它对 10^9 + 7 取余 后返回。
     *
     * 一个 子序列 指的是从原数组中删除若干个（可以一个也不删除）元素后，不改变剩余元素顺序得到的序列。
     * @param binary
     * @return int
     * @author marks
     * @CreateDate: 2025/10/21 10:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfUniqueGoodSubsequences(String binary) {
        int result;
        result = method_01(binary);
        return result;
    }


    /**
     * @Description:
     * E1:
     * 输入：binary = "101"
     * 输出：5
     * 1. charArray[n - 1] 是一个好的子序列, 假设charArray[0 ~ n - 2] 存在 x 个 好序列,
     * 并且可以推断出 好序列 + 好序列 组合而成的序列必定也是一个好序列 dp[n - 1] = sum(dp[0 ~ n - 2]) + 1(自己本身也是一个好序列)
     * 2. 继续递归处理 charArray[n - 2], 不对, 存在一个问题, 需要特殊处理好序列 '0', 由于前导 0 + 任意一个好序列之后的结果都不是一个好序列,
     * 所以需要特殊处理好序列 0, 最好的方式就是直接另行计算好序列0
     * 3. dp[n + 1], dp[0] = 0, dp[1] = 1, dp[2] = 1, dp[3] = 1 + 1 = 2 sum += dp[i];
     * 4. 忘记了一件事情, 那就是需要找到的是不同的好序列数量, 即不重复的好序列数量
     *
     * 查看官解后解答 need todo
     * AC: 9ms/44.29MB
     * @param binary
     * @return int
     * @author marks
     * @CreateDate: 2025/10/21 10:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String binary) {
        char[] charArray = binary.toCharArray();
        int zeroCount = 0;
        int n = charArray.length;
        int even = 0;
        int odd = 0;

        for (int i = 0; i < n; i++) {
            if (charArray[i] == '0') {
                zeroCount++;
                even = (even + odd) % MOD;
            } else {
                odd = (even + odd + 1) % MOD;
            }

        }
        int ans = (odd + even + zeroCount) % MOD;
        return ans;
    }

}
