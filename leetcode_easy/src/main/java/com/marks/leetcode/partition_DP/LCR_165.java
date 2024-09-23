package com.marks.leetcode.partition_DP;

import java.util.ArrayList;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/23 16:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_165 {
    private ArrayList<String> list = new ArrayList<>();
    /**
     * @Description: [
     * 现有一串神秘的密文 ciphertext，经调查，密文的特点和规则如下：
     *
     * 密文由非负整数组成
     * 数字 0-25 分别对应字母 a-z
     * 请根据上述规则将密文 ciphertext 解密为字母，并返回共有多少种解密结果。
     *
     * tips:
     * 0 <= ciphertext < 231
     * ]
     * @param ciphertext
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 16:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int crackNumber(int ciphertext) {
        int result = 0;
        result = method_01(ciphertext);
        return result;
    }
    /**
     * @Description: [
     * 0-25 数字对应 a ~ z
     * AC:1ms/39.51MB
     *
     * ]
     * @param ciphertext
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 16:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int ciphertext) {
        String str = ciphertext + "";
        int n = str.length();
        for (int i = 0; i < 26; i++) {
            list.add(i + "");
        }
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            if (list.contains(str.substring(i - 1, i))) {
                dp[i] += dp[i - 1];
            }
            if (list.contains(str.substring(i - 2, i))) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[n];
    }
}
