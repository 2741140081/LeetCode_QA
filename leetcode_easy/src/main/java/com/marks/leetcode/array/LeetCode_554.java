package com.marks.leetcode.array;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_554 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/7 9:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_554 {

    /**
     * @Description:
     * 你的面前有一堵矩形的、由 n 行砖块组成的砖墙。这些砖块高度相同（也就是一个单位高）但是宽度不同。每一行砖块的宽度之和相等。
     * 你现在要画一条 自顶向下 的、穿过 最少 砖块的垂线。如果你画的线只是从砖块的边缘经过，就不算穿过这块砖。你不能沿着墙的两个垂直边缘之一画线，这样显然是没有穿过一块砖的。
     * 给你一个二维数组 wall ，该数组包含这堵墙的相关信息。其中，wall[i] 是一个代表从左至右每块砖的宽度的数组。你需要找出怎样画才能使这条线 穿过的砖块数量最少 ，并且返回 穿过的砖块数量 。
     *
     * tips:
     * n == wall.length
     * 1 <= n <= 10^4
     * 1 <= wall[i].length <= 10^4
     * 1 <= sum(wall[i].length) <= 2 * 10^4
     * 对于每一行 i ，sum(wall[i]) 是相同的
     * 1 <= wall[i][j] <= 2^31 - 1
     * @param: wall
     * @return int
     * @author marks
     * @CreateDate: 2026/05/07 9:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int leastBricks(List<List<Integer>> wall) {
        int result;
        result = method_01(wall);
        return result;
    }

    /**
     * @Description:
     * 1. 使用前缀和, 得到每一行的砖块宽度, 然后统计 preSum_i 的个数, 返回最多一个 preSum_i, preSum_i 是第 i 行的前缀和值
     * 2. preSum_i 越多, 证明在此处画线所穿过的砖块越少。
     * 3. 使用map 存储 preSum_i 的个数, 然后返回 map 中 value数最大的值, n - value 即最小穿过的砖块数
     * AC: 12ms/50.59MB
     * @param: wall
     * @return int
     * @author marks
     * @CreateDate: 2026/05/07 9:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<List<Integer>> wall) {
        int n = wall.size();
        Map<Long, Integer> map = new HashMap<>();
        int ans = n; // 最少穿过的砖块数
        for (List<Integer> list : wall) {
            int m = list.size();
            long preSum_i = 0;
            // 由于不能沿着墙的两个垂直边缘之一画线, 所以最后一块砖不能添加到map 中
            for (int i = 0; i < m - 1; i++) {
                preSum_i += list.get(i);
                map.put(preSum_i, map.getOrDefault(preSum_i, 0) + 1);
            }
        }
        // 遍历map, 找到 value数最大的值, n - value 即最小穿过的砖块数
        for (Map.Entry<Long, Integer> entry : map.entrySet()) {
            ans = Math.min(ans, n - entry.getValue());
        }

        return ans;
    }

}
