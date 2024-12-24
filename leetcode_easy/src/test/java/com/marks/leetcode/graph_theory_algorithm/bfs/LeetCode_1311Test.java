package com.marks.leetcode.graph_theory_algorithm.bfs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/24 14:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1311Test {

    @Test
    void watchedVideosByFriends() {
        // watchedVideos = [["A","B"],["C"],["B","C"],["D"]], friends = [[1,2],[0,3],[0,3],[1,2]], id = 0, level = 1
        List<List<String>> watchVideos = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            watchVideos.add(new ArrayList<>());
        }
        watchVideos.get(0).add("A");
        watchVideos.get(0).add("B");
        watchVideos.get(1).add("C");
        watchVideos.get(2).add("B");
        watchVideos.get(2).add("C");
        watchVideos.get(3).add("D");
        int[][] friends = {{1, 2}, {0, 3}, {0, 3}, {1, 2}};
        int id = 0, level = 1;
        List<String> list = new LeetCode_1311().watchedVideosByFriends(watchVideos, friends, id, level);

    }
}