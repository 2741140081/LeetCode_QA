package com.marks.leetcode.daily_question;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1545 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/3 14:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1545 {

    /**
     * @Description:
     * 给你两个正整数 n 和 k，二进制字符串  Sn 的形成规则如下：
     *
     * S1 = "0"
     * 当 i > 1 时，Si = Si-1 + "1" + reverse(invert(Si-1))
     * 其中 + 表示串联操作，reverse(x) 返回反转 x 后得到的字符串，而 invert(x) 则会翻转 x 中的每一位（0 变为 1，而 1 变为 0）。
     *
     * 例如，符合上述描述的序列的前 4 个字符串依次是：
     *
     * S1 = "0"
     * S2 = "011"
     * S3 = "0111001"
     * S4 = "011100110110001"
     * 请你返回  Sn 的 第 k 位字符 ，题目数据保证 k 一定在 Sn 长度范围以内。
     *
     * 1 <= n <= 20
     * 1 <= k <= 2^n - 1
     * @param: n
     * @param: k
     * @return char
     * @author marks
     * @CreateDate: 2026/03/03 14:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public char findKthBit(int n, int k) {
        char result;
        result = method_01(n, k);
        result = method_02(n, k);
        return result;
    }

    /**
     * @Description:
     * 1. 查看官方题解, 使用递归来解决
     * 2. 先判断 k 存在与字符串的左右两侧的那个位置上 [] 1 [], sn 的长度是 2^n - 1, 中间值 1 的下标 是 mid1 = 2^(n - 1) - 1
     * 3. 判断 k 与 mid1 进行比较, 如果 k < 2^(n - 1), 则 k 在字符串的左侧, 反之, 则 k 在字符串的右侧
     * 4. 考虑右侧的情况, 如何将右侧转为对应的左侧 mid - (k - mid1) => nextK = 2 * mid1 - k, 并且用 count 记录反转次数
     * 5. 然后递归执行
     * @param: n
     * @param: k
     * @return char
     * @author marks
     * @CreateDate: 2026/03/03 15:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private char method_02(int n, int k) {
        if (k == 1) {
            return '0';
        }
        int mid = 1 << (n - 1); // 这个坐标算是从 1 开始算, 如果从0 开始算, 则 mid = 1 << (n - 1) - 1
        if (k == mid) {
            return '1';
        } else if (k < mid) {
            return findKthBit(n - 1, k);
        } else {
            k = mid * 2 - k;
            return invert(findKthBit(n - 1, k));
        }
    }

    public char invert(char bit) {
        return (char) ('0' + '1' - bit); // 相当于 bit ^ 1
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 3, k = 1
     * 输出："0"
     * 解释：S3 为 "0111001"，其第 1 位为 "0" 。
     * 1. 直接模拟
     * 2. 用 List<Integer> list 存储字符串, invert(list.get(i)), 这个能用异或操作吗? x ^ 1; 1 ^ 1 = 0; 0 ^ 1 = 1;
     * 3. 直接开始模拟, 对于 i 在 [0 ~ 2]
     * AC: 75ms/95.38MB
     * 看样子这种直接模拟的时间复杂度有点高, 看看官方题解, 当前时间复杂度: O(2^n)
     * @param: n
     * @param: k
     * @return char
     * @author marks
     * @CreateDate: 2026/03/03 14:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private char method_01(int n, int k) {
        List<Integer> ans = new ArrayList<>();
        // 初始值 0
        ans.add(0);
        // 执行 n - 1 次
        for (int i = 1; i < n; i++) {
            int size = ans.size();
            // 添加 1
            ans.add(1);
            // 从后往前遍历
            for (int j = size - 1; j >= 0; j--) {
                ans.add(ans.get(j) ^ 1);
            }
        }
        // 返回 k - 1 位字符, 并且将 Integer 转为 char
        return ans.get(k - 1) == 0 ? '0' : '1';
    }

}
