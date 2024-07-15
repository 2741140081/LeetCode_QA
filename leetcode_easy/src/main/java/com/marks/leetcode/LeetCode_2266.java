package com.marks.leetcode;

public class LeetCode_2266 {
    /**
     * E1:
     * 输入：pressedKeys = "22233"
     * 输出：8
     * 解释：
     * Alice 可能发出的文字信息包括：
     * "aaadd", "abdd", "badd", "cdd", "aaae", "abe", "bae" 和 "ce" 。
     * 由于总共有 8 种可能的信息，所以我们返回 8 。
     *
     * @param pressedKeys
     * @return
     */
    public int countTexts(String pressedKeys) {
        int result = 0;
        result = method_01(pressedKeys);
        return result;

    }

    private int method_01(String pressedKeys) {
        final int MOD = 1000000007;
        if (pressedKeys.length()<=1) {
            return 1;
        }
        int dp_length = pressedKeys.length() >= 4 ? pressedKeys.length() : 4;
        long[] dp_3 = new long[dp_length + 1];
        long[] dp_4 = new long[dp_length + 1];
        dp_3[0] = dp_4[0] = 1;
        dp_3[1] = dp_4[1] = 1;
        dp_3[2] = dp_4[2] = 2;
        dp_3[3] = dp_4[3] = 4;
        //初始化
        for (int i = 4; i <= pressedKeys.length(); i++) {
            dp_3[i] = (dp_3[i-1] + dp_3[i-2] + dp_3[i-3]) % MOD;
            dp_4[i] = (dp_4[i-1] + dp_4[i-2] + dp_4[i-3] + dp_4[i-4]) % MOD;
        }
        if (pressedKeys.length() <= 1) {
            return 1;
        }
        long ans = 1;
        int count = 1;
        char[] chars = pressedKeys.toCharArray();
        int len = chars.length;

        for (int i = 1; i < len; i++) {
            if (chars[i] == chars[i-1]) {
                count++;
            }else {
                if (chars[i-1] == '7' || chars[i-1] == '9') {
                    ans = (ans * dp_4[count]) % MOD;
                }else {
                    ans = (ans * dp_3[count]) % MOD;
                }
                ans = ans % MOD;
                count = 1;
            }
        }
        // 更新最后一段连续字符子串对应的方案数
        if (chars[len -1] == '7' || chars[len-1] == '9') {
            ans = ans * dp_4[count];
        }else {
            ans = ans * dp_3[count];
        }
        ans = ans % MOD;

        return (int) ans;
    }
}
