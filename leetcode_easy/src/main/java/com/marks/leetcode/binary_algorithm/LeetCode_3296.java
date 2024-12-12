package com.marks.leetcode.binary_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/22 10:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3296 {
    
    /**
     * @Description: [
     * 给你一个整数 mountainHeight 表示山的高度。
     *
     * 同时给你一个整数数组 workerTimes，表示工人们的工作时间（单位：秒）。
     *
     * 工人们需要 同时 进行工作以 降低 山的高度。对于工人 i :
     *
     * 山的高度降低 x，需要花费 workerTimes[i] + workerTimes[i] * 2 + ... + workerTimes[i] * x 秒。例如：
     * 山的高度降低 1，需要 workerTimes[i] 秒。
     * 山的高度降低 2，需要 workerTimes[i] + workerTimes[i] * 2 秒，依此类推。
     * 返回一个整数，表示工人们使山的高度降低到 0 所需的 最少 秒数。
     *
     * tips:
     * 1 <= mountainHeight <= 10^5
     * 1 <= workerTimes.length <= 10^4
     * 1 <= workerTimes[i] <= 10^6
     * ]
     * @param mountainHeight 
     * @param workerTimes
     * @return long
     * @author marks
     * @CreateDate: 2024/11/22 10:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {
        long result;
        result = method_01(mountainHeight, workerTimes);
        return result;
    }

    /**
     * @Description: [
     * 输入： mountainHeight = 4, workerTimes = [2,1,1]
     * 输出： 3
     * 将山的高度降低到 0 的一种方式是：
     *
     * 工人 0 将高度降低 1，花费 workerTimes[0] = 2 秒。
     * 工人 1 将高度降低 2，花费 workerTimes[1] + workerTimes[1] * 2 = 3 秒。
     * 工人 2 将高度降低 1，花费 workerTimes[2] = 1 秒。
     * 因为工人同时工作，所需的最少时间为 max(2, 3, 1) = 3 秒。
     * [1, 1, 2]
     * 1 + 2 + 3 + 4 = 10s;
     * left = 1, right = 10, mid = 5
     * 2 + 2 + 2 = 6 > mountainHeight
     * left = 1, right = 5, mid = 3
     * 2 + 2 + 1 = 5 > mountainHeight
     * left = 1, right = 3, mid = 2
     * 1 + 1 + 1 = 3 < mountainHeight
     * left = 3, right = 3, break
     *
     * AC:50ms/44.43MB
     * 将isEnoughHeight() 优化为checkHeight()
     * AC:15ms/44.43MB
     * ]
     * @param mountainHeight
     * @param workerTimes
     * @return long
     * @author marks
     * @CreateDate: 2024/11/22 10:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int mountainHeight, int[] workerTimes) {
        Arrays.sort(workerTimes);
        int min = workerTimes[0];
        long left = 1;
        long right = 0;
        long tempHeight = mountainHeight;
        while (tempHeight > 0) {
            right += tempHeight * min;
            tempHeight--;
        }

        while (left < right) {
            long mid = (right - left) / 2 + left;
//            if (isEnoughHeight(workerTimes, mid, mountainHeight)){
            if (checkHeight(workerTimes, mid, mountainHeight)){
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    /**
     * @Description: [该方法可以优化成数学方式
     * 设 t = workerTimes[i], 总计花费 m 秒, 下降高度设为x
     * t + t * 2 +.... + t * x = m
     * t * (1 + 2 + ... + x) = m
     * t * ((1 + x) * x / 2) = m
     * 设 k = m / t
     * x^2 + x - 2k = 0, 求这个一元二次方程的解
     * 详情查看方法checkHeight
     *
     * ]
     * @param workerTimes
     * @param totalTime
     * @param mountainHeight
     * @return boolean
     * @author marks
     * @CreateDate: 2024/11/22 11:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean isEnoughHeight(int[] workerTimes, long totalTime, int mountainHeight) {
        int height = 0;
        for (int i = 0; i < workerTimes.length; i++) {
            long time = totalTime;
            int tempHeight = 0;
            while (time - (long) workerTimes[i] * (tempHeight + 1) >= 0) {
                tempHeight++;
                time = time - (long) workerTimes[i] * tempHeight;
            }
            height += tempHeight;
            if (height >= mountainHeight) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Description: [
     * 设 t = workerTimes[i], 总计花费 m 秒, 设 k = m / t
     * x^2 + x - 2k = 0
     * 根据公式可得两个解分别是x1, x2
     * x1 = (-1 + Math.sqrt(1 - 4 * 1 * (-2k))) / 2 = (Math.sqrt(8k + 1) - 1) / 2
     * ]
     * @param workerTimes
     * @param totalTime
     * @param mountainHeight
     * @return boolean
     * @author marks
     * @CreateDate: 2024/11/22 14:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean checkHeight(int[] workerTimes, long totalTime, int mountainHeight) {
        for (int i = 0; i < workerTimes.length; i++) {
            long k = totalTime / workerTimes[i];
            mountainHeight -= (int) (Math.sqrt(8 * k + 1) - 1) / 2;
            if (mountainHeight <= 0) {
                return true;
            }
        }
        return false;
    }

}
