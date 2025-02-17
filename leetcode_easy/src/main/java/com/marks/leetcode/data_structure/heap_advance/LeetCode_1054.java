package com.marks.leetcode.data_structure.heap_advance;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/17 11:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1054 {
    public int[] rearrangeBarcodes(int[] barcodes) {
        int[] result;
        result = method_01(barcodes);
        return result;
    }

    /**
     * @Description:
     * AC: 48ms/45.41MB
     * @param barcodes
     * @return int[]
     * @author marks
     * @CreateDate: 2025/2/17 14:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] barcodes) {
        int n = barcodes.length;
        int[] ans = new int[n];
        Map<Integer, Integer> map = new HashMap<>();
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] == o2[0]) {
                return o1[1] - o2[1];
            } else {
                return o2[0] - o1[0];
            }
        });
        for (int i = 0; i < n; i++) {
            map.merge(barcodes[i], 1, Integer::sum);
        }
        for (Integer key : map.keySet()) {
            queue.offer(new int[]{map.get(key), key});
        }

        int index = 0;
        while (!queue.isEmpty()) {
            int curr_ch = 0;
            if (index > 0) {
                curr_ch = ans[index - 1];
            }
            if (queue.size() > 1) {
                // 每次取2个
                int[] first = queue.poll();
                if (curr_ch != first[1]) {
                    ans[index++] = first[1];
                    if (queue.peek()[0] == 1) {
                        ans[index++] = queue.poll()[1];
                    } else {
                        int[] second = queue.poll();
                        second[0]--;
                        ans[index++] = second[1];
                        queue.offer(second);
                    }
                    if (first[0] > 1) {
                        first[0]--;
                        queue.offer(first);
                    }

                }
            } else {
                if (queue.peek()[0] == 1) {
                    ans[index++] = queue.peek()[1];
                }
                break;
            }
        }

        return ans;
    }
}
