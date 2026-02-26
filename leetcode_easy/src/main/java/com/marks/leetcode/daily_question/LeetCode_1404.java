package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1404 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/26 10:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1404 {

    /**
     * @Description:
     * 给你一个以二进制形式表示的数字 s 。请你返回按下述规则将其减少到 1 所需要的步骤数：
     * 如果当前数字为偶数，则将其除以 2 。
     * 如果当前数字为奇数，则将其加上 1 。
     * 题目保证你总是可以按上述规则将测试用例变为 1 。
     * tips:
     * 1 <= s.length <= 500
     * s 由字符 '0' 或 '1' 组成。
     * s[0] == '1'
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/02/26 10:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numSteps(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 需要一个进位存储 int carry = 0; 然后需要一个下标 int index = n - 1; 从后往前遍历
     * 2. 处理 s[i] 时, 先判断是否有进位 carry, 如果有进位先处理, 判断s[i] == 0,
     * 3. 这个题目相当于是做一个模拟
     * 4. carry = 1; s[i] = 0; 需要多少步能进入下一个 index--, 当前是奇数, 需要加1 ans++; 然后变成偶数后, carry = 1, 并且除以2, ans++, index--; 需要2步
     * 5. carry = 1; s[i] = 1; 需要多少步能进入下一个 index--, 当前是偶数, 需要除以2 ans++, carry = 1, index--; 需要1步
     * 6. carry = 0; s[i] = 0; carry = 0; index--; ans++; 需要1步
     * 7. carry = 0, s[i] = 1; 需要2步 并且 carry = 1;
     * 8. 总结, carry != s[i] 需要2步, 并且 carry = 1; carry == s[i] 时, 需要1步, carry = s[i]
     * AC: 0ms/41.95MB
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/02/26 10:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        // 将s 转为 char[]
        char[] chars = s.toCharArray();
        // 进位器用 char carry = '0';
        char carry = '0';
        int ans = 0;
        int n = chars.length;
        for (int i = n - 1; i >= 1; i--) {
            char cur = chars[i];
            if (carry != cur) {
                ans += 2;
                carry = '1';
            } else {
                ans++;
            }
        }
        // 判断 carry 是否还有进位
        if (carry == '1') {
            ans++;
        }
        return ans;
    }

}
