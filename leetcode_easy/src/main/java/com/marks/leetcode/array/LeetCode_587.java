package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_587 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/30 14:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_587 {

    /**
     * @Description:
     * 给定一个数组 trees，其中 trees[i] = [xi, yi] 表示树在花园中的位置。
     * 你被要求用最短长度的绳子把整个花园围起来，因为绳子很贵。只有把 所有的树都围起来，花园才围得很好。
     * 返回恰好位于围栏周边的树木的坐标
     *
     * tips:
     * 1 <= points.length <= 3000
     * points[i].length == 2
     * 0 <= xi, yi <= 100
     * 所有给定的点都是 唯一 的。
     * @param: trees
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/04/30 14:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] outerTrees(int[][] trees) {
        int[][] result;
        result = method_01(trees);
        return result;
    }

    /**
     * @Description:
     * 1. 有4个点是一定围住的, 分别是最左侧, 最右侧, 最上侧, 最下侧
     * 2. 左侧点和上侧点可以构成一条线, 斜率位 k = (y2 - y1) / (x2 - x1), 判断 点 (x, y) 是在线左边还是右边, 需要对比 k 的大小
     * (y2 - y1)/(x2 - x1) 与 (y2 - y) / (x2 - x) 大小 => (y2 - y1) * (x2 - x) - (y2 - y) * (x2 - x1)
     * 3. 不对, 不能这样处理, 还是需要进行排序才行
     * @param: trees
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/04/30 14:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int[][] trees) {

        return null;
    }

}
