package com.marks.leetcode.data_structure.queue;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/22 11:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_950 {
    /**
     * @Description:
     * 牌组中的每张卡牌都对应有一个唯一的整数。你可以按你想要的顺序对这套卡片进行排序。
     *
     * 最初，这些卡牌在牌组里是正面朝下的（即，未显示状态）。
     *
     * 现在，重复执行以下步骤，直到显示所有卡牌为止：
     *
     * 从牌组顶部抽一张牌，显示它，然后将其从牌组中移出。
     * 如果牌组中仍有牌，则将下一张处于牌组顶部的牌放在牌组的底部。
     * 如果仍有未显示的牌，那么返回步骤 1。否则，停止行动。
     * 返回能以递增顺序显示卡牌的牌组顺序。
     *
     * 答案中的第一张牌被认为处于牌堆顶部。
     *
     * @param deck
     * @return int[]
     * @author marks
     * @CreateDate: 2025/1/22 11:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] deckRevealedIncreasing(int[] deck) {
        int[] result;
        result = method_01(deck);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：[17,13,11,2,3,5,7]
     * 输出：[2,13,3,11,5,17,7]
     * [1, 2]
     * [1, 2, 3]
     * [1, 3, 2]
     *
     * [1, 2, 3, 4]
     * [1, 3, 2, 4]
     *
     * [1, 2, 3, 4, 5]
     * [1, 4, 2, 5, 3]
     *
     * [0, 1, 2, 3, 4, 5, 6, 7]
     * [0, 2, 4, 6, 1, 3, 5, 7]
     * [1, 5, 3, 7]
     * [3, 7]
     *
     * [0, 1, 2, 3, 4, 5, 6]
     * [0, 2, 4]
     * [6, 1, 3, 5]
     *
     * AC:4ms/42.86MB
     * @param deck
     * @return int[]
     * @author marks
     * @CreateDate: 2025/1/22 11:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] deck) {
        Arrays.sort(deck);
        int n = deck.length;
        int[] ans = new int[n];
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            queue.offer(i);
        }
        int index = 0;
        while (!queue.isEmpty() && index < n) {
            int out = queue.poll();
            ans[out] = deck[index++];
            if (!queue.isEmpty()) {
                queue.offer(queue.poll());
            }
        }
        return ans;
    }
}
