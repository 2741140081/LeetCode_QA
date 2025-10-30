package com.marks.leetcode.DP;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class LeetCode_838 {


    /***
     * @Description:
     * n 张多米诺骨牌排成一行，将每张多米诺骨牌垂直竖立。在开始时，同时把一些多米诺骨牌向左或向右推。
     * 每过一秒，倒向左边的多米诺骨牌会推动其左侧相邻的多米诺骨牌。同样地，倒向右边的多米诺骨牌也会推动竖立在其右侧的相邻多米诺骨牌。
     * 如果一张垂直竖立的多米诺骨牌的两侧同时有多米诺骨牌倒下时，由于受力平衡， 该骨牌仍然保持不变。
     * 就这个问题而言，我们会认为一张正在倒下的多米诺骨牌不会对其它正在倒下或已经倒下的多米诺骨牌施加额外的力。
     * 给你一个字符串 dominoes 表示这一行多米诺骨牌的初始状态，其中：
     * dominoes[i] = 'L'，表示第 i 张多米诺骨牌被推向左侧，
     * dominoes[i] = 'R'，表示第 i 张多米诺骨牌被推向右侧，
     * dominoes[i] = '.'，表示没有推动第 i 张多米诺骨牌。
     * 返回表示最终状态的字符串。
     *
     * tips:
     * n == dominoes.length
     * 1 <= n <= 10^5
     * dominoes[i] 为 'L'、'R' 或 '.'
     * @param: dominoes
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/10/29 11:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String pushDominoes(String dominoes) {
        String result;
        result = method_01(dominoes);
        return result;
    }


    /***
     * @Description:
     * 1. 如果用广度有限搜索, 会存在一个问题, 没有办法直接确定下一个, 例如某个骨牌左右两侧同时到达了倒下, 无法确定 特色处理比较
     * AC: 82ms/54.19MB
     * @param dominoes
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/10/29 11:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String dominoes) {
        int n = dominoes.length();
        char[] chars = dominoes.toCharArray();
        boolean[] visited = new boolean[n];
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int i = 0; i < n; i++) {
            if (chars[i] == 'R') {
                queue.add(new int[]{0, i});
                visited[i] = true;
            } else if (chars[i] == 'L') {
                queue.add(new int[]{0, i});
                visited[i] = true;
            }
        }
        while (!queue.isEmpty()) {
            int size = queue.size();
            int x = queue.peek()[0] + 1;
            Map<Integer, Character> map = new HashMap<>();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int y = curr[1];
                int next;
                if (chars[y] == 'R') {
                    next = y + 1;
                    if (next < n && !visited[next]) {
                        if (map.containsKey(next)) {
                            map.remove(next);
                        } else {
                            map.put(next, 'R');
                        }
                    }
                } else {
                    // chars[y] == 'L'
                    next = y - 1;
                    if (next >= 0 && !visited[next]) {
                        if (map.containsKey(next)) {
                            map.remove(next);
                        } else {
                            map.put(next, 'L');
                        }
                    }
                }
            }
            for (Map.Entry<Integer, Character> entry : map.entrySet()) {
                queue.add(new int[]{x, entry.getKey()});
                chars[entry.getKey()] = entry.getValue();
                visited[entry.getKey()] = true;
            }

        }
        return new String(chars);
    }

}
