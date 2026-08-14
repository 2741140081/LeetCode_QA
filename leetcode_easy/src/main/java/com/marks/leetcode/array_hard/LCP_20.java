package com.marks.leetcode.array_hard;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_20 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/14 14:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_20 {

    /**
     * @Description:
     * 小扣打算去秋日市集，由于游客较多，小扣的移动速度受到了人流影响：
     * 小扣从 x 号站点移动至 x + 1 号站点需要花费的时间为 inc；
     * 小扣从 x 号站点移动至 x - 1 号站点需要花费的时间为 dec。
     * 现有 m 辆公交车，编号为 0 到 m-1。
     * 小扣也可以通过搭乘编号为 i 的公交车，从 x 号站点移动至 jump[i]*x 号站点，耗时仅为 cost[i]。
     * 小扣可以搭乘任意编号的公交车且搭乘公交次数不限。
     * 假定小扣起始站点记作 0，秋日市集站点记作 target，请返回小扣抵达秋日市集最少需要花费多少时间。
     * 由于数字较大，最终答案需要对 1000000007 (1e9 + 7) 取模。
     * 注意：小扣可在移动过程中到达编号大于 target 的站点。
     *
     * tips:
     * 1 <= target <= 10^9
     * 1 <= jump.length, cost.length <= 10
     * 2 <= jump[i] <= 10^6
     * 1 <= inc, dec, cost[i] <= 10^6
     * @param: target
     * @param: inc
     * @param: dec
     * @param: jump
     * @param: cost
     * @return int
     * @author marks
     * @CreateDate: 2026/08/14 14:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int busRapidTransit(int target, int inc, int dec, int[] jump, int[] cost) {
        int result;
        result = method_01(target, inc, dec, jump, cost);
        result = method_02(target, inc, dec, jump, cost);
        return result;
    }
    final int MOD = (int) 1e9 + 7;
    Map<Long, Long> map = new HashMap<>();
    int[] jump, cost;
    int inc, dec;

    // 查看题解 AC: 13ms/46.12MB
    private int method_02(int target, int inc, int dec, int[] jump, int[] cost) {
        this.cost = cost; this.jump = jump;
        this.inc = inc; this.dec = dec;
        return (int)(dfs(target) % MOD);
    }
    private long dfs(long n) {
        if(n == 0) return 0;
        if(n == 1) return inc;
        if(map.containsKey(n)) return map.get(n);
        long ans = n * inc;
        for(int i=0; i<jump.length; i++) {
            long reminder = n % jump[i];
            if(reminder == 0) {
                ans = Math.min(ans, dfs(n/jump[i]) + cost[i]);
            } else {
                ans = Math.min(ans, dfs(n / jump[i]) + cost[i] + reminder * inc);
                ans = Math.min(ans, dfs(n / jump[i] + 1) + cost[i] + (jump[i] - reminder) * dec);
            }
        }
        map.put(n, ans);
        return ans;
    }

    /**
     * @Description:
     * 1. 每个节点(除了节点0, 由于 0 * jump[0] 还是 0)都有3种方式移动到, 方式1, 跳跃方式, 下一个点的值是确定的.
     * 2. 这就相当于构建了图, 并且是带权的图, 图的起始点是 0, 目标点是 target, 要求时间最短, 可以采用 dijkstra 算法
     * 3. 错误了, 理解有问题, 即每个站台时, 都可以搭乘任意公交车, 公交车不受站台影响的, 两种之间没有关联.
     * 4. 由于公交车只能一只向前移动, 所以假设最短时间时 (target - 0) * inc < (max - target) * dec => 然后可以得到
     * max = (target + inc + target * dec) / dec = target * (inc + dec) / dec
     * 5. 由于站台的数据非常大, 是一个 long, 采用数组是不合理, 应该采用 Map 进行离散化存储
     * 6. 超时, 因为 target 的取值范围是 10^9, maxJump = Max(jump[i]). x * maxJump > target, 乘坐公交车是一个指数增长.
     * 7.
     * @param: target
     * @param: inc
     * @param: dec
     * @param: jump
     * @param: cost
     * @return int
     * @author marks
     * @CreateDate: 2026/08/14 14:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int target, int inc, int dec, int[] jump, int[] cost) {
        int n = jump.length; // n 台公交车
        int MOD = (int) 1e9 + 7;
        long max = (long) target * (inc + dec) / dec;
        Map<Long, Long> dist = new HashMap<>();
        dist.put(0L, 0L); // 初始时位于节点 0 处

        // 直接开始进行模拟吧
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[1])); // {id, time}
        pq.offer(new long[]{0L, 0L});
        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            long idx = curr[0];
            long time = curr[1];
            if (idx == target) {
                return (int) (time % MOD);
            } else if (idx > target) {
                // 只能执行后退方法, 直接把结果放入到队列中
                long spend = time + (idx - target) * dec;
                pq.offer(new long[]{target, spend});
            }

            if (idx - 1 > 0) { // 后退一个站台
                if (!dist.containsKey(idx - 1) || (dist.get(idx - 1) > time + dec)) {
                    // 添加到队列, 更新map
                    pq.offer(new long[]{idx - 1, time + dec});
                    dist.put(idx - 1, time + dec);
                }
            }
            if (idx + 1 <= target) { // 前进一个站台
                if (!dist.containsKey(idx + 1) || (dist.get(idx + 1) > time + inc)) {
                    // 添加到队列, 更新map
                    pq.offer(new long[]{idx + 1, time + inc});
                    dist.put(idx + 1, time + inc);
                }
            }
            // 乘坐公交车
            for (int i = 0; i < n; i++) {
                long next = idx * jump[i];
                if (next != idx && next < max) {
                    if (!dist.containsKey(next) || (dist.get(next) > time + cost[i])) {
                        // 添加到队列, 更新map
                        pq.offer(new long[]{next, time + cost[i]});
                        dist.put(next, time + cost[i]);
                    }
                }
            }
        }

        return 0;
    }

}
