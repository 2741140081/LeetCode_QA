package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_332Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/12 11:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_332Test {

    @Test
    void findItinerary() {
        // [["JFK","SFO"],["JFK","ATL"],["SFO","JFK"],["ATL","AAA"],["AAA","ATL"],["ATL","BBB"],["BBB","ATL"],["ATL","CCC"],
        // ["CCC","ATL"],["ATL","DDD"],["DDD","ATL"],["ATL","EEE"],["EEE","ATL"],["ATL","FFF"],["FFF","ATL"],["ATL","GGG"],
        // ["GGG","ATL"],["ATL","HHH"],["HHH","ATL"],["ATL","III"],["III","ATL"],["ATL","JJJ"],["JJJ","ATL"],["ATL","KKK"],
        // ["KKK","ATL"],["ATL","LLL"],["LLL","ATL"],["ATL","MMM"],["MMM","ATL"],["ATL","NNN"],["NNN","ATL"]]
        LeetCode_332 solution = new LeetCode_332();

        // 构建测试数据：复杂的多分支图结构
        List<List<String>> tickets = Arrays.asList(
                Arrays.asList("JFK", "SFO"),
                Arrays.asList("JFK", "ATL"),
                Arrays.asList("SFO", "JFK"),
                Arrays.asList("ATL", "AAA"),
                Arrays.asList("AAA", "ATL"),
                Arrays.asList("ATL", "BBB"),
                Arrays.asList("BBB", "ATL"),
                Arrays.asList("ATL", "CCC"),
                Arrays.asList("CCC", "ATL"),
                Arrays.asList("ATL", "DDD"),
                Arrays.asList("DDD", "ATL"),
                Arrays.asList("ATL", "EEE"),
                Arrays.asList("EEE", "ATL"),
                Arrays.asList("ATL", "FFF"),
                Arrays.asList("FFF", "ATL"),
                Arrays.asList("ATL", "GGG"),
                Arrays.asList("GGG", "ATL"),
                Arrays.asList("ATL", "HHH"),
                Arrays.asList("HHH", "ATL"),
                Arrays.asList("ATL", "III"),
                Arrays.asList("III", "ATL"),
                Arrays.asList("ATL", "JJJ"),
                Arrays.asList("JJJ", "ATL"),
                Arrays.asList("ATL", "KKK"),
                Arrays.asList("KKK", "ATL"),
                Arrays.asList("ATL", "LLL"),
                Arrays.asList("LLL", "ATL"),
                Arrays.asList("ATL", "MMM"),
                Arrays.asList("MMM", "ATL"),
                Arrays.asList("ATL", "NNN"),
                Arrays.asList("NNN", "ATL")
        );

        // 执行方法
        List<String> result = solution.findItinerary(tickets);
        System.out.println(result);
    }
}