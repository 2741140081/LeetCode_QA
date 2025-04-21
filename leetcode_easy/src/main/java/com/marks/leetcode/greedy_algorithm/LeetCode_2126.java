package com.marks.leetcode.greedy_algorithm;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/28 10:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2126 {
    /**
     * @Description:
     * 给你一个整数 mass ，它表示一颗行星的初始质量。再给你一个整数数组 asteroids ，其中 asteroids[i] 是第 i 颗小行星的质量。
     *
     * 你可以按 任意顺序 重新安排小行星的顺序，然后让行星跟它们发生碰撞。
     * 如果行星碰撞时的质量 大于等于 小行星的质量，那么小行星被 摧毁 ，并且行星会 获得 这颗小行星的质量。否则，行星将被摧毁。
     *
     * 如果所有小行星 都 能被摧毁，请返回 true ，否则返回 false 。
     * @param mass 
     * @param asteroids
     * @return boolean
     * @author marks
     * @CreateDate: 2025/3/28 10:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        boolean result;
        result = method_01(mass, asteroids);
        return result;
    }

    /**
     * @Description:
     * AC: 24ms/59.10MB
     * @param mass 
     * @param asteroids 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/3/28 10:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int mass, int[] asteroids) {
        boolean flag = true;
        Arrays.sort(asteroids);
        long init = mass;
        for (int asteroid : asteroids) {
            if (init >= asteroid) {
                init += asteroid;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
