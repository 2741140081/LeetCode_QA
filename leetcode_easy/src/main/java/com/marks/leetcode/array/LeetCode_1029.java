package com.marks.leetcode.array;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1029 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/8 10:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1029 {

    /**
     * @Description:
     * 公司计划面试 2n 人。给你一个数组 costs ，其中 costs[i] = [aCosti, bCosti] 。第 i 人飞往 a 市的费用为 aCosti ，飞往 b 市的费用为 bCosti 。
     * 返回将每个人都飞到 a 、b 中某座城市的最低费用，要求每个城市都有 n 人抵达。
     * @param: costs
     * @return int
     * @author marks
     * @CreateDate: 2026/05/08 10:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int twoCitySchedCost(int[][] costs) {
        int result;
        result = method_01(costs);
        return result;
    }

    /**
     * @Description:
     * 1. 感觉不是动态规划, 单纯排序问题
     * 2. 相当于是贡献法, costs[i] = [aCosti, bCosti], 它对整个共享是
     * 3. 以差值进行排序, sub = aCosti - bCosti;
     * AC: 5ms/42.9MB
     * @param: costs
     * @return int
     * @author marks
     * @CreateDate: 2026/05/08 10:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] costs) {
        int n = costs.length;
        // 根据差值升序排序
        Item[] sub = new Item[n];
        for (int i = 0; i < n; i++) {
            sub[i] = new Item();
            sub[i].sub = costs[i][0] - costs[i][1];
            sub[i].index = i;
        }
        Arrays.sort(sub, Comparator.comparingInt(a -> a.sub));
        int ans = 0;
        int left = 0, right = n - 1;
        while (left < right) {
            // add left 0, city a, because a city cost is cheaper b city.
            ans += costs[sub[left].index][0];
            left++;
            // add right 1, city b, because b city cost is cheaper a city.
            ans += costs[sub[right].index][1];
            right--;
        }

        return ans;
    }

    private class Item {
        int sub;
        int index;
    }

}
