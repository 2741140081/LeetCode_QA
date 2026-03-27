package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1959 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/26 10:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1959 {

    /**
     * @Description:
     * 你正在设计一个动态数组。给你一个下标从 0 开始的整数数组 nums ，其中 nums[i] 是 i 时刻数组中的元素数目。
     * 除此以外，你还有一个整数 k ，表示你可以 调整 数组大小的 最多 次数（每次都可以调整成 任意 大小）。
     *
     * t 时刻数组的大小 sizet 必须大于等于 nums[t] ，因为数组需要有足够的空间容纳所有元素。
     * t 时刻 浪费的空间 为 sizet - nums[t] ，总 浪费空间为满足 0 <= t < nums.length 的每一个时刻 t 浪费的空间 之和 。
     * 在调整数组大小不超过 k 次的前提下，请你返回 最小总浪费空间 。
     * 注意：数组最开始时可以为 任意大小 ，且 不计入 调整大小的操作次数。
     *
     * tips:
     * 1 <= nums.length <= 200
     * 1 <= nums[i] <= 10^6
     * 0 <= k <= nums.length - 1
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/03/26 10:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minSpaceWastedKResizing(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 初始值的取值范围是 [nums[0] ~ Math.max(nums[i])], 初始化一个数组 dp[i][k][l], i 表示下标，l 表示当前数组的 sizet,
     * 还需要一个字段 k 表示调整数组的次数，value 表示当前数组的已浪费的总空间大小.
     * 2. 当前位于 i 处，num = nums[i], 需要遍历整个 0 ~ max, 也就是 j 的取值范围，判断 num 于 j 的关系，
     * num <= l 时，会造成浪费空间，但是不需要调整数组大小，dp[i][j][k] = dp[i - 1][j][k] + (j - num);
     * num > l 时，需要调整数组大小，调整的值范围是 [num, max], 遍历 j 的范围，dp[i][j][k] = dp[i - 1][j][k - 1] + (j - num);
     * 3. 取值有问题，不需要取所有值 [nums[0] ~ max], 只需要取数组中存在的值即可，数组的大小是 nums.length <= 200, 大大降低时间复杂度。
     * 并且使用下标来替代具体值 (数组的大小), 降低空间复杂度。
     * 4. 时间复杂度 O(n^4) 超时了，看看有什么地方能在优化降低一个时间复杂度
     * 超时：65/73
     * 5. 优化方案：
     *    - 使用滚动数组优化空间，从三维 dp[n][k+1][n] 降为二维 dp[k+1][n]
     *    - 对于调整操作，预先计算每个位置的最优值，避免内层循环，时间复杂度从 O(n^4) 降为 O(n³)
     *    - 预先计算前缀和，快速计算区间浪费空间
     * AC: 166ms/46.64MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/03/26 10:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int k) {
        int INF = Integer.MAX_VALUE / 2;
        int n = nums.length;

        // 拷贝一份数组，并且进行升序排序
        int[] sortedNums = Arrays.copyOf(nums, n);
        Arrays.sort(sortedNums);

        // 构建 dp: dp[j][l] 表示使用 j 次调整，当前数组大小为 sortedNums[l] 时的最小浪费空间
        int[][] dp = new int[k + 1][n];
        // 初始化
        for (int j = 0; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }

        // 初始化 i = 0 时，获取在升序排序中 nums[0] 所在下标位置
        int index = Arrays.binarySearch(sortedNums, nums[0]);
        for (int l = index; l < n; l++) {
            dp[0][l] = sortedNums[l] - nums[0]; // 初始浪费的空间大小
        }

        // 遍历数组
        for (int i = 1; i < n; i++) {
            index = Arrays.binarySearch(sortedNums, nums[i]);
            // 创建新的临时数组，用于存储当前位置的状态
            int[][] newDp = new int[k + 1][n];
            for (int j = 0; j <= k; j++) {
                Arrays.fill(newDp[j], INF);
            }

            // 不调整的情况：保持当前数组大小
            for (int j = 0; j <= k; j++) {
                for (int l = index; l < n; l++) {
                    if (dp[j][l] != INF) {
                        newDp[j][l] = Math.min(newDp[j][l], dp[j][l] + (sortedNums[l] - nums[i]));
                    }
                }
            }

            // 调整的情况：使用一次调整机会
            for (int j = 0; j < k; j++) {
                // 找到从任意 l 转移到 index~n-1 的最小值
                int minPrev = INF;
                for (int l = 0; l < n; l++) {
                    if (dp[j][l] != INF) {
                        minPrev = Math.min(minPrev, dp[j][l]);
                    }
                }
                // 如果存在有效的前置状态
                if (minPrev != INF) {
                    for (int m = index; m < n; m++) {
                        newDp[j + 1][m] = Math.min(newDp[j + 1][m], minPrev + (sortedNums[m] - nums[i]));
                    }
                }
            }

            // 更新 dp 数组
            dp = newDp;
        }

        int result = INF;
        for (int j = 0; j <= k; j++) {
            for (int l = 0; l < n; l++) {
                result = Math.min(result, dp[j][l]);
            }
        }
        return result;
    }

    /**
     * @Description:
     * 1. 初始值的取值范围是[nums[0] ~ Math.max(nums[i])], 初始化一个数组 dp[i][k][l], i 表示下标, l 表示当前数组的sizet,
     * 还需要一个字段 k 表示调整数组的次数, value 表示当前数组的已浪费的总空间大小.
     * 2. 当前位于 i 处, num = nums[i], 需要遍历整个 0 ~ max, 也就是 j 的取值范围, 判断 num 于 j 的关系,
     * num <= l 时, 会造成浪费空间, 但是不需要调整数组大小, dp[i][j][k] = dp[i - 1][j][k] + (j - num);
     * num > l 时, 需要调整数组大小, 调整的值范围是 [num, max], 遍历 j 的范围, dp[i][j][k] = dp[i - 1][j][k - 1] + (j - num);
     * 3. 取值有问题, 不需要取所有值[nums[0] ~ max], 只需要取数组中存在的值即可, 数组的大小是nums.length <= 200, 大大降低时间复杂度.
     * 并且使用下标来替代具体值(数组的大小), 降低空间复杂度.
     * 4. 时间复杂度 O(n^4) 超时了, 看看有什么地方能在优化降低一个时间复杂度
     * 超时: 65/73
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/03/26 10:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int INF = Integer.MAX_VALUE / 2;
        int n = nums.length;

        // 拷贝一份数组, 并且进行升序排序
        int[] sortedNums = Arrays.copyOf(nums, n);
        Arrays.sort(sortedNums);
        int max = sortedNums[n - 1];
        // 构建dp
        int[][][] dp = new int[n][k + 1][n];
        // 初始化
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }

        // 初始化 i = 0 时, 获取在升序排序中 nums[0] 所在下标位置
        int index = Arrays.binarySearch(sortedNums, nums[0]);
        for (int j = index; j < n; j++) {
            dp[0][0][j] = sortedNums[j] - nums[0]; // 初始浪费的空间大小
        }

        // 遍历数组
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                index = Arrays.binarySearch(sortedNums, nums[i]);
                for (int l = 0; l < n; l++) {
                    if (l >= index) {
                        dp[i][j][l] = Math.min(dp[i][j][l], dp[i - 1][j][l] + (sortedNums[l] - nums[i]));
                    }
                    // 直接调整大小, 不论 l 于 index 的关系, 需要调整数组大小, 判断能否调整? j + 1 <= k
                    if (j + 1 <= k) {
                        // 还有调整空间
                        for (int m = index; m < n; m++) {
                            // 需要加一个判断, 由于我们是一个合并操作
                            dp[i][j + 1][m] = Math.min(dp[i][j + 1][m], dp[i - 1][j][l] + (sortedNums[m] - nums[i]));
                        }
                    }

                }
            }
        }
        int result = INF;
        for (int j = 0; j <= k; j++) {
            for (int l = 0; l < n; l++) {
                result = Math.min(result, dp[n - 1][j][l]);
            }
        }
        return result;
    }

}
