package com.marks.leetcode.data_structure.trie;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/28 10:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_14 {
    /**
     * @Description:
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     *
     * 如果不存在公共前缀，返回空字符串 ""。
     *
     * tips:
     * 1 <= strs.length <= 200
     * 0 <= strs[i].length <= 200
     * strs[i] 如果非空，则仅由小写英文字母组成
     * @param strs
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/2/28 10:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String longestCommonPrefix(String[] strs) {
        String result;
        result = method_01(strs);
        return result;
    }

    /**
     * @Description:
     * AC: 1ms/40.53MB
     * @param strs
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/2/28 10:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String[] strs) {
        StringBuilder ans = new StringBuilder();
        int index = 0;
        int n = Integer.MAX_VALUE;
        for (String str : strs) {
            n = Math.min(str.length(), n);
        }
        boolean flag = true;
        while (index < n && flag) {
            char ch = strs[0].charAt(index);
            for (String str : strs) {
                if (str.charAt(index) != ch) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                index++;
                ans.append(ch);
            }
        }
        return ans.toString();
    }
}
