package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_458 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/3 11:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_458 {

    /**
     * @Description:
     * 有 buckets 桶液体，其中 正好有一桶 含有毒药，其余装的都是水。它们从外观看起来都一样。
     * 为了弄清楚哪只水桶含有毒药，你可以喂一些猪喝，通过观察猪是否会死进行判断。
     * 不幸的是，你只有 minutesToTest 分钟时间来确定哪桶液体是有毒的。
     * 喂猪的规则如下：
     * 选择若干活猪进行喂养
     * 可以允许小猪同时饮用任意数量的桶中的水，并且该过程不需要时间。
     * 小猪喝完水后，必须有 minutesToDie 分钟的冷却时间。在这段时间里，你只能观察，而不允许继续喂猪。
     * 过了 minutesToDie 分钟后，所有喝到毒药的猪都会死去，其他所有猪都会活下来。
     * 重复这一过程，直到时间用完。
     * 给你桶的数目 buckets ，minutesToDie 和 minutesToTest ，返回 在规定时间内判断哪个桶有毒所需的 最小 猪数 。
     *
     * tips:
     * 1 <= buckets <= 1000
     * 1 <= minutesToDie <= minutesToTest <= 100
     * @param: buckets
     * @param: minutesToDie
     * @param: minutesToTest
     * @return int
     * @author marks
     * @CreateDate: 2026/02/03 11:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
        int result;
        result = method_01(buckets, minutesToDie, minutesToTest);
        result = method_02(buckets, minutesToDie, minutesToTest);
        return result;
    }

    /**
     * @Description:
     * 1. 根据香农的信息熵推算, 假设有 i 头猪, 进行 t 轮测试, 那么每头猪所携带的信息为
     * 猪可以在第 i 轮测试中死去或者存活到最后, 有 t + 1 种状态, 那么 i 头猪就会有 (t + 1)^i 个可能的结果
     * 2. (t + 1) ^ i >= buckets, 求得 i = log(buckets) / log(t + 1)
     * @param: buckets
     * @param: minutesToDie
     * @param: minutesToTest
     * @return int
     * @author marks
     * @CreateDate: 2026/02/03 15:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int buckets, int minutesToDie, int minutesToTest) {
        int t = minutesToTest / minutesToDie + 1;
        return (int) Math.ceil(Math.log(buckets) / Math.log(t - 1e-5));
    }

    /**
     * @Description:
     * E1:
     * 输入：buckets = 1000, minutesToDie = 15, minutesToTest = 60
     * 输出：5
     * 1. 能做多少轮测试, int t = minutesToTest / minutesToDie;
     * 2. 假设只能做一轮测试, 那么最少需要多少只猪? n 桶水, p0 喝下了(0 ~ n - 1) 个水桶所混合的水, p0 中毒.
     * p1 喝下了(0 ~ n - 2) 个水桶所混合的水, p1 中毒.
     * 3. 模拟E1 测试结果,
     *   一开始时: 1000, 5, 4
     * 第一次喝水:
     * 第二次喝水: 50, 3, 2
     * @param: buckets
     * @param: minutesToDie
     * @param: minutesToTest
     * @return int
     * @author marks
     * @CreateDate: 2026/02/03 11:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int buckets, int minutesToDie, int minutesToTest) {
        if (buckets == 1) {
            return 0;
        }
        int[][] combinations = new int[buckets + 1][buckets + 1];
        combinations[0][0] = 1;
        int iterations = minutesToTest / minutesToDie;
        int[][] f = new int[buckets][iterations + 1];
        for (int i = 0; i < buckets; i++) {
            f[i][0] = 1;
        }
        for (int j = 0; j <= iterations; j++) {
            f[0][j] = 1;
        }
        for (int i = 1; i < buckets; i++) {
            combinations[i][0] = 1;
            combinations[i][i] = 1;
            for (int j = 1; j < i; j++) {
                combinations[i][j] = combinations[i - 1][j - 1] + combinations[i - 1][j];
            }
            for (int j = 1; j <= iterations; j++) {
                for (int k = 0; k <= i; k++) {
                    f[i][j] += f[k][j - 1] * combinations[i][i - k];
                }
            }
            if (f[i][iterations] >= buckets) {
                return i;
            }
        }
        return 0;
    }

}
