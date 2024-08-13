package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_676 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/12 16:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class MagicDictionary {
    private String[] dictionary;

    public MagicDictionary() {
    }

    public void buildDict(String[] dictionary) {
        this.dictionary = dictionary;
    }

    public boolean search(String searchWord) {
        boolean res = false;
        res = method_01(searchWord);
        return res;
    }

    private boolean method_01(String searchWord) {
        int m = dictionary.length;
        int n = searchWord.length();
        boolean res = false;
        for (int i = 0; i < m; i++) {
            if (dictionary[i].length() == n && !searchWord.equals(dictionary[i])) {
                res = res | validateStr(searchWord, dictionary[i]);
            }
        }

        return res;
    }

    private boolean validateStr(String searchWord, String str) {
        // count 存储下标相同, 但是元素不同 的个数
        int count = 0;
        int i = 0;
        while (count <= 1 && i < searchWord.length()) {
            if (searchWord.charAt(i) != str.charAt(i)) {
                count++;
            }
            i++;
        }
        return count == 1 ? true : false;
    }
}
