package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2310 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/27 14:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2310 {

    /**
     * @Description:
     * 给你两个整数 num 和 k ，考虑具有以下属性的正整数多重集：
     * 每个整数个位数字都是 k 。
     * 所有整数之和是 num 。
     * 返回该多重集的最小大小，如果不存在这样的多重集，返回 -1 。
     * 注意：
     * 多重集与集合类似，但多重集可以包含多个同一整数，空多重集的和为 0 。
     * 个位数字 是数字最右边的数位。
     *
     * tips:
     * 0 <= num <= 3000
     * 0 <= k <= 9
     * @param: num
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/03/27 14:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumNumbers(int num, int k) {
        int result;
        result = method_01(num, k);
        return result;
    }

    /**
     * @Description:
     * 这题应该算是贪心, 反正我没有用动态规划处理
     * AC: 0ms/41.41MB
     * @param: num
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/03/27 14:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int num, int k) {
        // 处理特殊情况0
        if (num == 0) {
            return 0;
        }
        // 判断 num 和 k
        if (num < k) {
            return -1;
        }
        // 然后取num 的个位数字
        int target = num % 10;
        // 判断 1 ~ 10 个 k 得到的结果是否等于 target
        boolean flag = false;
        int sum = 0;
        int count = 1;
        while (count <= 10 && sum <= num) {
            sum += k;
            if (sum % 10 == target) {
                flag = true;
                break;
            }
            count++;
        }
        if (!flag) {
            return -1;
        }

        return count;
    }

}
