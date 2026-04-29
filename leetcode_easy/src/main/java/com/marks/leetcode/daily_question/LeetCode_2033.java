package com.marks.leetcode.daily_question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2033 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/28 11:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2033 {

    /**
     * @Description:
     * 给你一个大小为 m x n 的二维整数网格 grid 和一个整数 x 。每一次操作，你可以对 grid 中的任一元素 加 x 或 减 x 。
     * 单值网格 是全部元素都相等的网格。
     * 返回使网格化为单值网格所需的 最小 操作数。如果不能，返回 -1 。
     *
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 10^5
     * 1 <= m * n <= 10^5
     * 1 <= x, grid[i][j] <= 10^4
     * @param: grid
     * @param: x
     * @return int
     * @author marks
     * @CreateDate: 2026/04/28 11:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minOperations(int[][] grid, int x) {
        int result;
        result = method_01(grid, x);
        return result;
    }

    /**
     * @Description:
     * 1. grid所有元素对 x 取余, 如果余数结果不一致，则返回-1
     * 2. 使用map 记录key 和对应的数量, 使用list 存储 key
     * 3. 计算前缀和, 即将list 中前 i 个数提高到 list.get(i) 所需的操作数, 并且可以用dp来处理
     * AC: 64ms/112.1MB
     * 4. 看题解后, 需要改进, 不需要使用前缀和和后缀和, 只需要找到 list 的中位数即可
     * @param: grid
     * @param: x
     * @return int
     * @author marks
     * @CreateDate: 2026/04/28 11:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid, int x) {
        int n = grid.length;
        int m = grid[0].length;
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        int mod = grid[0][0] % x;
        for (int[] ints : grid) {
            for (int j = 0; j < m; j++) {
                int temp = ints[j] % x;
                if (temp != mod) {
                    return -1;
                }
                if (map.containsKey(ints[j])) {
                    map.put(ints[j], map.get(ints[j]) + 1);
                } else {
                    map.put(ints[j], 1);
                    list.add(ints[j]);
                }
            }
        }
        // 对 list 进行排序
        list.sort(Integer::compareTo);
        int len = list.size();
        // 前缀和
        int[] prevSum = new int[len];
        prevSum[0] = 0;
        int count = map.get(list.get(0));
        for (int i = 1; i < len; i++) {
            int sub = (list.get(i) - list.get(i - 1)) / x;
            prevSum[i] = prevSum[i - 1] + count * sub;
            count += map.get(list.get(i));
        }
        // 后缀和
        int[] suffixSum = new int[len];
        suffixSum[len - 1] = 0;
        count = map.get(list.get(len - 1));
        for (int i = len - 2; i >= 0; i--) {
            int sub = (list.get(i + 1) - list.get(i)) / x;
            suffixSum[i] = suffixSum[i + 1] + count * sub;
            count += map.get(list.get(i));
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < len; i++) {
            ans = Math.min(ans, prevSum[i] + suffixSum[i]);
        }


        return ans;
    }

}
