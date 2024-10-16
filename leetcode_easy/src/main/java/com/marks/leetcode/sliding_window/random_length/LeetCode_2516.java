package com.marks.leetcode.sliding_window.random_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/16 9:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2516 {
    /**
     * @Description: [
     * 给你一个由字符 'a'、'b'、'c' 组成的字符串 s 和一个非负整数 k 。每分钟，你可以选择取走 s 最左侧 还是 最右侧 的那个字符。
     *
     * 你必须取走每种字符 至少 k 个，返回需要的 最少 分钟数；如果无法取到，则返回 -1 。
     *
     * tips:
     * 1 <= s.length <= 10^5
     * s 仅由字母 'a'、'b'、'c' 组成
     * 0 <= k <= s.length
     * ]
     * @param s
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/16 9:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int takeCharacters(String s, int k) {
        int result = 0;
        result = method_01(s, k);
        return result;
    }
    /**
     * @Description: [
     * 输入：s = "aabaaaacaabc", k = 2
     * 输出：8
     * 解释：
     * 从 s 的左侧取三个字符，现在共取到两个字符 'a' 、一个字符 'b' 。
     * 从 s 的右侧取五个字符，现在共取到四个字符 'a' 、两个字符 'b' 和两个字符 'c' 。
     * 共需要 3 + 5 = 8 分钟。
     * 可以证明需要的最少分钟数是 8 。
     *
     * 查看官方题解:双指针
     * AC:12ms/44.38MB
     * ]
     * @param s 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/16 16:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, int k) {
        int n = s.length();
        if (n < 3 * k) {
            // 特殊情况,
            return -1;
        }
        // 遍历s, 将字符存储在count[] 中, 判断是否满足条件
        int[] count = new int[3]; // count[0] = a, count[1] = b, count[2] = c
        for (int i = 0; i < n; i++) {
            int index = s.charAt(i) - 'a';
            count[index]++;
        }
        for (int cnt : count) {
            // 任意字符不满足条件, rtn -1
            if (cnt < k) {
                return -1;
            }
        }
        int left = 0;
        int ans = 0; // 中间区间的最大长度
        for (int right = 0; right < n; right++) {
            int index = s.charAt(right) - 'a';
            count[index]--;
            while (count[index] < k) {
                count[s.charAt(left++) - 'a']++;
            }
            ans = Math.max(ans, right - left + 1);
        }

        return n - ans;
    }
}
