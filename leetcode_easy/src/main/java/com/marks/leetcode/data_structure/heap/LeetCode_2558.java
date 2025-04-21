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
 * @date 2025/2/7 16:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2558 {
    /**
     * @Description:
     * 给你一个整数数组 gifts ，表示各堆礼物的数量。每一秒，你需要执行以下操作：
     *
     * 选择礼物数量最多的那一堆。
     * 如果不止一堆都符合礼物数量最多，从中选择任一堆即可。
     * 将堆中的礼物数量减少到堆中原来礼物数量的平方根，向下取整。
     * 返回在 k 秒后剩下的礼物数量。
     * @param gifts 
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2025/2/7 16:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long pickGifts(int[] gifts, int k) {
        long result;
        result = method_01(gifts, k);
        return result;
    }
    
    /**
     * @Description:
     * AC:4ms/41.75MB
     * @param gifts 
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2025/2/7 16:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] gifts, int k) {
        int n = gifts.length;
        long ans = 0, sum = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int i = 0; i < n; i++) {
            ans += gifts[i];
            queue.offer(gifts[i]);
        }

        while (k > 0) {
            int curr_value = queue.poll();
            int value =(int) Math.sqrt(curr_value);
            queue.offer(value);
            sum += (curr_value - value);
            k--;
        }
        return ans - sum;
    }
}
