package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MovieRentingSystemTest </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/30 11:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class MovieRentingSystemTest {

    @Test
    void search() {
        int n = 3;
        // [[0, 1, 5], [0, 2, 6], [0, 3, 7], [1, 1, 4], [1, 2, 7], [2, 1, 5]]
        int[][] entries = {{0, 1, 5}, {0, 2, 6}, {0, 3, 7}, {1, 1, 4}, {1, 2, 7}, {2, 1, 5}};
        MovieRentingSystem rentingSystem = new MovieRentingSystem(n, entries);
        rentingSystem.search(1);
        rentingSystem.rent(0, 1);
        rentingSystem.rent(1, 2);
        rentingSystem.report();
        rentingSystem.drop(1,2);
        rentingSystem.search(2);
    }


    @Test
    void search2() {
        int n = 10;
        // [[4,374,55],[1,6371,21],[8,3660,24],[1,56,32],[5,374,71],[3,4408,36],[6,9322,73],[6,9574,92],[8,7834,62],[2,6084,27],[7,3262,89],[2,8959,53],[0,3323,41],[6,6565,45],[0,4239,20]]
        int[][] entries = {{4,374,55},{1,6371,21},{8,3660,24},{1,56,32},{5,374,71},{3,4408,36},{6,9322,73},{6,9574,92},{8,7834,62},{2,6084,27},{7,3262,89},{2,8959,53},{0,3323,41},{6,6565,45},{0,4239,20}};
        MovieRentingSystem rentingSystem = new MovieRentingSystem(n, entries);
        rentingSystem.rent(0, 4239);

        rentingSystem.drop(0, 4239);

        rentingSystem.rent(0, 4239);
        rentingSystem.rent(3, 4408);
        rentingSystem.rent(2, 6084);

        rentingSystem.drop(0, 4239);

        rentingSystem.search(9346);

        rentingSystem.report();

        rentingSystem.rent(6,9322);

        rentingSystem.search(8698);

    }
}