package com.marks.leetcode.sliding_window.random_length;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/28 18:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_76 {
    /**
     * @Description: [
     * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
     * ]
     * @param s
     * @param t
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2024/10/28 18:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String minWindow(String s, String t) {
        String res = "";
        res = method_01(s, t);
        return res;
    }
    /**
     * @Description: [
     * 查看官方题解思路后: 即滑动窗口的思想
     * AC:432ms/45.10MB
     * 感觉时间太长了,
     * ]
     * @param s
     * @param t
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2024/10/28 18:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s, String t) {
        int m = s.length();
        int n = t.length();
        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();
        HashMap<Character, Integer> preMap = new HashMap<>(); //存储t中字符数量
        for (int i = 0; i < n; i++) {
            preMap.put(tArray[i], preMap.getOrDefault(tArray[i], 0) + 1);
        }
        // 需要保证窗口中sub_s的字符数量满足preMap中的字符数量要求
        HashMap<Character, Integer> map = new HashMap<>(); // 存储窗口中字符数量
        int left = 0; // 左边界
        String ans = "";
        int len = m + 1; // 找到符合条件的最短的子串长度
        for (int i = 0; i < m; i++) {
            map.put(sArray[i], map.getOrDefault(sArray[i], 0) + 1);
            while (checkIsOverwrite(map, preMap)) {
                int tempLength = i - left + 1;
                if (len > tempLength) {
                    len = tempLength;
                    ans = s.substring(left, i + 1); // substring 右边界不包含
                }
                map.put(sArray[left], map.get(sArray[left]) - 1);
                left++;
            }

        }
        return ans;
    }

    private boolean checkIsOverwrite(HashMap<Character, Integer> map, HashMap<Character, Integer> preMap) {
        for (Map.Entry<Character, Integer> entry : preMap.entrySet()) {
            Character ch = entry.getKey();
            if (!map.containsKey(ch) || map.get(ch) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
}
