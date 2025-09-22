package com.marks.leetcode.bitwise_operation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/22 16:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2857 {

    /**
     * @Description:
     * 给你一个 二维 整数数组 coordinates 和一个整数 k ，
     * 其中 coordinates[i] = [xi, yi] 是第 i 个点在二维平面里的坐标。
     *
     * 我们定义两个点 (x1, y1) 和 (x2, y2) 的 距离 为 (x1 XOR x2) + (y1 XOR y2) ，XOR 指的是按位异或运算。
     * 请你返回满足 i < j 且点 i 和点 j之间距离为 k 的点对数目。
     *
     * tips:
     * 2 <= coordinates.length <= 50000
     * 0 <= xi, yi <= 10^6
     * 0 <= k <= 100
     * @param coordinates 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/9/22 16:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countPairs(List<List<Integer>> coordinates, int k) {
        int result;
        result = method_01(coordinates, k);
        return result;
    }

    /**
     * @Description:
     * 输入：coordinates = [[1,2],[4,2],[1,3],[5,2]], k = 5
     * 输出：2
     * 解释：以下点对距离为 k ：
     * - (0, 1)：(1 XOR 4) + (2 XOR 2) = 5 。
     * - (2, 3)：(1 XOR 5) + (3 XOR 2) = 5 。
     * 1. k = (x1 ^ x2) + (y1 ^ y2), k = 5, i = (1,2), 这样能找到j吗? j = (x, y)
     * 2. 假设 i 中 1 使得 x 增加/减少 1, 即 x 为奇数, x = x + 1 / x = x - 1
     * 3. 假设 i 中 2 使得 y 增加/减少 2, 即 y 为偶数, y = y + 2 / y = y - 2
     * 4. k = x - 1 + y - 2, x + y = (8, 6, 4, 2)
     * 5. 应该通过 k 的分配来计算, k = (x1 ^ x2) + (y1 ^ y2), [0,5],[1,4],[2,3],[3,2],[4,1],[5,0]
     * 6. 怎么设计这个 key? 直接 String key = x + "_" + y;
     * 暴力遍历
     * AC: 1381ms/71.51MB
     * @param coordinates
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/22 16:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<List<Integer>> coordinates, int k) {
        Map<String, Integer> map = new HashMap<>();

        int ans = 0;
        // int[][2] subs = new int[k + 1][2]
        int[][] subs = new int[k + 1][2];
        for (int i = 0; i <= k; i++) {
            subs[i][0] = i;
            subs[i][1] = k - i;
        }
        for (List<Integer> coordinate : coordinates) {
            int x = coordinate.get(0);
            int y = coordinate.get(1);
            for (int[] sub : subs) {
                int x1 = x ^ sub[0];
                int y1 = y ^ sub[1];
                String key = x1 + "_" + y1;
                if (map.containsKey(key)) {
                    ans += map.get(key);
                }
            }
            String currKey = x + "_" + y;
            map.put(currKey, map.getOrDefault(currKey, 0) + 1);
        }


        return ans;
    }

}
