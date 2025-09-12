package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/10 10:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3226 {

    /**
     * @Description:
     * 给你两个正整数 n 和 k。
     *
     * 你可以选择 n 的 二进制表示 中任意一个值为 1 的位，并将其改为 0。
     *
     * 返回使得 n 等于 k 所需要的更改次数。如果无法实现，返回 -1。
     *
     * tips:
     * 1 <= n, k <= 10^6
     * @param n 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/9/10 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minChanges(int n, int k) {
        int result;
        result = method_01(n, k);
        result = method_02(n, k);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入： n = 13, k = 4
     * 1. 通过位运行进行求解, 根据E1, n = 13, k = 4, 13 = 1101, 4 = 0100
     * 2. 进行位运算,
     * 1101 & 0100 = 0100, 1101 | 0100 = 1101, 1101 & 1101 = 1101, (n | k) & n != n, return -1;
     * 3. 异或 "^", 1101 ^ 0100 = 1001
     * E2: n = 14, k = 13, 1110 & 1101 = 1100, 0100
     *
     * AC: 0ms/40.03MB
     * @param n
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/9/10 11:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n, int k) {
        // 检查n的位为0, k的位为1, 不满足条件得情况, 返回-1
        if ((n & k) != k) {
            return -1;
        }
        // 当前 n ^ k 和 n & ~k 的结果相同, 但是 n & -k 更符合题意, 因为n & -k, 找的是 n 中为1, k中为0的数量
        return Integer.bitCount(n & ~k);
    }


    /**
     * @Description:
     * E1:
     * 输入： n = 13, k = 4
     * 输出： 2
     * E2:
     * 输入： n = 14, k = 13
     * 输出： -1
     * 1110
     * 1101
     * @param n 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/10 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int k) {
        if (k > n) {
            return -1;
        }
        String str1 = Integer.toBinaryString(n);
        String str2 = Integer.toBinaryString(k);
        // str1.length() >= str2.length()
        int ans = 0;
        // 双指针
        int left = str1.length() - 1, right = str2.length() - 1;
        while (left >= 0) {
            if (right >= 0) {
                if (str1.charAt(left) != str2.charAt(right)) {
                    if (str1.charAt(left) == '1') {
                        ans++;
                    } else {
                        // str1[left] = 0 && str2[right] = 1, so we can't change str1[left] to 1
                        return -1;
                    }
                }
                right--;
            } else {
                if (str1.charAt(left) == '1') {
                    ans++;
                }
            }
            left--;
        }
        return ans;
    }

}
