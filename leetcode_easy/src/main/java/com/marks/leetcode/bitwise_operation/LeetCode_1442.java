package com.marks.leetcode.bitwise_operation;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/18 14:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1442 {

    /**
     * @Description:
     * 给你一个整数数组 arr 。
     *
     * 现需要从数组中取三个下标 i、j 和 k ，其中 (0 <= i < j <= k < arr.length) 。
     *
     * a 和 b 定义如下：
     *
     * a = arr[i] ^ arr[i + 1] ^ ... ^ arr[j - 1]
     * b = arr[j] ^ arr[j + 1] ^ ... ^ arr[k]
     * 注意：^ 表示 按位异或 操作。
     *
     * 请返回能够令 a == b 成立的三元组 (i, j , k) 的数目。
     * tips:
     * 1 <= arr.length <= 300
     * 1 <= arr[i] <= 10^8
     * @param arr
     * @return int
     * @author marks
     * @CreateDate: 2025/9/18 14:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countTriplets(int[] arr) {
        int result;
        result = method_01(arr);
        result = method_02(arr);
        return result;
    }

    private int method_02(int[] arr) {
        int n = arr.length;
        Map<Integer, Integer> cnt = new HashMap<Integer, Integer>();
        Map<Integer, Integer> total = new HashMap<Integer, Integer>();
        int ans = 0, s = 0;
        for (int k = 0; k < n; ++k) {
            int val = arr[k];
            if (cnt.containsKey(s ^ val)) {
                ans += cnt.get(s ^ val) * k - total.get(s ^ val);
            }
            cnt.put(s, cnt.getOrDefault(s, 0) + 1);
            total.put(s, total.getOrDefault(s, 0) + k);
            s ^= val;
        }
        return ans;
    }


    /**
     * @Description:
     * E1:
     * 输入：arr = [2,3,1,6,7]
     * 输出：4
     * 解释：满足题意的三元组分别是 (0,1,2), (0,2,2), (2,3,4) 以及 (2,4,4)
     * 1. 前缀异或 int[] prevXor, 三重for循环, 分别是 k, j, i
     * 2. a = prevXor[j - 1],
     * AC: 37ms/40.20MB
     * @param arr 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/18 14:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {
        int n = arr.length;
        int[] prevXor = new int[n];
        prevXor[0] = arr[0];
        for (int i = 1; i < n; i++) {
            prevXor[i] = prevXor[i - 1] ^ arr[i];
        }
        int ans = 0;
        for (int k = prevXor.length - 1; k >= 1; k--) {
            for (int j = k; j >= 1; j--) {
                for (int i = j - 1; i >= 0; i--) {

                    int a = prevXor[j - 1] ^ (i == 0 ? 0 : prevXor[i - 1]);
                    int b = prevXor[k] ^ prevXor[j - 1];
                    if (a == b) {
                        // 输出i, j, k 的值
                        System.out.println("i: " + i + ", j: " + j + ", k: " + k);
                        ans++;
                    }
                }
            }
        }
        return ans;
    }

}
