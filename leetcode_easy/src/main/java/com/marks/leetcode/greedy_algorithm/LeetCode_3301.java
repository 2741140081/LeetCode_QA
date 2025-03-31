package com.marks.leetcode.greedy_algorithm;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/28 16:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3301 {
    /**
     * @Description:
     * 给你一个数组 maximumHeight ，其中 maximumHeight[i] 表示第 i 座塔可以达到的 最大 高度。
     *
     * 你的任务是给每一座塔分别设置一个高度，使得：
     *
     * 第 i 座塔的高度是一个正整数，且不超过 maximumHeight[i] 。
     * 所有塔的高度互不相同。
     * 请你返回设置完所有塔的高度后，可以达到的 最大 总高度。如果没有合法的设置，返回 -1
     * @param maximumHeight
     * @return long
     * @author marks
     * @CreateDate: 2025/3/28 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maximumTotalSum(int[] maximumHeight) {
        long result;
        result = method_01(maximumHeight);
        return result;
    }

    /**
     * @Description:
     * AC: 268ms/60.15MB
     * @param maximumHeight 
     * @return long
     * @author marks
     * @CreateDate: 2025/3/28 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] maximumHeight) {
        Arrays.sort(maximumHeight);

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = maximumHeight.length - 1; i >= 0; i--) {
            map.merge(maximumHeight[i], 1, Integer::sum);
        }
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByKey(Comparator.reverseOrder()));

        int token = Integer.MAX_VALUE; // 下一个空置位置的坐标
        long ans = 0;
        for (Map.Entry<Integer, Integer> entry : list) {
            int key = entry.getKey();
            int count = entry.getValue();
            if (key <= token) {
                token = key - count;
                ans += ((long) key + token + 1) * count / 2;
            } else {
                int start = token;
                token = start - count;
                ans += ((long) start + token + 1) * count / 2;
            }
            if (token < 0) {
                return -1;
            }
        }
        return ans;
    }
}
