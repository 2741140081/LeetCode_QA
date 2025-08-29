package com.marks.leetcode.backtrack;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/29 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_473 {


    /**
     * @Description:
     * 你将得到一个整数数组 matchsticks ，其中 matchsticks[i] 是第 i 个火柴棒的长度。你要用 所有的火柴棍 拼成一个正方形。
     * 你 不能折断 任何一根火柴棒，但你可以把它们连在一起，而且每根火柴棒必须 使用一次 。
     *
     * 如果你能使这个正方形，则返回 true ，否则返回 false 。
     * tips:
     * 1 <= matchsticks.length <= 15
     * 1 <= matchsticks[i] <= 10^8
     * @param matchsticks
     * @return boolean
     * @author marks
     * @CreateDate: 2025/8/29 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean makesquare(int[] matchsticks) {
        boolean result;
        result = method_01(matchsticks);
        return result;
    }


    /**
     * @Description:
     * E1:
     * 输入：matchsticks = [1,1,2,2,2]
     * 输出：true
     * 1. sum(matchsticks) % 4 != 0, return false; 即使 sum % 4 == 0, 也不一定有解
     * 2. 使用回溯 + 递归, 可以直接构造 int[] len = new int[4]; 记录4个边长
     * @param matchsticks
     * @return boolean
     * @author marks
     * @CreateDate: 2025/8/29 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private boolean ans;
    private int target; // 目标边长
    private boolean method_01(int[] matchsticks) {
        int sum = Arrays.stream(matchsticks).sum();
        if (sum % 4 != 0 || matchsticks.length < 4) {
            return false;
        }
        target = sum / 4;
        ans = false;
        int[] len = new int[4]; // 记录4个边长
        return ans;
    }

}
