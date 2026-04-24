package com.marks.leetcode.DP_II;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1125Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/24 15:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1125Test {

    @Test
    void smallestSufficientTeam() {
        // req_skills = ["algorithms","math","java","reactjs","csharp","aws"], people = [["algorithms","math","java"],["algorithms","math","reactjs"],["java","csharp","aws"],["reactjs","csharp"],["csharp","math"],["aws","java"]]
        String[] req_skills = {"algorithms","math","java","reactjs","csharp","aws"};
        List<List<String>> people = List.of(
                List.of("algorithms","math","java"),
                List.of("algorithms","math","reactjs"),
                List.of("java","csharp","aws"),
                List.of("reactjs","csharp"),
                List.of("csharp","math"),
                List.of("aws","java")
        );
        int[] res;
//        res = new LeetCode_1125().smallestSufficientTeam(req_skills, people);
//        for (int re : res) {
//            System.out.println(re);
//        }
        // req_skills = ["java","nodejs","reactjs"], people = [["java"],["nodejs"],["nodejs","reactjs"]]
        req_skills = new String[]{"java","nodejs","reactjs"};
        people = List.of(
                List.of("java"),
                List.of("nodejs"),
                List.of("nodejs","reactjs")
        );
//        res = new LeetCode_1125().smallestSufficientTeam(req_skills, people);
//        for (int re : res) {
//            System.out.println(re);
//        }

        // ["mmcmnwacnhhdd","vza","mrxyc"], [["mmcmnwacnhhdd"],[],[],["vza","mrxyc"]]
        req_skills = new String[]{"mmcmnwacnhhdd","vza","mrxyc"};
        people = List.of(
                List.of("mmcmnwacnhhdd"),
                List.of(),
                List.of(),
                List.of("vza","mrxyc")
        );
        res = new LeetCode_1125().smallestSufficientTeam(req_skills, people);
        for (int re : res) {
            System.out.println(re);
        }
    }
}