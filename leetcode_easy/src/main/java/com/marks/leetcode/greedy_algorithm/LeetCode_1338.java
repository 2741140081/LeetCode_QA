package com.marks.leetcode.greedy_algorithm;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/25 18:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1338 {
    /**
     * @Description:
     * 给你一个整数数组 arr。你可以从中选出一个整数集合，并删除这些整数在数组中的每次出现。
     *
     * 返回 至少 能删除数组中的一半整数的整数集合的最小大小。
     *
     * tips:
     * 1 <= arr.length <= 10^5
     * arr.length 为偶数
     * 1 <= arr[i] <= 10^5
     * @param arr
     * @return int
     * @author marks
     * @CreateDate: 2025/3/25 18:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minSetSize(int[] arr) {
        int result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * AC: 39ms/57.76MB
     * @param arr
     * @return int
     * @author marks
     * @CreateDate: 2025/3/25 18:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {
        int n = arr.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            map.merge(num, 1, Integer::sum);
        }
        List<Map.Entry<Integer, Integer>> list = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((o1, o2) -> o2 - o1))
                .collect(Collectors.toList());

        int target = n / 2;
        int ans = 0;
        for (Map.Entry<Integer, Integer> entry : list) {
            if (target > 0) {
                target -= entry.getValue();
                ans++;
            }
        }
        return ans;
    }
}
