package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1599 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/29 14:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1599 {

    /**
     * @Description:
     * 你正在经营一座摩天轮，该摩天轮共有 4 个座舱 ，每个座舱 最多可以容纳 4 位游客 。
     * 你可以 逆时针 轮转座舱，但每次轮转都需要支付一定的运行成本 runningCost 。
     * 给你一个长度为 n 的数组 customers ， customers[i] 是在第 i 次轮转（下标从 0 开始）之前到达的新游客的数量。
     * 这也意味着你必须在新游客到来前轮转 i 次。如果有座舱空闲就不能让游客等待。
     * 每位游客在登上离地面最近的座舱前都会支付登舱成本 boardingCost ，一旦该座舱再次抵达地面，他们就会离开座舱结束游玩。
     * 你可以随时停下摩天轮，即便是 在服务所有游客之前 。如果你决定停止运营摩天轮，为了保证所有游客安全着陆，将免费进行所有后续轮转 。
     * 注意，如果有超过 4 位游客在等摩天轮，那么只有 4 位游客可以登上摩天轮，其余的需要等待 下一次轮转 。
     * 返回最大化利润所需执行的 最小轮转次数 。 如果不存在利润为正的方案，则返回 -1 。
     *
     * tips:
     * n == customers.length
     * 1 <= n <= 10^5
     * 0 <= customers[i] <= 50
     * 1 <= boardingCost, runningCost <= 100
     * @param: customers
     * @param: boardingCost
     * @param: runningCost
     * @return int
     * @author marks
     * @CreateDate: 2026/07/29 14:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minOperationsMaxProfit(int[] customers, int boardingCost, int runningCost) {
        int result;
        result = method_01(customers, boardingCost, runningCost);
        return result;
    }

    /**
     * @Description:
     * 1. 摩天轮有4个座舱, 用 int[] seats = new int[4]; 并且 int currIdx 指针执行当前位于地面处的座舱
     * 2. customers[i] 是第 i 次轮转之前到达的, 所以应该先安排游玩. 由于顾客数量可能大于 4, int remainingCustomer 表示剩余等待的顾客数量
     * 3. 如果 runningCost > 4 * boardingCost, 则利润必定是负数, 此时直接返回 -1
     * 4. 这相当于是一个模拟, 找出模拟过程中利润最大的最小次数, int maxEarn 为最大利润, int ans = 0; ans 是最小轮转次数. int cnt 是当前轮转总数.
     * AC: 3ms/61.79MB
     * @param: customers
     * @param: boardingCost
     * @param: runningCost
     * @return int
     * @author marks
     * @CreateDate: 2026/07/29 14:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] customers, int boardingCost, int runningCost) {
        int n = customers.length;
        if (runningCost > 4 * boardingCost) {
            return -1;
        }

        int remainingCustomer = 0;
        int maxEarn = -(runningCost * n), ans = 0, cnt = 0;
        int cost = 0; // 当前获取的利润
        for (int i = 0; i < n; i++) {
            // 添加 i 次轮转前等待的顾客
            remainingCustomer += customers[i];
            // 登录的顾客
            int take = Math.min(remainingCustomer, 4);
            // 轮转花费和获取游玩费用
            cost += (boardingCost * take - runningCost);
            // 更新剩余顾客数量
            remainingCustomer -= take;
            cnt++; // 执行轮转
            // 更新最大利润
            if (cost > maxEarn) {
                maxEarn = cost;
                ans = cnt;
            }
        }
        if (remainingCustomer != 0 && (runningCost < 4 * boardingCost) ) {
            // 仍然剩余顾客在等待
            int full = remainingCustomer / 4; // 满载情况下需要轮转次数
            int take = 4;
            cost += ((boardingCost * take - runningCost) * full);
            cnt += full;
            if (cost > maxEarn) {
                maxEarn = cost;
                ans = cnt;
            }
            if (remainingCustomer % 4 != 0) {
                take = remainingCustomer % 4; // 最后一趟的人数
                cost += (boardingCost * take - runningCost);
                cnt++;
                if (cost > maxEarn) {
                    maxEarn = cost;
                    ans = cnt;
                }
            }
        }

        return maxEarn <= 0 ? -1 : ans;
    }

}
