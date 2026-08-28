package com.marks.leetcode.array_medium;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2731 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/28 10:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2731 {

    /**
     * @Description:
     * 有一些机器人分布在一条无限长的数轴上，他们初始坐标用一个下标从 0 开始的整数数组 nums 表示。
     * 当你给机器人下达命令时，它们以每秒钟一单位的速度开始移动。
     * 给你一个字符串 s ，每个字符按顺序分别表示每个机器人移动的方向。
     * 'L' 表示机器人往左或者数轴的负方向移动，'R' 表示机器人往右或者数轴的正方向移动。
     * 当两个机器人相撞时，它们开始沿着原本相反的方向移动。
     * 请你返回指令重复执行 d 秒后，所有机器人之间两两距离之和。
     * 由于答案可能很大，请你将答案对 10^9 + 7 取余后返回。
     * 注意：
     * 对于坐标在 i 和 j 的两个机器人，(i,j) 和 (j,i) 视为相同的坐标对。也就是说，机器人视为无差别的。
     * 当机器人相撞时，它们 立即改变 它们的前进方向，这个过程不消耗任何时间。
     * 当两个机器人在同一时刻占据相同的位置时，就会相撞。
     * 例如，如果一个机器人位于位置 0 并往右移动，另一个机器人位于位置 2 并往左移动，下一秒，它们都将占据位置 1，并改变方向。
     * 再下一秒钟后，第一个机器人位于位置 0 并往左移动，而另一个机器人位于位置 2 并往右移动。
     * 例如，如果一个机器人位于位置 0 并往右移动，另一个机器人位于位置 1 并往左移动，
     * 下一秒，第一个机器人位于位置 0 并往左行驶，而另一个机器人位于位置 1 并往右移动。
     *
     * tips:
     * 2 <= nums.length <= 10^5
     * -2 * 10^9 <= nums[i] <= 2 * 10^9
     * 0 <= d <= 10^9
     * nums.length == s.length
     * s 只包含 'L' 和 'R' 。
     * nums[i] 互不相同。
     * @param: nums
     * @param: s
     * @param: d
     * @return int
     * @author marks
     * @CreateDate: 2026/08/28 10:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int sumDistance(int[] nums, String s, int d) {
        int result;
        result = method_01(nums, s, d);
        return result;
    }

    /**
     * @Description:
     * 1. 由于相撞无影响, 所以可以得到机器人在 d 秒后的坐标位置
     * 2. 对坐标位置进行升序排序, 然后按照贡献法来得到两两直接的距离
     * 1, -3, -1, [-3, -1, 1] 2 + 4 + 2
     * 3. 计算贡献度, nums[i] 的贡献度为: nums[i] - nums[j] (j 取值 [0 ~ i - 1]) 并且
     * nums[i] 对右侧的贡献度为 nums[k] - nums[i], (k 的取值是 (i + 1 ~ n - 1)), 总的贡献度
     * i * nums[i] - sumLeft + sumRight - (n - i - 1) * nums[i]
     * AC: 14ms/72.46MB
     * @param: nums
     * @param: s
     * @param: d
     * @return int
     * @author marks
     * @CreateDate: 2026/08/28 10:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, String s, int d) {
        int n = nums.length;
        long[] idx = new long[n];
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (ch == 'L') {
                idx[i] = (long) nums[i] - d;
            } else {
                idx[i] = (long) nums[i] + d;
            }
        }
        long ans = 0;
        int MOD = (int) 1e9 + 7;
        Arrays.sort(idx);
        long sumLeft = 0;
        for (int i = 0; i < n; i++) {
            // 左侧贡献度
            ans = (ans + (idx[i] * i) - sumLeft) % MOD;
            sumLeft += idx[i];
        }

        return (int) (ans % MOD);
    }


}
