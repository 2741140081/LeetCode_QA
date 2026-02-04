package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1402 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/4 10:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1402 {

    /**
     * @Description:
     * 一个厨师收集了他 n 道菜的满意程度 satisfaction ，这个厨师做出每道菜的时间都是 1 单位时间。
     * 一道菜的 「 like-time 系数 」定义为烹饪这道菜结束的时间（包含之前每道菜所花费的时间）乘以这道菜的满意程度，也就是 time[i]*satisfaction[i] 。
     * 返回厨师在准备了一定数量的菜肴后可以获得的最大 like-time 系数 总和。
     * 你可以按 任意 顺序安排做菜的顺序，你也可以选择放弃做某些菜来获得更大的总和。
     * tips:
     * n == satisfaction.length
     * 1 <= n <= 500
     * -1000 <= satisfaction[i] <= 1000
     * @param: satisfaction
     * @return int
     * @author marks
     * @CreateDate: 2026/02/04 10:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxSatisfaction(int[] satisfaction) {
        int result;
        result = method_01(satisfaction);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：satisfaction = [-1,-8,0,5,-9]
     * 输出：14
     * 1. 对satisfaction进行排序, 做菜的顺序与下标无关, 然后计算s[i] >= 0的和 preSum, 并且计算初始的 like-time系数(只包含s[i] >= 0) 的 sum
     * 2. 每增加一道 s[i] < 0 的菜品, 例如E1, preSum = 5, sum = 0 * 1 + 5 * 2 = 10
     * 3. 然后添加一道菜品 -1, -1 * 1 + preSum + sum = 10 - 1 + 5 = 14, 并且更新 preSum += s[i](-1), preSum = 4
     * 4. 然后再次添加一道菜品 -8, -8 * 1 + preSum => 直接判断 preSum + s[i] >= 0
     * AC: 5ms/43.1MB
     * @param: satisfaction
     * @return int
     * @author marks
     * @CreateDate: 2026/02/04 10:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] satisfaction) {
        int ans = 0;
        int preSum = 0; // 存储当时间增加1时, 会增加的 like-time系数
        Arrays.sort(satisfaction);
        int n = satisfaction.length;
        for (int i = n - 1; i >= 0; i--) {
            if (satisfaction[i] + preSum >= 0) {
                ans += satisfaction[i] + preSum;
                preSum += satisfaction[i];
            }
        }
        return ans;
    }

}
