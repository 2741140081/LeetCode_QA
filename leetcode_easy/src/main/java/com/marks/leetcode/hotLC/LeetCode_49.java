package com.marks.leetcode.hotLC;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_49 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/1 16:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_49 {

    /**
     * @Description:
     * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
     * 字母异位词:字母异位词是通过重新排列不同单词或短语的字母而形成的单词或短语，并使用所有原字母一次。
     *
     * tips:
     * 1 <= strs.length <= 10^4
     * 0 <= strs[i].length <= 100
     * strs[i] 仅包含小写字母
     * @param: strs
     * @return java.util.List<java.util.List<java.lang.String>>
     * @author marks
     * @CreateDate: 2025/12/01 16:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result;
        result = method_01(strs);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
     * 1. 创建一个map, key:排序后的字符串, value:字符串本身
     * AC: 7ms/49.04MB
     * @param: strs
     * @return java.util.List<java.util.List<java.lang.String>>
     * @author marks
     * @CreateDate: 2025/12/01 16:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<String>> method_01(String[] strs) {
        List<List<String>> ans = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] currArray = str.toCharArray();
            Arrays.sort(currArray);
            String key = new String(currArray);
            if (map.containsKey(key)) {
                map.get(key).add(str);
            } else {
                List<String> list = new ArrayList<>();
                list.add(str);
                map.put(key, list);
            }
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            ans.add(entry.getValue());
        }
        return ans;
    }

}
