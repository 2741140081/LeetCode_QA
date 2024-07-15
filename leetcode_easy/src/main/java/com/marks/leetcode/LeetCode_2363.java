package com.marks.leetcode;

import java.util.*;

/**
 *给你两个二维整数数组items1 和items2，表示两个物品集合。每个数组items有以下特质：
 *
 * items[i] = [valuei, weighti] 其中valuei表示第i件物品的价值，weighti表示第 i件物品的 重量。
 * items中每件物品的价值都是 唯一的。
 * 请你返回一个二维数组ret，其中ret[i] = [valuei, weighti]，weighti是所有价值为valuei物品的重量之和。
 *
 * 注意：ret应该按价值 升序排序后返回。
 * 
 */
public class LeetCode_2363 {
    public static void main(String[] args) {
        int[][] items1 = {{1,1},{4,5},{3,8}};
        int[][] items2 = {{3,1},{1,5}};

        mergeSimilarItems(items1, items2);
    }

    public static List<List<Integer>> mergeSimilarItems(int[][] items1, int[][] items2) {

        List<List<Integer>> ret = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < items1.length; i++) {
            for (int i1 = 0; i1 < items1[i].length -1; i1++) {
                map.put(items1[i][i1],items1[i][i1+1]);
            }
        }

        for (int i = 0; i < items2.length; i++) {
            for (int i1 = 0; i1 < items2[i].length -1; i1++) {
                if (map.containsKey(items2[i][i1])) {
                    map.put(items2[i][i1], map.get(items2[i][i1]) + items2[i][i1+1]);
                }else {
                    map.put(items2[i][i1], items2[i][i1+1]);
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(entry.getKey());
            temp.add(entry.getValue());
            ret.add(temp);
        }

        Collections.sort(ret, (a,b) -> a.get(0) - b.get(0));


        return ret;
    }
}
