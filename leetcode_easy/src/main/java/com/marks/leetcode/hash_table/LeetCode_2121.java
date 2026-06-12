package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2121 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/9 10:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2121 {

    /**
     * @Description:
     * 给你一个下标从 0 开始、由 n 个整数组成的数组 arr 。
     * arr 中两个元素的 间隔 定义为它们下标之间的 绝对差 。
     * 更正式地，arr[i] 和 arr[j] 之间的间隔是 |i - j| 。
     * 返回一个长度为 n 的数组 intervals ，其中 intervals[i] 是 arr[i] 和 arr 中每个相同元素（与 arr[i] 的值相同）的 间隔之和 。
     * 注意：|x| 是 x 的绝对值。
     *
     * tips:
     * n == arr.length
     * 1 <= n <= 10^5
     * 1 <= arr[i] <= 10^5
     * @param: arr
     * @return long[]
     * @author marks
     * @CreateDate: 2026/06/09 10:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long[] getDistances(int[] arr) {
        long[] result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * 1. 使用前缀和, 并且使用 map 记录相同元素的下标和作为前缀和, 同理记录后缀和
     * 2. prefixMap<Integer, Pair> 记录相同元素的前缀和, 当处理到 i 时,  如果 map.containsKey(arr[i])
     * Pair pair = map.get(arr[i]); pair 包含两个数 long sum, int count, 当前元素与前 count 个元素间隔之和
     * ans[i] = i * count - sum, 然后更新sum, count
     * AC: 52ms/161.15MB
     * @param: arr
     * @return long[]
     * @author marks
     * @CreateDate: 2026/06/09 10:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long[] method_01(int[] arr) {
        int n = arr.length;
        long[] ans = new long[n];
        Map<Integer, Pair> prefixMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (prefixMap.containsKey(arr[i])) {
                Pair pair = prefixMap.get(arr[i]);
                ans[i] = i * pair.count - pair.sum;
                pair.sum += i;
                pair.count++;
            } else {
                prefixMap.put(arr[i], new Pair(i, 1L));
            }
        }
        Map<Integer, Pair> suffixMap = new HashMap<>();
        for (int i = n - 1; i >= 0; i--) {
            if (suffixMap.containsKey(arr[i])) {
                Pair pair = suffixMap.get(arr[i]);
                ans[i] += pair.sum - i * pair.count;
                pair.sum += i;
                pair.count++;
            } else {
                suffixMap.put(arr[i], new Pair(i, 1L));
            }
        }

        return ans;
    }

    private class Pair {
        long sum;
        long count;
        public Pair(long sum, long count) {
            this.sum = sum;
            this.count = count;
        }
    }

}
