package com.marks.leetcode.data_structure.difference;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/17 17:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1094 {
    /**
     * @Description:
     * 车上最初有 capacity 个空座位。车 只能 向一个方向行驶（也就是说，不允许掉头或改变方向）
     *
     * 给定整数 capacity 和一个数组 trips ,  trip[i] = [numPassengersi, fromi, toi] 表示第 i 次旅行有 numPassengersi 乘客，
     * 接他们和放他们的位置分别是 fromi 和 toi 。这些位置是从汽车的初始位置向东的公里数。
     *
     * 当且仅当你可以在所有给定的行程中接送所有乘客时，返回 true，否则请返回 false。
     * @param trips 
     * @param capacity
     * @return boolean
     * @author marks
     * @CreateDate: 2025/2/17 17:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean carPooling(int[][] trips, int capacity) {
        boolean result;
        result = method_01(trips, capacity);
        return result;
    }

    /**
     * @Description:
     * 差分:
     * AC: 1ms/43.39MB
     * @param trips
     * @param capacity
     * @return boolean
     * @author marks
     * @CreateDate: 2025/2/17 17:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[][] trips, int capacity) {
        int maxValue = 0;
        for (int[] trip : trips) {
            maxValue = Math.max(maxValue, trip[2]);
        }

        int[] diff = new int[maxValue + 1];
        for (int[] trip : trips) {
            int numPassengers = trip[0], from = trip[1], to = trip[2];
            diff[from] += numPassengers;
            diff[to] -= numPassengers;
        }
        int count = 0;
        for (int i = 0; i <= maxValue; i++) {
            count += diff[i];
            if (count > capacity) {
                return false;
            }
        }
        return true;
    }
}
