package com.marks.leetcode.data_structure.prefix_sum;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/14 11:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class Interview_qs_17_05 {
    /**
     * @Description:
     * 给定一个放有字母和数字的数组，找到最长的子数组，且包含的字母和数字的个数相同。
     *
     * 返回该子数组，若存在多个最长子数组，返回左端点下标值最小的子数组。若不存在这样的数组，返回一个空数组。
     * @param array
     * @return java.lang.String[]
     * @author marks
     * @CreateDate: 2025/1/14 11:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String[] findLongestSubarray(String[] array) {
        String[] result;
        result = method_01(array);
        return result;
    }

    /**
     * @Description:
     * AC:32ms/63.71MB
     * 将 isDigit(array[i]) 替换为 Character.isLetter(array[i].charAt(0))
     * AC:28ms/57.23MB
     * @param array
     * @return java.lang.String[]
     * @author marks
     * @CreateDate: 2025/1/14 11:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String[] method_01(String[] array) {
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        int left = -1;
        int len = 0;
        map.put(0, -1);
        for (int i = 0; i < array.length; i++) {
//            count = (count + (isDigit(array[i]) ? 1 : -1));
            count = (count + (Character.isLetter(array[i].charAt(0)) ? 1 : -1));
            if (map.containsKey(count)) {
                if (len < (i - map.get(count))) {
                    len = i - map.get(count);
                    left = map.get(count) + 1;
                }else if (len == (i - map.get(count))) {
                    left = Math.min(left, i - map.get(count) + 1);
                }
            }else {
                map.put(count, i);
            }
        }
        String[] ans = new String[len];
        for (int i = 0; i < len; i++) {
            ans[i] = array[left++];
        }
        return ans;
    }

    private boolean isDigit(String str) {
        return "0123456789".indexOf(str.substring(0, 1)) != -1;
    }
}
