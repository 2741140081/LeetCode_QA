package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_037 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/29 16:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_037 {

    /**
     * @Description:
     * 给定一个整数数组 asteroids，表示在同一行的小行星。
     * 对于数组中的每一个元素，其绝对值表示小行星的大小，正负表示小行星的移动方向（正表示向右移动，负表示向左移动）。每一颗小行星以相同的速度移动。
     * 找出碰撞后剩下的所有小行星。碰撞规则：两个行星相互碰撞，较小的行星会爆炸。如果两颗行星大小相同，则两颗行星都会爆炸。两颗移动方向相同的行星，永远不会发生碰撞。
     * @param: asteroids
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/29 16:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] asteroidCollision(int[] asteroids) {
        int[] result;
        result = method_01(asteroids);
        result = method_02(asteroids);
        return result;
    }


    /**
     * @Description:
     * 官方题解的优雅写法(减少很多的条件判断, 很简洁)
     * @param: asteroids
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/29 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<Integer>();
        for (int aster : asteroids) {
            boolean alive = true;
            while (alive && aster < 0 && !stack.isEmpty() && stack.peek() > 0) {
                alive = stack.peek() < -aster; // aster 是否存在
                if (stack.peek() <= -aster) {  // 栈顶小行星爆炸
                    stack.pop();
                }
            }
            if (alive) {
                stack.push(aster);
            }
        }
        int size = stack.size();
        int[] ans = new int[size];
        for (int i = size - 1; i >= 0; i--) {
            ans[i] = stack.pop();
        }
        return ans;
    }

    /**
     * @Description:
     * 1. 使用栈存储剩余的小行星, 包含2个部分 向左移动的(栈底部分, 可能为空) 和向右移动的(可能为空)
     * AC: 2ms/46.27MB
     * @param: asteroids
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/29 16:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] asteroids) {
        // 构建栈
        Deque<Integer> stack = new ArrayDeque<>();
        // 遍历数组
        for (int asteroid : asteroids) {
            if (stack.isEmpty()) {
                stack.push(asteroid);
            } else {
                // 栈不为空, 取栈顶元素
                int top = stack.peek();
                if (top < 0) {
                    // 栈中全部小行星向左移动, 可以直接添加到栈中, 无碰撞发生
                    stack.push(asteroid);
                } else {
                    // top 不存在为0 的情况, 所以不需要考虑0 的影响, 这里的条件就是 top > 0, 栈顶元素向右移动
                    if (asteroid > 0) {
                        stack.push(asteroid); // 同向, 不发生碰撞
                    } else {
                        // 反向, 必定发生碰撞, 需要判断大小
                        int abs = -asteroid;
                        // 删除所有小于 abs 的 向右移动的小行星
                        while (!stack.isEmpty() && stack.peek() > 0 && stack.peek() < abs) {
                            stack.poll();
                        }
                        // 判断质量是否相同, 优化判断
                        if (stack.isEmpty() || stack.peek() < 0) {
                            stack.push(asteroid);
                        } else {
                            if (stack.peek() == abs)
                                stack.poll();
                        }
                    }
                }
            }
        }
        int[] result = new int[stack.size()];
        for (int i = stack.size() - 1; i >= 0; i--) {
            result[i] = stack.poll();
        }
        return result;
    }

}
