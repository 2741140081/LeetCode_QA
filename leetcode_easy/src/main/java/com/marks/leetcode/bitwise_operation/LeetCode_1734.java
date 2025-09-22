package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/22 15:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1734 {

    
    /**
     * @Description:
     * 给你一个整数数组 perm ，它是前 n 个正整数的排列，且 n 是个 奇数 。
     * 它被加密成另一个长度为 n - 1 的整数数组 encoded ，满足 encoded[i] = perm[i] XOR perm[i + 1] 。
     * 比方说，如果 perm = [1,3,2] ，那么 encoded = [2,1] 。
     *
     * 给你 encoded 数组，请你返回原始数组 perm 。题目保证答案存在且唯一。
     *
     * tips:
     * 3 <= n < 10^5
     * n 是奇数。
     * encoded.length == n - 1
     * @param encoded
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/22 15:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] decode(int[] encoded) {
        int[] result;
        result = method_01(encoded);
        result = method_02(encoded);
        return result;
    }

    /**
     * @Description:
     * 查看官方题解!!!
     * 详细思路见 method_01 (7 ~ 8)
     *
     * AC: 3ms/62.33MB
     * @param encoded
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/22 16:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int[] encoded) {
        int total = 0;
        int n = encoded.length + 1;
        for (int i = 1; i <= n; i++) {
            total ^= i;
        }
        int odd = 0;
        for (int i = 1; i < encoded.length; i += 2) {
            odd ^= encoded[i];
        }
        int[] perm = new int[n];
        perm[0] = total ^ odd;
        for (int i = 1; i < n; i++) {
            perm[i] = perm[i - 1] ^ encoded[i - 1];
        }
        return perm;
    }


    /**
     * @Description:
     * E1:
     * 输入：encoded = [6,5,4,6]
     * 输出：[2,4,1,5,3]
     * 1. 前n个整数的排列, 即 int[] perm 存储 1 ~ n 个数, 并且 n 是奇数
     * 2. 如果我专注于突破 encoded[0], encoded[0] = perm[0] XOR perm[1], prem[0] 和 prem[1] 有多少种可能性, 太笨了, 不太行
     * 3. e[0] ^ e[1] = p[0] ^ p[1] ^ p[1] ^ p[2], 得到 e[0] ^ e[1]^...^e[n - 1] = p[0] ^ p[n] => p[0] = x ^ p[n]
     * 4. 所以我需要一个encoded 的前缀异或和, int[] prevXor = new int[encoded.length + 1]; prevXor[i + 1] = prevXor[i] ^ encoded[i];
     * 5. p[n] = prevXor[n] ^ prevXor[0], p[n - 1] = prevXor[n - 1] ^ prevXor[0] ..., p[1] = prevXor[1] ^ prevXor[0]
     * 6. p[0] + p[1] + ... + p[n] = ((1 + n) * n)/2, 超时!!!
     * 7. 原来 p[0] 可以被计算出来, e[0] = p[0] ^ p[1], e[1] = p[1] ^ p[2], 但是如果我只计算e[1], e[3], e[n - 1], 这些奇数的异或,
     * odd = e[1] ^ e[3] ^ .... ^ e[n - 1] = p[1] ^ p[2] ^ p[3]^....p[n], total = p[0] ^ p[1] ^ .... ^ p[n]
     * 8. p[0] = total ^ odd; amazing!!!
     * @param encoded 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/22 15:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] encoded) {
        int n = encoded.length + 1;
        int[] prevXor = new int[encoded.length + 1];
        for (int i = 0; i < encoded.length; i++) {
            prevXor[i + 1] = prevXor[i] ^ encoded[i];
        }
        int sum = ((1 + n) * n)/2;

        for (int i = 1; i <= n; i++) {
            int t = 0;
            for (int j = 0; j < n; j++) {
                t += (i ^ prevXor[j]);
            }
            if (t == sum) {
                int[] result = new int[n];
                result[0] = i;
                for (int j = 1; j < n; j++) {
                    result[j] = result[j - 1] ^ encoded[j - 1];
                }
                return result;
            }
        }
        return new int[0];
    }

}
