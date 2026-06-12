package com.marks.leetcode.difference_array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1094 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/10 11:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1094 {

    /**
     * @Description:
     * 车上最初有 capacity 个空座位。车 只能 向一个方向行驶（也就是说，不允许掉头或改变方向）
     * 给定整数 capacity 和一个数组 trips ,  trips[i] = [numPassengersi, fromi, toi]
     * 表示第 i 次旅行有 numPassengersi 乘客，接他们和放他们的位置分别是 fromi 和 toi 。这些位置是从汽车的初始位置向东的公里数。
     * 当且仅当你可以在所有给定的行程中接送所有乘客时，返回 true，否则请返回 false。
     *
     * tips:
     * 1 <= trips.length <= 1000
     * trips[i].length == 3
     * 1 <= numPassengersi <= 100
     * 0 <= fromi < toi <= 1000
     * 1 <= capacity <= 10^5
     * @param: trips
     * @param: capacity
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/10 11:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean carPooling(int[][] trips, int capacity) {
        boolean result;
        result = method_01(trips, capacity);
        return result;
    }

    /**
     * @Description:
     * 1. 使用差分数组, from 处上车, to 处下车
     * AC: 2ms/45.5MB
     * @param: trips
     * @param: capacity
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/10 11:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[][] trips, int capacity) {
        int[] diff = new int[1001];
        for (int[] trip : trips) {
            int numPassengers = trip[0];
            int from = trip[1];
            int to = trip[2];
            diff[from] += numPassengers;
            diff[to] -= numPassengers;
        }
        int sum = 0;
        int maxC = 0;
        for (int i = 0; i < 1001; ++i) {
            sum += diff[i];
            maxC = Math.max(sum, maxC);
        }
        return maxC <= capacity;
    }

}
