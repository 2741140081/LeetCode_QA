package com.marks.leetcode.DP_II;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1553 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/24 17:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1553 {

    /**
     * @Description:
     * 厨房里总共有 n 个橘子，你决定每一天选择如下方式之一吃这些橘子：
     *
     * 吃掉一个橘子。
     * 如果剩余橘子数 n 能被 2 整除，那么你可以吃掉 n/2 个橘子。
     * 如果剩余橘子数 n 能被 3 整除，那么你可以吃掉 2*(n/3) 个橘子。
     * 每天你只能从以上 3 种方案中选择一种方案。
     *
     * 请你返回吃掉所有 n 个橘子的最少天数。
     *
     * tips:
     * 1 <= n <= 2*10^9
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/04/24 17:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minDays(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 1. 动态规划
     * 2. 当前有 n 个橘子,  使用
     * 方法1, dp[i] = dp[i-1] + 1;
     * 假设 n % 2 == 0; dp[i] = dp[i/2] + 1;
     * 假设 n % 3 == 0; dp[i] = dp[i/3] + 1;
     * dp[0] = 0, dp[1] = 1; dp[2] = dp[1] + 1 = 2; dp[3] = Math.min(dp[2] + 1, dp[1] + 1) = 2;
     * 3. 由于 n 很大, 所以不能直接遍历, 需要通过递归来处理
     * 4. 通过map 进行离散化的存储dp值
     * 5. 添加将 n 减少到能被2 或者 3 整除的天数, 即 n % 2 和 n % 3 的余数
     * AC: 3ms/43.23MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/04/24 17:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private Map<Integer, Integer> map;
    private int method_01(int n) {
        map = new HashMap<>();
        map.put(0, 0);
        map.put(1, 1);
        return dfs(n);
    }

    private int dfs(int n) {
        if (map.containsKey(n)) {
            return map.get(n);
        }
        // 判断 n 能否被 3 整除
        int ans = n; // 执行方法1所需天数
        ans = Math.min(ans, n % 2 + 1 + dfs(n / 2));
        ans = Math.min(ans, n % 3 + 1 + dfs(n / 3));
        map.put(n, ans);

        return ans;
    }

}
