package com.marks.leetcode.array_medium;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3947 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/20 11:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3947 {

    /**
     * @Description:
     * 给你一个二维整数数组 items，其中 items[i] = [factori, pricei] 表示下标为 i 的物品。同时给你一个整数 budget。
     * 每种物品都有无限个可供购买。你可以购买任意数量的任意物品，但购买物品的总花费最多为 budget。
     * 购买物品后，你可以根据以下规则获得免费的物品：
     * 购买的每一份物品 i 最多 可以让你获得 一份 免费的其他物品 j。
     * 免费物品必须满足 i != j 且 factori 可以整除 factorj。
     * 对于每个有序对 (i, j)，无论你购买了多少个物品 i，你从物品 i 的购买中 最多只能一次 免费获得物品 j。
     * 如果免费物品 j 是通过购买不同种类的物品获得的，那么同一种物品 j 可以被免费获得多次。
     * 返回你在购买物品花费最多为 budget 的前提下，能够获得的 物品最大总数 ，包括购买的物品和免费的物品。
     *
     * tips:
     * 1 <= items.length <= 10^5
     * items[i] = [factori, pricei]
     * 1 <= factori <= items.length
     * 1 <= pricei <= 10^9
     * 1 <= budget <= 10^9
     * @param: items
     * @param: budget
     * @return int
     * @author marks
     * @CreateDate: 2026/09/20 11:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumSaleItems(int[][] items, int budget) {
        int result;
        result = method_01(items, budget);
        return result;
    }

    /**
     * @Description:
     * 1. 使用 Map 存储 factori 可以整除的 factorj 的数量
     * 2. 对 items 按照价格进行升序排序
     * 3. 如果 i 存在factorj，则表示购买当前 pricei 可以得到两份物品, 如果无法得到免费物品, 则表示只能得到一份物品
     * 4. 构建优先队列, 按照获取两份物品的价格进行降序排序(大根堆), 如果可以获得免费物品, 则两份价格是原始价格, 如果不能获得免费物品, 则两份价格是原始价格 * 2.
     * 5. 在处理 i 时, 只需要判断 当前 i 是否存在 factorj, 如果存在则表示可以获得免费物品, 并且通过 map 可以得到能够最多获取的免费物品数量 cnt,
     * 然后判断 pricei 与优先队列栈顶元素的大小关系, 如果 pricei 小于栈顶元素, 则弹出栈顶元素, 并且可以获取栈顶元素总花费, 然后将该花费用于购买 物品 i,
     * 直到物品 i 购买完成 或者 优先队列栈顶的价格小于 pricei.
     * 6. 最后遍历优先队列, 总计数量, 返回结果即可.
     * 7. 存在一个问题, 即如果要构建 map 需要进行双重for 循环, 这必然超时, 但是有想不到可以优化的方向
     * 由于数值不大 10^5, 所以采用筛法进行优化
     * AC: 400ms/227.21MB
     * @param: items
     * @param: budget
     * @return int
     * @author marks
     * @CreateDate: 2026/09/20 11:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] items, int budget) {
        int MAX_FACTOR = 100000;
        int[] cnt = new int[MAX_FACTOR + 1];
        int[] totalDivisibleCount = new int[MAX_FACTOR + 1];
        for (int[] item : items) {
            int factor = item[0];
            cnt[factor]++;
        }
        for (int i = 1; i <= MAX_FACTOR; i++) {
            if (cnt[i] == 0) {
                continue;
            }
            for (int j = i; j <= MAX_FACTOR; j += i) {
                if (cnt[j] > 0) {
                    totalDivisibleCount[i] += cnt[j];
                }
            }
        }
        // 需要减去自身
        for (int i = 1; i <= MAX_FACTOR; i++) {
            if (totalDivisibleCount[i] > 0) {
                totalDivisibleCount[i]--;
            }
        }
        // 然后对 items 根据价格进行升序排序
        Arrays.sort(items, Comparator.comparingInt(a -> a[1])); // 按价格升序排序
        int remainingBudget = budget;
        int minPrice = items[0][1];
        int ans = 0;
        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];
            int itemCount = totalDivisibleCount[factor];
            if (itemCount > 0 && remainingBudget >= price && price < 2 * minPrice) {
                int currCnt = Math.min(itemCount, remainingBudget / price);
                ans += (currCnt * 2);
                remainingBudget -= (currCnt * price);
            }
        }
        if (remainingBudget >= minPrice) {
            ans += (remainingBudget / minPrice);
        }

        return ans;
    }

}
