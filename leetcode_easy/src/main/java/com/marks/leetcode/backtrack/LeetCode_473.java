package com.marks.leetcode.backtrack;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/29 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_473 {


    /**
     * @Description:
     * 你将得到一个整数数组 matchsticks ，其中 matchsticks[i] 是第 i 个火柴棒的长度。你要用 所有的火柴棍 拼成一个正方形。
     * 你 不能折断 任何一根火柴棒，但你可以把它们连在一起，而且每根火柴棒必须 使用一次 。
     *
     * 如果你能使这个正方形，则返回 true ，否则返回 false 。
     * tips:
     * 1 <= matchsticks.length <= 15
     * 1 <= matchsticks[i] <= 10^8
     * @param matchsticks
     * @return boolean
     * @author marks
     * @CreateDate: 2025/8/29 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean makesquare(int[] matchsticks) {
        boolean result;
        result = method_01(matchsticks);
        result = method_02(matchsticks);
        return result;
    }

    private boolean method_02(int[] matchsticks) {
        int n = matchsticks.length;
        int sum = 0, maxValue = 0;
        for (int matchstick : matchsticks) {
            sum += matchstick;
            maxValue = Math.max(maxValue, matchstick);
        }

        int target = sum / 4;
        if (sum % 4 != 0 || n < 4 || maxValue > target) {
            return false;
        }

        // dp[state] 表示当前状态下已经组成的边的长度
        int[] dp = new int[1 << n];
        Arrays.fill(dp, -1);
        dp[0] = 0;

        // 遍历所有状态
        for (int state = 0; state < (1 << n); state++) {
            if (dp[state] == -1) {
                continue;
            }

            // 尝试添加每一根火柴
            for (int i = 0; i < n; i++) {
                // 如果第 i 根火柴未被使用
                if ((state & (1 << i)) == 0) {
                    int nextState = state | (1 << i);
                    // 如果添加这根火柴后不会超过目标边长
                    if (dp[state] + matchsticks[i] <= target) {
                        // 更新下一个状态的边长
                        dp[nextState] = (dp[state] + matchsticks[i]) % target;
                    }
                }
            }
        }

        // 最终状态应该是所有火柴都用完，且刚好组成4条完整的边
        return dp[(1 << n) - 1] == 0;
    }


    /**
     * @Description:
     * E1:
     * 输入：matchsticks = [1,1,2,2,2]
     * 输出：true
     * 1. sum(matchsticks) % 4 != 0, return false; 即使 sum % 4 == 0, 也不一定有解
     * 2. 使用回溯 + 递归, 可以直接构造 int[] len = new int[4]; 记录4个边长
     * 3. int index, 记录matchsticks[index], index == n 时, 递归结束, 判断 len[i] == target, ans = true;
     * AC: 2823ms/40.70MB
     * 这个用回溯差点超时!!!
     * chaos
     * @param matchsticks
     * @return boolean
     * @author marks
     * @CreateDate: 2025/8/29 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private boolean ans;
    private int target; // 目标边长
    private int n;
    private boolean method_01(int[] matchsticks) {
        n = matchsticks.length;
        int sum = 0, maxValue = 0;
        for (int i = 0; i < n; i++) {
            sum += matchsticks[i];
            maxValue = Math.max(maxValue, matchsticks[i]);
        }
        target = sum / 4;
        if (sum % 4 != 0 || matchsticks.length < 4 || maxValue > target) {
            return false;
        }

        ans = false;

        int[] len = new int[4]; // 记录4个边长
        backtrack(matchsticks, len, 0);
        return ans;
    }

    private void backtrack(int[] matchsticks, int[] len, int index) {
        if (ans) {
            // 判断是否已经找到解
            return;
        }
        if (index == n) {
            boolean flag = true;
            for (int i = 0; i < 4; i++) {
                if (len[i] != target) {
                    flag = false;
                }
            }
            if (flag) {
                ans = true;
            }
            return;
        }
        for (int i = 0; i < 4; i++) {
            if (len[i] + matchsticks[index] > target) {
                continue;
            }
            len[i] += matchsticks[index];
            backtrack(matchsticks, len, index + 1);
            len[i] -= matchsticks[index];
        }
    }

}
