package com.marks.leetcode.data_structure.common_enum_technique;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/7 16:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2001 {
    /**
     * @Description: [
     * 用一个下标从 0 开始的二维整数数组 rectangles 来表示 n 个矩形，其中 rectangles[i] = [widthi, heighti] 表示第 i 个矩形的宽度和高度。
     *
     * 如果两个矩形 i 和 j（i < j）的宽高比相同，则认为这两个矩形 可互换 。更规范的说法是，两个矩形满足 widthi/heighti == widthj/heightj（使用实数除法而非整数除法），则认为这两个矩形 可互换 。
     *
     * 计算并返回 rectangles 中有多少对 可互换 矩形。
     * tips:
     * n == rectangles.length
     * 1 <= n <= 10^5
     * rectangles[i].length == 2
     * 1 <= widthi, heighti <= 10^5
     *
     * ]
     * @param rectangles
     * @return long
     * @author marks
     * @CreateDate: 2025/1/7 16:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long interchangeableRectangles(int[][] rectangles) {
        long result;
        result = method_01(rectangles);
        result = method_02(rectangles);
        return result;
    }

    private long method_02(int[][] rectangles) {
        Map<String, Long> map = new HashMap<>();
        long ans = 0;
        for (int[] rectangle : rectangles) {
            int g = gcd(rectangle[0], rectangle[1]);
            String key = rectangle[0] / g + "_" + rectangle[1] / g;
            ans += map.getOrDefault(key, 0L);
            map.put(key, map.getOrDefault(key, 0L) + 1);
        }
        return ans;
    }

    private int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }


    /**
     * @Description: [
     * TLE: 42/46
     * TLE‌：Time Limit Exceeded的缩写，表示程序运行时间超过了题目设置的限制‌
     * ]
     * @param rectangles
     * @return long
     * @author marks
     * @CreateDate: 2025/1/7 16:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[][] rectangles) {
        int n = rectangles.length;
        long ans = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (rectangles[i][0] * rectangles[j][1] == rectangles[i][1] * rectangles[j][0]) {
                    ++ans;
                }
            }
        }
        return ans;
    }
}
