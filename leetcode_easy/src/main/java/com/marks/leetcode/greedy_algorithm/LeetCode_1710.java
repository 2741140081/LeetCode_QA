package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/26 9:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1710 {
    /**
     * @Description:
     * 请你将一些箱子装在 一辆卡车 上。给你一个二维数组 boxTypes ，其中 boxTypes[i] = [numberOfBoxesi, numberOfUnitsPerBoxi] ：
     *
     * numberOfBoxesi 是类型 i 的箱子的数量。
     * numberOfUnitsPerBoxi 是类型 i 每个箱子可以装载的单元数量。
     * 整数 truckSize 表示卡车上可以装载 箱子 的 最大数量 。只要箱子数量不超过 truckSize ，你就可以选择任意箱子装到卡车上。
     *
     * 返回卡车可以装载 单元 的 最大 总数。
     * @param boxTypes
     * @param truckSize
     * @return int
     * @author marks
     * @CreateDate: 2025/3/26 9:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        int result;
        result = method_01(boxTypes, truckSize);
        return result;
    }

    /**
     * @Description:
     * AC: 8ms/44.31MB
     * @param boxTypes
     * @param truckSize
     * @return int
     * @author marks
     * @CreateDate: 2025/3/26 9:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] boxTypes, int truckSize) {
        Arrays.sort(boxTypes, (o1, o2) -> o2[1] - o1[1]);
        int ans = 0;

        for (int[] boxType : boxTypes) {
            if (truckSize > 0) {
                int count = boxType[0];
                int num = boxType[1];
                if (truckSize >= count) {
                    ans += count * num;
                } else {
                    ans += truckSize * num;
                }
                truckSize -= count;
            }
        }

        return ans;
    }
}
