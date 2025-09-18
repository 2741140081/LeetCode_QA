package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/17 17:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2683 {

    /**
     * @Description:
     * 下标从 0 开始、长度为 n 的数组 derived 是由同样长度为 n 的原始 二进制数组 original 通过计算相邻值的 按位异或（⊕）派生而来。
     *
     * 特别地，对于范围 [0, n - 1] 内的每个下标 i ：
     *
     * 如果 i = n - 1 ，那么 derived[i] = original[i] ⊕ original[0]
     * 否则 derived[i] = original[i] ⊕ original[i + 1]
     * 给你一个数组 derived ，请判断是否存在一个能够派生得到 derived 的 有效原始二进制数组 original 。
     *
     * 如果存在满足要求的原始二进制数组，返回 true ；否则，返回 false 。
     *
     * 二进制数组是仅由 0 和 1 组成的数组。
     * @param derived
     * @return boolean
     * @author marks
     * @CreateDate: 2025/9/17 17:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean doesValidArrayExist(int[] derived) {
        boolean result;
        result = method_01(derived);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入：derived = [1,1,0]
     * 输出：true
     * 解释：能够派生得到 [1,1,0] 的有效原始二进制数组是 [0,1,0] ：
     * derived[0] = original[0] ⊕ original[1] = 0 ⊕ 1 = 1
     * derived[1] = original[1] ⊕ original[2] = 1 ⊕ 0 = 1
     * derived[2] = original[2] ⊕ original[0] = 0 ⊕ 0 = 0
     * 1. o[i] = d[i] ^ o[(i + 1) % n], o[i + 1] = d[i + 1] ^ o[(i + 2) % n]
     * 2. 直接猜测 o[0] = 0 或者 o[0] = 1, 只有这两种结果, 判断两种结果是否成立
     * 3. 101, 1, 1, 0
     * AC: 9ms/59.61MB
     * @param derived 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/9/17 17:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] derived) {
        int n = derived.length;
        int[] original = new int[derived.length];
        original[n - 1] = 0;
        boolean flag_0 = checkXor(derived, n, original);

        original = new int[derived.length];
        original[n - 1] = 1;
        boolean flag_1 = checkXor(derived, n, original);
        return flag_1 || flag_0;
    }

    private boolean checkXor(int[] derived, int n, int[] original) {
        for (int i = n - 2; i >= 0; i--) {
            original[i] = derived[i] ^ original[i + 1];
            if (original[i] > 1) {
                return false;
            }
        }

        // 判断是否成立
        int result = derived[n - 1] ^ original[n - 1];
        if (result == original[0]) {
            return true;
        } else {
            return false;
        }
    }

}
