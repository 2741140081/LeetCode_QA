package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1578 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/25 15:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1578 {

    /**
     * @Description:
     * Alice 把 n 个气球排列在一根绳子上。
     * 给你一个下标从 0 开始的字符串 colors ，其中 colors[i] 是第 i 个气球的颜色。
     *
     * Alice 想要把绳子装扮成 五颜六色的 ，且她不希望两个连续的气球涂着相同的颜色，所以她喊来 Bob 帮忙。
     * Bob 可以从绳子上移除一些气球使绳子变成 彩色 。
     * 给你一个 下标从 0 开始 的整数数组 neededTime ，其中 neededTime[i] 是 Bob 从绳子上移除第 i 个气球需要的时间（以秒为单位）。
     *
     * 返回 Bob 使绳子变成 彩色 需要的 最少时间
     * @param: colors
     * @param: neededTime
     * @return int
     * @author marks
     * @CreateDate: 2025/11/25 15:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minCost(String colors, int[] neededTime) {
        int result;
        result = method_01(colors, neededTime);
        return result;
    }

    /**
     * @Description:
     * 1. int count = 0; 记录当前颜色气球的个数, sum = 0; 记录当前颜色气球总耗时, min = Math.min(neededTime[i~j]); 存储当前颜色气球的最小耗时
     * 2. int[] dp = new int[colors.length()];
     * AC: 5ms/92.36MB
     * @param: colors
     * @param: neededTime
     * @return int
     * @author marks
     * @CreateDate: 2025/11/25 15:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String colors, int[] neededTime) {
        char[] array = colors.toCharArray();
        int count = 1;
        int sum = neededTime[0];
        int max = neededTime[0];
        int ans = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] == array[i - 1]) {
                count++;
                sum += neededTime[i];
                max = Math.max(max, neededTime[i]);
            } else {
                if (count > 1) {
                    ans += sum - max;
                }
                sum = neededTime[i];
                max = neededTime[i];
                count = 1;
            }
        }
        if (count > 1) {
            ans += sum - max;
        }
        return ans;
    }

}
