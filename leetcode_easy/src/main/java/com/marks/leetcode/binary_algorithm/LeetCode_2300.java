package com.marks.leetcode.binary_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/15 10:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2300 {
    /**
     * @Description: [
     * 给你两个正整数数组 spells 和 potions ，长度分别为 n 和 m ，
     * 其中 spells[i] 表示第 i 个咒语的能量强度，potions[j] 表示第 j 瓶药水的能量强度。
     * 同时给你一个整数 success 。一个咒语和药水的能量强度 相乘 如果 大于等于 success ，那么它们视为一对 成功 的组合。
     *
     * 请你返回一个长度为 n 的整数数组 pairs，其中 pairs[i] 是能跟第 i 个咒语成功组合的 药水 数目。
     *
     *
     * ]
     * @param spells
     * @param potions
     * @param success
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/15 10:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        int[] result;
        result = method_01(spells, potions, success);
        return result;
    }
    /**
     * @Description: [
     * AC:48ms/64.62MB
     * ]
     * @param spells
     * @param potions
     * @param success
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/15 10:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int[] method_01(int[] spells, int[] potions, long success) {
        int n = spells.length;
        int m = potions.length;
        Arrays.sort(potions);
        int[] pairs = new int[n];
        for (int i = 0; i < n; i++) {
            pairs[i] = m - binarySearch(spells[i], potions, success);
        }

        return pairs;
    }

    /**
     * @Description: [
     * E1:
     * 第 0 个咒语：5 * [1,2,3,4,5] = [5,10,15,20,25] 。总共 4 个成功组合。
     * success = 7
     * left = 0, right = 4, mid = 2, potions[2] = 3, 5 * 3 > 7
     * left = 0, right = 1, mid = 0, potions[0] = 1, 5 * 1 < 7
     * left = 1, right = 1, mid = 1, potions[1] = 2, 5 * 2 > 7
     * left = 1, right = 0, break
     * m - 1
     *
     * 第 1 个咒语：1 * [1,2,3,4,5] = [1,2,3,4,5] 。总共 0 个成功组合。
     * left = 0, right = 4, mid = 2, potions[2] = 3, 1 * 3 < 7
     * left = 3, right = 4, mid = 3, potions[3] = 4, 1 * 4 < 7
     * left = 4, right = 4, mid = 4, potions[4] = 5, 1 * 5 < 7;
     * left = 5, right = 4, break;
     * m - 5 = 0
     * ]
     * @param spell
     * @param potions
     * @param success
     * @return int
     * @author marks
     * @CreateDate: 2024/11/15 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int binarySearch(int spell, int[] potions, long success) {
        int left = 0;
        int right = potions.length - 1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if ((long) potions[mid] * spell < success) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
}
