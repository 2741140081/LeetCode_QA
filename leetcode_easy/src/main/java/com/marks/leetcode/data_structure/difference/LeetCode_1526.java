package com.marks.leetcode.data_structure.difference;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/25 11:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1526 {
    /**
     * @Description:
     * 给你一个整数数组 target 和一个数组 initial ，initial 数组与 target  数组有同样的维度，且一开始全部为 0 。
     *
     * 请你返回从 initial 得到  target 的最少操作次数，每次操作需遵循以下规则：
     *
     * 在 initial 中选择 任意 子数组，并将子数组中每个元素增加 1 。
     * 答案保证在 32 位有符号整数以内。
     *
     * tips:
     * 1 <= target.length <= 10^5
     * 1 <= target[i] <= 10^5
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2025/2/25 11:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minNumberOperations(int[] target) {
        int result;
        result = method_01(target);
        return result;
    }

    /**
     * @Description:
     * E1:
     * target = [1,2,3,2,1]
     * 怎么差分? [1,1,1,-1,-1]
     *
     * AC: 4ms/55.86MB
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2025/2/25 11:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] target) {
        int ans = target[0];
        for (int i = 1; i < target.length; i++) {
            ans += (Math.max(target[i] - target[i - 1], 0));
        }
        return ans;
    }
}
