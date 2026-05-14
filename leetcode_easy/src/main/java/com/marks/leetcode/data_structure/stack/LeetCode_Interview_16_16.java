package com.marks.leetcode.data_structure.stack;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_Interview_16_16 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/14 16:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_Interview_16_16 {

    /**
     * @Description:
     * 给定一个整数数组，编写一个函数，找出索引m和n，只要将索引区间[m,n]的元素排好序，整个数组就是有序的。
     * 注意：n-m尽量最小，也就是说，找出符合条件的最短序列。
     * 函数返回值为[m,n]，若不存在这样的m和n（例如整个数组是有序的），请返回[-1,-1]。
     * tips:
     * 0 <= len(array) <= 1000000
     * @param: array
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/14 16:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] subSort(int[] array) {
        int[] result;
        result = method_01(array);
        return result;
    }

    /**
     * @Description:
     * AC: 4ms/69.24MB
     * @param: array
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/14 16:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] array) {
        if(array == null || array.length == 0) return new int[]{-1, -1};
        int last = -1, first = -1;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        int len = array.length;
        for(int i = 0; i < len; i++){
            if(array[i] < max){
                last = i;
            }else{
                max = Math.max(max, array[i]);
            }

            if(array[len - 1 - i] > min){
                first = len - 1 - i;
            }else{
                min = Math.min(min, array[len - 1 - i]);
            }
        }
        return new int[] {first, last};
    }

}
