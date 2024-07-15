package com.marks.leetcode.array;

/*
* 输入：words = ["adc","wzy","abc"]
输出："abc"
解释：
- "adc" 的差值整数数组是 [3 - 0, 2 - 3] = [3, -1] 。
- "wzy" 的差值整数数组是 [25 - 22, 24 - 25]= [3, -1] 。
- "abc" 的差值整数数组是 [1 - 0, 2 - 1] = [1, 1] 。
不同的数组是 [1, 1]，所以返回对应的字符串，"abc"。
* */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeetCode_2451 {
    public static void main(String[] args) {
        String[] words = {"adc","wzy","abc"};
        System.out.println(oddString(words));
    }

    private static String oddString(String[] words) {
        Map<String, List<Integer>> maps = new HashMap<>();
        Map<List<Integer>, Integer> mapValue = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String str = words[i];
            char[] chars = str.toCharArray();
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < chars.length-1; j++) {
                Integer temp = chars[j+1]-chars[j];
                list.add(temp);
            }
            System.out.println(mapValue.get(list));
            mapValue.put(list,mapValue.getOrDefault(list,0)+1);
            maps.put(str, list);
        }
        List<Integer> list2 = new ArrayList<>();

        /**
         for(Map.Entry entry : map1.entrySet()) {
             System.out.println(entry.getKey() + "->" + entry.getValue());
         }
        */
        for (Map.Entry<List<Integer>, Integer> listIntegerEntry : mapValue.entrySet()) {
            if(listIntegerEntry.getValue() == 1) {
                list2 = listIntegerEntry.getKey();
            }
        }

        for (Map.Entry<String, List<Integer>> stringListEntry : maps.entrySet()) {
            if (stringListEntry.getValue() == list2) {
                return stringListEntry.getKey();
            }
        }
        return "";
    }
}
