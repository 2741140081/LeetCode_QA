package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1465 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/28 16:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1465 {

    /**
     * @Description:
     * 矩形蛋糕的高度为 h 且宽度为 w，给你两个整数数组 horizontalCuts 和 verticalCuts，其中：
     *  horizontalCuts[i] 是从矩形蛋糕顶部到第  i 个水平切口的距离
     * verticalCuts[j] 是从矩形蛋糕的左侧到第 j 个竖直切口的距离
     * 请你按数组 horizontalCuts 和 verticalCuts 中提供的水平和竖直位置切割后，请你找出 面积最大 的那份蛋糕，并返回其 面积 。
     * 由于答案可能是一个很大的数字，因此需要将结果 对 10^9 + 7 取余 后返回。
     *
     * tips:
     * 2 <= h, w <= 10^9
     * 1 <= horizontalCuts.length <= min(h - 1, 10^5)
     * 1 <= verticalCuts.length <= min(w - 1, 10^5)
     * 1 <= horizontalCuts[i] < h
     * 1 <= verticalCuts[i] < w
     * 题目数据保证 horizontalCuts 中的所有元素各不相同
     * 题目数据保证 verticalCuts 中的所有元素各不相同
     * @param: h
     * @param: w
     * @param: horizontalCuts
     * @param: verticalCuts
     * @return int
     * @author marks
     * @CreateDate: 2026/07/28 16:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        int result;
        result = method_01(h, w, horizontalCuts, verticalCuts);
        return result;
    }

    /**
     * @Description:
     * 1. 通过 horizontalCuts 可以获取最大高度 highMax, 以及通过 verticalCuts 可以获取最大宽度 widthMax
     * 2. 最大面积 area = (highMax * widthMax) % MOD
     * @param: h
     * @param: w
     * @param: horizontalCuts
     * @param: verticalCuts
     * @return int
     * @author marks
     * @CreateDate: 2026/07/28 16:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        int MOD = (int) 1e9 + 7;
        long highMax = 0, widthMax = 0;
        // 排序对数组
        Arrays.sort(horizontalCuts);
        Arrays.sort(verticalCuts);
        int prev = 0;
        for (int horizontalCut : horizontalCuts) {
            highMax = Math.max(highMax, horizontalCut - prev);
            prev = horizontalCut;
        }
        // 末尾
        highMax = Math.max(highMax, h - prev);
        // 重置 prev
        prev = 0;
        for (int verticalCut : verticalCuts) {
            widthMax = Math.max(widthMax, verticalCut - prev);
            prev = verticalCut;
        }
        // 末尾
        widthMax = Math.max(widthMax, w - prev);
        return (int) ((highMax * widthMax) % MOD); // 特别注意 (int) (highMax * widthMax) % MOD, 这种情况下会先进行 (int) 即转成 int, 然后进行 mod 运算
    }

}
