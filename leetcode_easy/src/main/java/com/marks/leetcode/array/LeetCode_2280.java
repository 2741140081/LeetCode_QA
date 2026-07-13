package com.marks.leetcode.array;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2280 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/13 10:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2280 {

    /**
     * @Description:
     * 给你一个二维整数数组 stockPrices ，其中 stockPrices[i] = [dayi, pricei] 表示股票在 dayi 的价格为 pricei 。
     * 折线图 是一个二维平面上的若干个点组成的图，横坐标表示日期，纵坐标表示价格，折线图由相邻的点连接而成。比方说下图是一个例子：
     * 请你返回要表示一个折线图所需要的 最少线段数 。
     *
     * tips:
     * 1 <= stockPrices.length <= 10^5
     * stockPrices[i].length == 2
     * 1 <= dayi, pricei <= 10^9
     * 所有 dayi 互不相同 。
     * @param: stockPrices
     * @return int
     * @author marks
     * @CreateDate: 2026/07/13 10:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumLines(int[][] stockPrices) {
        int result;
        result = method_01(stockPrices);
        return result;
    }

    /**
     * @Description:
     * 1. 需要根据 day_i 进行排序, 升序排序.
     * 2. n < 3 时返回1, 当 n >= 3 时, 判断斜率是否相等, 如果相等维持 ans 不变, 如果不相等, ans++;
     * 3. 斜率的判断方式, 假设有 a(x1, y1), b(x2, y2), c(x3, y3) 3个相邻的点, 但是点之间的斜率计算为
     * k1 = (x1 - x2)/(y1 - y2), k2 = (x2 - x3)/(y2 - y3) => (x1 - x2) * (y2 - y3) - (x2 - x3) * (y1 - y2) 与 0 比较即可,
     * 如果等于 0 则斜率相等, 否则斜率不相等
     * AC: 57ms/123.34MB
     * @param: stockPrices
     * @return int
     * @author marks
     * @CreateDate: 2026/07/13 10:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] stockPrices) {
        int n = stockPrices.length;
        if (n == 1) {
            return 0;
        } else if (n == 2) {
            return 1;
        }
        // 排序根据day_i进行升序排序
        Arrays.sort(stockPrices, Comparator.comparingInt(a -> a[0]));
        int ans = 1;
        for (int i = 2; i < n; i++) {
            int x1 = stockPrices[i - 2][0];
            int y1 = stockPrices[i - 2][1];
            int x2 = stockPrices[i - 1][0];
            int y2 = stockPrices[i - 1][1];
            int x3 = stockPrices[i][0];
            int y3 = stockPrices[i][1];
            if ((x1 - x2) * (y2 - y3) - (x2 - x3) * (y1 - y2) != 0) {
                ans++;
            }
        }

        return ans;
    }

}
