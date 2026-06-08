package com.marks.leetcode.hash_table;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3664 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/4 9:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3664 {

    /**
     * @Description:
     * 给你一副由字符串数组 cards 表示的牌，每张牌上都显示两个小写字母。
     * 同时给你一个字母 x。你按照以下规则进行游戏：
     *
     * 从 0 分开始。
     * 在每一轮中，你必须从牌堆中找到两张 兼容的 牌，这两张牌对应的字符串都包含字母 x。
     * 移除这对牌并获得 1 分。
     * 当你再也找不到兼容的牌对时，游戏结束。
     * 返回在最优策略下你能获得的 最大 分数。
     *
     * 如果两张牌的字符串在 恰好 1 个位置上不同，则它们是兼容的。
     *
     * tips:
     * 2 <= cards.length <= 10^5
     * cards[i].length == 2
     * 每个 cards[i] 仅由 'a' 到 'j' 之间的小写英文字母组成。
     * x 是一个 'a' 到 'j' 之间的小写英文字母。
     * @param: cards
     * @param: x
     * @return int
     * @author marks
     * @CreateDate: 2026/06/04 9:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int score(String[] cards, char x) {
        int result;
//        result = method_01(cards, x);
        result = method_02(cards, x);
        return result;
    }

    /**
     * @Description:
     * AC: 37ms/108.16MB
     * @param: cards
     * @param: x
     * @return int
     * @author marks
     * @CreateDate: 2026/06/04 14:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String[] cards, char x) {
        Map<String, Integer> map = new HashMap<>();
        for (String card : cards) {
            if (card.charAt(0) == x || card.charAt(1) == x) {
                map.merge(card, 1, Integer::sum);
            }
        }
        List<Integer> list0 = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getKey().charAt(0) == x && entry.getKey().charAt(1) != x) {
                list0.add(entry.getValue());
            } else if (entry.getKey().charAt(0) != x && entry.getKey().charAt(1) == x) {
                list1.add(entry.getValue());
            }
        }

        int all = map.getOrDefault(x + "" + x, 0);
        int ans = 0;
        if (all > 0) {
            for (int i = 0; i <= all; i++) {
                int s0 = getMaxScore(list0, i);
                int s1 = getMaxScore(list1, all - i);
                ans = Math.max(ans, s0 + s1);
            }
        } else {
            int s0 = getMaxScore(list0, 0);
            int s1 = getMaxScore(list1, 0);
            ans = Math.max(ans, s0 + s1);
        }

        return ans;
    }

    /**
     * @Description:
     * 获取最大得分
     * @param: list
     * @param: add
     * @return int
     * @author marks
     * @CreateDate: 2026/06/04 14:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int getMaxScore(List<Integer> list, int add) {
        if (list.isEmpty()) {
            return 0;
        }
        int max = 0;
        int sum = 0;
        for (Integer num : list) {
            sum += num;
            max = Math.max(max, num);
        }
        sum += add;
        max = Math.max(max, add);

        int other = sum - max;
        return Math.min(sum / 2, other);

    }

    /**
     * @Description:
     * 输入： cards = ["aa","ab","ba","ac"], x = "a"
     * 1. 由于 cards[i] 的返回是 'a' ~ 'j' 共10个字母, 并且 cards[i].length == 2, 所以总计最大有 100 种类型,
     * 使用 map 存储对应类型及其出现的次数. 并且由于只有包含 字母 x 才有兼容的可能性, 所以去除不包含 x 的情况.
     * 2. 然后考虑 key 中 第 0 位是 x 的情况
     * 3. 使用优先队列进行贪心是错误的, 例如 3, 3, 4, 最大得分是 1 + 4 = 5， 但是按照贪心策略最大得分是 3 + 1 = 4
     * 4.
     * @param: cards
     * @param: x
     * @return int
     * @author marks
     * @CreateDate: 2026/06/04 9:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] cards, char x) {
        Map<String, Integer> map = new HashMap<>();
        for (String card : cards) {
            if (card.charAt(0) == x || card.charAt(1) == x) {
                map.merge(card, 1, Integer::sum);
            }
        }
        // 处理第0 位是 x, 但是 第 1位不是 x 的 情况
        PriorityQueue<Integer> pq0 = new PriorityQueue<>((o1, o2) -> o2 - o1); // 需要大根堆
        PriorityQueue<Integer> pq1 = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getKey().charAt(0) == x && entry.getKey().charAt(1) != x) {
                pq0.add(entry.getValue());
            } else if (entry.getKey().charAt(0) != x && entry.getKey().charAt(1) == x) {
                pq1.add(entry.getValue());
            }
        }
        int all = map.getOrDefault(x + "" + x, 0);
        int ans = 0;
        if (all > 0) {

            for (int k = 0; k <= all; k++) {
                int currSum = 0;
                // create a new pq
                PriorityQueue<Integer> pqCopy0 = new PriorityQueue<>((o1, o2) -> o2 - o1);
                PriorityQueue<Integer> pqCopy1 = new PriorityQueue<>((o1, o2) -> o2 - o1);
                pqCopy0.addAll(pq0);
                pqCopy1.addAll(pq1);
                pqCopy0.add(k);
                pqCopy1.add(all - k);
                while (pqCopy0.size() > 1) {
                    int a = pqCopy0.poll();
                    int b = pqCopy0.poll();
                    currSum += b;
                    if (a > b) {
                        pqCopy0.add(a - b);
                    }
                }
                while (pqCopy1.size() > 1) {
                    int a = pqCopy1.poll();
                    int b = pqCopy1.poll();
                    currSum += b;
                    if (a > b) {
                        pqCopy1.add(a - b);
                    }
                }
                ans = Math.max(ans, currSum);
            }

        } else {
            while (pq0.size() > 1) {
                int a = pq0.poll();
                int b = pq0.poll();
                ans += b;
                if (a > b) {
                    pq0.add(a - b);
                }
            }
            while (pq1.size() > 1) {
                int a = pq1.poll();
                int b = pq1.poll();
                ans += b;
                if (a > b) {
                    pq1.add(a - b);
                }
            }
        }

        return ans;
    }

}
