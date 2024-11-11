package com.marks.leetcode.double_pointer.single_sequence;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/11 10:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_18 {
    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description: [
     * 小扣在秋日市集选择了一家早餐摊位，一维整型数组 staple 中记录了每种主食的价格，一维整型数组 drinks 中记录了每种饮料的价格。小扣的计划选择一份主食和一款饮料，且花费不超过 x 元。请返回小扣共有多少种购买方案。
     *
     * 注意：答案需要以 1e9 + 7 (1000000007) 为底取模，如：计算初始结果为：1000000008，请返回 1
     *
     * tips:
     * 1 <= staple.length <= 10^5
     * 1 <= drinks.length <= 10^5
     * 1 <= staple[i],drinks[i] <= 10^5
     * 1 <= x <= 2*10^5
     * ]
     * @param staple
     * @param drinks
     * @param x
     * @return int
     * @author marks
     * @CreateDate: 2024/11/11 10:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int breakfastNumber(int[] staple, int[] drinks, int x) {
        int result;
        result = method_01(staple, drinks, x);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：staple = [2,1,1], drinks = [8,9,5,1], x = 9
     * [1, 1, 2]
     * [1, 5, 8, 9]
     * 输出：8
     *
     * AC:77ms/63.54MB
     * ]
     * @param staple
     * @param drinks
     * @param x
     * @return int
     * @author marks
     * @CreateDate: 2024/11/11 10:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] staple, int[] drinks, int x) {
        // 先排序
        Arrays.sort(staple);
        Arrays.sort(drinks);
        int m = staple.length;
        int n = drinks.length;
        int left = 0;
        int right = n - 1;
        int ans = 0;
        while (left < m && right >= 0) {
            if (staple[left] + drinks[right] <= x) {
                ans = (ans + right + 1) % MOD;
                left++;
            }else {
                right--;
            }
        }
        return ans;
    }
}
