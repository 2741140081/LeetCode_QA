package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1105 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/19 10:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1105 {

    /**
     * @Description:
     * 给定一个数组 books ，其中 books[i] = [thicknessi, heighti] 表示第 i 本书的厚度和高度。你也会得到一个整数 shelfWidth 。
     *
     * 按顺序 将这些书摆放到总宽度为 shelfWidth 的书架上。
     *
     * 先选几本书放在书架上（它们的厚度之和小于等于书架的宽度 shelfWidth ），然后再建一层书架。重复这个过程，直到把所有的书都放在书架上。
     *
     * 需要注意的是，在上述过程的每个步骤中，摆放书的顺序与给定图书数组 books 顺序相同。
     *
     * 例如，如果这里有 5 本书，那么可能的一种摆放情况是：第一和第二本书放在第一层书架上，第三本书放在第二层书架上，第四和第五本书放在最后一层书架上。
     * 每一层所摆放的书的最大高度就是这一层书架的层高，书架整体的高度为各层高之和。
     *
     * 以这种方式布置书架，返回书架整体可能的最小高度。
     * tips:
     * 1 <= books.length <= 1000
     * 1 <= thicknessi <= shelfWidth <= 1000
     * 1 <= heighti <= 1000
     * @param: books
     * @param: shelfWidth
     * @return int
     * @author marks
     * @CreateDate: 2025/11/19 10:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minHeightShelves(int[][] books, int shelfWidth) {
        int result;
        result = method_01(books, shelfWidth);
        result = method_02(books, shelfWidth);
        return result;
    }

    private int method_02(int[][] books, int shelfWidth) {
        int n = books.length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 1000000);
        dp[0] = 0;
        for (int i = 0; i < n; ++i) {
            int curWidth = 0, maxHeight = 0;
            for (int j = i; j >= 0; --j) {
                curWidth += books[j][0];
                if (curWidth > shelfWidth) {
                    break;
                }
                maxHeight = Math.max(maxHeight, books[j][1]);
                dp[i + 1] = Math.min(dp[i + 1], dp[j] + maxHeight);
            }
        }
        return dp[n];
    }

    /**
     * @Description:
     * @param: books
     * @param: shelfWidth
     * @return int
     * @author marks
     * @CreateDate: 2025/11/19 10:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] books, int shelfWidth) {
        int n = books.length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 1000000);
        dp[0] = 0;
        for (int i = 0; i < n; ++i) {
            int maxHeight = 0, curWidth = 0;
            for (int j = i; j >= 0; --j) {
                curWidth += books[j][0];
                if (curWidth > shelfWidth) {
                    break;
                }
                maxHeight = Math.max(maxHeight, books[j][1]);
                dp[i + 1] = Math.min(dp[i + 1], dp[j] + maxHeight);
            }
        }
        return dp[n];
    }
}
