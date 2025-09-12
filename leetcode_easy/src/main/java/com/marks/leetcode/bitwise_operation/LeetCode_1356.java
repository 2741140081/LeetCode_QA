package com.marks.leetcode.bitwise_operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/10 14:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1356 {

    
    /**
     * @Description:
     * 给你一个整数数组 arr 。
     * 请你将数组中的元素按照其二进制表示中数字 1 的数目升序排序。
     *
     * 如果存在多个数字二进制中 1 的数目相同，则必须将它们按照数值大小升序排列。
     *
     * 请你返回排序后的数组。
     *
     * tips:
     * 1 <= arr.length <= 500
     * 0 <= arr[i] <= 10^4
     * @param arr 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/10 14:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] sortByBits(int[] arr) {
        int[] result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：arr = [0,1,2,3,4,5,6,7,8]
     * 输出：[0,1,2,4,8,3,5,6,7]
     * 1. arr = [0000, 0001, 0010, 0011, 0100, 0101, 0110, 0111, 1000]
     *
     * AC: 7ms/44.13MB
     * @param arr 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/10 14:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] arr) {
        // 先排序
        Arrays.sort(arr);
        // 最大的1的数量是多少? 10^4, 10000 的二进制数, 2^10 = 1024 * 2^4 = 2^14 > 10^4
        int max_bit_num = 14;
        List<Integer>[] lists = new ArrayList[max_bit_num];
        for (int i = 0; i < max_bit_num; i++) {
            lists[i] = new ArrayList<>();
        }
        for (int num : arr) {
            int bit_num = Integer.bitCount(num);
            lists[bit_num].add(num);
        }

        List<Integer> list = new ArrayList<>();
        for (List<Integer> nums : lists) {
            list.addAll(nums);
        }
        // 将list 转为 int[]
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

}
