package com.marks.leetcode.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1648 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/30 14:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1648 {

    /**
     * @Description:
     * 你有一些球的库存 inventory ，里面包含着不同颜色的球。
     * 一个顾客想要 任意颜色 总数为 orders 的球。
     * 这位顾客有一种特殊的方式衡量球的价值：每个球的价值是目前剩下的 同色球 的数目。比方说还剩下 6 个黄球，那么顾客买第一个黄球的时候该黄球的价值为 6 。
     * 这笔交易以后，只剩下 5 个黄球了，所以下一个黄球的价值为 5 （也就是球的价值随着顾客购买同色球是递减的）
     * 给你整数数组 inventory ，其中 inventory[i] 表示第 i 种颜色球一开始的数目。
     * 同时给你整数 orders ，表示顾客总共想买的球数目。你可以按照 任意顺序 卖球。
     * 请你返回卖了 orders 个球以后 最大 总价值之和。由于答案可能会很大，请你返回答案对 10^9 + 7 取余数 的结果。
     * tips:
     * 1 <= inventory.length <= 10^5
     * 1 <= inventory[i] <= 10^9
     * 1 <= orders <= min(sum(inventory[i]), 10^9)
     * @param: inventory
     * @param: orders
     * @return int
     * @author marks
     * @CreateDate: 2026/06/30 14:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxProfit(int[] inventory, int orders) {
        int result;
        result = method_01(inventory, orders);
        return result;
    }

    /**
     * @Description:
     * 1. 需要使得价值最大, 可以贪心的每次取剩余最多颜色的球, 使用 map 存储 球的数量, 同数量下的球的种类数
     * 2. 假设最大球数量是 max, 可以得到一个 dp[] 数组表示第 i 个球的价值, 可以使用前缀和, 得到 [i, j] 的价值之和
     * 3. 还是有问题, 看看题解
     * need todo.
     * @param: inventory
     * @param: orders
     * @return int
     * @author marks
     * @CreateDate: 2026/06/30 14:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int MOD = (int) 1e9 + 7;
    private int method_01(int[] inventory, int orders) {
        long ans = 0, maxOrder = orders;
        Map<Long, Long> map = new HashMap<>();
        for (int num : inventory) {
            map.merge((long) num, 1L, Long::sum);
        }
        // 将 key 转为 list, 并且对 list 进行降序排序
        List<Long> list = new ArrayList<>(map.keySet());
        list.add(0L); // 添加 0
        list.sort((o1, o2) -> (int) (o2 - o1));
        int n = list.size();

        long cnt = 0;
        for (int i = 0; i < n - 1 && maxOrder > 0; i++) {
            long num = list.get(i);
            long next = list.get(i + 1);
            // 有 cnt 种个数为 nums 球, 能拿下多少的订单数量, (num - next) * cnt 个订单
            cnt += map.get(num);
            long sum = (num - next) * cnt;
            long sub = num - next;
            long n1;
            if (sum <= maxOrder) {
                maxOrder -= sum;
                n1 = (num + next + 1) * sub / 2;
                ans = (ans + mulMod(n1, cnt)) % MOD;
            } else {
                long a = maxOrder / cnt; // 每组可以取 a 个球
                n1 = (num + (num - a + 1)) * a / 2;
                ans = (ans + mulMod(n1, a)) % MOD;
                long b = maxOrder % cnt; // 可以再 b 组中取1个球, 每个球的价值是 c;
                long c = cnt - a;
                ans = (ans + mulMod(b, c)) % MOD;
                maxOrder = 0;
            }
        }

        return (int) (ans % MOD);
    }

    private long mulMod(long a, long b) {
        // 先对 a, b 取模，减小初始值规模（可选，但推荐）
        a %= MOD;
        b %= MOD;

        long res = 0;
        while (b > 0) {
            // 如果 b 的最低位为 1，则累加当前的 a
            if ((b & 1) == 1) {
                res = (res + a) % MOD;
            }
            // a 翻倍，b 右移一位
            a = (a << 1) % MOD; // 注意：a << 1 也可能溢出，所以必须 % MOD
            // 更安全的写法是: a = (a + a) % MOD;
            b >>= 1;
        }
        return res;
    }

}
