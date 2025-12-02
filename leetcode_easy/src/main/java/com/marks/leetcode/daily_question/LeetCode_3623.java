package com.marks.leetcode.daily_question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3623 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/2 14:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3623 {
    private static final int MOD = (int) 1e9 + 7;
    /**
     * @Description:
     * 给你一个二维整数数组 points，其中 points[i] = [xi, yi] 表示第 i 个点在笛卡尔平面上的坐标。
     * 水平梯形 是一种凸四边形，具有 至少一对 水平边（即平行于 x 轴的边）。两条直线平行当且仅当它们的斜率相同。
     * 返回可以从 points 中任意选择四个不同点组成的 水平梯形 数量。
     * 由于答案可能非常大，请返回结果对 109 + 7 取余数后的值。
     *
     * tips:
     * 4 <= points.length <= 10^5
     * –10^8 <= xi, yi <= 10^8
     * 所有点两两不同。
     * @param: points
     * @return int
     * @author marks
     * @CreateDate: 2025/12/02 14:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countTrapezoids(int[][] points) {
        int result;
        result = method_01(points);
        return result;
    }

    /**
     * @Description:
     * 1. 需要给这些点进行分类, 将同一条水平直线(这些点的y值相同, x值不同)上的点放在一起, Map<Integer, List<Integer>> map;
     * 2. 遍历 points, 当前points[i], x = points[i][0], y = points[i][1]; map.get(y) => null, 添加一个新的List<Integer>, 将x添加到List中
     * int size = map.get(y).size(); 然后需要遍历map, value.size() >= 2, ans += C(size,2)
     * 3. 将当前点添加到map中, 处理下一个点, 我应该不需要关心List<Integer> 中存储的数据, 只需要关注大小即可, 所以 map 修改 Map<Integer, Integer> map;
     * 4. 注意溢出, List<Integer> list 可能发生溢出， 需要用 Long
     * AC: 44ms/202.31MB
     * @param: points
     * @return int
     * @author marks
     * @CreateDate: 2025/12/02 14:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] points) {
        int n = points.length;
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int y = points[i][1];
            // 添加
            map.merge(y, 1, Integer::sum);
        }
        // 使用list 存储value >= 2 的方法数目
        List<Long> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 1) {
                long size = entry.getValue();
                long count = (size * (size - 1)) / 2;
                list.add(count);
            }
        }
        // 使用前缀和计算方法数目
        long ans = 0;
        int len = list.size();
        if (len < 2) {
            return 0;
        }
        long prefixSum = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            long curr = list.get(i);
            ans = (ans + curr * prefixSum) % MOD;
            prefixSum = (prefixSum + curr) % MOD;
        }
        return (int) (ans % MOD);
    }

}
