package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/17 16:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1310 {

    /**
     * @Description:
     * 有一个正整数数组 arr，现给你一个对应的查询数组 queries，其中 queries[i] = [Li, Ri]。
     *
     * 对于每个查询 i，请你计算从 Li 到 Ri 的 XOR 值（即 arr[Li] xor arr[Li+1] xor ... xor arr[Ri]）作为本次查询的结果。
     *
     * 并返回一个包含给定查询 queries 所有结果的数组。
     * @param arr 
     * @param queries
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/17 16:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] xorQueries(int[] arr, int[][] queries) {
        int[] result;
        result = method_01(arr, queries);
        return result;
    }

    
    /**
     * @Description:
     * 1. 相当于前缀和, 这里是前缀异或, int[] preXor = new int[n]; preXor[i] = arr[0] ^ arr[1] ^ ... ^ arr[i]
     * 2. queries[i] = [Li, Ri], 则 preXor[Ri] ^ preXor[Li - 1] = ans[Ri]
     * AC: 3ms/59.76MB
     * @param arr 
     * @param queries 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/17 16:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] arr, int[][] queries) {
        int[] preXor = new int[arr.length];
        preXor[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            preXor[i] = preXor[i - 1] ^ arr[i];
        }
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int start =  queries[i][0];
            int end = queries[i][1];
            ans[i] = start == 0 ? preXor[end] : preXor[end] ^ preXor[start - 1];
        }
        return ans;
    }

}
