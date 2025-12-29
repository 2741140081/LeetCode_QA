package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2483 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/26 11:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2483 {

    /**
     * @Description:
     * 给你一个顾客访问商店的日志，用一个下标从 0 开始且只包含字符 'N' 和 'Y' 的字符串 customers 表示：
     *
     * 如果第 i 个字符是 'Y' ，它表示第 i 小时有顾客到达。
     * 如果第 i 个字符是 'N' ，它表示第 i 小时没有顾客到达。
     * 如果商店在第 j 小时关门（0 <= j <= n），代价按如下方式计算：
     *
     * 在开门期间，如果某一个小时没有顾客到达，代价增加 1 。
     * 在关门期间，如果某一个小时有顾客到达，代价增加 1 。
     * 请你返回在确保代价 最小 的前提下，商店的 最早 关门时间。
     *
     * 注意，商店在第 j 小时关门表示在第 j 小时以及之后商店处于关门状态。
     * @param: customers
     * @return int
     * @author marks
     * @CreateDate: 2025/12/26 14:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int bestClosingTime(String customers) {
        int result;
        result = method_01(customers);
        result = method_02(customers);
        return result;
    }

    /**
     * @Description:
     * 代价最小的位置, 即 N 的数量 大于 Y 的数量的最大值, 即为代价最小的位置
     * 1. 降序遍历, 从后向前遍历, 使用 countN  记录 N 的数量, countY 记录 Y 的数量
     * AC: 6ms/46.18MB
     * @param: customers
     * @return int
     * @author marks
     * @CreateDate: 2025/12/26 14:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String customers) {
        int n = customers.length();
        char[] custArray = customers.toCharArray();
        int countN = 0, countY = 0;
        int sub = 0; // 记录countN - countY 的最大值
        int ans = n; // 记录index
        for (int i = n - 1; i >= 0; i--) {
            if (custArray[i] == 'Y') {
                countY++;
            } else {
                countN++;
                if (countN - countY >= sub) {
                    sub = countN - countY;
                    ans = i;
                }
            }
        }
        return ans;
    }

    /**
     * @Description:
     * 1. 假设在第 i 小时进行关门, i+1 ~ n-1 中存在 a 个 Y 和 b 个 N, 那么代价是多少? a, 如果不关门, 代价是 b
     * 2. 从后向前遍历, if a == b, 则表示可以在i进行关门, 使用前缀表示开门的代价(N的个数), 后缀 表示关门的代价(Y的个数)
     * AC: 13ms/46.51MB
     * @param: customers
     * @return int
     * @author marks
     * @CreateDate: 2025/12/26 14:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String customers) {
        int n = customers.length();
        // 将 customers 转换成 Array
        char[] custArray = customers.toCharArray();
        int[] prefix = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + (custArray[i - 1] == 'N' ? 1 : 0);
        }
        int[] suffix = new int[n];
        suffix[n - 1] = (custArray[n - 1] == 'Y' ? 1 : 0);
        for (int i = n - 2; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + (custArray[i] == 'Y' ? 1 : 0);
        }
        int ans = n; // 记录index
        int min = prefix[n];
        for (int i = n - 1; i >= 0; i--) {
            // 在第 i 小时进行关门
            int open = prefix[i];
            int close = suffix[i];
            if (open + close <= min) {
                ans = i;
                min = open + close;
            }
        }

        return ans;
    }

}
