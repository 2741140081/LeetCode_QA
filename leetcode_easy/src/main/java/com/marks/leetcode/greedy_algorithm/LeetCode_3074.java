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
 * @date 2025/3/25 17:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3074 {

    /**
     * @Description:
     * 给你一个长度为 n 的数组 apple 和另一个长度为 m 的数组 capacity 。
     *
     * 一共有 n 个包裹，其中第 i 个包裹中装着 apple[i] 个苹果。同时，还有 m 个箱子，第 i 个箱子的容量为 capacity[i] 个苹果。
     *
     * 请你选择一些箱子来将这 n 个包裹中的苹果重新分装到箱子中，返回你需要选择的箱子的 最小 数量。
     *
     * 注意，同一个包裹中的苹果可以分装到不同的箱子中。
     * @param apple
     * @param capacity
     * @return int
     * @author marks
     * @CreateDate: 2025/3/25 17:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumBoxes(int[] apple, int[] capacity) {
        int result;
        result = method_01(apple, capacity);
        return result;
    }

    /**
     * @Description:
     * AC: 3ms/41.54MB
     * @param apple 
     * @param capacity
     * @return int
     * @author marks
     * @CreateDate: 2025/3/25 17:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] apple, int[] capacity) {
        int count = Arrays.stream(apple).sum();
//        int maxSize = Arrays.stream(capacity).sum();
//        if (count > maxSize) {
//            return -1;
//        }
        Arrays.sort(capacity);
        int ans = 0;
        for (int i = capacity.length - 1; i >= 0; i--) {
            if (count > 0) {
                count -= capacity[i];
                ans++;
            }
        }

        return ans;
    }
}
