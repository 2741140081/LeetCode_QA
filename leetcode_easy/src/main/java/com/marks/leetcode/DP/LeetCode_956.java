package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_956 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/14 14:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_956 {

    /**
     * @Description:
     * 你正在安装一个广告牌，并希望它高度最大。这块广告牌将有两个钢制支架，两边各一个。每个钢支架的高度必须相等。
     * 你有一堆可以焊接在一起的钢筋 rods。举个例子，如果钢筋的长度为 1、2 和 3，则可以将它们焊接在一起形成长度为 6 的支架。
     * 返回 广告牌的最大可能安装高度 。如果没法安装广告牌，请返回 0 。
     *
     * tips:
     * 0 <= rods.length <= 20
     * 1 <= rods[i] <= 1000
     * sum(rods[i]) <= 5000
     * @param: rods
     * @return int
     * @author marks
     * @CreateDate: 2026/04/14 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int tallestBillboard(int[] rods) {
        int result;
        result = method_01(rods);
        return result;
    }

    /**
     * @Description:
     * 1. 能否将数组分割成相等的两份. 数据范围有点小, 尝试使用二分法来找到最大高度
     * @param: rods
     * @return int
     * @author marks
     * @CreateDate: 2026/04/14 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] rods) {
        int n = rods.length;
        int sum = Arrays.stream(rods).sum();
        int left = 0;
        int right = sum / 2;
        int ans = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canPartition(rods, mid)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }


        return 0;
    }

    private boolean canPartition(int[] rods, int target) {

        return false;
    }

}
