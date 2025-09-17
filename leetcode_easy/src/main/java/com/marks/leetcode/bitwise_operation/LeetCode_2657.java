package com.marks.leetcode.bitwise_operation;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/12 10:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2657 {

    /**
     * @Description:
     * 给你两个下标从 0 开始长度为 n 的整数排列 A 和 B 。
     * A 和 B 的 前缀公共数组 定义为数组 C ，其中 C[i] 是数组 A 和 B 到下标为 i 之前公共元素的数目。
     * 请你返回 A 和 B 的 前缀公共数组 。
     * 如果一个长度为 n 的数组包含 1 到 n 的元素恰好一次，我们称这个数组是一个长度为 n 的 排列 。
     *
     * tips:
     * 1 <= A.length == B.length == n <= 50
     * 1 <= A[i], B[i] <= n
     * 题目保证 A 和 B 两个数组都是 n 个元素的排列。
     * @param A
     * @param B
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/12 10:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] findThePrefixCommonArray(int[] A, int[] B) {
        int[] result;
        result = method_01(A, B);
        return result;
    }


    /**
     * @Description:
     * 暴力查询求解? 怎么用位运算解决?
     * AC: 5ms/44.5MB
     *
     * @param A
     * @param B
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/12 10:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] A, int[] B) {
        int n = A.length;
        int[] result = new int[n];
        Set<Integer> setA = new HashSet<>();
        Set<Integer> setB = new HashSet<>();
        if (A[0] == B[0]) {
            result[0] = 1;
        } else {
            setA.add(A[0]);
            setB.add(B[0]);
        }
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1];
            if (A[i] == B[i]) {
                result[i]++;
            } else {
                checkResult(B, result, setA, setB, i);
                checkResult(A, result, setB, setA, i);
            }
        }


        return result;
    }

    private void checkResult(int[] B, int[] result, Set<Integer> setA, Set<Integer> setB, int index) {
        if (setA.contains(B[index])) {
            result[index]++;
            setA.remove(B[index]);
        } else {
            setB.add(B[index]);
        }
    }

}
