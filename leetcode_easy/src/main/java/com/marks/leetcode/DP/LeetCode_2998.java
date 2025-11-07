package com.marks.leetcode.DP;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2998 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/4 16:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2998 {

    /**
     * @Description:
     * 给你两个正整数 x 和 y 。
     *
     * 一次操作中，你可以执行以下四种操作之一：
     *
     * 如果 x 是 11 的倍数，将 x 除以 11 。
     * 如果 x 是 5 的倍数，将 x 除以 5 。
     * 将 x 减 1 。
     * 将 x 加 1 。
     * 请你返回让 x 和 y 相等的 最少 操作次数。
     *
     * tips:
     * 1 <= x, y <= 10^4
     * @param: x
     * @param: y
     * @return int
     * @author marks
     * @CreateDate: 2025/11/04 16:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumOperationsToMakeEqual(int x, int y) {
        int result;
        result = method_01(x, y);
        return result;
    }

    private final Map<Integer, Integer> memo = new HashMap<>();
    /**
     * @Description:
     * 1. dp[x][y] 表示 x 和 y 相等需要的最少操作次数
     * 2. 我只能操作 x, 并且使得 x 和 y 相等, 无法操作 y.
     * 3. 如果 y > x, dp[x][y] = y - x;
     * 4. 如果 x > y, max = Math.abs(x - y).
     * 5. x 增加 m 个 1, (x + t1) % 5 == 0, (x + t2) % 11 == 0, 所以怎么求出t1, t2?
     * 6. t1 = 5 - x % 5, t2 = 11 - x % 11
     * AC: 10ms/46.38MB
     * @param: x
     * @param: y
     * @return int
     * @author marks
     * @CreateDate: 2025/11/04 16:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int x, int y) {
        if (x <= y) {
            return y - x;
        }

        int ans = dfs(x, y);
        return ans;
    }

    private int dfs(int x, int y) {
        if (x <= y) {
            return y - x;
        }
        if (memo.containsKey(x)) {
            return memo.get(x);
        }

        int res = x - y;
        // 加到5的倍数
        int t1 = x % 5 == 0 ? 0 : 5 - x % 5;
        res = Math.min(res, dfs((x + t1) / 5, y) + t1 + 1);
        // 加到11的倍数
        int t2 = x % 11 == 0 ? 0 : 11 - x % 11;
        res = Math.min(res, dfs((x + t2) / 11, y) + t2 + 1);
        res = Math.min(res, dfs(x - 1, y) + 1);

        memo.put(x, res);
        return res;
    }

}
