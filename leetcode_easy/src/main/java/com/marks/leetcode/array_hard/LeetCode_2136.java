package com.marks.leetcode.array_hard;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2136 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/11 17:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2136 {

    /**
     * @Description:
     * 你有 n 枚花的种子。每枚种子必须先种下，才能开始生长、开花。
     * 播种需要时间，种子的生长也是如此。给你两个下标从 0 开始的整数数组 plantTime 和 growTime ，每个数组的长度都是 n ：
     * plantTime[i] 是 播种 第 i 枚种子所需的 完整天数 。每天，你只能为播种某一枚种子而劳作。
     * 无须 连续几天都在种同一枚种子，但是种子播种必须在你工作的天数达到 plantTime[i] 之后才算完成。
     * growTime[i] 是第 i 枚种子完全种下后生长所需的 完整天数 。在它生长的最后一天 之后 ，将会开花并且永远 绽放 。
     * 从第 0 开始，你可以按 任意 顺序播种种子。
     * 返回所有种子都开花的 最早 一天是第几天。
     * tips:
     * n == plantTime.length == growTime.length
     * 1 <= n <= 10^5
     * 1 <= plantTime[i], growTime[i] <= 10^4
     * @param: plantTime
     * @param: growTime
     * @return int
     * @author marks
     * @CreateDate: 2026/08/11 17:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public int earliestFullBloom(int[] plantTime, int[] growTime) {
        int result;
        result = method_01(plantTime, growTime);
        return result;
    }

    /**
     * @Description:
     * 1. 只需要照顾种子的播种过程即可, 生长过程不需要照顾. 并且不需要连续天数来播种某一种种子.
     * 2. 假设[3,3] 和一个 [2,2] 的种子, 哪种方案时间更少, 3 + 2 + 2 = 7 天, 2 + 3 + 3 = 8,
     * 1 + 3 + 3 = 7, 通过这个可以看出应该优先处理那些生长周期更长的花, 使得花在生长期时可以处理其它花的播种
     * 3. 播种的天数是固定的, sumPlanTime(不能减少的), 并且需要排除模拟的方式来处理
     * 4. 应该是排序或者是优先队列类似的解决方法, 先把花的数据进行合并
     * 5. 当生长时间相同时 [a, b] [c, b] 它们的生长时间都是 b, 那种方案时间更少, 需要讨论 a, b, c 之间的关系
     * 5.1 a > b > c, 先处理a, 最终时间 a + c + b, 即 a, c 谁先处理最终时间都是 a + c + b,
     * 5.2 假设有3个 [a, b], [c, b], [d, b], 并且 a > c > d, 那么最终时间必定时 a + c + d + b
     * 5.3 假设还是3个, 但是现在生长时间不一致[a, b1], [c, b2], [d, b3], a > c > d, b1 > b2 > b3,
     * 还是先处理a, [a + c + d + b3, (a + b1), (a + c + b2)] 这三者取最大值.
     * 如果先处理 c, [c + d + a + b1], 那么这个必定大于优先处理 a 的情况, 所以优先处理生长周期更长的花, 并且实时统计最大值.
     * AC: 58ms/117.02MB
     * @param: plantTime
     * @param: growTime
     * @return int
     * @author marks
     * @CreateDate: 2026/08/11 17:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] plantTime, int[] growTime) {
        int n = plantTime.length;
        int[][] flowers = new int[n][2];
        for (int i = 0; i < n; i++) {
            flowers[i][0] = plantTime[i];
            flowers[i][1] = growTime[i];
        }
        // 对 flowers[i][1] 进行降序排序
        Arrays.sort(flowers, (a, b) -> b[1] - a[1]);
        int ans = 0;
        int prevSum = 0; // 记录播种种子时间
        for (int i = 0; i < n; i++) {
            prevSum += flowers[i][0];
            ans = Math.max(ans, prevSum + flowers[i][1]);
        }
        return ans;
    }

}
