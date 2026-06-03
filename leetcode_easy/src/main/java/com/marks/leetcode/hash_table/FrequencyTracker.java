package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: FrequencyTracker </p>
 * <p>描述: LeetCode_2671. 频率跟踪器 </p>
 * 请你设计并实现一个能够对其中的值进行跟踪的数据结构，并支持对频率相关查询进行应答。
 * 实现 FrequencyTracker 类：
 * FrequencyTracker()：使用一个空数组初始化 FrequencyTracker 对象。
 * void add(int number)：添加一个 number 到数据结构中。
 * void deleteOne(int number)：从数据结构中删除一个 number 。数据结构 可能不包含 number ，在这种情况下不删除任何内容。
 * bool hasFrequency(int frequency): 如果数据结构中存在出现 frequency 次的数字，则返回 true，否则返回 false。
 *
 * tips:
 * 1 <= number <= 10^5
 * 1 <= frequency <= 10^5
 * 最多调用 add、deleteOne 和 hasFrequency 共计 2 * 10^5 次
 * @author marks
 * @version v1.0
 * @date 2026/6/3 16:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class FrequencyTracker {
    private Map<Integer, Integer> frequencyMap;
    private Map<Integer, Integer> numberMap;

    /**
     * @Description:
     * 1. 将数字出现频率存储在Map中
     * 2. 需要查询对于的频率是否存在, 使用map 存储频率和次数
     * AC: 42ms/112.9MB
     * @return
     * @author marks
     * @CreateDate: 2026/06/03 17:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public FrequencyTracker() {
        frequencyMap = new HashMap<>();
        numberMap = new HashMap<>();
    }

    public void add(int number) {
        // 判断 numberMap 中是否存在 number
        if (!numberMap.containsKey(number)) {
            numberMap.put(number, 1);
            frequencyMap.merge(1, 1, Integer::sum);
        } else {
            int frequency = numberMap.get(number);
            int newFrequency = frequency + 1;
            frequencyMap.merge(frequency, -1, Integer::sum);
            frequencyMap.merge(newFrequency, 1, Integer::sum);
            // 更新 numberMap 中 number 的 frequency
            numberMap.put(number, newFrequency);
        }
    }

    public void deleteOne(int number) {
        // 判断 number是否存在 numberMap
        if (numberMap.containsKey(number)) {
            int frequency = numberMap.get(number);
            if (frequency == 1) { // 防止出现负数的频率
                frequencyMap.merge(frequency, -1, Integer::sum);
                numberMap.remove(number);
                return;
            }
            int newFrequency = frequency - 1;
            frequencyMap.merge(frequency, -1, Integer::sum);
            frequencyMap.merge(newFrequency, 1, Integer::sum);
            numberMap.put(number, newFrequency);
        }
    }

    public boolean hasFrequency(int frequency) {
        if (frequencyMap.containsKey(frequency)) {
            return frequencyMap.get(frequency) > 0;
        }
        return false;
    }

}
