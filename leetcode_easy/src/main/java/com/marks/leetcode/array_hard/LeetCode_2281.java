package com.marks.leetcode.array_hard;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2281 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/6 16:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2281 {

    /**
     * @Description:
     * 作为国王的统治者，你有一支巫师军队听你指挥。
     * 给你一个下标从 0 开始的整数数组 strength ，其中 strength[i] 表示第 i 位巫师的力量值。
     * 对于连续的一组巫师（也就是这些巫师的力量值是 strength 的 子数组），总力量 定义为以下两个值的 乘积 ：
     * 巫师中 最弱 的能力值。
     * 组中所有巫师的个人力量值 之和 。
     * 请你返回 所有 巫师组的 总 力量之和。
     * 由于答案可能很大，请将答案对 10^9 + 7 取余 后返回。
     * 子数组 是一个数组里 非空 连续子序列。
     * tips:
     * 1 <= strength.length <= 10^5
     * 1 <= strength[i] <= 10^9
     * @param: strength
     * @return int
     * @author marks
     * @CreateDate: 2026/08/06 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int totalStrength(int[] strength) {
        int result;
        result = method_01(strength);
        return result;
    }

    /**
     * @Description:
     * 1. 需要分析 递增序列, 递减序列 以及凹形序列时计算 i 处的力量值之和
     * 2. 先分析递增序列, 假设 [i, j] 是一个递增序列, s[i] 是序列的最小值, 可以得到一个贡献度, 即以s[i] 为起始点, s[j] 为终点
     * 这样一个序列的力量值总和是 s[i] * (j - i + 1) + s[i + 1] * (j - i) + ..... + s[j]
     * @param: strength
     * @return int
     * @author marks
     * @CreateDate: 2026/08/06 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] strength) {

        return 0;
    }

}
