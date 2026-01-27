package com.marks.leetcode.sliding_window;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_395 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/23 15:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_395 {

    /**
     * @Description:
     * 给你一个字符串 s 和一个整数 k ，请你找出 s 中的最长子串， 要求该子串中的每一字符出现次数都不少于 k 。返回这一子串的长度。
     * 如果不存在这样的子字符串，则返回 0。
     * tips:
     * 1 <= s.length <= 10^4
     * s 仅由小写英文字母组成
     * 1 <= k <= 10^5
     * @param: s
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/23 15:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestSubstring(String s, int k) {
        int result;
        result = method_01(s, k);
        result = method_02(s, k);
        return result;
    }

    /**
     * @Description:
     * 1. 怎么用分治法来解决这个问题?
     * 2. 第一次时, 统计s 中所有字符出现的次数,int[] count; 然后判断 0 < count[i] < k, 记录此时的 char ch = i + 'a';
     * 3. 可以推断可以结论是, 任意包含 ch 的子串都是不合法的, 所以可以进行分割, ch 相当于是分割点(方便判断)
     * 4. 最开始的是 left = 0, right = n - 1, 然后遍历 int index = left; 判断 array[index] == ch;
     * 然后把 [left, index - 1] 作为一个分割区间进行递归求解
     * AC: 0ms/41.96MB
     * @param: s
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/26 14:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String s, int k) {
        int n = s.length();
        char[] array = s.toCharArray();
        return dfs(array, 0, n - 1, k);
    }

    private int dfs(char[] array, int left, int right, int k) {
        int[] count = new int[26];
        for (int i = left; i <= right; i++) {
            int index = array[i] - 'a';
            count[index]++;
        }
        char ch = 0;
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0 && count[i] < k) {
                ch = (char) (i + 'a');
                // 找到一个就行
                break;
            }
        }
        if (ch == 0) {
            // 所有count[i] 都符合条件
            return right - left + 1;
        }
        int index = left;
        int res = 0; // 记录子串的长度
        while (index <= right) {
            while (index <= right && array[index] == ch) {
                // 可能存在连续的 ch, 第一个不为ch的index
                index++;
            }
            if (index > right) {
                // 超出长度限制
                break;
            }
            // 找到第一个ch
            int start = index;
            // 找到下一个ch
            while (index <= right && array[index] != ch) {
                index++;
            }
            // [start, index - 1] 即为不包含ch 的子串
            int len = dfs(array, start, index - 1, k);
            res = Math.max(res, len);
        }
        return res;
    }

    /**
     * @Description:
     * 1. 先看数据范围吧
     * 2. 难道是二分法 + 滑动窗口? 即假设最后的窗口的长度是 mid, 然后通过判断在mid 的情况下是否存在结果
     * 如果存在, left = mid + 1, 如果不存在 mid - 1
     * 3. 查看官方题解, 使用的是滑动窗口, 按照窗口中所包含的种类(不同的字符类型)进行滑动窗口
     * 4. 最少的种类是1种, 最多的种类是26种, int less; 记录少于k的种类数,
     * 当int[] count, count[i] == 1(0 -> 1) || count[i] == k - 1(k -> k - 1)时, less++;
     * count[i] = 0, less--;
     * 5. int total; 存储总的类型数量(不为0的类型数量), count[i] = 1, total++; count[i] = 0, total--;
     * 6. 当 less = 0时, 可以记录此时的长度, res = Math.max(res, right - left + 1);
     * AC: 4ms/42.39MB
     * @param: s
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/23 15:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, int k) {
        int n = s.length();
        char[] array = s.toCharArray();
        int ans = 0; // 子串最大长度

        for (int i = 1; i <= 26; i++) { // 子串中包含的种类数
            int[] count = new int[26]; // 存储每个字符出现的次数
            int left = 0, right = 0;
            int less = 0; // 少于k的种类数
            int total = 0; // 不为0的类型数量
            while (right < n) {
                // 向右滑动窗口
                int index = array[right] - 'a';
                count[index]++;
                if (count[index] == 1) {
                    // 子串中种类数增加1
                    less++;
                    total++;
                }

                if (count[index] == k) {
                    // 子串中不满k的种类数减少1
                    less--;
                }

                // 当前种类数大于 i, 需要缩小窗口, left 进行移动
                while (total > i) {
                    int lIndex = array[left] - 'a';
                    count[lIndex]--;
                    if (count[lIndex] == 0) {
                        less--;
                        total--;
                    }
                    if (count[lIndex] == k - 1) {
                        less++;
                    }
                    left++;
                }
                // 判断此时 less 是否为0
                if (less == 0) {
                    ans = Math.max(ans, right - left + 1);
                }
                // 移动右边界
                right++;
            }
        }

        return ans;
    }

}
