package com.marks.leetcode.sliding_window.random_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/29 10:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1356 {
    /**
     * @Description: [
     * 给你一个字符串 s ，它只包含三种字符 a, b 和 c 。
     *
     * 请你返回 a，b 和 c 都 至少 出现过一次的子字符串数目。
     *
     * tips:
     * 3 <= s.length <= 5 x 10^4
     * s 只包含字符 a，b 和 c 。
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/10/29 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfSubstrings(String s) {
        int result = 0;
//        result = method_01(s);
        result = method_02(s);
        return result;
    }
    /**
     * @Description: [
     * E1:s = "abcabc"
     * right = 5, left = 3
     *
     * AC:15ms/43.44MB
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/10/29 10:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String s) {
        int n = s.length();
        char[] chars = s.toCharArray();
        int ans = 0;
        int[] cnt = new int[3]; // 存储a, b, c的数量
        int left = n - 1;
        cnt[chars[left] - 'a']++;
        // 两层循环, 外层循环遍历right, 由[minRight, n - 1], 其中minRight是满足条件的最小的右边界
        for (int right = n - 1; right >= 2; right--) {
            while (!checkIsOverWrite(cnt) && left >= 1) {
                left--;
                cnt[chars[left] - 'a']++;
            }
            if (checkIsOverWrite(cnt)) {
                // 因为当满足条件时, left已经往后移动, 所以满足条件的节点是left+1, 符合条件的范围是[0, left+1], 所以left + 2个数量
                ans += (left + 1);
            }
            cnt[chars[right] - 'a']--;
        }
        return ans;
    }

    /**
     * @Description: [
     * 当前超时, 需要优化, 见method_02
     * 1.窗口可以重复利用, 不需要重置, 使用其滚动的特性, 通过移除 cnt[chars[right] - 'a']-- 来实现
     * 2.left可以先进行 left-- 操作, 更加贴合实际, 更好理解
     * 3.当时犯了一个错误是进行了cnt[chars[right] - 'a']++ , 因为当下想的是将n - 1这个位置的字符添加进来,
     * 但是, 由于right存在于for 循环中, 这样会对[right, n - 2]这些字段全部计算两边,
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/10/29 10:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        char[] chars = s.toCharArray();
        int ans = 0;

        // 两层循环, 外层循环遍历right, 由[minRight, n - 1], 其中minRight是满足条件的最小的右边界
        for (int right = n - 1; right > 0; right--) {
            int left = right;
            int[] cnt = new int[3]; // 存储a, b, c的数量
            while (!checkIsOverWrite(cnt) && left >= 0) {
                cnt[chars[left] - 'a']++;
                left--;
            }
            if (checkIsOverWrite(cnt)) {
                // 因为当满足条件时, left已经往后移动, 所以满足条件的节点是left+1, 符合条件的范围是[0, left+1], 所以left + 2个数量
                ans += (left + 2);
            }

        }
        return ans;
    }

    private boolean checkIsOverWrite(int[] cnt) {
        for (int i : cnt) {
            if (i < 1) {
                return false;
            }
        }
        return true;
    }
}
