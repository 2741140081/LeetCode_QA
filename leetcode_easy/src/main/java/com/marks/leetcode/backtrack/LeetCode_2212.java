package com.marks.leetcode.backtrack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/18 14:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2212 {

    /**
     * @Description:
     * Alice 和 Bob 是一场射箭比赛中的对手。比赛规则如下：
     *
     * Alice 先射 numArrows 支箭，然后 Bob 也射 numArrows 支箭。
     * 分数按下述规则计算：
     * 箭靶有若干整数计分区域，范围从 0 到 11 （含 0 和 11）。
     * 箭靶上每个区域都对应一个得分 k（范围是 0 到 11），Alice 和 Bob 分别在得分 k 区域射中 ak 和 bk 支箭。如果 ak >= bk ，那么 Alice 得 k 分。
     * 如果 ak < bk ，则 Bob 得 k 分
     * 如果 ak == bk == 0 ，那么无人得到 k 分。
     * 例如，Alice 和 Bob 都向计分为 11 的区域射 2 支箭，那么 Alice 得 11 分。如果 Alice 向计分为 11 的区域射 0 支箭，但 Bob 向同一个区域射 2 支箭，那么 Bob 得 11 分。
     *
     * 给你整数 numArrows 和一个长度为 12 的整数数组 aliceArrows ，该数组表示 Alice 射中 0 到 11 每个计分区域的箭数量。现在，Bob 想要尽可能 最大化 他所能获得的总分。
     *
     * 返回数组 bobArrows ，该数组表示 Bob 射中 0 到 11 每个 计分区域的箭数量。且 bobArrows 的总和应当等于 numArrows 。
     *
     * 如果存在多种方法都可以使 Bob 获得最大总分，返回其中 任意一种 即可。
     * @param numArrows
     * @param aliceArrows
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/18 14:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] maximumBobPoints(int numArrows, int[] aliceArrows) {
        int[] result;
        result = method_01(numArrows, aliceArrows);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：numArrows = 9, aliceArrows = [1,1,0,1,0,0,2,1,0,1,2,0]
     * 输出：[0,0,0,0,1,1,0,0,1,2,3,1]
     * 1. 回溯法, 对于 index, 假设我要拿 index 当前的得分, 必须要要射箭 count = aliceArrows[index] + 1
     * 2. 递归调用, 递归调用的时候, numArrows - count, 递归的结束条件 numArrows = 0 或者 index == n
     * 3. 还有一个问题, 当 n == index 是, 可能 numArrows > 0, 即还有箭未射出, 将这些未射完的箭赋值到 ans[0] = numArrows
     * 4. just do it!
     * @param numArrows 
     * @param aliceArrows
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/18 14:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int n;
    private int maxScore;
    private int[] ans;
    private int[] method_01(int numArrows, int[] aliceArrows) {
        n = aliceArrows.length;
        maxScore = 0;
        ans = new int[n];
        int[] bobArrows = new int[n];
        backtrack(numArrows, 0, 0, bobArrows, aliceArrows);
        return ans;
    }

    private void backtrack(int numArrows, int index, int score, int[] bobArrows, int[] aliceArrows) {
        if (numArrows == 0 || index == n) {
            if (score > maxScore) {
                maxScore = score;
                ans = bobArrows.clone();
                ans[n - 1] += numArrows;
            }
            return;
        }
        if (numArrows > aliceArrows[index]) {
            // 取当前
            bobArrows[index] = aliceArrows[index] + 1;
            backtrack(numArrows - bobArrows[index], index + 1, score + index, bobArrows, aliceArrows);
            // 回溯
            bobArrows[index] = 0;
            backtrack(numArrows, index + 1, score, bobArrows, aliceArrows);
        } else {
            backtrack(numArrows, index + 1, score, bobArrows, aliceArrows);
        }
    }
}
