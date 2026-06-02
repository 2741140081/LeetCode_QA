package com.marks.leetcode.hash_table;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1711 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/2 10:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1711 {

    /**
     * @Description:
     * 大餐 是指 恰好包含两道不同餐品 的一餐，其美味程度之和等于 2 的幂。
     * 你可以搭配 任意 两道餐品做一顿大餐。
     * 给你一个整数数组 deliciousness ，其中 deliciousness[i] 是第 i 道餐品的美味程度，返回你可以用数组中的餐品做出的不同 大餐 的数量。结果需要对 109 + 7 取余。
     * 注意，只要餐品下标不同，就可以认为是不同的餐品，即便它们的美味程度相同。
     *
     * tips:
     * 1 <= deliciousness.length <= 10^5
     * 0 <= deliciousness[i] <= 2^20
     * @param: deliciousness
     * @return int
     * @author marks
     * @CreateDate: 2026/06/02 10:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countPairs(int[] deliciousness) {
        int result;
        result = method_01(deliciousness);
        return result;
    }

    /**
     * @Description:
     * 1. 先找到 max = Max(deliciousness), 然后得到 2^x >= 2 * max, 使用 list 记录 [2^0 ~ 2^x] 的值
     * AC: 67ms/79.15MB
     * @param: deliciousness
     * @return int
     * @author marks
     * @CreateDate: 2026/06/02 10:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] deliciousness) {
        int MOD = 1000000007;
        int max = Arrays.stream(deliciousness).max().getAsInt();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        int temp = 2;
        while (temp <= 2 * max) {
            list.add(temp);
            temp *= 2;
        }
        long ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : deliciousness) {
            map.merge(num, 1, Integer::sum);
        }
        List<Integer> sortedList = new ArrayList<>(map.keySet());
        Collections.sort(sortedList);
        int n = list.size();
        for (int curr : sortedList) {
            for (int j = 0; j < n; j++) {
                int target = list.get(j) - curr;
                if (target >= curr && map.containsKey(target)) {
                    if (curr == target) {
                        int cnt = map.get(curr);
                        ans = (ans + ((long) cnt * (cnt - 1) / 2)) % MOD;
                    } else {
                        int cnt1 = map.get(curr);
                        int cnt2 = map.get(target);
                        ans = (ans + (long) cnt1 * cnt2) % MOD;
                    }
                }
            }
        }

        return (int) ans;
    }

}
