package com.marks.leetcode.bfs_algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_752 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/24 14:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_752 {

    /**
     * @Description:
     * 你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。
     * 每个拨轮可以自由旋转：例如把 '9' 变为 '0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。
     * 锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。
     * 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。
     * 字符串 target 代表可以解锁的数字，你需要给出解锁需要的最小旋转次数，如果无论如何不能解锁，返回 -1 。
     * @param: deadends
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2025/12/24 14:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int openLock(String[] deadends, String target) {
        int result;
        result = method_01(deadends, target);
        return result;
    }

    /**
     * @Description:
     * 1. 广度优先搜索, 使用Set<String> 存储已访问的数字
     * 2. 对于每一个拨轮的数字, 可以进行加1或者减1
     * AC: 91ms/47.51MB
     * @param: deadends
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2025/12/24 14:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] deadends, String target) {
        // 将deadends转换成Set<String>, 方便进行查询
        Set<String> deadendsSet = new HashSet<>();
        for (String deadend : deadends) {
            deadendsSet.add(deadend);
        }
        if (deadendsSet.contains("0000")) {
            // 死亡数字中包含"0000", 返回-1
            return -1;
        }
        Set<String> visited = new HashSet<>();
        visited.add("0000");
        Queue<String> queue = new LinkedList<>();
        queue.offer("0000");
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                if (curr.equals(target)) {
                    return step;
                }
                char[] array = curr.toCharArray();
                for (int j = 0; j < 4; j++) {
                    char temp = array[j];
                    // 加1, 需要判断当前是否是9
                    if (temp == '9') {
                        array[j] = '0';
                    } else {
                        array[j]++;
                    }
                    String next = new String(array);
                    if (!deadendsSet.contains(next) && !visited.contains(next)) {
                        queue.offer(next);
                        visited.add(next);
                    }
                    // 减1, 需要判断当前是否是0
                    array[j] = temp;
                    if (temp == '0') {
                        array[j] = '9';
                    } else {
                        array[j]--;
                    }
                    next = new String(array);
                    if (!deadendsSet.contains(next) && !visited.contains(next)) {
                        queue.offer(next);
                        visited.add(next);
                    }
                    array[j] = temp; // 还原
                }
            }
            step++;
        }

        return -1;
    }

}
