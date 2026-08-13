package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2546 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/23 10:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2546 {

    /**
     * @Description:
     * 给你两个下标从 0 开始的 二元 字符串 s 和 target ，两个字符串的长度均为 n 。你可以对 s 执行下述操作 任意 次：
     * 选择两个 不同 的下标 i 和 j ，其中 0 <= i, j < n 。
     * 同时，将 s[i] 替换为 (s[i] OR s[j]) ，s[j] 替换为 (s[i] XOR s[j]) 。
     * 例如，如果 s = "0110" ，你可以选择 i = 0 和 j = 2，然后同时将 s[0] 替换为 (s[0] OR s[2] = 0 OR 1 = 1)，并将 s[2] 替换为 (s[0] XOR s[2] = 0 XOR 1 = 1)，最终得到 s = "1110" 。
     * 如果可以使 s 等于 target ，返回 true ，否则，返回 false 。
     * tips:
     * n == s.length == target.length
     * 2 <= n <= 10^5
     * s 和 target 仅由数字 0 和 1 组成
     * @param: s
     * @param: target
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/23 10:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean makeStringsEqual(String s, String target) {
        boolean result;
        result = method_01(s, target);
        result = method_02(s, target);
        return result;
    }

    // 优化, 不需要记录1的个数, 之需要判断s 和 target 是否包含1即可, AC: 0ms/46.58MB
    private boolean method_02(String s, String target) {
        boolean a = s.contains("1");
        boolean b = target.contains("1");
        return a == b;
    }

    /**
     * @Description:
     * 1. 分情况讨论, 有4种情况
     * 1.1 s[i] = 0, s[j] = 0; 执行操作后, s[i] = 0, s[j] = 0; 保持不变
     * 1.2 s[i] = 0, s[j] = 1; 执行操作后, s[i] = 1, s[j] = 1; s[i] 改变为1
     * 1.3 s[i] = 1, s[j] = 0; 执行操作后, s[i] = 1, s[j] = 1; s[j] 改变为1
     * 1.4 s[i] = 1, s[j] = 1; 执行操作后, s[i] = 1, s[j] = 0; s[j] 改变为0
     * 2. 通过上述总结可以得到结果, [0, 0] 无法进行转换, 并且[0, 0] 与其它结果直接不能进行转换,
     * 所以只需要判断 s 为全0, 或者 target 为全0, 并且 s 和 target 不同时为全0, 直接返回 false
     * AC: 8ms/46.81MB
     * @param: s
     * @param: target
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/23 10:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s, String target) {
        int n = s.length();
        int a = 0; // 记录 s 中 1 的个数
        int b = 0; // 记录 target 中 1 的个数
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                a++;
            }
            if (target.charAt(i) == '1') {
                b++;
            }
        }
        if (a == 0 && b == 0) {
            return true;
        } else {
            return a != 0 && b != 0;
        }
    }
}
