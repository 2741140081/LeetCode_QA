package com.marks.leetcode.DP;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1218 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/26 10:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1218 {

    /**
     * @Description:
     * 给你一个整数数组 arr 和一个整数 difference，请你找出并返回 arr 中最长等差子序列的长度，该子序列中相邻元素之间的差等于 difference 。
     * 子序列 是指在不改变其余元素顺序的情况下，通过删除一些元素或不删除任何元素而从 arr 派生出来的序列。
     * tips:
     * 1 <= arr.length <= 10^5
     * -10^4 <= arr[i], difference <= 10^4
     * @param: arr
     * @param: difference
     * @return int
     * @author marks
     * @CreateDate: 2026/02/26 10:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestSubsequence(int[] arr, int difference) {
        int result;
        result = method_01(arr, difference);
        return result;
    }

    /**
     * @Description:
     * 1. int prev = arr[i] - difference; prev 可以存储到 map 中， 存储的格式为 prev -> count
     * 2. map.getOrDefault(prev, 0) + 1
     * AC: 51ms/76.95MB
     * @param: arr
     * @param: difference
     * @return int
     * @author marks
     * @CreateDate: 2026/02/26 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr, int difference) {
        int ans = 1;
        Map<Integer, Integer> map = new HashMap<>();
        for (int j : arr) {
            int prev = j - difference;
            map.put(j, map.getOrDefault(prev, 0) + 1);
            ans = Math.max(ans, map.get(j));
        }
        return ans;
    }


}
