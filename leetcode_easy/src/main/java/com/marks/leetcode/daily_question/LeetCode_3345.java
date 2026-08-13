package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3345 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/6 10:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3345 {

    /**
     * @Description:
     * 给你两个整数 n 和 t 。请你返回大于等于 n 的 最小 整数，且该整数的 各数位之积 能被 t 整除。
     * @param: n
     * @param: t
     * @return int
     * @author marks
     * @CreateDate: 2026/08/06 10:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int smallestNumber(int n, int t) {
        int result;
        result = method_01(n, t);
        return result;
    }

    /**
     * @Description:
     * 1. 0 必定能被 t 整除, 所以就会有一个范围, 可以进行枚举范围是否有更优解
     * 2. 例如 n = 15, t = 3, 那么枚举的范围是 15 => 24
     * 3. 也就是枚举 10 个数, 然后判断该数是否能被 t 整除
     * AC: 1ms/41.91MB
     * @param: n
     * @param: t
     * @return int
     * @author marks
     * @CreateDate: 2026/08/06 10:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int t) {
        // 由于10位以上的数字不会被修改, 所以可以提前得到结果
        int prev = n / 10;
        int tMuti = 1;
        int mod = 10;
        while (prev != 0) {
            int temp = prev % mod;
            tMuti *= temp;
            prev /= mod;
        }
        if (tMuti == 0) {
            return n;
        }
        for (int i = 0; i < 10; i++) {
            int curr = n + i;
            // 获取个位数值
            int muti = curr % mod;
            if ((tMuti * muti) % t == 0) {
                return curr;
            }
        }

        return 0;
    }

}
