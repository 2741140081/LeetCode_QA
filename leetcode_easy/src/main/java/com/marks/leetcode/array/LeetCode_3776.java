package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3776 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/14 16:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3776 {

    /**
     * @Description:
     * 给你一个长度为 n 的 环形 数组 balance，其中 balance[i] 是第 i 个人的净余额。
     * 在一次移动中，一个人可以将 正好 1 个单位的余额转移给他的左邻居或右邻居。
     * 返回使每个人都拥有 非负 余额所需的 最小 移动次数。如果无法实现，则返回 -1。
     * 注意：输入保证初始时 至多 有一个下标具有 负 余额。
     * tips:
     * 1 <= n == balance.length <= 10^5
     * -10^9 <= balance[i] <= 10^9
     * balance 中初始至多有一个负值。
     * @param: balance
     * @return long
     * @author marks
     * @CreateDate: 2026/07/14 16:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minMoves(int[] balance) {
        long result;
        result = method_01(balance);
        return result;
    }

    /**
     * @Description:
     * 1. 判断 -1 的情况, 如果sum < 0, 返回 -1.
     * 2. 最多只有1个负数, 只需要针对这个负数进行求解即可, 假设 balance[i] < 0, 此时需要计算分别从左侧和右侧向 i 处进行转移需要的代价。
     * 3. 分别使用left 和 right 指向 i 的左右两边, left = (i - 1 + n) % n, right = (i + 1) % n; 由于 left 不断减少以及 right不断增加,
     * 所需的值也会代价也会不断增加, 初始方向: int direction = left - right; 当前方向 int currDirection = left - right;
     * 如果 direction * currDirection < 0, 停止移动.
     * 4. long cost = 1, 初始值为1, 即 cost 为 left 与 right 移动一个单位到 i 处所需的代价是 cost
     * 5. 如果n 是奇数, 则left 和 right 的终止条件是 两者的路径存在交集, 当 n = 偶数时, 终止条件时 left == right
     * AC: 3ms/136.18MB
     * @param: balance
     * @return long
     * @author marks
     * @CreateDate: 2026/07/14 16:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] balance) {
        long sum = 0;
        // 计算sum
        int n = balance.length;
        int idx = -1;
        for (int i = 0; i < n; i++) {
            if (balance[i] < 0) {
                idx = i;
            }
            sum += balance[i];
        }
        // 判断 -1 的情况
        if (sum < 0) {
            return -1;
        } else if (idx == -1) {
            // 不存在负数
            return 0;
        }
        // 存在一个负数, 下标是 idx, 分别构建 left, right, cost
        int left = (idx - 1 + n) % n, right = (idx + 1) % n;
        long cost = 1;
        long ans = 0;
        // 将负数变成正数
        long need = -balance[idx];
        boolean[] visited = new boolean[n];
        visited[idx] = true;

        while (left != right && !(visited[left] && visited[right])) {
            // 在 left 和 right 处能获取的总余额
            int cnt = balance[left] + balance[right];
            if (cnt < need) {
                ans += (cost * cnt);
                need -= cnt;
            } else {
                ans += (cost * need);
                need = 0;
                break;
            }
            // 标记已访问
            visited[left] = true;
            visited[right] = true;
            // 执行移动操作
            left = (left - 1 + n) % n;
            right = (right + 1) % n;
            cost++;
        }

        // 如果此时还有剩余 balance[idx]
        if (need > 0) {
            // 需要取用 left 或者 right, 但是不能同时使用两者
            ans += (cost * need);
        }

        return ans;
    }

}
