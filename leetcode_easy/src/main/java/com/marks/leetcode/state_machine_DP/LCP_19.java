package com.marks.leetcode.state_machine_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称: 秋叶收藏集 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/11 15:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_19 {
    /**
     * @Description: [
     * 小扣出去秋游，途中收集了一些红叶和黄叶，他利用这些叶子初步整理了一份秋叶收藏集 leaves， 字符串 leaves 仅包含小写字符 r 和 y，
     * 其中字符 r 表示一片红叶，字符 y 表示一片黄叶。 出于美观整齐的考虑，小扣想要将收藏集中树叶的排列调整成「红、黄、红」三部分。
     * 每部分树叶数量可以不相等，但均需大于等于 1。每次调整操作，小扣可以将一片红叶替换成黄叶或者将一片黄叶替换成红叶。
     * 请问小扣最少需要多少次调整操作才能将秋叶收藏集调整完毕。
     *
     * tips:
     * 3 <= leaves.length <= 10^5
     * leaves 中只包含字符 'r' 和字符 'y'
     * ]
     * @param leaves
     * @return int
     * @author marks
     * @CreateDate: 2024/9/11 15:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumOperations(String leaves) {
        int result = 0;
//        result = method_01(leaves);
//        result = method_02(leaves);
//        result = method_03(leaves);
//        result = method_04(leaves);
        result = method_05(leaves);
        return result;
    }

    /**
     * @Description: [基于method_04, 结合官方题解2, 其中题解中提及的细节可以优化
     * 方程1:Pre_Y(n - 1) + (Pre_Y(x) - Pre_R(x)) - (Pre_Y(y) - Pre_R(y)), 这个的计算
     * 优化过程如下:
     * Pre_R(i) + Pre_Y(i) = i + 1; // 即前缀和的红叶和黄叶和等于当前下标值加1
     * 那么Pre_Y(x) - PreR(x) ==> Pre_Y(x) - (x+1 - Pre_Y(x)) ==> 2 * Pre_Y(x) - (x + 1)
     * 令g(x) = 2 * Pre_Y(x) - (x + 1)
     *
     * 那么方程1改写成: Pre_Y(n - ) + g(x) - g(y)
     *
     * ]
     * @param leaves
     * @return int
     * @author marks
     * @CreateDate: 2024/9/14 10:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_05(String leaves) {
        int n = leaves.length();
        int g = leaves.charAt(0) == 'y' ? 1 : -1;
        int gmin = g;
        int ans = Integer.MAX_VALUE;
        for (int i = 1; i < n; ++i) {
            int isYellow = leaves.charAt(i) == 'y' ? 1 : 0;
            g += 2 * isYellow - 1;
            if (i != n - 1) {
                ans = Math.min(ans, gmin - g);
            }
            gmin = Math.min(gmin, g);
        }
        return ans + (g + n) / 2;
    }

    /**
     * @Description: [官方题解之前缀和 + 动态规划
     * 官方题解有点看不懂, 拿个案例来仔细看看
     * 先搞定一部分, 题解中细节等下在做
     * Pre_Y(x) + (Pre_R(y) - Pre_R(x)) + (Pre_Y(n - 1) - Pre_Y(y))
     * ===>Pre_Y(n - 1) + (Pre_Y(x) - Pre_R(x)) - (Pre_Y(y) - Pre_R(y))
     * AC:15ms/44.54MB
     * ]
     * @param leaves
     * @return int
     * @author marks
     * @CreateDate: 2024/9/13 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_04(String leaves) {
        int n = leaves.length();
        int[] pre_R = new int[n];
        int[] pre_Y = new int[n];
        pre_R[0] = leaves.charAt(0) == 'r' ? 1 : 0;
        pre_Y[0] = leaves.charAt(0) == 'y' ? 1 : 0;
        // 初始化 Pre_R, Pre_Y, 并且找到x, 使得pre_Y[x] - pre_R[x] 最小
        for (int i = 1; i < n; i++) {
            if (leaves.charAt(i) == 'r') {
                pre_R[i] = pre_R[i - 1] + 1;
                pre_Y[i] = pre_Y[i - 1];
            }else {
                pre_R[i] = pre_R[i - 1];
                pre_Y[i] = pre_Y[i - 1] + 1;
            }
        }
        // 在此基础上找到y, 通过for 循环遍历x + 1 ~ n - 1, y 取值范围[1, n - 2]
        int ans = n;
        int temp1 = pre_Y[0] - pre_R[0];
        for (int i = 1; i < n - 1; i++) {
            temp1 = Math.min(temp1, pre_Y[i - 1] - pre_R[i - 1]);
            ans = Math.min(ans, pre_Y[n - 1] + temp1 - (pre_Y[i] - pre_R[i]));
        }
        return ans;
    }

    /**
     * @Description: [题解思路: 官方题解2, 枚举
     * 分别枚举x, y
     * 使得[0 ~ x] 为红色 [x + 1, y]为黄色 [y + 1, n - 1]为红色
     * 记录count = change[1] + change[2] + change[3]
     * 成功还原前缀和的方法, 成功超时!!! 基于method_03
     * ]
     * @param leaves
     * @return int
     * @author marks
     * @CreateDate: 2024/9/13 11:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(String leaves) {
        int n = leaves.length();
        int[] dp_0 = new int[n];
        int[] dp_2 = new int[n];
        Arrays.fill(dp_0, Integer.MAX_VALUE / 2);
        Arrays.fill(dp_2, Integer.MAX_VALUE / 2);
        int ans = Integer.MAX_VALUE;
        dp_0[0] = leaves.charAt(0) == 'r' ? 0 : 1;
        dp_2[n - 1] = leaves.charAt(n - 1) == 'r' ? 0 : 1;
        for (int i = 1; i < n - 2; i++) {
            dp_0[i] =dp_0[i - 1] + (leaves.charAt(i) == 'r' ? 0 : 1);
        }
        for (int i = n - 1; i >= 3; i--) {
            dp_2[i - 1] = dp_2[i] + (leaves.charAt(i - 1) == 'r' ? 0 : 1);
        }
        for (int i = 0; i < n - 2; i++) {
            int[] dp_1 = new int[n];
            for (int j = i + 1; j < n - 1; j++) {
                dp_1[j] = dp_1[j - 1] + (leaves.charAt(j) == 'y' ? 0 : 1);
                ans = Math.min(ans, dp_0[i] + dp_1[j] + dp_2[j + 1]);
            }
        }
        return ans;
    }



    /**
     * @Description: [
     * 基于method_01
     * 优化空间复杂度
     * AC:18ms/44.14MB
     * ]
     * @param leaves
     * @return int
     * @author marks
     * @CreateDate: 2024/9/13 11:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String leaves) {
        int n = leaves.length();
        int[] dp = new int[3];
        dp[0] = leaves.charAt(0) == 'r' ? 0 : 1;
        dp[1] = dp[2] = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            int isRed = leaves.charAt(i) == 'r' ? 0 : 1;
            int isYellow = leaves.charAt(i) == 'y' ? 0 : 1;

            if (i >= 2) {
                dp[2] = Math.min(dp[1], dp[2]) + isRed;
            }
            dp[1] = Math.min(dp[0], dp[1]) + isYellow;
            dp[0] = dp[0] + isRed;
        }
        return dp[2];
    }

    /**
     * @Description: [
     * 需要调整成 R - Y - R 模式
     * l[0] == 'r'
     * l[n - 1] == 'r'
     * l[1 ~ n - 2] contain 'y'
     * E1: "rrryyyrryyyrr"
     * 从左往右遍历
     * for i 1 ~ n - 1
     * dpy = [0,0,1,2,3,0,0,1,2,3,0]
     * dpr = [1,2,0,0,0,1,2,0,0,0,1]
     * dp[i] =
     * 以上思路错误=======================<p/>
     * 查看官方题解: 动态规划
     * int[][] dp = new int[n][3]
     * dp[i][j]
     * 设置3种状态,
     * j == 0, 表示前面的红色部分,
     * j == 1, 表示中间的黄色部分,
     * j == 2, 表示后面的红色部分。
     * 对于j == 0, 如我们需要将第i片叶子变成红色, 则i - 1的叶子全部已经变成红色,
     * dp[i][0] = dp[i - 1][0] + leaves.charAt(i) == 'r' ? 0 : + 1;
     *
     * j == 1, 即可以从dp[i - 1][1], 也可以从dp[i - 1][0]得到, 即取两者的最小值
     *
     * j == 2, 即可以从dp[i - 1][2], 也可以从dp[i - 1][1]得到, 取两者小值
     *
     * AC:62ms/54.84MB
     * ]
     * @param leaves
     * @return int
     * @author marks
     * @CreateDate: 2024/9/11 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String leaves) {
        int n = leaves.length();
        int[][] dp = new int[n][3];
        dp[0][0] = leaves.charAt(0) == 'r' ? 0 : 1;
        dp[0][1] = dp[0][2] = dp[1][2] = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            int isRed = leaves.charAt(i) == 'r' ? 0 : 1;
            int isYellow = leaves.charAt(i) == 'y' ? 0 : 1;
            dp[i][0] = dp[i - 1][0] + isRed;
            dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]) + isYellow;
            if (i >= 2) {
                dp[i][2] = Math.min(dp[i - 1][1], dp[i - 1][2]) + isRed;
            }
        }
        return dp[n - 1][2];
    }
}
