package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3387Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/8 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3387Test {

    @Test
    void maxAmount() {
        // initialCurrency = "EUR", pairs1 = [["EUR","USD"],["USD","JPY"]], rates1 = [2.0,3.0],
        // pairs2 = [["JPY","USD"],["USD","CHF"],["CHF","EUR"]], rates2 = [4.0,5.0,6.0]
        String initialCurrency = "EUR";
        List<List<String>> pairs1 = new ArrayList<>();
        pairs1.add(new ArrayList<>());
        pairs1.get(0).add("EUR");
        pairs1.get(0).add("USD");
        pairs1.add(new ArrayList<>());
        pairs1.get(1).add("USD");
        pairs1.get(1).add("JPY");
        double[] rates1 = new double[]{2.0, 3.0};
        List<List<String>> pairs2 = new ArrayList<>();
        pairs2.add(new ArrayList<>());
        pairs2.get(0).add("JPY");
        pairs2.get(0).add("USD");
        pairs2.add(new ArrayList<>());
        pairs2.get(1).add("USD");
        pairs2.get(1).add("CHF");
        pairs2.add(new ArrayList<>());
        pairs2.get(2).add("CHF");
        pairs2.get(2).add("EUR");
        double[] rates2 = new double[]{4.0, 5.0, 6.0};
        LeetCode_3387 leetCode_3387 = new LeetCode_3387();
        double result = leetCode_3387.maxAmount(initialCurrency, pairs1, rates1, pairs2, rates2);
        System.out.println(result);
    }
}