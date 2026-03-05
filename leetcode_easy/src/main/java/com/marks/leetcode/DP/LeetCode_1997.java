package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1997 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/3 17:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1997 {

    /**
     * @Description:
     * 你需要访问 n 个房间，房间从 0 到 n - 1 编号。同时，每一天都有一个日期编号，从 0 开始，依天数递增。你每天都会访问一个房间。
     * 最开始的第 0 天，你访问 0 号房间。给你一个长度为 n 且 下标从 0 开始 的数组 nextVisit 。在接下来的几天中，你访问房间的 次序 将根据下面的 规则 决定：
     * 假设某一天，你访问 i 号房间。
     * 如果算上本次访问，访问 i 号房间的次数为 奇数 ，那么 第二天 需要访问 nextVisit[i] 所指定的房间，其中 0 <= nextVisit[i] <= i 。
     * 如果算上本次访问，访问 i 号房间的次数为 偶数 ，那么 第二天 需要访问 (i + 1) mod n 号房间。
     * 请返回你访问完所有房间的第一天的日期编号。题目数据保证总是存在这样的一天。由于答案可能很大，返回对 10^9 + 7 取余后的结果。
     * tips:
     * n == nextVisit.length
     * 2 <= n <= 10^5
     * 0 <= nextVisit[i] <= i
     * @param: nextVisit
     * @return int
     * @author marks
     * @CreateDate: 2026/03/03 17:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int firstDayBeenInAllRooms(int[] nextVisit) {
        int result;
        result = method_01(nextVisit);
        result = method_02(nextVisit);
        return result;
    }

    /**
     * @Description:
     * 直接看的官方题解, 后续自行实现
     * AC: 14ms/86.31MB need todo
     * @param: nextVisit
     * @return int
     * @author marks
     * @CreateDate: 2026/03/05 10:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nextVisit) {
        int mod = 1000000007;
        int len = nextVisit.length;
        int[] dp = new int[len];

        dp[0] = 2; //初始化原地待一天 + 访问下一个房间一天
        for (int i = 1; i < len; i++) {
            int to = nextVisit[i];
            dp[i] = 2 + dp[i - 1];
            if (to != 0) {
                dp[i] = (dp[i] - dp[to - 1] + mod) % mod; //避免负数
            }

            dp[i] = (dp[i] + dp[i - 1]) % mod;
        }
        return dp[len - 2]; //题目保证n >= 2
    }

    /**
     * @Description:
     * E1:
     * 输入：nextVisit = [0,0,2]
     * 输出：6
     * 解释：
     * 你每天访问房间的次序是 [0,0,1,0,0,1,2,...] 。
     * 第 6 天是你访问完所有房间的第一天。
     * 1. 需要记录 int[] count = new int[n]; 访问房间的次数, 初始值为0, count[0] = 1, next = nextVisit[0] = 0
     * 2. 继续访问房间 0, 此时 count[0] = 2, next = (0 + 1) % 3 = 1, next = 1
     * 3. 继续访问房间 1, 此时 count[1] = 1, next = nextVisit[1] = 0, next = 0
     * 4. 继续访问房间 0, 此时 count[0] = 3, next = nextVisit[0], next = 0
     * 5. 继续访问房间 0, 此时 count[0] = 4, next = (0 + 1) % 3 = 1, next = 1
     * 6. 继续访问房间 1, 此时 count[1] = 2, next = (1 + 1) % 3 = 2, next = 2
     * 7. 继续访问房间 2, 此时 count[2] = 1, next = nextVisit[2] = 2, next = 2; 此时所有房间已经全部访问过一次
     * 8. 看起来像是动态规划, dp[i] 是从 dp[i - 1] 得到, 但是需要访问次数是偶数时, 才能 dp[i] = dp[i - 1] + 1
     * 9. 然后每次第一次抵达 i 号房间时, 此时的访问次数必定是奇数, 下一个房间 next = nextVisit[i], 是否可以记录 dp[next] 与 dp[i] 之间的差值
     * dp[i] += (dp[i] - dp[next]), 即从 next 房间再次访问到 i 号房间 所需的天数
     * @param: nextVisit
     * @return int
     * @author marks
     * @CreateDate: 2026/03/03 17:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nextVisit) {
        int mod = 1000000007;
        int n = nextVisit.length;
        int[] dp = new int[n];
        dp[0] = 2;
        for (int i = 1; i < n; i++) {
            if (i == n - 1) {
                dp[i] = dp[i - 1] + 1;
                continue;
            }
            int next = nextVisit[i];
            dp[i] = (2 * dp[i - 1] - dp[next] + 2) % mod;
        }
        return dp[n - 1] ;
    }


}
