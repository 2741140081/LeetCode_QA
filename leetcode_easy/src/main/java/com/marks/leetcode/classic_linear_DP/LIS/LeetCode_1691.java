package com.marks.leetcode.classic_linear_DP.LIS;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/23 10:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1691 {
    /**
     * @Description: [
     * 给你 n 个长方体 cuboids ，其中第 i 个长方体的长宽高表示为 cuboids[i] = [widthi, lengthi, heighti]（下标从 0 开始）。
     * 请你从 cuboids 选出一个 子集 ，并将它们堆叠起来。
     * 如果 widthi <= widthj 且 lengthi <= lengthj 且 heighti <= heightj ，你就可以将长方体 i 堆叠在长方体 j 上。
     * 你可以通过旋转把长方体的长宽高重新排列，以将它放在另一个长方体上。
     * 返回 堆叠长方体 cuboids 可以得到的 最大高度
     *
     * tips:
     * n == cuboids.length
     * 1 <= n <= 100
     * 1 <= widthi, lengthi, heighti <= 100
     *
     * ]
     * @param cuboids
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 10:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxHeight(int[][] cuboids) {
        int result = 0;
        result = method_01(cuboids);
        return result;
    }

    /**
     * @Description: [
     * cuboids = [[50,45,20],[95,37,53],[45,23,12]]
     * 第 1 个长方体放在底部，53x37 的一面朝下，高度为 95 。
     * 第 0 个长方体放在中间，45x20 的一面朝下，高度为 50 。
     * 第 2 个长方体放在上面，23x12 的一面朝下，高度为 45 。
     *
     * 根据答案退结果可知,
     * 1. 首先对每一个cuboids[i]元素进行排序, 将长宽高的升序排序
     * 2. 让后对整体cuboids进行长宽高排序
     * ]
     * @param cuboids
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 10:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] cuboids) {
        int n = cuboids.length;
        for (int i = 0; i < n; i++) {
            Arrays.sort(cuboids[i]);
        }
        if (n == 1) {
            return cuboids[0][2];
        }
        Arrays.sort(cuboids, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[2] != o2[2]) {
                    return o1[2] - o2[2];
                }else {
                    if (o1[1] != o2[1]) {
                        return o1[1] - o2[1];
                    }else {
                        return o1[0] - o2[0];
                    }
                }
            }
        });
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = cuboids[i][2];
            for (int j = 0; j < i; j++) {
                if (cuboids[i][2] >= cuboids[j][2] && cuboids[i][1] >= cuboids[j][1] &&
                        cuboids[i][0] >= cuboids[j][0]) {
                    dp[i] = Math.max(dp[i], dp[j] + cuboids[i][2]);
                }
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }
}
