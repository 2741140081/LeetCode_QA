package com.marks.leetcode.data_structure.common_enum_technique;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/8 14:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2260 {
    /**
     * @Description: [
     * 给你一个整数数组 cards ，其中 cards[i] 表示第 i 张卡牌的 值 。
     * 如果两张卡牌的值相同，则认为这一对卡牌 匹配 。
     *
     * 返回你必须拿起的最小连续卡牌数，以使在拿起的卡牌中有一对匹配的卡牌。如果无法得到一对匹配的卡牌，返回 -1 。
     *
     * tips:
     * 1 <= cards.length <= 10^5
     * 0 <= cards[i] <= 10^6
     * ]
     * @param cards 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumCardPickup(int[] cards) {
        int result;
        result = method_01(cards);
        result = method_02(cards);
        return result;
    }

    /**
     * @Description: 使用Map 解决, Map中村cards[i] 和 i, 由于i不断增大, cards[j] = cards[i] = cards[pre_i]
     * 由于i > pre_i, 所以可以覆盖map.put(card[i], i) 覆盖 map.put(card[pre_i], pre_i)
     * AC:45ms/61.13MB
     * @param cards
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] cards) {
        int n = cards.length;
        final int INF = Integer.MAX_VALUE / 2;
        int ans = INF;
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            if (map.containsKey(cards[i])) {
                int size = i - map.get(cards[i]) + 1;
                ans = Math.min(ans, size);
            }
            map.put(cards[i], i);
        }
        return ans == INF ? -1 : ans;
    }

    /**
     * @Description: int[][] array = {{card, index}}, 将card排序(默认升序), 并且将index升序排序
     * AC:133ms/56.06MB
     * @param cards 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] cards) {
        int n = cards.length;
        final int INF = Integer.MAX_VALUE / 2;
        int[][] arrays = new int[n][2];
        for (int i = 0; i < n; i++) {
            arrays[i][0] = cards[i];
            arrays[i][1] = i;
        }
        Arrays.sort(arrays, (o1, o2) -> {
            if (o1[0] == o2[0]) {
                return o1[1] - o2[1];
            } else {
                return o1[0] - o2[0];
            }
        });
        int ans = INF;
        int pre_value = -1;
        int pre_index = -1;
        for (int[] array : arrays) {
            int curr_value = array[0];
            int curr_index = array[1];
            if (curr_value != pre_value) {
                pre_value = curr_value;
                pre_index = curr_index;
            } else {
                ans = Math.min(ans, curr_index - pre_index + 1);
                pre_index = curr_index;
            }
        }
        return ans == INF ? -1 : ans;
    }
}
