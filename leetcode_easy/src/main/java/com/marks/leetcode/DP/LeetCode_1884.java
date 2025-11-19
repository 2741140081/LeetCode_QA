package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1884 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/18 16:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1884 {

    /**
     * @Description:
     * 给你 2 枚相同 的鸡蛋，和一栋从第 1 层到第 n 层共有 n 层楼的建筑。
     * 已知存在楼层 f ，满足 0 <= f <= n ，任何从 高于 f 的楼层落下的鸡蛋都 会碎 ，从 f 楼层或比它低 的楼层落下的鸡蛋都 不会碎 。
     *
     * 每次操作，你可以取一枚 没有碎 的鸡蛋并把它从任一楼层 x 扔下（满足 1 <= x <= n）。如果鸡蛋碎了，你就不能再次使用它。
     * 如果某枚鸡蛋扔下后没有摔碎，则可以在之后的操作中 重复使用 这枚鸡蛋。
     *
     * 请你计算并返回要确定 f 确切的值 的 最小操作次数 是多少？
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/18 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int twoEggDrop(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 输入：n = 100
     * 输出：14
     * 解释：
     * 一种最优的策略是：
     * - 将第一枚鸡蛋从 9 楼扔下。如果碎了，那么 f 在 0 和 8 之间。将第二枚从 1 楼扔下，然后每扔一次上一层楼，在 8 次内找到 f 。总操作次数 = 1 + 8 = 9 。
     * - 如果第一枚鸡蛋没有碎，那么再把第一枚鸡蛋从 22 层扔下。
     * 如果碎了，那么 f 在 9 和 21 之间。将第二枚鸡蛋从 10 楼扔下，然后每扔一次上一层楼，在 12 次内找到 f 。总操作次数 = 2 + 12 = 14 。
     * - 如果第一枚鸡蛋没有再次碎掉，则按照类似的方法从 34, 45, 55, 64, 72, 79, 85, 90, 94, 97, 99 和 100 楼分别扔下第一枚鸡蛋。
     * 不管结果如何，最多需要扔 14 次来确定 f 。
     * 1. 这个感觉像是爬楼梯了, 只不过每次爬的步数加1, 例如第一次爬1步, 第二次爬2步, 第三次爬3步, 以此类推
     * 1 + 2 + 3 + 4 + ... + n >= 100, 计算 n 的值 (1 + n) * n  = 200 => n = 14
     * (1 + n) * n > 4 => n = 2,
     * 2. (1 + f) * f >= 2 * n => f?
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/18 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {

        return (int) Math.ceil((-1 + Math.sqrt(1 + 8 * n)) / 2);
    }

    /**
     * @Description:
     * 使用动态规划解决3个鸡蛋的问题
     * dp[i][j] 表示用 i 个鸡蛋测试 j 层楼时所需的最少实验次数
     * @param: n 楼层数
     * @return int 最少操作次数
     * @author marks
     * @CreateDate: 2025/11/18 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_dp_3eggs(int n) {
        // dp[i][j] 表示用 i 个鸡蛋测试 j 层楼时所需的最少实验次数
        int[][] dp = new int[4][n + 1]; // 只需要用到 0~3 个鸡蛋的情况

        // 初始化：只有1个鸡蛋的情形
        for (int f = 0; f <= n; f++) {
            dp[1][f] = f; // 只有一个鸡蛋时，只能线性查找
        }

        // 自底向上填表
        for (int e = 2; e <= 3; e++) { // 2个和3个鸡蛋的情况
            for (int f = 1; f <= n; f++) {
                dp[e][f] = Integer.MAX_VALUE;
                for (int x = 1; x <= f; x++) {
                    // 在第x层扔鸡蛋
                    // 如果碎了：剩下 e-1 个鸡蛋，需要测 x-1 层
                    // 如果没碎：还有 e 个鸡蛋，需要测上面 f-x 层
                    int res = Math.max(dp[e - 1][x - 1], dp[e][f - x]) + 1;
                    if (res < dp[e][f]) {
                        dp[e][f] = res;
                    }
                }
            }
        }

        return dp[3][n];
    }

}
