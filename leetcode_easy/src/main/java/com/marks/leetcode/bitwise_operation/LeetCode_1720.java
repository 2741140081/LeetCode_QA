package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/17 16:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1720 {

    
    /**
     * @Description:
     * 未知 整数数组 arr 由 n 个非负整数组成。
     * 经编码后变为长度为 n - 1 的另一个整数数组 encoded ，其中 encoded[i] = arr[i] XOR arr[i + 1] 。
     * 例如，arr = [1,0,2,1] 经编码后得到 encoded = [1,2,3] 。
     * 给你编码后的数组 encoded 和原数组 arr 的第一个元素 first（arr[0]）。
     * 请解码返回原数组 arr 。可以证明答案存在并且是唯一的。
     * @param encoded 
     * @param first
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/17 16:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] decode(int[] encoded, int first) {
        int[] result;
        result = method_01(encoded, first);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：encoded = [1,2,3], first = 1
     * 输出：[1,0,2,1]
     * 解释：若 arr = [1,0,2,1] ，那么 first = 1 且 encoded = [1 XOR 0, 0 XOR 2, 2 XOR 1] = [1,2,3]
     * ans[2] ^ first = 1 => ans[2] ^ (first ^ first) = 1 ^ first, 是逆元运算 A ^ B = C => A = C ^ B
     * @param encoded
     * @param first 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/17 16:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] encoded, int first) {
        int n = encoded.length + 1;
        int[] result = new int[n];
        result[0] = first;
        for (int i = 0; i < n - 1; i++) {
            result[i + 1] = result[i] ^ encoded[i];
        }
        return result;
    }

}
