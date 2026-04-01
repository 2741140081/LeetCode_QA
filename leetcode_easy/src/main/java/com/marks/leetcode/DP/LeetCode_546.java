package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_546 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/1 14:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_546 {

    /**
     * @Description:
     * 给出一些不同颜色的盒子 boxes ，盒子的颜色由不同的正数表示。
     * 你将经过若干轮操作去去掉盒子，直到所有的盒子都去掉为止。
     * 每一轮你可以移除具有相同颜色的连续 k 个盒子（k >= 1），这样一轮之后你将得到 k * k 个积分。
     * 返回 你能获得的最大积分和 。
     *
     * tips:
     * 1 <= boxes.length <= 100
     * 1 <= boxes[i] <= 100
     * @param: boxes
     * @return int
     * @author marks
     * @CreateDate: 2026/04/01 14:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int removeBoxes(int[] boxes) {
        int result;
        result = method_01(boxes);
        result = method_02(boxes);
        return result;
    }



    /**
     * @Description:
     * 官方题解, need todo
     *
     * @param: boxes
     * @return int
     * @author marks
     * @CreateDate: 2026/04/01 15:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][][] dp;
    private int method_02(int[] boxes) {
        int length = boxes.length;
        dp = new int[length][length][length];
        return calculatePoints(boxes, 0, length - 1, 0);
    }

    public int calculatePoints(int[] boxes, int l, int r, int k) {
        if (l > r) {
            return 0;
        }
        if (dp[l][r][k] == 0) {
            int r1 = r, k1 = k;
            while (r1 > l && boxes[r1] == boxes[r1 - 1]) {
                r1--;
                k1++;
            }
            dp[l][r][k] = calculatePoints(boxes, l, r1 - 1, 0) + (k1 + 1) * (k1 + 1);
            for (int i = l; i < r1; i++) {
                if (boxes[i] == boxes[r1]) {
                    dp[l][r][k] = Math.max(dp[l][r][k], calculatePoints(boxes, l, i, k1 + 1) + calculatePoints(boxes, i + 1, r1 - 1, 0));
                }
            }
        }
        return dp[l][r][k];
    }

    /**
     * @Description:
     * E1:
     * 输入：boxes = [1,3,2,2,2,3,4,3,1]
     * 输出：23
     * 1. 动态规划
     * 2. 想不到怎么进行动态转移, 看看提示吧, 没有提示!
     * 3. 想到的第一步是先将只出现一次的元素进行移除, 先分析以下
     * ABAB 类型, 移除A/B 结果相同 1 + 2 * 2 + 1 = 6
     * ABABA 类型, 1+ 3 * 3 + 1 = 10, 2 * 2 + 2 * 2 + 1 = 9
     * ABABAB 类型, 1 + 1 + 9 + 1 = 12, 1 + 4 + 4 + 1 = 10
     * 可以得到一个结果是, 元素最多的他一定
     * 3. 看看题解吧, 想不到了
     * 4. 看不懂题解, 直接CV
     * @param: boxes
     * @return int
     * @author marks
     * @CreateDate: 2026/04/01 14:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] boxes) {
        int n = boxes.length;
        // 处理个数只有一个的情况
        boolean[] remove = new boolean[n]; // 判断当前 i 是否移除
        int[] count = new int[101];
        for (int i = 0; i < n; i++) {
            count[boxes[i]]++;
        }
        int ans = 0;
        for (int i = 0; i < 101; i++) {
            if (count[i] == 1) {
                ans += 1;

            }
        }

        return 0;
    }

}
