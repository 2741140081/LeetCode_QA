package com.marks.leetcode.hotLC;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_347 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/10 14:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_347 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
     * @param: nums
     * @param: k
     * @return int[]
     * @author marks
     * @CreateDate: 2025/12/10 14:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] topKFrequent(int[] nums, int k) {
        int[] result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 遍历数组, 使用Map 存储数组元素出现的次数
     * 2. 手写一个大根堆, 然后取k次元素
     * AC: 10ms/46.68MB
     * @param: nums
     * @param: k
     * @return int[]
     * @author marks
     * @CreateDate: 2025/12/10 14:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            // 使用map.merge 方法
            map.merge(num, 1, Integer::sum);
            // map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        // 手写大根堆, 即不使用现有的优先队列
        int heapSize = map.size();
        int[][] array = buildMaxHeap(map, heapSize);

        int[] ans = new int[k];
        // 取出k个元素
        for (int i = 0; i < k; i++) {
            ans[i] = array[0][0]; // 永远取堆顶的元素, 因为堆顶是最大元素
            heapSize--;
            swap(array, 0, heapSize);
            maxHeapify(array, 0, heapSize);
        }

        return ans;
    }

    private int[][] buildMaxHeap(Map<Integer, Integer> map, int heapSize) {
        int[][] array = new int[heapSize][2];
        int i = 0;
        // 遍历map, 填充array
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            array[i][0] = entry.getKey();
            array[i][1] = entry.getValue();
            i++;
        }
        for (int j = heapSize / 2 - 1; j >= 0; j--) {
            maxHeapify(array, j, heapSize);
        }
        return array;
    }

    private void maxHeapify(int[][] array, int i, int heapSize) {
        int left = i * 2 + 1, right = i * 2 + 2, largest = i;
        if (left < heapSize && array[left][1] > array[largest][1]) {
            largest = left;
        }
        if (right < heapSize && array[right][1] > array[largest][1]) {
            largest = right;
        }
        if (largest != i) {
            swap(array, i, largest);
            maxHeapify(array, largest, heapSize);
        }
    }

    private void swap(int[][] array, int i, int j) {
        int temp0 = array[i][0];
        int temp1 = array[i][1];
        array[i][0] = array[j][0];
        array[i][1] = array[j][1];
        array[j][0] = temp0;
        array[j][1] = temp1;
    }

}
