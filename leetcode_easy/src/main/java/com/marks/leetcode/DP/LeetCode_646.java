package com.marks.leetcode.DP;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_646 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/2 16:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_646 {

    /**
     * @Description:
     * 给你一个由 n 个数对组成的数对数组 pairs ，其中 pairs[i] = [lefti, righti] 且 lefti < righti 。
     * 现在，我们定义一种 跟随 关系，当且仅当 b < c 时，数对 p2 = [c, d] 才可以跟在 p1 = [a, b] 后面。我们用这种形式来构造 数对链 。
     * 找出并返回能够形成的 最长数对链的长度 。
     * 你不需要用到所有的数对，你可以以任何顺序选择其中的一些数对来构造。
     *
     * tips:
     * n == pairs.length
     * 1 <= n <= 1000
     * -1000 <= lefti < righti <= 1000
     * @param: pairs
     * @return int
     * @author marks
     * @CreateDate: 2026/03/02 16:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findLongestChain(int[][] pairs) {
        int result;
        result = method_01(pairs);
        return result;
    }

    /**
     * @Description:
     * 1. 首先需要对 pairs 进行排序, 到底使用left 还是 right 进行排序? 先试试left 进行升序排序
     * 2. 对于 pairs[i], 遍历 j, j 属于 [0 ~ i - 1], 找到 pairs[j][1] < pairs[i][0] dp[i] = Math.max(each dp[j]) + 1
     * 不满足条件 即 pairs[j][1] >= pairs[i][0], 则就没办法将 pairs[i] 加到 j 的后面, dp[i] = Math.max(dp[i], dp[j]);
     * 动态规划
     * AC: 49ms/46.56MB
     * @param: pairs
     * @return int
     * @author marks
     * @CreateDate: 2026/03/02 16:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] pairs) {
        int n = pairs.length;
        int[] dp = new int[n];
        // 排序
        Arrays.sort(pairs, (o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o1[0] - o2[0];
            } else {
                return o1[1] - o2[1];
            }
        });
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (pairs[j][1] < pairs[i][0]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                } else {
                    dp[i] = Math.max(dp[i], dp[j]);
                }
            }
        }
        return dp[n - 1];
    }

}
