package com.marks.leetcode.binary_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/22 14:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_475 {
    /**
     * @Description: [
     * 冬季已经来临。 你的任务是设计一个有固定加热半径的供暖器向所有房屋供暖。
     * 在加热器的加热半径范围内的每个房屋都可以获得供暖。
     * 现在，给出位于一条水平线上的房屋 houses 和供暖器 heaters 的位置，请你找出并返回可以覆盖所有房屋的最小加热半径。
     * 注意：所有供暖器 heaters 都遵循你的半径标准，加热的半径也一样。
     *
     * tips:
     * 1 <= houses.length, heaters.length <= 3 * 10^4
     * 1 <= houses[i], heaters[i] <= 10^9
     * ]
     * @param houses
     * @param heaters
     * @return int
     * @author marks
     * @CreateDate: 2024/11/22 14:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findRadius(int[] houses, int[] heaters) {
        int result;
        result = method_01(houses, heaters);
        return result;
    }

    /**
     * @Description: [
     * 输入: houses = [1,2,3,4], heaters = [1,4]
     * 输出: 1
     * 解释: 在位置 1, 4 上有两个供暖器。我们需要将加热半径设为 1，这样所有房屋就都能得到供暖。
     * int center = heaters[0];
     * int right = 0;
     * if(center < houses[0]) {
     *     right = houses[n - 1] - center;
     * } else if(center > houses[n - 1]) {
     *     right = center - houses[0];
     * } else {
     *     right = Math.max(center - houses[0], houses[n - 1] - center);
     * }
     * method_01(): 排序 + 二分法 + 双指针
     * AC:20ms/46.07MB
     * ]
     * @param houses
     * @param heaters
     * @return int
     * @author marks
     * @CreateDate: 2024/11/22 14:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] houses, int[] heaters) {
        Arrays.sort(houses);
        Arrays.sort(heaters);
        int n = houses.length;
        int left = 1;
        int right;
        int center = heaters[0];
        if (center < houses[0]) {
            right = houses[n - 1] - center;
        } else if (center > houses[n - 1]) {
            right = center - houses[0];
        } else {
            right = Math.max(center - houses[0], houses[n - 1] - center);
        }

        while (left < right) {
            int mid = (right - left) / 2 + left;
            if (checkAllHousesAreHeat(houses, heaters, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * @Description: [
     * 输入: houses = [1,2,3,4], heaters = [1,4]
     * left = 1, right = 3, mid = 2,
     * 这种用双指针会不会好一点
     * left = 0, right = 0, houses[0] = 1, heaters[0] = 1, mid = 2, left++
     * left = 1, right = 0, houses[1] = 2, heaters[0] = 1, mid = 2, left++
     * left = 2, right = 0, houses[2] = 3, heaters[0] = 1, mid = 2, left++
     * left = 3, right = 0, houses[3] = 4, heaters[0] = 1, mid = 2, right++
     * left = 3, right = 1, houses[3] = 4, heaters[1] = 4, mid = 2, left++
     * left = 4, right = 1, break
     * ]
     * @param houses
     * @param heaters
     * @param mid
     * @return boolean
     * @author marks
     * @CreateDate: 2024/11/22 15:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean checkAllHousesAreHeat(int[] houses, int[] heaters, int mid) {
        int left = 0;
        int right = 0;
        while (left < houses.length && right < heaters.length) {
            if (Math.abs(houses[left] - heaters[right]) <= mid) {
                left++;
            } else {
                right++;
            }
        }
        if (left == houses.length) {
            return true;
        } else {
            return false;
        }
    }
}
