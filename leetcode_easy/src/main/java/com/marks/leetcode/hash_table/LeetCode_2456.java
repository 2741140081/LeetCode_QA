package com.marks.leetcode.hash_table;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2456 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/29 9:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2456 {

    /**
     * @Description:
     * 给你两个字符串数组 creators 和 ids ，和一个整数数组 views ，所有数组的长度都是 n 。平台上第 i 个视频者是 creator[i] ，视频分配的 id 是 ids[i] ，且播放量为 views[i] 。
     * 视频创作者的 流行度 是该创作者的 所有 视频的播放量的 总和 。请找出流行度 最高 创作者以及该创作者播放量 最大 的视频的 id 。
     * 如果存在多个创作者流行度都最高，则需要找出所有符合条件的创作者。
     * 如果某个创作者存在多个播放量最高的视频，则只需要找出字典序最小的 id 。
     * 返回一个二维字符串数组 answer ，其中 answer[i] = [creatori, idi] 表示 creatori 的流行度 最高 且其最流行的视频 id 是 idi ，可以按任何顺序返回该结果。
     * @param: creators
     * @param: ids
     * @param: views
     * @return java.util.List<java.util.List<java.lang.String>>
     * @author marks
     * @CreateDate: 2026/05/29 9:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<String>> mostPopularCreator(String[] creators, String[] ids, int[] views) {
        List<List<String>> result;
        result = method_01(creators, ids, views);
        return result;
    }

    /**
     * @Description:
     * 1. 创建一个Map，key为creator，value为creator的播放量 和该creator最流行的视频的id
     * AC: 25ms/93.55MB
     * @param: creators
     * @param: ids
     * @param: views
     * @return java.util.List<java.util.List<java.lang.String>>
     * @author marks
     * @CreateDate: 2026/05/29 9:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<String>> method_01(String[] creators, String[] ids, int[] views) {
        Map<String, Pair> map = new HashMap<>();
        int n = creators.length;
        long maxView = -1;
        for (int i = 0; i < n; i++) {
            String creator = creators[i];
            String id = ids[i];
            int view = views[i];
            Pair pair;
            if (map.containsKey(creator)) {
                pair = map.get(creator);
                pair.viewSum += view;
                if (pair.maxView < view) {
                    pair.maxView = view;
                    pair.maxId = id;
                } else if (pair.maxView == view) {
                    pair.maxId = pair.maxId.compareTo(id) <= 0 ? pair.maxId : id;
                }
            }  else {
                pair = new Pair(view, id);
                map.put(creator, pair);
            }
            maxView = Math.max(maxView, pair.viewSum);
        }
        List<List<String>> ans = new ArrayList<>();
        for (Map.Entry<String, Pair> entry : map.entrySet()) {
            String creator = entry.getKey();
            Pair pair = entry.getValue();
            if (pair.viewSum == maxView) {
                ans.add(Arrays.asList(creator, pair.maxId));
            }
        }

        return ans;
    }

    private class Pair {
        private long viewSum;
        private int maxView;
        private String maxId;

        public Pair(int currView, String currId) {
            this.viewSum = currView;
            this.maxView = currView;
            this.maxId = currId;
        }
    }

}
