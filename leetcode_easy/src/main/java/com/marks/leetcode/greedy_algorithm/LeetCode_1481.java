package com.marks.leetcode.greedy_algorithm;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/25 17:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1481 {
    /**
     * @Description:
     * 给你一个整数数组 arr 和一个整数 k 。现需要从数组中恰好移除 k 个元素，请找出移除后数组中不同整数的最少数目。
     * @param arr 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/3/25 17:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        int result;
        result = method_01(arr, k);
        return result;
    }


    /**
     * @Description:
     * AC: 54ms/55.35MB
     * @param arr 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/25 17:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            map.merge(i, 1, Integer::sum);
        }
        int ans = map.size();
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        for (Map.Entry<Integer, Integer> entry : list) {
            if (k >= entry.getValue()) {
                k -= entry.getValue();
                ans--;
            }
        }
        return ans;
    }
}
