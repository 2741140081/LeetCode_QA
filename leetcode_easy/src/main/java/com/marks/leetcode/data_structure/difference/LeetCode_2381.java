package com.marks.leetcode.data_structure.difference;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/18 15:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2381 {
    /**
     * @Description:
     *
     * 给你一个小写英文字母组成的字符串 s 和一个二维整数数组 shifts ，其中 shifts[i] = [starti, endi, directioni] 。对于每个 i ，
     * 将 s 中从下标 starti 到下标 endi （两者都包含）所有字符都进行移位运算，如果 directioni = 1 将字符向后移位，如果 directioni = 0 将字符向前移位。
     *
     * 将一个字符 向后 移位的意思是将这个字符用字母表中 下一个 字母替换（字母表视为环绕的，所以 'z' 变成 'a'）。
     * 类似的，将一个字符 向前 移位的意思是将这个字符用字母表中 前一个 字母替换（字母表是环绕的，所以 'a' 变成 'z' ）。
     *
     * 请你返回对 s 进行所有移位操作以后得到的最终字符串。
     * @param s
     * @param shifts
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/2/18 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String shiftingLetters(String s, int[][] shifts) {
        String result;
        result = method_01(s, shifts);
        return result;
    }

    /**
     * @Description:
     * 差分
     * AC: 10ms/72.58MB
     *
     * @param s
     * @param shifts
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/2/18 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s, int[][] shifts) {
        int[] diff = new int[s.length() + 1];
        for (int[] shift : shifts) {
            int start = shift[0], end = shift[1], direction = shift[2];
            if (direction > 0) {
                diff[start]++;
                diff[end + 1]--;
            } else {
                diff[start]--;
                diff[end + 1]++;
            }
        }
        int[] pre = new int[s.length()];
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            count += diff[i];
            pre[i] = count % 26;
        }
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (pre[i] >= 0) {
                if (ch + pre[i] > 'z') {
                    char c = (char) (((ch + pre[i]) % ('z' + 1)) + 'a');
                    ans.append(c);
                } else {
                    ans.append((char) (ch + pre[i]));
                }

            } else {
                if (ch + pre[i] >= 'a') {
                    ans.append((char) (ch + pre[i]));
                } else {
                    char c = (char) ('z' - (Math.abs(pre[i]) - (ch - ('a' - 1))));
                    ans.append(c);
                }
            }
        }
        return ans.toString();
    }
}
