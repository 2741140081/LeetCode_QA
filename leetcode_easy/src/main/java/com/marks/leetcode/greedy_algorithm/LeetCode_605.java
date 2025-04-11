package com.marks.leetcode.greedy_algorithm;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/9 11:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_605 {
    
    /**
     * @Description:
     * 假设有一个很长的花坛，一部分地块种植了花，另一部分却没有。可是，花不能种植在相邻的地块上，它们会争夺水源，两者都会死去。
     *
     * 给你一个整数数组 flowerbed 表示花坛，由若干 0 和 1 组成，其中 0 表示没种植花，1 表示种植了花。另有一个数 n ，能否在不打破种植规则的情况下种入 n 朵花？
     * 能则返回 true ，不能则返回 false 。
     * @param flowerbed 
     * @param n
     * @return boolean
     * @author marks
     * @CreateDate: 2025/4/9 11:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        boolean result;
        result = method_01(flowerbed, n);
        return result;
    }

    /**
     * @Description:
     *
     * AC: 1ms/44.69MB
     *
     * @param flowerbed
     * @param n 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/4/9 11:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] flowerbed, int n) {
        int ans = 0;
        if (flowerbed.length == 1) {
            if (flowerbed[0] == 1) {
                return n == 0;
            } else {
                return n <= 1;
            }
        }
        if (flowerbed[0] == 0 && flowerbed[1] == 0) {
            ans++;
            flowerbed[0] = 1;
        }
        if (flowerbed[flowerbed.length - 1] == 0 && flowerbed[flowerbed.length - 2] == 0) {
            ans++;
            flowerbed[flowerbed.length - 1] = 1;
        }
        for (int i = 1; i < flowerbed.length - 1; i++) {
            if (flowerbed[i] == 0 && flowerbed[i - 1] == 0 && flowerbed[i + 1] == 0) {
                flowerbed[i] = 1;
                ans++;
            }
        }
        return ans >= n;
    }
}
