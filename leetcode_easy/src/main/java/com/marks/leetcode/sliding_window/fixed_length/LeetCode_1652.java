package com.marks.leetcode.sliding_window.fixed_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/11 16:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1652 {
    /**
     * @Description: [
     * 你有一个炸弹需要拆除，时间紧迫！你的情报员会给你一个长度为 n 的 循环 数组 code 以及一个密钥 k 。
     *
     * 为了获得正确的密码，你需要替换掉每一个数字。所有数字会 同时 被替换。
     *
     * 如果 k > 0 ，将第 i 个数字用 接下来 k 个数字之和替换。
     * 如果 k < 0 ，将第 i 个数字用 之前 k 个数字之和替换。
     * 如果 k == 0 ，将第 i 个数字用 0 替换。
     * 由于 code 是循环的， code[n-1] 下一个元素是 code[0] ，且 code[0] 前一个元素是 code[n-1] 。
     *
     * 给你 循环 数组 code 和整数密钥 k ，请你返回解密后的结果来拆除炸弹！
     *
     * tips:
     * n == code.length
     * 1 <= n <= 100
     * 1 <= code[i] <= 100
     * -(n - 1) <= k <= n - 1
     * ]
     * @param code
     * @param k
     * @return int[]
     * @author marks
     * @CreateDate: 2024/10/11 16:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] decrypt(int[] code, int k) {
        int[] result;
        result = method_01(code, k);
        result = method_02(code, k);
        return result;
    }
    /**
     * @Description: [
     * 官方题解:
     *
     * ]
     * @param code
     * @param k
     * @return int[]
     * @author marks
     * @CreateDate: 2024/10/11 17:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int[] code, int k) {
        int n = code.length;
        if (k == 0) {
            return new int[n];
        }
        int[] res = new int[n];
        int[] newCode = new int[n * 2];
        System.arraycopy(code, 0, newCode, 0, n);
        System.arraycopy(code, 0, newCode, n, n);
        code = newCode;
        int l = k > 0 ? 1 : n + k;
        int r = k > 0 ? k : n - 1;
        int w = 0;
        for (int i = l; i <= r; i++) {
            w += code[i];
        }
        for (int i = 0; i < n; i++) {
            res[i] = w;
            w -= code[l];
            w += code[r + 1];
            l++;
            r++;
        }
        return res;
    }

    /**
     * @Description: [
     * E1:
     * 输入：code = [5,7,1,4], k = 3
     * 输出：[12,10,16,13]
     * 解释：每个数字都被接下来 3 个数字之和替换。解密后的密码为 [7+1+4, 1+4+5, 4+5+7, 5+7+1]。注意到数组是循环连接的。
     *
     * AC:0ms/41.40MB
     * ]
     * @param code
     * @param k
     * @return int[]
     * @author marks
     * @CreateDate: 2024/10/11 16:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] code, int k) {
        int n = code.length;
        int[] ans = new int[n];
        if (k == 0) {
            return ans;
        }else if (k > 0) {
            int sum = 0;
            // 还是扩大吧
            int[] pre = new int[2 * n];
            for (int i = 0; i < n; i++) {
                pre[i] = pre[i + n] = code[i];
            }
            for (int i = 1; i <= k; i++) {
                sum += pre[i];
            }
            ans[0] = sum;
            for (int i = k + 1; i < n + k; i++) {
                sum = sum + pre[i] - pre[i - k];
                ans[i - k] = sum;
            }
        } else {
            // k < 0
            int sum = 0;
            // 还是扩大吧
            k = Math.abs(k);
            int[] pre = new int[2 * n];
            for (int i = 0; i < n; i++) {
                pre[i] = pre[i + n] = code[i];
            }
            for (int i = 2 * n - 2; i >= 2 * n - 1 - k; i--) {
                sum += pre[i];
            }
            ans[n - 1] = sum;
            for (int i = 2 * n - 2 - k; i >= n - k; i--) {
                sum = sum + pre[i] - pre[i + k];
                ans[i - n + k] = sum;
            }
        }
        return ans;
    }
}
