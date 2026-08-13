package com.marks.leetcode.array_hard;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2106 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/4 16:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2106 {

    /**
     * @Description:
     * 在一个无限的 x 坐标轴上，有许多水果分布在其中某些位置。
     * 给你一个二维整数数组 fruits ，其中 fruits[i] = [positioni, amounti] 表示共有 amounti 个水果放置在 positioni 上。
     * fruits 已经按 positioni 升序排列 ，每个 positioni 互不相同 。
     * 另给你两个整数 startPos 和 k 。最初，你位于 startPos 。
     * 从任何位置，你可以选择 向左或者向右 走。在 x 轴上每移动 一个单位 ，就记作 一步 。
     * 你总共可以走 最多 k 步。你每达到一个位置，都会摘掉全部的水果，水果也将从该位置消失（不会再生）。
     * 返回你可以摘到水果的 最大总数 。
     *
     * tips:
     * 1 <= fruits.length <= 10^5
     * fruits[i].length == 2
     * 0 <= startPos, positioni <= 2 * 10^5
     * 对于任意 i > 0 ，positioni-1 < positioni 均成立（下标从 0 开始计数）
     * 1 <= amounti <= 10^4
     * 0 <= k <= 2 * 10^5
     * @param: fruits
     * @param: startPos
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/08/04 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        int result;
        result = method_01(fruits, startPos, k);
        return result;
    }

    /**
     * @Description:
     * 1. 枚举 + 贪心 + 前缀和
     * 2. 先确定 startPos 位于 fruits 中的下标位置 int init, 可以使用二分查找得到
     * 3. 得到前缀和数组 int[] preSum, 分别出力 init 的左右两侧, preSum[init] = init > 0 ? fruits[init] : 0
     * 4. 枚举右侧的节点, 假设 j > i, 并且 fruits[j][0] - startPos < k, 那么会剩余的步数 int leftDist = k - 2 * fruits[j][0],
     * leftDist 即为向左侧移动能到达的最远距离, 如果 leftDist > 0, int leftPos = startPos + leftDist, 然后通过二分搜索,
     * 找到 leftPos 对应的下标 p, 此时 int sum = preSum[j] + preSum[p] - preSum[init]; 由于 init 会重复计算一次.
     * AC: 16ms/126.07MB
     * @param: fruits
     * @param: startPos
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/08/04 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] fruits, int startPos, int k) {
        int n = fruits.length;
        // 使用二分查找, 查询 startPos 所在下标位置
        int startIndex = getTargetIndex(fruits, startPos, n);
        // 创建前缀和数组
        int[] preSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            preSum[i + 1] = preSum[i] + fruits[i][1];
        }
        if (startIndex == -1) {
            // startPos 在 n - 1 的右侧
            int endPos = startPos - k;
            // 通过二分查找找到 endPos 坐标
            int endIndex = getTargetIndex(fruits, endPos, n);
            if (endIndex == -1) {
                return 0; // 无法得到任何水果
            }
            return preSum[n] - preSum[endIndex];
        }
        // 枚举右侧点
        int endPos = startPos + k;
        int endIndex = getTargetIndex(fruits, endPos, n);
        if (endIndex == -1) {
            endIndex = n;
        } else if (fruits[endIndex][0] == endPos) {
            endIndex++;
        }
        int ans = preSum[endIndex] - preSum[startIndex];
        for (int i = startIndex; i < endIndex; i++) {
            // 向右侧移动的步数
            int rightStep = (fruits[i][0] - startPos) * 2;
            if (rightStep < 0) {
                break;
            }
            int leftStep = k - rightStep; // 向左侧移动的步数
            if (leftStep <= 0) { // 提前进行剪枝操作
                break;
            }
            int leftEndPos = startPos - leftStep;
            int leftIndex = getTargetIndex(fruits, leftEndPos, n);
            ans = Math.max(ans, preSum[i + 1] - preSum[leftIndex]);
        }
        // 枚举左侧点
        endPos = startPos - k;
        int leftIndex = getTargetIndex(fruits, endPos, n);
        ans = Math.max(ans, preSum[startIndex] - preSum[leftIndex]);
        for (int i = startIndex - 1; i >= leftIndex; i--) {
            // 向左侧移动的步数
            int leftStep = (startPos - fruits[i][0]) * 2;
            if (leftStep < 0) {
                break;
            }
            int rightStep = k - leftStep; // 向右侧移动的步数
            if (rightStep <= 0) { // 提前进行剪枝操作
                break;
            }
            int rightEndPos = startPos + rightStep;
            int rightIndex = getTargetIndex(fruits, rightEndPos, n);
            if (rightIndex == -1) {
                rightIndex = n;
            } else if (fruits[rightIndex][0] == rightEndPos) {
                rightIndex++;
            }
            ans = Math.max(ans, preSum[rightIndex] - preSum[i]);
        }

        return ans;
    }

    private int getTargetIndex(int[][] fruits, int target, int n) {
        int ans = -1;
        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (fruits[mid][0] >= target) {
                right = mid - 1;
                ans = mid;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

}
