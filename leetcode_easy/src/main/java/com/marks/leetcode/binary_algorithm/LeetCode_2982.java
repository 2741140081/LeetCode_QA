package com.marks.leetcode.binary_algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/25 14:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2982 {
    private int n;
    private char[] array;
    /**
     * @Description: [
     * 给你一个仅由小写英文字母组成的字符串 s 。
     * 如果一个字符串仅由单一字符组成，那么它被称为 特殊 字符串。
     * 例如，字符串 "abc" 不是特殊字符串，而字符串 "ddd"、"zz" 和 "f" 是特殊字符串。
     *
     * 返回在 s 中出现 至少三次 的 最长特殊子字符串 的长度，如果不存在出现至少三次的特殊子字符串，则返回 -1 。
     *
     * 子字符串 是字符串中的一个连续 非空 字符序列。
     * tips:
     * 3 <= s.length <= 5 * 10^5
     * s 仅由小写英文字母组成。
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/11/25 14:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumLength(String s) {
        int result;
//        result = method_01(s);
        result = method_02(s);
        return result;
    }

    /**
     * @Description: [
     * 官解 方式一: 二分查找
     * 有两个关键点
     * 1. 一次for循环, 将字符串中特殊字符个数和长度存在在map中, 很强!!!
     * 2. count += num - mid + 1; 计算count时, 如果
     * AC:143ms/45.02MB
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/11/25 15:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String s) {
        int m = s.length();
        HashMap<Character, List<Integer>> map = new HashMap<>();

        for (int i = 0, j = 0; i < m; i = j) {
            while (j < m && s.charAt(j) == s.charAt(i)) {
                j++;
            }
            map.computeIfAbsent(s.charAt(i), k -> new ArrayList<>()).add(j - i);
        }
        int res = -1;
        for (List<Integer> list : map.values()) {
            int left = 0, right = m - 2;
            while (left <= right) {
                int mid = (right - left) / 2 + left;
                int count = 0;
                for (int num: list) {
                    if (num >= mid) {
                        count += num - mid + 1;
                    }
                }
                if (count >= 3) {
                    res = Math.max(res, mid);
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return res;
    }

    /**
     * @Description: [
     * E1:
     * 输入：s = "aaaa"
     * 输出：2
     * 解释：出现三次的最长特殊子字符串是 "aa" ：子字符串 "aaaa"、"aaaa" 和 "aaaa"。
     *
     * 超时: 905/906
     *
     * 调整一下right 的大小, 低空过线,
     * AC:1457ms/49.13MB
     * 算了还是看看官方题解吧, 我感觉自己写的思路没问题, 但是时间复杂度不会骗人, 太高了...
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/11/25 15:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        n = s.length();
        array = s.toCharArray();
        int[] pre = new int[26];
        int left = -1;
        int right = 1; // 全部由一种字符组成, 最长长度为n - 2, 分别是[0, n - 3], [1, n - 2], [2, n - 1]
        int count = 1;
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                count = 1;
            } else if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
                right = Math.max(right, count);
            } else if (s.charAt(i) != s.charAt(i - 1)) {
                count = 1;
            }
            pre[array[i] - 'a']++;
            if (pre[array[i] - 'a'] >= 3) {
                left = 1;
            }
        }
        if (left == -1) {
            return left;
        }

        int ans = left;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (checkThreeSubString(mid)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * 感觉这里可以用滑动窗口来判断出现次数是否 >= 3次
     * ]
     * @param mid
     * @return boolean
     * @author marks
     * @CreateDate: 2024/11/25 14:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean checkThreeSubString(int mid) {
        // 固定大小的滑动窗口
        HashMap<Character, Integer> map = new HashMap<>();
        int left = 0; // 滑动窗口左边界
        int[] count = new int[26]; // 记录特殊字符串出现次数

        for (int i = 0; i < mid; i++) {
            map.put(array[i], map.getOrDefault(array[i], 0) + 1);
        }
        if (map.size() == 1) {
            count[array[0] - 'a']++;
        }

        for (int right = mid; right < n; right++) {
            map.put(array[right], map.getOrDefault(array[right], 0) + 1);
            if (map.get(array[left]) == 1) {
                map.remove(array[left]);
            } else {
                map.put(array[left], map.get(array[left]) - 1);
            }
            if (map.size() == 1) {
                count[array[right] - 'a']++;
                if (count[array[right] - 'a'] >= 3) {
                    return true;
                }
            }
            left++; // 窗口移动
        }
        return false;
    }
}
