package com.marks.leetcode.data_structure.stack;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_769 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/13 14:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_769 {

    /**
     * @Description:
     * 给定一个长度为 n 的整数数组 arr ，它表示在 [0, n - 1] 范围内的整数的排列。
     * 我们将 arr 分割成若干 块 (即分区)，并对每个块单独排序。将它们连接起来后，使得连接的结果和按升序排序后的原数组相同。
     * 返回数组能分成的最多块数量。
     *
     * tips:
     * n == arr.length
     * 1 <= n <= 10
     * 0 <= arr[i] < n
     * arr 中每个元素都 不同
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/05/13 14:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxChunksToSorted(int[] arr) {
        int result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * 1. 假设能够分成 x 个块, 那么最后一个块[i, n - 1] 所包含的元素范围是 [i, n - 1]
     * 2. 这种模拟的方式可以使用回溯来进行处理.
     * 3. 按照正向处理, 假设[0 ~ i] 可以构成一个块, 则这个块的数据范围是 [0 ~ i]
     * AC: 0ms/41.92MB
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/05/13 14:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {
        int m = 0, res = 0;
        for (int i = 0; i < arr.length; i++) {
            m = Math.max(m, arr[i]);
            if (m == i) {
                res++;
            }
        }
        return res;
    }

}
