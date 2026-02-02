package com.marks.leetcode.divide_and_conquer;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_932 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/28 14:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_932 {

    /**
     * @Description:
     * 如果长度为 n 的数组 nums 满足下述条件，则认为该数组是一个 漂亮数组 ：
     * nums 是由范围 [1, n] 的整数组成的一个排列。
     * 对于每个 0 <= i < j < n ，均不存在下标 k（i < k < j）使得 2 * nums[k] == nums[i] + nums[j] 。
     * 给你整数 n ，返回长度为 n 的任一 漂亮数组 。本题保证对于给定的 n 至少存在一个有效答案。
     * @param: n
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/28 14:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] beautifulArray(int n) {
        int[] result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 5
     * 输出：[3,1,2,5,4]
     * 1. [1,2,3,4,5,6] => 分割为3个部分 [1,2] [3,4] [5,6] 交换 [3,4] [1,2]
     * 2. 查看官方题解得到一个结论 2 * nums[k] == nums[i] + nums[j];如果需要不满足这个条件, 如何处理 i, j;
     * 就需要 nums[i] 是一个奇数, nums[j] 是一个偶数;
     * 3. 差不多能理解了, [1] => [1,2] => [1,3,2,4] => [1, 5, 3, 2, 6, 4]
     * AC: 0ms/42.98MB
     * need todo(当前是查看官方题解得到的代码, 需要自行理解)
     * @param: n
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/28 14:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private Map<Integer, int[]> memo;
    private int[] method_01(int n) {
        memo = new HashMap<>();
        return fun(n);
    }

    private int[] fun(int n) {
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        int[] ans = new int[n];
        if (n == 1) {
            ans[0] = 1;
        } else {
            int t = 0;
            int[] left = fun((n + 1) / 2);
            int[] right = fun(n / 2);
            for (int x : left) { // 奇数
                ans[t++] = 2 * x - 1;
            }
            for (int x : right) { // 偶数
                ans[t++] = 2 * x;
            }
        }
        memo.put(n, ans);
        return ans;
    }

}
