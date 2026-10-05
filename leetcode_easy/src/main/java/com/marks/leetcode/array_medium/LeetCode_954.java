package com.marks.leetcode.array_medium;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_954 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/18 14:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_954 {

    /**
     * @Description:
     * 给定一个长度为偶数的整数数组 arr，只有对 arr 进行重组后可以满足
     * “对于每个 0 <= i < len(arr) / 2，都有 arr[2 * i + 1] = 2 * arr[2 * i]” 时，
     * 返回 true；否则，返回 false。
     *
     * tips:
     * 0 <= arr.length <= 3 * 10^4
     * arr.length 是偶数
     * -10^5 <= arr[i] <= 10^5
     * @param: arr
     * @return boolean
     * @author marks
     * @CreateDate: 2026/09/18 14:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean canReorderDoubled(int[] arr) {
        boolean result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * 1. 要求奇数位比左侧的相邻偶数位是倍数关系，且倍数关系为2
     * 2. 对 arr 进行降序排序, 对于 arr[i], 如果 map 中不存在 arr[i] * 2 的数, 则返回 false
     * AC: 86ms/53.38MB
     * @param: arr
     * @return boolean
     * @author marks
     * @CreateDate: 2026/09/18 14:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> pList = new ArrayList<>(); // 存储>=0
        List<Integer> nList = new ArrayList<>(); // 存储<0
        for (int num : arr) {
            map.merge(num, 1, Integer::sum);
            if (num >= 0) {
                pList.add(num);
            } else {
                nList.add(num);
            }
        }
        // 排序, 正数是升序排序, 负数是降序排序
        pList.sort(Integer::compare);
        nList.sort((a, b) -> b - a);
        // 分类处理正数和负数
        for (int num : pList) {
            if (map.get(num) == 0) {
                continue;
            }
            map.merge(num, -1, Integer::sum);
            if (map.getOrDefault(num * 2, 0) > 0) {
                map.merge(num * 2, -1, Integer::sum);
            } else {
                return false;
            }
        }
        for (int num : nList) {
            if (map.get(num) == 0) {
                continue;
            }
            map.merge(num, -1, Integer::sum);
            if (map.getOrDefault(num * 2, 0) > 0) {
                map.merge(num * 2, -1, Integer::sum);
            } else {
                return false;
            }
        }

        return true;
    }

}
