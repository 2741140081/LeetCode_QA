package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_789 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/7 14:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_789 {

    /**
     * @Description:
     * 你在进行一个简化版的吃豆人游戏。你从 [0, 0] 点开始出发，你的目的地是 target = [xtarget, ytarget] 。
     * 地图上有一些阻碍者，以数组 ghosts 给出，第 i 个阻碍者从 ghosts[i] = [xi, yi] 出发。所有输入均为 整数坐标 。
     * 每一回合，你和阻碍者们可以同时向东，西，南，北四个方向移动，每次可以移动到距离原位置 1 个单位 的新位置。当然，也可以选择 不动 。所有动作 同时 发生。
     * 如果你可以在任何阻碍者抓住你 之前 到达目的地（阻碍者可以采取任意行动方式），则被视为逃脱成功。如果你和阻碍者 同时 到达了一个位置（包括目的地） 都不算 是逃脱成功。
     * 如果不管阻碍者怎么移动都可以成功逃脱时，输出 true ；否则，输出 false 。
     *
     * tips:
     * 1 <= ghosts.length <= 100
     * ghosts[i].length == 2
     * -10^4 <= xi, yi <= 10^4
     * 同一位置可能有 多个阻碍者 。
     * target.length == 2
     * -10^4 <= xtarget, ytarget <= 10^4
     * @param: ghosts
     * @param: target
     * @return boolean
     * @author marks
     * @CreateDate: 2026/05/07 14:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean escapeGhosts(int[][] ghosts, int[] target) {
        boolean result;
        result = method_01(ghosts, target);
        return result;
    }

    /**
     * @Description:
     * 1. 计算起始点(0, 0) 到达 目标点(xTarget, yTarget) 的距离, int dist = Math.abs(xTarget - 0) + Math.abs(yTarget - 0);
     * 2. 遍历所有的阻碍者, 计算阻碍者到达目标点的距离, int dist = Math.abs(xTarget - xi) + Math.abs(yTarget - yi);
     * 3. 遍历所有的阻碍者, 如果有阻碍者距离小于起始点距离, 则返回false
     * AC: 0ms/43.28MB
     * @param: ghosts
     * @param: target
     * @return boolean
     * @author marks
     * @CreateDate: 2026/05/07 14:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[][] ghosts, int[] target) {
        int minDist = Math.abs(target[0]) + Math.abs(target[1]);
        for (int[] ghost : ghosts) {
            int dist = Math.abs(target[0] - ghost[0]) + Math.abs(target[1] - ghost[1]);
            if (dist <= minDist) {
                return false;
            }
        }

        return true;
    }

}
