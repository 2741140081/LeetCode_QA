package com.marks.leetcode.data_structure.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/7 16:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2530 {
    public long maxKelements(int[] nums, int k) {
        long result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * AC:124ms/61.90MB
     * @param nums 
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2025/2/7 16:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int k) {
        int n = nums.length;
        long ans = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (int i = 0; i < n; i++) {
            queue.offer(nums[i]);
        }
        while (k > 0) {
            int value = queue.poll();
            ans += value;
            int temp =(int) Math.ceil((double) value / 3);
            queue.offer(temp);
            k--;
        }
        return ans;
    }
}
