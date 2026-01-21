package com.marks.leetcode.daily_question;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2943 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/15 15:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2943 {

    /**
     * @Description:
     * 给你两个整数 n 和 m，以及两个整数数组 hBars 和 vBars。
     * 网格由 n + 2 条水平线和 m + 2 条竖直线组成，形成 1x1 的单元格。网格中的线条从 1 开始编号。
     * 你可以从 hBars 中 删除 一些水平线条，并从 vBars 中删除一些竖直线条。注意，其他线条是固定的，无法删除。
     * 返回一个整数表示移除一些线条（可以不移除任何线条）后，网格中 正方形空洞的最大面积 。
     * @param: n
     * @param: m
     * @param: hBars
     * @param: vBars
     * @return int
     * @author marks
     * @CreateDate: 2026/01/15 15:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximizeSquareHoleArea(int n, int m, int[] hBars, int[] vBars) {
        int result;
//        result = method_01(n, m, hBars, vBars);
        result = method_02(n, m, hBars, vBars);
        return result;
    }

    /**
     * @Description:
     * AC: 5ms/44.61MB
     * @param: n
     * @param: m
     * @param: hBars
     * @param: vBars
     * @return int
     * @author marks
     * @CreateDate: 2026/01/15 15:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n, int m, int[] hBars, int[] vBars) {
        // 对 hBars 和 vBars 进行排序, 找到最大连续的长度
        Arrays.sort(hBars);
        Arrays.sort(vBars);
        int r = hBars[0] != 1 ? 2 : 1;
        int maxH = r;
        for (int i = 1; i < hBars.length; i++) {
            if (hBars[i] - hBars[i - 1] == 1 && hBars[i] != n + 2) {
                r++;
                maxH = Math.max(maxH, r);
            } else {
                r = 2;
            }
        }

        int c = vBars[0] != 0 ? 2 : 1;
        int maxV = c;
        for (int i = 1; i < vBars.length; i++) {
            if (vBars[i] - vBars[i - 1] == 1 && vBars[i] != m + 2) {
                c++;
                maxV = Math.max(maxV, c);
            } else {
                c = 2;
            }
        }
        return Math.min(maxH, maxV) * Math.min(maxH, maxV);
    }

    /**
     * @Description:
     * 1. 贪心的来讲, 把所有的 hBars 和 vBars 进行删除, 然后找到最大的正方形空洞
     * 2. 另外如果需要构成正方形, 不能删除最外侧的边, 即 边1 和 边n + 2/m + 2
     * 3. 分别用 row 和 col 来记录 横向和竖向的边的最大值
     * 超时! 503/524
     * @param: n
     * @param: m
     * @param: hBars
     * @param: vBars
     * @return int
     * @author marks
     * @CreateDate: 2026/01/15 15:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int m, int[] hBars, int[] vBars) {
        Set<Integer> row = new HashSet<>();
        Set<Integer> col = new HashSet<>();
        // 遍历 hBars
        for (int i = 0; i < hBars.length; i++) {
            row.add(hBars[i] - 1);
        }
        for (int i = 0; i < vBars.length; i++) {
            col.add(vBars[i] - 1);
        }
        // 给边界重新添加
        row.remove(0);
        row.remove(n + 1);
        col.remove(0);
        col.remove(m + 1);
        int r = 1, c = 1; // 记录最大长度
        int maxR = 1, maxC = 1;
        for (int i = 1; i <= n; i++) {
            if (row.contains(i)) {
                r++;
                maxR = Math.max(maxR, r);
            } else {
                r = 1;
            }
        }
        for (int i = 1; i <= m; i++) {
            if (col.contains(i)) {
                c++;
                maxC = Math.max(maxC, c);
            } else {
                c = 1;
            }
        }
        return Math.min(maxR, maxC) * Math.min(maxR, maxC);
    }

}
