package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/17 16:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_735 {
    public int[] asteroidCollision(int[] asteroids) {
        int[] result;
        result = method_01(asteroids);
        return result;
    }

    /**
     * @Description:
     * AC:6ms/44.67MB
     * @param asteroids
     * @return int[]
     * @author marks
     * @CreateDate: 2025/1/17 16:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] asteroids) {
        List<Integer> list = new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();
        int n = asteroids.length;
        int left = 0;
        int right = n - 1;
        // 找到第一个向右, 即asteroids[left] > 0
        while (left < n && asteroids[left] < 0) {
            list.add(asteroids[left]);
            left++;
        }
        // 找到最后一个向左, 即asteroids[right] < 0
        while (right >= 0 && asteroids[right] > 0) {
            right--;
        }
        // [left, right] 之间的会发生碰撞
        if (left > right) {
            return asteroids;
        }else {
            stack.push(asteroids[left]);
        }

        for (int i = left + 1; i <= right; i++) {
            if (asteroids[i] > 0) {
                stack.push(asteroids[i]);
            }else {
                boolean flag = true;
                int abs = -asteroids[i];
                while (!stack.isEmpty() && stack.peek() <= abs) {
                    if (abs > stack.peek()) {
                        stack.poll();
                    } else if (abs == stack.peek()){
                        stack.poll();
                        flag = false;
                        break;
                    } else {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    list.add(asteroids[i]);
                }
            }
        }
        while (!stack.isEmpty()) {
            list.add(stack.pollLast());
        }
        for (int i = right + 1; i < n; i++) {
            list.add(asteroids[i]);
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}
