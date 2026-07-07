package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_Interview_16_6 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/3 15:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_Interview_16_6 {

    /**
     * @Description:
     * 给定两个整数数组a和b，计算具有最小差绝对值的一对数值（每个数组中取一个值），并返回该对数值的差
     *
     * tips:
     * 1 <= a.length, b.length <= 100000
     * -2147483648 <= a[i], b[i] <= 2147483647
     * 正确结果在区间 [0, 2147483647] 内
     * @param: a
     * @param: b
     * @return int
     * @author marks
     * @CreateDate: 2026/07/03 15:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int smallestDifference(int[] a, int[] b) {
        int result;
        result = method_01(a, b);
        return result;
    }

    /**
     * @Description:
     * 1. 对 a[] 进行排序
     * 2. 遍历 b, 对于 b[i], 找到 a[] 二分插入的位置, Arrays.binarySearch(), 返回下标 j,
     * 如果 j > 0, min = 0, 直接返回; 如果 j < 0, idx = -j - 1; 比较前后 a[idx - 1] - b[i] 和 a[idx] - b[i], 取最小值, 返回;
     * 3. 使用 long min = Long.MAX_VALUE; (防止发生数组溢出),
     * AC: 32ms/56.41MB
     * @param: a
     * @param: b
     * @return int
     * @author marks
     * @CreateDate: 2026/07/03 15:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] a, int[] b) {
        Arrays.sort(a);

        long min = Long.MAX_VALUE;

        for (int num : b) {
            int j = Arrays.binarySearch(a, num);

            if (j >= 0) {
                return 0;
            }

            int idx = -j - 1;

            if (idx < a.length) {
                min = Math.min(min, Math.abs((long) a[idx] - num));
            }

            if (idx > 0) {
                min = Math.min(min, Math.abs((long) a[idx - 1] - num));
            }
        }

        return (int) min;
    }

}
