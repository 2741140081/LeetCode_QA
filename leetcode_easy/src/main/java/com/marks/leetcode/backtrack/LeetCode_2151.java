package com.marks.leetcode.backtrack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/19 10:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2151 {

    
    /**
     * @Description:
     * 游戏中存在两种角色：
     * 好人：该角色只说真话。
     * 坏人：该角色可能说真话，也可能说假话。
     * 给你一个下标从 0 开始的二维整数数组 statements ，大小为 n x n ，表示 n 个玩家对彼此角色的陈述。具体来说，statements[i][j] 可以是下述值之一：
     *
     * 0 表示 i 的陈述认为 j 是 坏人 。
     * 1 表示 i 的陈述认为 j 是 好人 。
     * 2 表示 i 没有对 j 作出陈述。
     * 另外，玩家不会对自己进行陈述。形式上，对所有 0 <= i < n ，都有 statements[i][i] = 2 。
     *
     * 根据这 n 个玩家的陈述，返回可以认为是 好人 的 最大 数目。
     *
     * @param statements
     * @return int
     * @author marks
     * @CreateDate: 2025/8/19 10:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumGood(int[][] statements) {
        int result;
        result = method_01(statements);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入：statements = [[2,1,2],[1,2,2],[2,0,2]]
     * 输出：2
     * 1. 还是用回溯法, 取 s[index] 是一个好人, s[index][j] = 1 的全部是好人, s[index][j] = 0 的全部是坏人, 使用 good[n] 和 bad[n] 记录
     * 2. boolean flag, 表示当前 s[index] 是好人还是坏人, 基于 good[index] = 1, 表示是好人, 则 flag = true, 否则 bad[index] = 1, 则 flag = false
     * 3. 对于好人, 需要全部相信, 表述中的好人和坏人全部记录到 good[] 和 bad[], 对于坏人, 则只相信其表述中为1的部分, 即仅记录 good[j]
     * 4. 对于 s[index], 可以选择当前是好人还是坏人, 规则与3相同
     * 5. just do it!
     * 6. 存在一点小问题, 即如果所有人都对其他人没有表述, 那么我们就可以认为所有人都是好人, sum(bad[i]) = 0,
     * 也就是说我们可以通过计算 bad[i] 数组的总和来得到坏人的数量, 当坏人数量最小时, 好人数量就是最大值, 需要改一下sum, 记录坏人的数量,
     * 7. 还是不对啊, 如果我们不取任意一个人, 我们如果取当前是好人, 那么sum - 1, sum - 1 + temp,
     * 8. 重新理一理思路, 整个系统中只存在两种人, 好人 or 坏人,
     * 如何确定一个人是坏人?
     *  a. 假设当前 s[index] 是好人, 那么 s[index][j] = 1, 表示 s[index] 认为 j 是好人, s[index][j] = 0 是坏人
     *  b. 直接假设当前人是坏人, 那么 bad[index] = 1, 还是需要通过sum来计算坏人的数量,
     * 9. 等一下, 遇到一个测试用例, 该用例揭示了, 当遇到冲突时, 不应该仅仅只是将 s[index] 设置为坏人,
     * 假设当前 s[1] 是好人, 并且认为 s[0] 和 s[2] 都是好人, 当我们遍历到 s[2] 时, 因为 s[2] 是好人, 但是 s[2] 认为 s[0] 是坏人,
     * 这就导致冲突了
     * 10. 所以 在假设 s[index] 是好人之前, 我们需要先去看 good[index] 是否是一个好人, 如果是好人, 我们我们需要全盘接收s[index] 的表述,
     * 并且判断其表述是否与现有结论冲突, 即 s[j] = 1, bad[j] = 1, 那么此时就冲突, s[j] = 0, good[j] = 1, 那么也冲突了, 这种就需要提前剪枝返回
     *
     * AC: 22ms(51.49%)/43.85MB(5.36%)
     * @param statements 
     * @return int
     * @author marks
     * @CreateDate: 2025/8/19 10:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int n;
    private int ans;
    private int method_01(int[][] statements) {
        ans = 0;
        n = statements.length;
        int[] good = new int[n];
        int[] bad = new int[n];
        int sum = 0; // 当前好人的数量
        backtrack(statements, good, bad, sum, 0);
        return ans;
    }

    private void backtrack(int[][] statements, int[] good, int[] bad, int sum, int index) {
        if (index == n) {
            ans = Math.max(ans, n - sum);
            return;
        }

        if (good[index] == 1) {
            // 验证当前好人的表述是否与现有结论冲突
            boolean valid = false; // 默认不冲突
            // 当前 s[index] 是好人, 需要全盘接收其表述, 验证其表述是否与现有结论冲突
            for (int j = 0; j < n; j++) {
                if (statements[index][j] == 1 && bad[j] == 1) {
                    // s[index] 表述 j 是好人, 但是现有结论 j 是坏人, 冲突
                    valid = true;
                } else if (statements[index][j] == 0 && good[j] == 1) {
                    // s[index] 表述 j 是坏人, 但是现有结论 j 是好人, 冲突
                    valid = true;
                }
            }
            if (valid) {
                // 由于冲突, 需要提前结束
                return;
            }
        }

        boolean flag = true;
        // 判断当前 s[index] 是否可以取好人, 即 s[i][j] 与 bad[] 和 good[] 是否冲突
        for (int j = 0; j < n; j++) {
            if (statements[index][j] == 1 && bad[j] == 1) {
                flag = false;
                break;
            } else if (statements[index][j] == 0 && good[j] == 1) {
                flag = false;
                break;
            }
        }
        if (flag) {
            // 取当前 s[index] 为好人
            int[] goodCopy = good.clone();
            int[] badCopy = bad.clone();
            int temp = 0;
            for (int j = 0; j < n; j++) {
                if (statements[index][j] == 1 && goodCopy[j] == 0) {
                    // 该好人是新增的
                    goodCopy[j] = 1;
                } else if (statements[index][j] == 0 && badCopy[j] == 0) {
                    // 该坏人是新增的
                    badCopy[j] = 1;
                    temp++;
                }
            }
            goodCopy[index] = 1;
            backtrack(statements, goodCopy, badCopy, sum + temp, index + 1);
            // 不取当前 s[index] 为好人, 那么当前s[index] 就只能是坏人了
        }
        // 如果当前s[index] 取坏人, 是否与现有结论冲突?
        if (good[index] == 1) {
            return;
        }
        // 如果不冲突, 表示s[index] 可以取坏人
        if (bad[index] == 0) {
            bad[index] = 1;
            sum++;
        }
        backtrack(statements, good, bad, sum, index + 1);
    }

}
