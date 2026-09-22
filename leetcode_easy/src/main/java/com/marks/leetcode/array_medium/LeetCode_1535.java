package com.marks.leetcode.array_medium;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1535 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/21 16:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1535 {

    /**
     * @Description:
     * 给你一个由 不同 整数组成的整数数组 arr 和一个整数 k 。
     * 每回合游戏都在数组的前两个元素（即 arr[0] 和 arr[1] ）之间进行。
     * 比较 arr[0] 与 arr[1] 的大小，较大的整数将会取得这一回合的胜利并保留在位置 0 ，较小的整数移至数组的末尾。
     * 当一个整数赢得 k 个连续回合时，游戏结束，该整数就是比赛的 赢家 。
     * 返回赢得比赛的整数。
     * 题目数据 保证 游戏存在赢家。
     *
     * tips:
     * 2 <= arr.length <= 10^5
     * 1 <= arr[i] <= 10^6
     * arr 所含的整数 各不相同 。
     * 1 <= k <= 10^9
     * @param: arr
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/09/21 16:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int getWinner(int[] arr, int k) {
        int result;
        result = method_01(arr, k);
        return result;
    }

    /**
     * @Description:
     * 1. 先遍历, 如果一次遍历还是没有找到符合的k, 则直接返回最后一个最大数即可
     * AC: 1ms/65.77MB
     * @param: arr
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/09/21 16:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr, int k) {
        int cnt = 0; // 当前数赢的次数
        int maxNum = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > maxNum) {
                maxNum = arr[i];
                cnt = 1; // 与前一个数比较, 已经赢了一次
            } else {
                cnt++;
            }
            if (cnt == k) {
                return maxNum;
            }
        }

        return maxNum;
    }

}
