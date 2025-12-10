package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3577 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/10 15:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3577 {

    /**
     * @Description:
     * 给你一个长度为 n 的数组 complexity。
     * 在房间里有 n 台 上锁的 计算机，这些计算机的编号为 0 到 n - 1，每台计算机都有一个 唯一 的密码。
     * 编号为 i 的计算机的密码复杂度为 complexity[i]。
     *
     * 编号为 0 的计算机密码已经 解锁 ，并作为根节点。其他所有计算机必须通过它或其他已经解锁的计算机来解锁，具体规则如下：
     *
     * 可以使用编号为 j 的计算机的密码解锁编号为 i 的计算机，其中 j 是任何小于 i 的整数，
     * 且满足 complexity[j] < complexity[i]（即 j < i 并且 complexity[j] < complexity[i]）。
     * 要解锁编号为 i 的计算机，你需要事先解锁一个编号为 j 的计算机，满足 j < i 并且 complexity[j] < complexity[i]。
     * 求共有多少种 [0, 1, 2, ..., (n - 1)] 的排列方式，能够表示从编号为 0 的计算机（唯一初始解锁的计算机）开始解锁所有计算机的有效顺序。
     *
     * 由于答案可能很大，返回结果需要对 10^9 + 7 取余数。
     *
     * 注意：编号为 0 的计算机的密码已解锁，而 不是 排列中第一个位置的计算机密码已解锁。
     *
     * 排列 是一个数组中所有元素的重新排列。
     * @param: complexity
     * @return int
     * @author marks
     * @CreateDate: 2025/12/10 15:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countPermutations(int[] complexity) {
        int result;
        result = method_01(complexity);
        return result;
    }

    /**
     * @Description:
     * 如果i = 0, complexity[0] 是最小的元素, 那么可以使用计算机0来解锁所有的计算机
     * AC: 2ms/90.14MB
     * @param: complexity
     * @return int
     * @author marks
     * @CreateDate: 2025/12/10 15:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] complexity) {
        // 创建MOD
        final int MOD = (int) 1e9 + 7;
        // 判断complexity[0] 是否是最小的元素, 并且是唯一最小值
        int min = complexity[0];
        int n = complexity.length;
        for (int i = 1; i < n; i++) {
            if (complexity[i] <= min) {
                // 如果有最小值小于complexity[0], 那么complexity[0] 不是最小的元素, 表示这台计算机永远无法解锁
                return 0;
            }
        }
        // 已经证明complexity[0] 是最小的元素, 那么 complexity[0] 可以解锁所有的计算机
        // 这个应该是 (n - 1)!
        long ans = 1;
        for (int i = 1; i < n; i++) {
            ans = (ans * i) % MOD;
        }

        return (int) (ans % MOD);
    }

}
