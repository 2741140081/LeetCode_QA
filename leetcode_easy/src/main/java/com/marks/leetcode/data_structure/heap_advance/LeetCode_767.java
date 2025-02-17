package com.marks.leetcode.data_structure.heap_advance;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/17 10:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_767 {
    /**
     * @Description:
     * 给定一个字符串 s ，检查是否能重新排布其中的字母，使得两相邻的字符不同。
     * 返回 s 的任意可能的重新排列。若不可行，返回空字符串 "" 。
     *
     * tips:
     * 1 <= s.length <= 500
     * s 只包含小写字母
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/2/17 10:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String reorganizeString(String s) {
        String result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * AC: 3ms/40.80MB
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/2/17 10:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        StringBuilder ans = new StringBuilder();
        boolean flag = true;

        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] == o2[0]) {
                return o1[1] - o2[1];
            } else {
                return o2[0] - o1[0];
            }
        });
        int[] array = new int[26];
        for (int i = 0; i < s.length(); i++) {
            array[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (array[i] > 0) {
                queue.offer(new int[]{array[i], i + 'a'});
            }
        }
        ans.append('#');
        while (!queue.isEmpty() && flag) {
            int curr_len = ans.length();
            char curr_ch = ans.charAt(curr_len - 1);
            if (queue.size() > 1) {
                // 每次取2个
                int[] first = queue.poll();
                if (curr_ch != first[1]) {
                    ans.append((char) first[1]);
                    if (queue.peek()[0] == 1) {
                        ans.append((char) queue.poll()[1]);
                    } else {
                        int[] second = queue.poll();
                        second[0]--;
                        ans.append((char) second[1]);
                        queue.offer(second);
                    }
                    if (first[0] > 1) {
                        first[0]--;
                        queue.offer(first);
                    }

                }

            } else {
                if ( queue.peek()[0] > 1 || (curr_ch == queue.peek()[1]) ) {
                    flag = false;
                } else {
                    if (queue.peek()[0] == 1) {
                        ans.append((char) queue.peek()[1]);
                    }
                }
                break;
            }
        }

        return flag ? ans.substring(1) : "";
    }
}
