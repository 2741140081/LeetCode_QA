package com.marks.leetcode.knapsack;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/2 16:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1774 {
    /**
     * @Description: [
     * 你打算做甜点，现在需要购买配料。目前共有 n 种冰激凌基料和 m 种配料可供选购。
     * 而制作甜点需要遵循以下几条规则：
     *
     * 必须选择 一种 冰激凌基料。
     * 可以添加 一种或多种 配料，也可以不添加任何配料。
     * 每种类型的配料 最多两份 。
     * 给你以下三个输入：
     *
     * baseCosts ，一个长度为 n 的整数数组，其中每个 baseCosts[i] 表示第 i 种冰激凌基料的价格。
     * toppingCosts，一个长度为 m 的整数数组，其中每个 toppingCosts[i] 表示 一份 第 i 种冰激凌配料的价格。
     * target ，一个整数，表示你制作甜点的目标价格。
     * 你希望自己做的甜点总成本尽可能接近目标价格 target 。
     *
     * 返回最接近 target 的甜点成本。如果有多种方案，返回 成本相对较低 的一种。
     *
     * tips:
     * n == baseCosts.length
     * m == toppingCosts.length
     * 1 <= n, m <= 10
     * 1 <= baseCosts[i], toppingCosts[i] <= 10^4
     * 1 <= target <= 10^4
     * 
     * ]
     * @param baseCosts
     * @param toppingCosts
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/8/2 16:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        int result = 0;
//        result = method_01(baseCosts, toppingCosts, target); // error
//        result = method_02(baseCosts, toppingCosts, target);
        result = method_03(baseCosts, toppingCosts, target);
        return result;
    }

    /**
     * @Description: [动态规划]
     * 定义一个 boolean can[target+1]
     * @param baseCosts
     * @param toppingCosts
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/8/5 10:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int[] baseCosts, int[] toppingCosts, int target) {
        int x = Arrays.stream(baseCosts).min().getAsInt();
        if (x >= target) {
            return x;
        }
        res = 2 * target - x; // 基料的上边界, 上下边界分别是[target - x, 2 * target - x], 相当于A 、 A`两点 关于点[target, 0] 对称
        boolean[] can = new boolean[target + 1];
        // 初始化
        for (int baseCost : baseCosts) {
            if (baseCost <= target) {
                can[baseCost] = true;
            }else {
                // baseCost > target
                // 找到> target 中最接近target的值
                res = Math.min(res, baseCost);
            }
        }

        for (int toppingCost : toppingCosts) {
            for (int count = 0; count < 2; count++) {
                for (int i = target; i > 0; i--) {
                    // 可能存在值 基料 + 配料 > target
                    if (can[i] && i + toppingCost > target) {
                        res = Math.min(res, i + toppingCost);
                    }

                    // 转态转移方程 can[i] = can[i] | can[i - c]
                    if (i - toppingCost > 0) {
                        can[i] = can[i] | can[i - toppingCost];
                    }
                }
            }
        }
        // 找到can[i] == true 中i的最大值
        // 已经存在了 res , 如果此时 target - i > res - target, 那么取 res, 否则取 i
        for (int i = 0; i <= res - target; i++) {
            if (can[target - i]) {
                return target - i;
            }
        }
        return res;
    }


    /**
     * @Description: [官方题解: <方式一> 回溯]
     *
     * @param baseCosts
     * @param toppingCosts
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/8/5 9:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int res;

    private int method_02(int[] baseCosts, int[] toppingCosts, int target) {
        res = Arrays.stream(baseCosts).min().getAsInt();
        if (res >= target) {
            return res;
        }
        for (int baseCost : baseCosts) {
            dfs(toppingCosts, 0, baseCost, target);
        }
        return res;
    }

    /**
     * @Description: [深度优先]
     * 必须选择一种冰淇淋基料；
     * 可以添加一种或多种配料，也可以不添加任何配料；
     * 每种配料最多两份。
     *
     * 对于每一种冰淇淋基料用回溯的方式来针对它进行甜品制作。又因为每一种配料都是正整数，所以在回溯的过程中总开销只能只增不减，
     * 当回溯过程中当前开销大于目标价格 target 后，继续往下搜索只能使开销与 target 的差值更大，
     * 所以如果此时差值已经大于等于我们已有最优方案的差值，我们可以停止继续往下搜索，及时回溯。
     * @param toppingCosts
     * @param j
     * @param currCost
     * @param target
     * @return void
     * @author marks
     * @CreateDate: 2024/8/5 9:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void dfs(int[] toppingCosts, int j, int currCost, int target) {
        if (Math.abs(target - res) < currCost - target) {
            return;
        } else if (Math.abs(target - res) >= Math.abs(target - currCost)) {
            if (Math.abs(target - res) > Math.abs(target - currCost)) {
                res = currCost;
            }else {
                res = Math.min(res, currCost);
            }
        }

        if (j == toppingCosts.length) {
            return;
        }
        dfs(toppingCosts, j + 1, currCost + toppingCosts[j] * 2, target);
        dfs(toppingCosts, j + 1, currCost + toppingCosts[j], target);
        dfs(toppingCosts, j + 1, currCost, target);
    }

    /**
     * @Description: [
     * E1:
     * 输入：baseCosts = [1,7], toppingCosts = [3,4], target = 10
     * 输出：10
     * 解释：考虑下面的方案组合（所有下标均从 0 开始）：
     * - 选择 1 号基料：成本 7
     * - 选择 1 份 0 号配料：成本 1 x 3 = 3
     * - 选择 0 份 1 号配料：成本 0 x 4 = 0
     * 总成本：7 + 3 + 0 = 10 。
     * ]
     * @param baseCosts
     * @param toppingCosts
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/8/2 17:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] baseCosts, int[] toppingCosts, int target) {
        Arrays.sort(baseCosts);
        if (baseCosts[0] >= target) {
            return baseCosts[0];
        }
        int n = baseCosts.length;
        int m = toppingCosts.length;
        int[] nums = new int[2 * m];
        System.arraycopy(toppingCosts, 0, nums, 0, m);

        for (int i = m; i < 2 * m; i++) {
            nums[i] = toppingCosts[i-m];
        }
        Arrays.sort(nums);
        int[][] dp = new int[n + 1][2 * m + 1];
        // 暴力破解
        for (int i = 0; i < n; i++) {
            dp[i][0] = baseCosts[i];
        }
        int res = 0;
        int temp = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= 2 * m; j++) {
                dp[i][j] = dp[i][j-1] + nums[j-1];
                if (temp > Math.abs(dp[i][j] - target)) {
                    temp = Math.abs(dp[i][j] - target);
                    res = dp[i][j];
                }
            }
        }

        return res;
    }
}
