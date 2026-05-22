package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_846 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/20 16:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_846 {

    /**
     * @Description:
     * Alice 手中有一把牌，她想要重新排列这些牌，分成若干组，使每一组的牌数都是 groupSize ，并且由 groupSize 张连续的牌组成。
     * 给你一个整数数组 hand 其中 hand[i] 是写在第 i 张牌上的数值。如果她可能重新排列这些牌，返回 true ；否则，返回 false 。
     *
     * tips:
     * 1 <= hand.length <= 10^4
     * 0 <= hand[i] <= 10^9
     * 1 <= groupSize <= hand.length
     * @param: hand
     * @param: groupSize
     * @return boolean
     * @author marks
     * @CreateDate: 2026/05/20 16:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isNStraightHand(int[] hand, int groupSize) {
        boolean result;
        result = method_01(hand, groupSize);
        return result;
    }

    /**
     * @Description:
     * 1. 统计每个数字出现的次数
     * 2. 从小到大遍历, 假设当前 list 中的数字为 x, 整个groupSize 个数字的数字范围是 [x, x + 1, ...., x + groupSize - 1]
     * 3. 如果 map 中 x 有 cnt 个, 那么后续 groupSize 中的数字个数需要 >= cnt
     * AC: 44ms/47.21MB
     * @param: hand
     * @param: groupSize
     * @return boolean
     * @author marks
     * @CreateDate: 2026/05/20 16:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] hand, int groupSize) {
        int length = hand.length;
        if (length % groupSize != 0) {
            return false;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : hand) {
            map.merge(num, 1, Integer::sum);
        }
        // 将 key 转换为 list, 并且升序排序
        int[] list = map.keySet().stream().sorted().mapToInt(Integer::intValue).toArray();
        for (int x : list) {
            int cnt = map.get(x);
            if (cnt > 0) {
                for (int j = 1; j < groupSize; j++) {
                    if (!map.containsKey(x + j) || map.get(x + j) < cnt) {
                        return false;
                    }
                    map.merge(x + j, -cnt, Integer::sum);
                }
            }
        }

        return true;
    }

}
