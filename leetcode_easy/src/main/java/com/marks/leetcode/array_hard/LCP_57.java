package com.marks.leetcode.array_hard;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_57 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/3 16:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_57 {

    /**
     * @Description:
     * 欢迎各位勇者来到力扣城，本次试炼主题为「打地鼠」。
     * 勇者面前有一个大小为 3*3 的打地鼠游戏机，地鼠将随机出现在各个位置，
     * moles[i] = [t,x,y] 表示在第 t 秒会有地鼠出现在 (x,y) 位置上，并于第 t+1 秒该地鼠消失。
     * 勇者有一把可敲打地鼠的锤子，初始时刻（即第 0 秒）锤子位于正中间的格子 (1,1)，锤子的使用规则如下：
     * 锤子每经过 1 秒可以往上、下、左、右中的一个方向移动一格，也可以不移动
     * 锤子只可敲击所在格子的地鼠，敲击不耗时
     * 请返回勇者最多能够敲击多少只地鼠。
     * 注意：
     * 输入用例保证在相同时间相同位置最多仅有一只地鼠
     * tips:
     * 1 <= moles.length <= 10^5
     * moles[i].length == 3
     * 0 <= moles[i][0] <= 10^9
     * 0 <= moles[i][1], moles[i][2] < 3
     * @param: moles
     * @return int
     * @author marks
     * @CreateDate: 2026/09/03 16:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int getMaximumNumber(int[][] moles) {
        int result;
        result = method_01(moles);
        return result;
    }


    /**
     * @Description:
     * 1. 应该是动态规划
     * 2. 先对 moles 按时间排序, 并且两个坐标点之间的移动的时间等于曼哈顿距离
     * 3.
     * @param: moles
     * @return int
     * @author marks
     * @CreateDate: 2026/09/03 16:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] moles) {

        return 0;
    }

}
