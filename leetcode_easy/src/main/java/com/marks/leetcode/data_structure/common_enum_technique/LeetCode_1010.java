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
 * @date 2025/1/8 15:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1010 {
    /**
     * @Description:
     * 在歌曲列表中，第 i 首歌曲的持续时间为 time[i] 秒。
     *
     * 返回其总持续时间（以秒为单位）可被 60 整除的歌曲对的数量。
     * 形式上，我们希望下标数字 i 和 j 满足  i < j 且有 (time[i] + time[j]) % 60 == 0。
     * tips:
     * 1 <= time.length <= 6 * 10^4
     * 1 <= time[i] <= 500
     * @param time
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 15:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numPairsDivisibleBy60(int[] time) {
        int result;
        result = method_01(time);
        result = method_02(time);
        return result;
    }

    /**
     * @Description: 使用一维数组int[] pre = new int[60] 存储 time[i] % 60的取余的值的数量
     * AC:2ms/52.26MB
     * @param time
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 15:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] time) {
        int[] pre = new int[60];
        int ans = 0;
        for (int t : time) {
            int key = t % 60;
            int temp_key = 60 - key;
            if (key == 0 && pre[key] > 0) {
                ans += pre[key];
            } else if (key != 0 && pre[temp_key] > 0) {
                ans += pre[temp_key];
            }
            pre[key]++;
        }
        return ans;
    }

    /**
     * @Description: Map存time[i] % 60 后的余数 作为key, 数量作为value,
     * AC:22ms/51.74MB
     * @param time
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 15:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] time) {
        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int t : time) {
            int key = t % 60;
            int temp_key = 60 - key;
            if (key == 0 && map.containsKey(key)) {
                ans += map.get(key);
            } else if (map.containsKey(temp_key)){
                // key != 0
                ans += map.get(temp_key);
            }
            map.put(key, map.getOrDefault(key, 0) + 1);
        }
        return ans;
    }
}
