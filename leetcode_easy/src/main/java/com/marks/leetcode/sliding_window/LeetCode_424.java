package com.marks.leetcode.sliding_window;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_424 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/23 14:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_424 {

    /**
     * @Description:
     * 给你一个字符串 s 和一个整数 k 。你可以选择字符串中的任一字符，并将其更改为任何其他大写英文字符。该操作最多可执行 k 次。
     * 在执行上述操作后，返回 包含相同字母的最长子字符串的长度。
     * @param: s
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/23 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int characterReplacement(String s, int k) {
        int result;
        result = method_01(s, k);
        result = method_02(s, k);
        return result;
    }

    /**
     * @Description:
     * 双指针 + 窗口
     * 1. 代码更加优秀
     * @param: s
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/23 15:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String s, int k) {
        int[] num = new int[26];
        int n = s.length();
        int maxn = 0;
        int left = 0, right = 0;
        while (right < n) {
            num[s.charAt(right) - 'A']++;
            maxn = Math.max(maxn, num[s.charAt(right) - 'A']);
            if (right - left + 1 - maxn > k) {
                num[s.charAt(left) - 'A']--;
                left++;
            }
            right++;
        }
        return right - left;
    }

    /**
     * @Description:
     * 1. 记录整个窗口中各种字符出现次数, int[] count; 存储
     * AC: 10ms/45.29MB
     * @param: s
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/23 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, int k) {
        int ans = 0;
        int[] count = new int[26]; // 26个大写字母的个数
        int left = 0;
        // 遍历字符串
        int n = s.length();
        for (int right = 0; right < n; right++) {
            // 添加字符到窗口
            count[s.charAt(right) - 'A']++;
            // 获取当前窗口中字符出现的最大次数
            int maxCount = 0;
            int index = 0;
            for (int i = 0; i < 26; i++) {
                // 记录最大次数时的index
                if (count[i] > maxCount) {
                    maxCount = count[i];
                    index = i;
                }
            }
            while (right - left + 1 - maxCount > k) {
                // 判断 left 的删除是否删除的不是 index 的下标
                if (s.charAt(left) - 'A' != index) {
                    count[s.charAt(left) - 'A']--;
                    left++;
                    break;
                } else {
                    count[s.charAt(left) - 'A']--;
                    left++;
                }
            }
            // 现在窗口中的数据是有效数据
            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }

}
