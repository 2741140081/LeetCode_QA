package com.marks.leetcode.array_hard;


import java.util.Map;
import java.util.HashMap;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1815 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/3 11:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1815 {

    private static final int K_WIDTH = 5;
    private static final int K_WIDTH_MASK = (1 << K_WIDTH) - 1;

    /**
     * @Description:
     * 有一个甜甜圈商店，每批次都烤 batchSize 个甜甜圈。
     * 这个店铺有个规则，就是在烤一批新的甜甜圈时，之前 所有 甜甜圈都必须已经全部销售完毕。
     * 给你一个整数 batchSize 和一个整数数组 groups ，数组中的每个整数都代表一批前来购买甜甜圈的顾客，
     * 其中 groups[i] 表示这一批顾客的人数。每一位顾客都恰好只要一个甜甜圈。
     * 当有一批顾客来到商店时，他们所有人都必须在下一批顾客来之前购买完甜甜圈。
     * 如果一批顾客中第一位顾客得到的甜甜圈不是上一组剩下的，那么这一组人都会很开心。
     * 你可以随意安排每批顾客到来的顺序。
     * 请你返回在此前提下，最多 有多少组人会感到开心。
     *
     * tips:
     * 1 <= batchSize <= 9
     * 1 <= groups.length <= 30
     * 1 <= groups[i] <= 10^9
     * @param: batchSize
     * @param: groups
     * @return int
     * @author marks
     * @CreateDate: 2026/08/03 11:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxHappyGroups(int batchSize, int[] groups) {
        int result;
        result = method_01(batchSize, groups);
        return result;
    }

    /**
     * @Description:
     * 1. 贪心, 先处理 group[i] % batchSize == 0, 这些组都是开心的
     * 2. 处理 group[i] % batchSize != 0 的组, 并且将数量存储在 int[] cnt = new int[batchSize];
     * 3. 如何使用动态规划求最多能构成的组数
     * 4. 直接看官方题解
     * @param: batchSize
     * @param: groups
     * @return int
     * @author marks
     * @CreateDate: 2026/08/03 11:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int batchSize, int[] groups) {
        int[] cnt = new int[batchSize];
        // 1. 统计每个模 batchSize 的余数对应的顾客数量
        // 例如：如果 batchSize=4，groups=[1,2,3,1,2,3]，则 cnt[1]=2, cnt[2]=2, cnt[3]=2
        for (int x : groups) {
            ++cnt[x % batchSize];
        }

        long start = 0;
        // 2. 状态压缩：将统计结果压缩到一个 long 变量中
        // 假设 K_WIDTH 是每个计数值占用的二进制位数（例如 5 位，最多表示 31 人）
        // 我们从余数 batchSize-1 遍历到 1，将 cnt[i] 的值存入 start 的对应位段
        // 注意：这里不存储余数为 0 的数量，因为余数为 0 的人可以直接成组，不需要参与复杂的 DP 匹配
        // 最终 start 的二进制结构类似：[cnt[3]的5位][cnt[2]的5位][cnt[1]的5位]... (取决于batchSize大小)
        for (int i = batchSize - 1; i >= 1; --i) {
            // cnt[i] <= 30, 所以2^5 可以完全表示, 并且 最大种类是 1 ~ 8, 有 8 * 5 = 40 < 63, 所以 long 足够存储
            start = (start << K_WIDTH) | cnt[i];
        }

        Map<Long, Integer> memo = new HashMap<Long, Integer>();

        // 3. 开始递归计算
        // 初始结果 = dfs计算的最大组数 + 余数为0的直接成组数
        return dfs(memo, batchSize, start) + cnt[0];
    }

    /**
     * @Description:
     *
     * @param: memo: 记忆化存储
     * @param: batchSize: 目标模
     * @param: mask: 压缩的状态值
     * @return int
     * @author marks
     * @CreateDate: 2026/08/03 14:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int dfs(Map<Long, Integer> memo, int batchSize, long mask) {
        // 1. 终止条件：如果 mask 为 0，说明所有余数非 0 的顾客都已经分配完毕，返回 0 组
        if (mask == 0) {
            return 0;
        }

        // 2. 记忆化检查：如果当前状态 mask 已经计算过，直接返回结果
        if (!memo.containsKey(mask)) {
            long total = 0;
            // 计算当前状态下所有剩余顾客的总人数（用于后续判断是否能凑整）
            // 遍历每一个余数 i (1 到 batchSize-1)
            for (int i = 1; i < batchSize; ++i) {
                // 位运算提取：从 mask 中取出余数为 i 的顾客数量
                // 右移 (i-1)*K_WIDTH 位到低位，再与掩码 K_WIDTH_MASK 进行与运算
                long amount = ((mask >> ((i - 1) * K_WIDTH)) & K_WIDTH_MASK);
                // 累加总人数：余数 i * 人数 amount
                total += i * amount;
            }

            int best = 0;
            // 3. 尝试决策：枚举当前这一步可以选择哪一个余数的顾客作为"开头"
            // 我们尝试从余数 1 到 batchSize-1 的顾客中选一个人作为当前组的第一个人
            for (int i = 1; i < batchSize; ++i) {
                // 获取余数为 i 的顾客数量
                long amount = ((mask >> ((i - 1) * K_WIDTH)) & K_WIDTH_MASK);

                // 如果还有该余数的顾客，尝试拿走一个
                if (amount > 0) {
                    // 递归调用：更新 mask
                    // mask - (1L << ((i - 1) * K_WIDTH)) 表示将余数为 i 的计数减 1
                    int result = dfs(memo, batchSize, mask - (1L << ((i - 1) * K_WIDTH)));

                    // 核心逻辑：判断当前剩余的总人数减去当前选的人(i)后，是否能被 batchSize 整除
                    // 如果 (total - i) % batchSize == 0，说明剩下的顾客可以完美地凑成若干组，
                    // 那么当前这一步选择的这个人就可以成功"关闭"一个组，所以结果 +1
                    if ((total - i) % batchSize == 0) {
                        ++result;
                    }

                    // 更新最大值：在所有可能的尝试中，取能产生最大组数的方案
                    best = Math.max(best, result);
                }
            }

            // 4. 记录结果：将当前状态 mask 的最优解存入 memo 表
            memo.put(mask, best);
        }
        // 返回计算结果
        return memo.get(mask);
    }

}
