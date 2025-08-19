package com.marks.leetcode.backtrack;

import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/18 14:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1239 {

    
    /**
     * @Description:
     *
     * 给定一个字符串数组 arr，字符串 s 是将 arr 的含有 不同字母 的 子序列 字符串 连接 所得的字符串。
     *
     * 请返回所有可行解 s 中最长长度。
     *
     * 子序列 是一种可以从另一个数组派生而来的数组，通过删除某些元素或不删除元素而不改变其余元素的顺序。
     *
     * @param arr
     * @return int
     * @author marks
     * @CreateDate: 2025/8/18 14:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxLength(List<String> arr) {
        int result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：arr = ["cha","r","act","ers"]
     * 输出：6
     * 1. 使用回溯法, 创建一个 boolean[26] used, 记录当前字符是否被使用过
     * 2. 创建一个 int ans, 记录最长长度, 初始值为 0, 因为只需要返回最大长度, 所以不需要返回具体字符串
     * 3. 创建一个 int sum, 记录当前字符串的长度, 初始值为 0
     * 4. 递归截止条件 index == n, 并且需要对字符串 arr[index] 进行判断, flag = true, 此字符串的长度才能 sum += s.length();
     * 5. just do it!
     * 6. 需要处理 包含自身有重复字符的字符串, 但是这种不需要处理吧, 并不需要s中 不存在重复字符串,
     * 我的理解是, 添加到s中的任意 i, j, arr[i] 和 arr[j] 这两者不存在相同字符即可.
     * 7. 好吧还是要处理重复字符, 判断当前字符是否合法
     *
     * AC: 9ms(50.50%)/43.79MB(51.98%)
     * @param arr 
     * @return int
     * @author marks
     * @CreateDate: 2025/8/18 14:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int n;
    private int ans;
    private int method_01(List<String> arr) {
        n = arr.size();
        ans = 0;
        int sum = 0;
        boolean[] used = new boolean[26];
        backtrack(arr, 0, sum, used);
        return ans;
    }

    private void backtrack(List<String> arr, int index, int sum, boolean[] used) {
        if (index == n) {
            ans = Math.max(ans, sum);
            return;
        }
        boolean flag = true;
        char[] tempArray = arr.get(index).toCharArray();
        int len = tempArray.length;

        int[] count = new int[26];
        for (int i = 0; i < len; i++) {
            count[tempArray[i] - 'a']++;
            if (used[tempArray[i] - 'a'] || count[tempArray[i] - 'a'] > 1) {
                flag = false;
                break;
            }
        }

        if (flag) {
            // 取当前 arr[index], 拷贝一份新的 used 数组
            boolean[] copy = used.clone();
            for (int i = 0; i < len; i++) {
                copy[tempArray[i] - 'a'] = true;
            }
            backtrack(arr, index + 1, sum + len, copy);

            backtrack(arr, index + 1, sum, used);
        } else {
            backtrack(arr, index + 1, sum, used);
        }
    }
}
