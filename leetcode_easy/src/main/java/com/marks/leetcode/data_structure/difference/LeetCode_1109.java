package com.marks.leetcode.data_structure.difference;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/17 17:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1109 {
    /**
     * @Description: [功能描述]
     * @param bookings
     * @param n
     * @return int[]
     * @author marks
     * @CreateDate: 2025/2/17 17:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] result;
        result = method_01(bookings, n);
        return result;
    }

    /**
     * @Description:
     * 差分
     * AC: 2ms/58.37MB
     * @param bookings
     * @param n
     * @return int[]
     * @author marks
     * @CreateDate: 2025/2/17 17:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] bookings, int n) {
        int maxValue = 0;
        for (int[] booking : bookings) {
            maxValue = Math.max(maxValue, booking[1]);
        }
        int[] diff = new int[maxValue + 2];
        for (int[] booking : bookings) {
            int start = booking[0], end = booking[1], seat = booking[2];
            diff[start] += seat;
            diff[end + 1] -= seat;
        }
        int[] ans = new int[n];
        int count = 0;
        for (int i = 1; i <= maxValue; i++) {
            count += diff[i];
            ans[i - 1] = count;
        }
        return ans;
    }
}
