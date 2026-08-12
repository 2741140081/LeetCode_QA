package com.marks.leetcode.array_hard;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2532 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/12 10:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2532 {

    /**
     * @Description:
     * 共有 k 位工人计划将 n 个箱子从右侧的（旧）仓库移动到左侧的（新）仓库。
     * 给你两个整数 n 和 k，以及一个二维整数数组 time ，数组的大小为 k x 4 ，其中 time[i] = [righti, picki, lefti, puti] 。
     * 一条河将两座仓库分隔，只能通过一座桥通行。旧仓库位于河的右岸，新仓库在河的左岸。开始时，所有 k 位工人都在桥的左侧等待。
     * 为了移动这些箱子，第 i 位工人（下标从 0 开始）可以：
     * 从左岸（新仓库）跨过桥到右岸（旧仓库），用时 righti 分钟。
     * 从旧仓库选择一个箱子，并返回到桥边，用时 picki 分钟。不同工人可以同时搬起所选的箱子。
     * 从右岸（旧仓库）跨过桥到左岸（新仓库），用时 lefti 分钟。
     * 将箱子放入新仓库，并返回到桥边，用时 puti 分钟。不同工人可以同时放下所选的箱子。
     * 如果满足下面任一条件，则认为工人 i 的 效率低于 工人 j ：
     *
     * lefti + righti > leftj + rightj
     * lefti + righti == leftj + rightj 且 i > j
     * 工人通过桥时需要遵循以下规则：
     * 同时只能有一名工人过桥。
     * 当桥梁未被使用时，优先让右侧 效率最低 的工人（已经拿起盒子的工人）过桥。如果不是，优先让左侧 效率最低 的工人通过。
     * 如果左侧已经派出足够的工人来拾取所有剩余的箱子，则 不会 再从左侧派出工人。
     * 请你返回最后一个箱子 到达桥左侧 的时间。
     *
     * tips:
     * 1 <= n, k <= 10^4
     * time.length == k
     * time[i].length == 4
     * 1 <= lefti, picki, righti, puti <= 1000
     * @param: n
     * @param: k
     * @param: time
     * @return int
     * @author marks
     * @CreateDate: 2026/08/12 10:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findCrossingTime(int n, int k, int[][] time) {
        int result;
        result = method_01(n, k, time);
        return result;
    }

    /**
     * @Description:
     * 1. 需要先对time 处理, 得到一个优先队列, 存储在 leftWait 的优先队列, 大根堆, 排序方式时效率低的在顶部
     * 2. 4个优先队列, leftWait: 左岸等待工人, leftBusy: 左岸将盒子送往新仓库的人员, rightWait: 右岸等待的人员,
     * rightBusy: 右岸从旧仓库拿回箱子的人员, int cnt 记录剩余的箱子数
     * 3. 然后开始模拟, 将工人全部放入到 leftWait 优先队列中, 需要从 leftWait 弹出顶部工人过桥, 时间花费 r1,
     * 然后他会去取箱子, 并且将他添加到 rightBusy 队列, 这个队列是时间的小根堆, r1 + p1 放入优先队列 rightBusy,
     * 由于右岸当前没有等待的人员, 此时的时间是 r1, 那么再次从 leftWait 弹出顶部工人过桥, 花费时间 r2, 并且t = r1 + r2,
     * 然后判断 rightBusy 是否有时间小于 t, 如果有则取出放入到 rightWait 当中, rightWait 是效率的大根堆,
     * 并且将第二个工人放入 rightBusy 队列, r1 + r2 + p2. 并且 cnt--, 箱子数量减1, 重复上述过程.
     * 4. 当rightWait 不为空时, 取出顶部工人过桥, 假设此时的时间是 t, t + l1, 然后放入 leftBusy t + l1 + pt1
     * 先处理 rightBusy 弹出工人并放入 rightWait, 并且将 leftBusy 中的到达员工放入 leftWait 当中.
     * 5. 当 cnt 为 0 时, 如果 rightWait 还是空, 此时应该将时间提高到 rightBusy 的顶部时间值, 即将 cnt != 0 和 cnt == 0,
     * 分开来进行计算, cnt > 0 时执行模拟, cnt = 0 时, 此时只需要执行将 rightBusy 和 rightWait 的元素计算时间的最大值即为最终结果.
     * AC: 58ms/50.18MB
     * @param: n
     * @param: k
     * @param: time
     * @return int
     * @author marks
     * @CreateDate: 2026/08/12 10:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int k, int[][] time) {
        int cnt = n;
        // 创建 leftWait, [righti, picki, lefti, puti, i]
        PriorityQueue<int[]> leftWait = new PriorityQueue<>((o1, o2) -> {
            int s1 = o1[0] + o1[2];
            int s2 = o2[0] + o2[2];
            if (s1 == s2) {
                return o2[4] - o1[4]; // i > j
            } else {
                return s2 - s1;
            }
        });
        PriorityQueue<int[]> leftBusy = new PriorityQueue<>(((o1, o2) -> o1[5] - o2[5]));// 小根堆的时间排序
        PriorityQueue<int[]> rightWait = new PriorityQueue<>((o1, o2) -> {
            int s1 = o1[0] + o1[2];
            int s2 = o2[0] + o2[2];
            if (s1 == s2) {
                return o2[4] - o1[4]; // i > j
            } else {
                return s2 - s1;
            }
        });
        PriorityQueue<int[]> rightBusy = new PriorityQueue<>(((o1, o2) -> o1[5] - o2[5]));// 小根堆的时间排序

        // 1. 将左岸等待的工人全部存入 leftWait
        for (int i = 0; i < k; i++) {
            leftWait.offer(new int[]{time[i][0], time[i][1], time[i][2], time[i][3], i});
        }

        // 处理 cnt 大于 0 的情况
        int currTime = 0;
        // {right_i, pick_i, left_i, put_i, i, time_i}
        while (cnt > 0) {
            // 处理 leftBusy
            while (!leftBusy.isEmpty() && leftBusy.peek()[5] <= currTime) {
                // 取出并且放入 leftWait
                int[] p = leftBusy.poll();
                leftWait.offer(new int[]{p[0], p[1], p[2], p[3], p[4]});
            }
            // 处理 rightBusy
            while (!rightBusy.isEmpty() && rightBusy.peek()[5] <= currTime) {
                // 取出并且放入 rightWait
                int[] p = rightBusy.poll();
                rightWait.offer(new int[]{p[0], p[1], p[2], p[3], p[4]});
            }
            // 执行过桥操作
            if (!rightWait.isEmpty()) {
                // 优先处理右岸等待
                int[] p = rightWait.poll();
                currTime += p[2]; // left_i
                // 以及过桥后添加到 leftBusy
                leftBusy.offer(new int[]{p[0], p[1], p[2], p[3], p[4], currTime + p[3]}); // currTime + put_i
            } else if (!leftWait.isEmpty()){
                // 处理左岸等待
                int[] p = leftWait.poll();
                currTime += p[0]; // right_i
                rightBusy.offer(new int[]{p[0], p[1], p[2], p[3], p[4], currTime + p[1]}); // currTime + pick_i
                cnt--;
            } else {
                // 当 leftWait 和 rightWait 都为空时, 更新 currTime 为 rightBusy 和 leftBusy 的最小值
                int rightTime = rightBusy.isEmpty() ? Integer.MAX_VALUE : rightBusy.peek()[5];
                int leftTime = leftBusy.isEmpty() ? Integer.MAX_VALUE : leftBusy.peek()[5];
                currTime = Math.min(leftTime, rightTime);
            }
        }
        int ans = currTime; // ans 记录最大时间
        // 处理 cnt == 0 时的情况, 此时 leftWait 可以忽略, 3 个优先队列均不为空
        while (!rightWait.isEmpty() || !rightBusy.isEmpty() || !leftBusy.isEmpty()) {
            // 先处理 leftBusy
            while (!leftBusy.isEmpty() && leftBusy.peek()[5] <= currTime) {
                int[] p = leftBusy.poll();
                ans = Math.max(ans, p[5] - p[3]); // 只需要到达左岸, 不需要放入新仓库
            }
            // 处理 rightBusy
            while (!rightBusy.isEmpty() && rightBusy.peek()[5] <= currTime) {
                // 取出并且放入 rightWait
                int[] p = rightBusy.poll();
                rightWait.offer(new int[]{p[0], p[1], p[2], p[3], p[4]});
            }
            // 处理过桥
            if (!rightWait.isEmpty()) {
                // 优先处理右岸等待
                int[] p = rightWait.poll();
                currTime += p[2]; // left_i
                // 以及过桥后添加到 leftBusy
                leftBusy.offer(new int[]{p[0], p[1], p[2], p[3], p[4], currTime + p[3]}); // currTime + put_i
            } else {
                // 当 leftWait 和 rightWait 都为空时, 更新 currTime 为 rightBusy 和 leftBusy 的最小值
                int rightTime = rightBusy.isEmpty() ? Integer.MAX_VALUE : rightBusy.peek()[5];
                int leftTime = leftBusy.isEmpty() ? Integer.MAX_VALUE : leftBusy.peek()[5];
                currTime = Math.min(leftTime, rightTime);
            }
        }

        return ans;
    }


}
