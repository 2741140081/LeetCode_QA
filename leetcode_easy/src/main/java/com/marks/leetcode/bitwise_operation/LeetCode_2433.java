package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/17 16:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2433 {

    /**
     * @Description:
     * 给你一个长度为 n 的 整数 数组 pref 。找出并返回满足下述条件且长度为 n 的数组 arr ：
     *
     * pref[i] = arr[0] ^ arr[1] ^ ... ^ arr[i].
     * 注意 ^ 表示 按位异或（bitwise-xor）运算。
     *
     * 可以证明答案是 唯一 的。
     * @param pref
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/17 16:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] findArray(int[] pref) {
        int[] result;
        result = method_01(pref);
        return result;
    }

    /**
     * @Description:
     * 1. pre = ans[0] ^ ans[1] ^ ... ^ ans[i - 1];
     * 2. pref[i] = pre ^ ans[i]; ==> ans[i] = pref[i] ^ pre;
     * AC: 2ms/55.6MB
     * @param pref
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/17 16:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] pref) {
        int[] ans = new int[pref.length];
        ans[0] = pref[0];
        int prev = pref[0];
        for (int i = 1; i < pref.length; i++) {
            ans[i] = pref[i] ^ prev;
            prev ^= ans[i];
        }

        return ans;
    }

}
