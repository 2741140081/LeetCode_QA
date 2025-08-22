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
 * @date 2025/8/22 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_131 {
    
    
    /**
     * @Description:
     * 给你一个字符串 s，请你将 s 分割成一些 子串，使每个子串都是 回文串 。返回 s 所有可能的分割方案。
     * @param s
     * @return java.util.List<java.util.List<java.lang.String>>
     * @author marks
     * @CreateDate: 2025/8/22 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<String>> partition(String s) {
        List<List<String>> result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：s = "aab"
     * 输出：[["a","a","b"],["aa","b"]]
     * 1. List<String> list, 存储划分下来的子串, 先是一个一般的思路, 将 s 的每一个可以划分的点记录,
     * 例如 "aab", 那么 划分点集合为[0, 1, 2], 3不可取, 因为3 作为划分点与0相同, 由此可以得出一般规律 划分点集合 [0 ~ n - 1]
     * 2. 每次取划分点时, 由于每一个划分点都是一种方案, 所以需要对当前划分点方案进行验证, 判断当前划分下来的子串是否为回文串, 如果不是, 直接剪枝
     * 3. 并且递归的参数中包含有前一个划分点 index, 假设已经划分了list(0) = "a", index = 1, 下一个划分点 index+1 ~ n
     * 4. 初始化 index = 0, 即表示, 默认我们有一个在0位置的划分点, 所以最大的划分点在 n 位置, 0 ~ n 作为一个划分, 即取整个 s 串
     * AC: 10ms(39.47%)/56.01MB(30.92%)
     * @param s
     * @return java.util.List<java.util.List<java.lang.String>>
     * @author marks
     * @CreateDate: 2025/8/22 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    List<List<String>> ans;
    private int n;
    private List<List<String>> method_01(String s) {
        ans = new ArrayList<>();
        n = s.length();
        int left = 0;
        List<String> list = new ArrayList<>();
        backtrack(s, left, list);
        return ans;
    }

    private void backtrack(String s, int left, List<String> list) {
        if (left == n) {
            ans.add(new ArrayList<>(list));
            return;
        }
        for (int right = left; right < n; right++) {
            // 验证 s 的子串是否是回文串
            if (check(s, left, right)) {
                list.add(s.substring(left, right + 1));
                backtrack(s, right + 1, list);
                list.remove(list.size() - 1);
            }
        }
    }

    private boolean check(String s, int left, int right) {
        boolean res = true;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                res = false;
                break;
            }
            left++;
            right--;
        }
        return res;
    }
}
