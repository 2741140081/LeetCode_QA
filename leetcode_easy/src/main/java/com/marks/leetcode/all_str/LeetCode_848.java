package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_848 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/23 16:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_848 {

    /**
     * @Description:
     * 有一个由小写字母组成的字符串 s，和一个长度相同的整数数组 shifts。
     * 我们将字母表中的下一个字母称为原字母的 移位 shift() （由于字母表是环绕的， 'z' 将会变成 'a'）。
     * 例如，shift('a') = 'b', shift('t') = 'u', 以及 shift('z') = 'a'。
     * 对于每个 shifts[i] = x ， 我们会将 s 中的前 i + 1 个字母移位 x 次。
     * 返回 将所有这些移位都应用到 s 后最终得到的字符串 。
     *
     * tips:
     * 1 <= s.length <= 10^5
     * s 由小写英文字母组成
     * shifts.length == s.length
     * 0 <= shifts[i] <= 10^9
     * @param: s
     * @param: shifts
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/23 16:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String shiftingLetters(String s, int[] shifts) {
        String result;
        result = method_01(s, shifts);
        return result;
    }

    /**
     * @Description:
     * 1. 后缀和, 并且循环次数最大值是 26, 需要对 26 进行取余
     * AC: 15ms/71.3MB
     * @param: s
     * @param: shifts
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/23 16:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s, int[] shifts) {
        int n = s.length();
        int[] sum = new int[n];
        sum[n - 1] = shifts[n - 1] % 26;
        for (int i = n - 2; i >= 0; i--) {
            sum[i] = (sum[i + 1] + shifts[i]) % 26;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            int move = sum[i];
            if (c + move > 'z') {
                sb.append((char) (c + move - 26));
            } else {
                sb.append((char) (c + move));
            }
        }

        return sb.toString();
    }

}
