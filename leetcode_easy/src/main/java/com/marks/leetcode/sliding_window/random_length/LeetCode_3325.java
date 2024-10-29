package com.marks.leetcode.sliding_window.random_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/29 17:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3325 {
    public int numberOfSubstrings(String s, int k) {
        int result = 0;
        result = method_01(s, k);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入： s = "abacb", k = 2
     * 输出： 4
     *
     * AC:3ms/41.26MB
     * ]
     * @param s
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/29 17:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, int k) {
        int n = s.length();
        char[] chars = s.toCharArray();
        int[] cnt = new int[26]; // s 全部由小写字母组成
        int left = n - 1;
        int ans = 0;
        cnt[chars[left] - 'a']++;
        for (int right = n - 1; right >= 0; right--) {
            while (!checkIsOver(cnt, k) && left > 0) {
                left--;
                cnt[chars[left] - 'a']++;
            }
            if (checkIsOver(cnt, k)) {
                ans += (left + 1);
            }
            cnt[chars[right] - 'a']--;
        }
        return ans;
    }

    private boolean checkIsOver(int[] cnt, int k) {
        for (int x : cnt) {
            if (x >= k) {
                return true;
            }
        }
        return false;
    }
}
