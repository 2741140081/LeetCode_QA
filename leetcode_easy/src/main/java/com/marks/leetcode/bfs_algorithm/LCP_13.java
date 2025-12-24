package com.marks.leetcode.bfs_algorithm;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_13 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/23 17:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_13 {

    /**
     * @Description:
     * 我们得到了一副藏宝图，藏宝图显示，在一个迷宫中存在着未被世人发现的宝藏。
     *
     * 迷宫是一个二维矩阵，用一个字符串数组表示。它标识了唯一的入口（用 'S' 表示），和唯一的宝藏地点（用 'T' 表示）。
     * 但是，宝藏被一些隐蔽的机关保护了起来。在地图上有若干个机关点（用 'M' 表示），只有所有机关均被触发，才可以拿到宝藏。
     *
     * 要保持机关的触发，需要把一个重石放在上面。迷宫中有若干个石堆（用 'O' 表示），每个石堆都有无限个足够触发机关的重石。
     * 但是由于石头太重，我们一次只能搬一个石头到指定地点。
     *
     * 迷宫中同样有一些墙壁（用 '#' 表示），我们不能走入墙壁。剩余的都是可随意通行的点（用 '.' 表示）。
     * 石堆、机关、起点和终点（无论是否能拿到宝藏）也是可以通行的。
     *
     * 我们每步可以选择向上/向下/向左/向右移动一格，并且不能移出迷宫。搬起石头和放下石头不算步数。
     * 那么，从起点开始，我们最少需要多少步才能最后拿到宝藏呢？如果无法拿到宝藏，返回 -1 。
     * @param: maze
     * @return int
     * @author marks
     * @CreateDate: 2025/12/23 17:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimalSteps(String[] maze) {
        int result;
        result = method_01(maze);
        return result;
    }

    /**
     * @Description:
     * 1. 判断是否可以拿到宝藏, 如果不能拿到宝藏, 返回 -1
     * 2. 创建一个二维数组, 用来记录是否访问过, false 表示未访问, true 表示已访问
     * need todo
     * @param: maze
     * @return int
     * @author marks
     * @CreateDate: 2025/12/23 17:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] maze) {

        return 0;
    }

}
