package com.marks.leetcode.data_structure.heap_advance;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/12 10:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1354 {
    public boolean isPossible(int[] target) {
        boolean result;
        result = method_01(target);
        return result;
    }

    /**
     * @Description:
     * x 如果表示当前的最小值
     *
     *
     * 需要的参数
     * 1. 已经确定的值的sum
     * 2. 未初始化的 "1"的数量 count
     * 3. 当前的总和
     * @param target
     * @return boolean
     * @author marks
     * @CreateDate: 2025/2/12 10:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] target) {
        if (target.length == 1) {
            return true;
        }
        PriorityQueue<Long> pq = new PriorityQueue<>(Collections.reverseOrder());
        long sum = 0;
        for (int i = 0; i < target.length; i++) {
            sum += target[i];
            pq.offer((long)target[i]);
        }
        //如果此时队列为空或者最大值就是1，直接return true
        if (pq.isEmpty() || pq.peek() == 1) {
            return true;
        }
        while (true) {
            //取出最大的那个
            Long poll = pq.poll();
            //如果此时堆中最大的为1
            if (pq.peek() == 1) {
                //直接看它满足或不满足公式
                return (poll - 1) % (sum - poll) == 0;
            } else {
                //需要计算多少轮才能比第二小的数小
                long n = (poll - pq.peek()) / (sum - poll) + 1;
                //得到这个数字
                long x = poll - n * (sum - poll);
                if (x <= 0) {
                    return false;
                }
                //更新sum
                sum = poll - (sum - poll) * (n - 1);
                pq.offer(x);
            }
        }
    }
}
