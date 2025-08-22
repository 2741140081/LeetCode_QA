package com.marks.leetcode.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/22 11:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2002 {


    /**
     * @Description:
     * 给你一个字符串 s ，请你找到 s 中两个 不相交回文子序列 ，使得它们长度的 乘积最大 。
     * 两个子序列在原字符串中如果没有任何相同下标的字符，则它们是 不相交 的。
     *
     * 请你返回两个回文子序列长度可以达到的 最大乘积 。
     *
     * 子序列 指的是从原字符串中删除若干个字符（可以一个也不删除）后，剩余字符不改变顺序而得到的结果。
     * 如果一个字符串从前往后读和从后往前读一模一样，那么这个字符串是一个 回文字符串 。
     * tips:
     * 2 <= s.length <= 12
     * s 只含有小写英文字母。
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/8/22 11:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxProduct(String s) {
        int result;
        result = method_01(s);
        return result;
    }


    /**
     * @Description:
     * E1:
     * 输入：s = "accbcaxxcxx"
     * 输出：25
     * 1. 需要两条回文串, 假设分别是 s1 和 s2, 并且 s1 与 s2 不存在相同的下标, 使用 List<char> s1, s2 存储
     * 2. 分析 对于当前的 index, char curr = s.charAt(index),
     * 存在3种可能性, a. s1 取 curr, s2 不取; b. s1 不取 curr, s2 取 curr; c. s1 s2 都不取 curr
     * 3. 回溯递归, s1.add(curr) or s2.add(curr); s1.remove(curr) or s2.remove(curr); 递归结束条件 index == n
     * 当 s1 和 s2 都是回文串时, 执行 ans = Math.max(ans, s1.size() * s2.size())
     * 4. 我们还需要一个检测 List<char> 是否是回文串的额外方法, 使用双指针进行检测
     * 5. just do it!
     * AC: 644ms(12.5%)/40.82MB(67.19%)
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/8/22 11:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int ans;
    private int n;
    private int method_01(String s) {
        ans = 0;
        n = s.length();
        List<Character> s1 = new ArrayList<>();
        List<Character> s2 = new ArrayList<>();
        backtrack(s, 0, s1, s2);
        return ans;
    }

    private void backtrack(String s, int index, List<Character> s1, List<Character> s2) {
        if (index == n) {
            if (isPalindrome(s1) && isPalindrome(s2)) {
                ans = Math.max(ans, s1.size() * s2.size());
            }
            return;
        }
        // a. s1 取 index, s2 不取
        s1.add(s.charAt(index));
        backtrack(s, index + 1, s1, s2);
        s1.remove(s1.size() - 1);

        // b. s2 取 index, s1 不取
        s2.add(s.charAt(index));
        backtrack(s, index + 1, s1, s2);
        s2.remove(s2.size() - 1);

        // b. s1, s2 都不取 index
        backtrack(s, index + 1, s1, s2);
    }

    private boolean isPalindrome(List<Character> list) {
        boolean flag = true;
        int left = 0, right = list.size() - 1;
        while (left < right) {
            if (list.get(left) != list.get(right)) {
                flag = false;
                break;
            }
            left++;
            right--;
        }
        return flag;
    }

}
