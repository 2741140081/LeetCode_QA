package com.marks.leetcode.data_structure.trie;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/4 9:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1233 {
    /**
     * @Description:
     * 你是一位系统管理员，手里有一份文件夹列表 folder，你的任务是要删除该列表中的所有 子文件夹，并以 任意顺序 返回剩下的文件夹。
     *
     * 如果文件夹 folder[i] 位于另一个文件夹 folder[j] 下，那么 folder[i] 就是 folder[j] 的 子文件夹 。
     * folder[j] 的子文件夹必须以 folder[j] 开头，后跟一个 "/"。例如，"/a/b" 是 "/a" 的一个子文件夹，但 "/b" 不是 "/a/b/c" 的一个子文件夹。
     *
     * 文件夹的「路径」是由一个或多个按以下格式串联形成的字符串：'/' 后跟一个或者多个小写英文字母。
     *
     * 例如，"/leetcode" 和 "/leetcode/problems" 都是有效的路径，而空字符串和 "/" 不是。
     *
     * @param folder
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/3/4 9:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> removeSubfolders(String[] folder) {
        List<String> result;
        result = method_01(folder);
        return result;
    }

    /**
     * @Description:
     * E1:
     * "/a","/a/b","/c/d","/c/d/e","/c/f"
     * 感觉这种暴力, 极大概率超时
     * @param folder
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/3/4 9:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_01(String[] folder) {
        Arrays.sort(folder);
        List<String> ans = new ArrayList<>();

        ans.add(folder[0]);
        for (int i = 1; i < folder.length; ++i) {
            int pre = ans.get(ans.size() - 1).length();
            if (!(pre < folder[i].length() && ans.get(ans.size() - 1).equals(folder[i].substring(0, pre)) && folder[i].charAt(pre) == '/')) {
                ans.add(folder[i]);
            }
        }
        return ans;
    }

    static class TireString {
        Map<String, TireString> child;
        boolean isEnd;

        public TireString() {
            child = new HashMap<>();
            isEnd = false;
        }
    }
}
