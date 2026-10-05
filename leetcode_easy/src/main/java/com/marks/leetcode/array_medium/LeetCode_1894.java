package com.marks.leetcode.array_medium;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1894 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/22 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1894 {

    /**
     * @Description:
     * 一个班级里有 n 个学生，编号为 0 到 n - 1 。
     * 每个学生会依次回答问题，编号为 0 的学生先回答，然后是编号为 1 的学生，以此类推，直到编号为 n - 1 的学生，
     * 然后老师会重复这个过程，重新从编号为 0 的学生开始回答问题。
     * 给你一个长度为 n 且下标从 0 开始的整数数组 chalk 和一个整数 k 。
     * 一开始粉笔盒里总共有 k 支粉笔。当编号为 i 的学生回答问题时，他会消耗 chalk[i] 支粉笔。
     * 如果剩余粉笔数量 严格小于 chalk[i] ，那么学生 i 需要 补充 粉笔。
     * 请你返回需要 补充 粉笔的学生 编号 。
     *
     * tips:
     * chalk.length == n
     * 1 <= n <= 10^5
     * 1 <= chalk[i] <= 10^5
     * 1 <= k <= 10^9
     * @param: chalk
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/09/22 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int chalkReplacer(int[] chalk, int k) {
        int result;
        result = method_01(chalk, k);
        return result;
    }

    /**
     * @Description:
     * 1. 求出 sum(chalk), 然后更新 k = k % sum(chalk)
     * 2. 之后进行模拟操作, 找到第一个学生, 该学生消耗的粉笔数量 > k, 返回该学生编号
     * AC: 2ms/80.14MB
     * @param: chalk
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/09/22 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] chalk, int k) {
        long sum = 0;
        for (int i : chalk) {
            sum += i;
        }
        long remainder = k % sum;
        for (int i = 0; i < chalk.length; i++) {
            remainder -= chalk[i];
            if (remainder < 0) {
                return i;
            }
        }
        return -1;
    }

}
