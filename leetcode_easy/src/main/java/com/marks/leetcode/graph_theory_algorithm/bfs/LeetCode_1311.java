package com.marks.leetcode.graph_theory_algorithm.bfs;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/24 10:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1311 {
    /**
     * @Description: [
     * 有 n 个人，每个人都有一个  0 到 n-1 的唯一 id 。
     * 给你数组 watchedVideos  和 friends ，其中 watchedVideos[i]  和 friends[i] 分别表示 id = i 的人观看过的视频列表和他的好友列表。
     * Level 1 的视频包含所有你好友观看过的视频，level 2 的视频包含所有你好友的好友观看过的视频，以此类推。一般的，Level 为 k 的视频包含所有从你出发，最短距离为 k 的好友观看过的视频。
     * 给定你的 id  和一个 level 值，请你找出所有指定 level 的视频，并将它们按观看频率升序返回。如果有频率相同的视频，请将它们按字母顺序从小到大排列。
     * ]
     * @param watchedVideos 
     * @param friends 
     * @param id 
     * @param level 
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2024/12/24 11:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> watchedVideosByFriends(List<List<String>> watchedVideos, int[][] friends, int id, int level) {
        List<String> result;
        result = method_01(watchedVideos, friends, id, level);
        return result;
    }

    /**
     * @Description: [
     * 不清楚问题出在哪里, 很烦!!!
     * 下午再看看, 感觉代码逻辑好像是没问题, 还是无法AC
     * Result:39/54, Wrong
     *
     * 找到错误了, pre[curr_i] = 1, 需要再添加 j 的时候直接修改pre[j] = 1, 而不是等queue.poll(), 取出时修改, 会导致重复的错误
     *
     * AC:29ms/45.42MB
     * ]
     * @param watchedVideos
     * @param friends
     * @param id
     * @param level
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2024/12/24 11:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_01(List<List<String>> watchedVideos, int[][] friends, int id, int level) {
        int n = watchedVideos.size();
        int[] pre = new int[n];

        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        HashMap<String, Integer> map = new HashMap<>();
        // BFS查找level = k 的friend
        int init_level = 0; // 定义当前level, level0表示 我自己这个节点\
        queue.offer(new int[] {init_level, id});
        pre[id] = 1;
        while (!queue.isEmpty()) {
            int[] array = queue.poll();
            int curr_level = array[0];
            int curr_i = array[1];

//            pre[curr_i] = 1; 从队列中取出, 然后修改, 会导致节点重复放入, 正确应该是放入时, 直接修改pre[j] = 1
            if (curr_level == level) {
                List<String> targetFriendList = watchedVideos.get(curr_i);
                for (String key : targetFriendList) {
                    map.put(key, map.getOrDefault(key, 0) + 1);
                }
            }
            // add next friends to the queue
            for (int j : friends[curr_i]) {
                if (pre[j] == 0 && curr_level < level) {
                    queue.offer(new int[] {curr_level + 1, j});
                    pre[j] = 1;
                }
            }
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(map.entrySet());
        Collections.sort(entryList, (o1, o2) -> {
            int compareResult = Integer.compare(o1.getValue(), o2.getValue());
            if (compareResult != 0) {
                return compareResult;
            }
            return o1.getKey().compareTo(o2.getKey());
        });

        List<String> ans = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            ans.add(entry.getKey());
        }
        return ans;
    }
}
