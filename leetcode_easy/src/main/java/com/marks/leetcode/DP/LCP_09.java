package com.marks.leetcode.DP;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/11 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_09 {

    
    /**
     * @Description:
     * 为了给刷题的同学一些奖励，力扣团队引入了一个弹簧游戏机。游戏机由 N 个特殊弹簧排成一排，编号为 0 到 N-1。
     * 初始有一个小球在编号 0 的弹簧处。
     * 若小球在编号为 i 的弹簧处，通过按动弹簧，可以选择把小球向右弹射 jump[i] 的距离，或者向左弹射到任意左侧弹簧的位置。
     * 也就是说，在编号为 i 弹簧处按动弹簧，小球可以弹向 0 到 i-1 中任意弹簧或者 i+jump[i] 的弹簧（若 i+jump[i]>=N ，则表示小球弹出了机器）。
     * 小球位于编号 0 处的弹簧时不能再向左弹。
     *
     * 为了获得奖励，你需要将小球弹出机器。请求出最少需要按动多少次弹簧，可以将小球从编号 0 弹簧弹出整个机器，即向右越过编号 N-1 的弹簧。
     * tips:
     * 1 <= jump.length <= 10^6
     * 1 <= jump[i] <= 10000
     * @param jump
     * @return int
     * @author marks
     * @CreateDate: 2025/10/11 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minJump(int[] jump) {
        int result;
        result = method_01(jump);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入：jump = [2, 5, 1, 1, 1, 1]
     * 输出：3
     * 1. 差不多明白了, 小球如果向右弹射, 只能弹射在 jump[i] + i 处, 不是 i + 1 ~ jump[i] + i 处,
     * 但是小球向左弹射时, 可以弹射到 0 ~ i-1 处
     * 2. 需要比较向左和向右哪一个弹射的更远,
     * 3. 可能不是越远越好, 因为向右弹射是一个定点弹射, 可能最远的那一个点时最新, 反而中间某一个点是一个较大的值
     * 4. 直接用笨一点的办法, 枚举吧
     * 查看题解后使用动态规划来解决, 从后向前进行动态规划
     *
     * AC: 11ms/126.13MB
     * @param jump 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/11 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] jump) {
        int n = jump.length;
        int[] dp = new int[n];
        dp[n - 1] = 1; // n - 1 位置跳出机械还需要一次跳动
        for (int i = n - 2; i >= 0; i--) {
            // 如果当前位置 jump[i] + i >= n, 表示可以跳出机械, 只需要跳一次即可
            dp[i] = jump[i] + i >= n ? 1 : dp[jump[i] + i] + 1;
            //如果遍历到某dp[j]<dp[i]+1就不需要向右遍历了,因为j到dp.length的值会被当前遍历到的dp[j]更新而不是dp[i]+1
            for(int j = i + 1; j < n && dp[j] >= dp[i] + 1; ++j){
                dp[j] = dp[i] + 1;
            }
        }

        return dp[0];
    }

}
