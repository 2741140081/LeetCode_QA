package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3043 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/21 10:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3043 {

    /**
     * @Description:
     * 给你两个 正整数 数组 arr1 和 arr2 。
     * 正整数的 前缀 是其 最左边 的一位或多位数字组成的整数。例如，123 是整数 12345 的前缀，而 234 不是 。
     * 设若整数 c 是整数 a 和 b 的 公共前缀 ，那么 c 需要同时是 a 和 b 的前缀。例如，5655359 和 56554 有公共前缀 565 和 5655，而 1223 和 43456 没有 公共前缀。
     * 你需要找出属于 arr1 的整数 x 和属于 arr2 的整数 y 组成的所有数对 (x, y) 之中最长的公共前缀的长度。
     * 返回所有数对之中最长公共前缀的长度。如果它们之间不存在公共前缀，则返回 0 。
     *
     * tips:
     * 1 <= arr1.length, arr2.length <= 5 * 10^4
     * 1 <= arr1[i], arr2[i] <= 10^8
     * @param: arr1
     * @param: arr2
     * @return int
     * @author marks
     * @CreateDate: 2026/05/21 10:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestCommonPrefix(int[] arr1, int[] arr2) {
        int result;
        result = method_01(arr1, arr2);
        return result;
    }

    /**
     * @Description:
     * 1. 第一个想到的解法是字典树, 将 arr[i] 放入字典树中, 然后遍历字典树, 找到最长的公共前缀
     * 2. 将 arr1[] 数组存入字典树中, 然后遍历arr2[], 判断 arr2[i] 在字典树中的最长公共前缀.
     * 3. 字典树使用数组实现int[] child = new int[10]; 存储 0 ~ 9, 并且判断 finished = true;
     * AC: 41ms/80.1MB
     * @param: arr1
     * @param: arr2
     * @return int
     * @author marks
     * @CreateDate: 2026/05/21 10:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr1, int[] arr2) {
        Tire root = new Tire();
        for (int num : arr1) {
            root.insert(num);
        }
        int ans = 0;
        for (int num : arr2) {
            int length = root.getLongestCommonPrefix(num);
            ans = Math.max(ans, length);
        }

        return ans;
    }

    private class Tire {
        private Tire[] child;
        private boolean finished;

        public Tire() {
            child = new Tire[10];
            finished = false;
        }

        public void insert(int num) {
            Tire node = this;
            // 得到num的每一位, 直接将 num 转为 String
            String str = String.valueOf(num);
            for (int i = 0; i < str.length(); i++) {
                int index = str.charAt(i) - '0';
                if (node.child[index] == null) {
                    node.child[index] = new Tire();
                }
                node = node.child[index];
            }
            node.finished = true;
        }
        public int getLongestCommonPrefix(int num) {
            Tire node = this;
            String str = String.valueOf(num);
            for (int i = 0; i < str.length(); i++) {
                int index = str.charAt(i) - '0';
                if (node.child[index] == null) {
                    return i;
                }
                node = node.child[index];
            }
            return str.length();
        }
    }

}
