package com.marks.leetcode.binary_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/20 15:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1011 {
    
    /**
     * @Description: [
     * 传送带上的包裹必须在 days 天内从一个港口运送到另一个港口。
     *
     * 传送带上的第 i 个包裹的重量为 weights[i]。每一天，我们都会按给出重量（weights）的顺序往传送带上装载包裹。
     * 我们装载的重量不会超过船的最大运载重量。
     *
     * 返回能在 days 天内将传送带上的所有包裹送达的船的最低运载能力。
     *
     * tips:
     * 1 <= days <= weights.length <= 5 * 10^4
     * 1 <= weights[i] <= 500
     * ]
     * @param weights 
     * @param days 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/20 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int shipWithinDays(int[] weights, int days) {
        int result;
        result = method_01(weights, days);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：weights = [3,2,2,4,1,4], days = 3
     * 输出：6
     * 解释：
     * 船舶最低载重 6 就能够在 3 天内送达所有包裹，如下所示：
     * 第 1 天：3, 2
     * 第 2 天：2, 4
     * 第 3 天：1, 4
     * pre = [3, 5, 7, 11, 12, 16]
     * left = 1, right = 16, mid = 8, totalDay = 3 = days = 3
     * left = 1, right = 8, mid = 4, totalDay = 5 > days = 3
     * left = 5, right = 8, mid = 6, totalDay = 3 = days = 3
     * left = 5, right = 6, mid = 5, totalDay = 4 > days = 3
     * left = 6, right = 6, break;
     *
     * AC:17ms/46.13MB
     *
     * 优化 getTotalDay() 方法
     * AC:15ms/46.09MB
     * ]
     * @param weights 
     * @param days 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/20 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] weights, int days) {
        int left = Arrays.stream(weights).max().getAsInt();
        int right = Arrays.stream(weights).sum();
        while (left < right) {
            int mid = (right - left) / 2 + left;
            int totalDay = getTotalDay(weights, mid);
            if (totalDay <= days) {
                right = mid;
            }else {
                left = mid + 1;
            }
        }
        return left;
    }

    private int getTotalDay(int[] weights, int maxWeight) {
        int countDay = 1;
        int tempWeight = 0;
        for (int i = 0; i < weights.length; i++) {
            if (tempWeight + weights[i] > maxWeight) {
                countDay++;
                tempWeight = 0;
            }
            tempWeight += weights[i];

        }
        return countDay;
    }
}
