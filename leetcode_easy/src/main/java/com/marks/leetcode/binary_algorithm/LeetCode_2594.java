package com.marks.leetcode.binary_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/22 15:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2594 {

    /**
     * @Description: [
     * 给你一个整数数组 ranks ，表示一些机械工的 能力值 。ranksi 是第 i 位机械工的能力值。
     * 能力值为 r 的机械工可以在 r * n2 分钟内修好 n 辆车。
     *
     * 同时给你一个整数 cars ，表示总共需要修理的汽车数目。
     * 请你返回修理所有汽车 最少 需要多少时间。
     * 注意：所有机械工可以同时修理汽车。
     * tips:
     * 1 <= ranks.length <= 10^5
     * 1 <= ranks[i] <= 100
     * 1 <= cars <= 10^6
     * ]
     * @param ranks
     * @param cars
     * @return long
     * @author marks
     * @CreateDate: 2024/11/22 15:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long repairCars(int[] ranks, int cars) {
        long result;
        result = method_01(ranks, cars);
        return result;
    }
    
    /**
     * @Description: [
     * 输入：ranks = [4,2,3,1], cars = 10
     * 输出：16
     * 解释：
     * - 第一位机械工修 2 辆车，需要 4 * 2 * 2 = 16 分钟。
     * - 第二位机械工修 2 辆车，需要 2 * 2 * 2 = 8 分钟。
     * - 第三位机械工修 2 辆车，需要 3 * 2 * 2 = 12 分钟。
     * - 第四位机械工修 4 辆车，需要 1 * 4 * 4 = 16 分钟。
     * 16 分钟是修理完所有车需要的最少时间。
     *
     * AC:31ms/54.94MB
     * ]
     * @param ranks 
     * @param cars 
     * @return long
     * @author marks
     * @CreateDate: 2024/11/22 15:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] ranks, int cars) {
        Arrays.sort(ranks);
        long left = 1;
        long right = (long)ranks[0] * cars * cars;
        while (left < right) {
            long mid = (right - left) / 2 + left;
            if (checkAllCarsAreRepaired(ranks, cars, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private boolean checkAllCarsAreRepaired(int[] ranks, int cars, long mid) {
        for (int rank : ranks) {
            cars -= (int) Math.sqrt(mid / rank);
            if (cars <= 0) {
                return true;
            }
        }
        return false;
    }
}
