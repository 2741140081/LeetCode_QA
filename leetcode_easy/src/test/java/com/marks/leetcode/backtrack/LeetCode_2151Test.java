package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LeetCode_2151 Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <version number>
 */
public class LeetCode_2151Test {

    private LeetCode_2151 solution;

    @BeforeEach
    public void setUp() {
        solution = new LeetCode_2151();
    }

    /**
     * Method: maximumGood(int[][] statements)
     */
    @Test
    public void testMaximumGoodWithExample1() {
        // Test case: statements = [[2,1,2],[1,2,2],[2,0,2]]
        // Expected result: 2
        int[][] statements = {
            {2, 1, 2},
            {1, 2, 2},
            {2, 0, 2}
        };
        int result = solution.maximumGood(statements);
        assertEquals(2, result, "Example 1: Expected maximum good people is 2");
    }

    @Test
    public void testMaximumGoodWithExample2() {
        // Test case: statements = [[2,0],[0,2]]
        // Expected result: 1
        int[][] statements = {
            {2, 0},
            {0, 2}
        };
        int result = solution.maximumGood(statements);
        assertEquals(1, result, "Example 2: Expected maximum good people is 1");
    }

    @Test
    public void testMaximumGoodWithAllGoodPeople() {
        // Test case: All people can be good
        // statements = [[2,1],[1,2]]
        // Expected result: 2
        int[][] statements = {
            {2, 1},
            {1, 2}
        };
        int result = solution.maximumGood(statements);
        assertEquals(2, result, "All people can be good: Expected maximum good people is 2");
    }

    @Test
    public void testMaximumGoodWithContradiction() {
        // Test case: Contradiction in statements
        // statements = [[2,0],[1,2]]
        // Expected result: 1
        int[][] statements = {
            {2, 0},
            {1, 2}
        };
        int result = solution.maximumGood(statements);
        assertEquals(1, result, "Contradiction case: Expected maximum good people is 1");
    }

    @Test
    public void testMaximumGoodWithSinglePerson() {
        // Test case: Only one person
        // statements = [[2]]
        // Expected result: 1
        int[][] statements = {{2}};
        int result = solution.maximumGood(statements);
        assertEquals(1, result, "Single person case: Expected maximum good people is 1");
    }

    @Test
    public void testMaximumGoodWithNoStatements() {
        // Test case: Everyone has no statements about others
        // statements = [[2,2],[2,2]]
        // Expected result: 2
        int[][] statements = {
            {2, 2},
            {2, 2}
        };
        int result = solution.maximumGood(statements);
        assertEquals(2, result, "No statements case: Expected maximum good people is 2");
    }
    // [[2,2,2,2],[1,2,1,0],[0,2,2,2],[0,0,0,2]]

    @Test
    public void testMaximumGoodWithExample3() {
        // Test case: statements = [[2,1,2],[1,2,2],[2,0,2]]
        // Expected result: 2
        int[][] statements = {
                {2,2,2,2},
                {1,2,1,0},
                {0,2,2,2},
                {0,0,0,2}
        };
        int result = solution.maximumGood(statements);
        assertEquals(1, result, "Example 3: Expected maximum good people is 1");
    }


    // [[2,1,1,2],[1,2,1,0],[2,2,2,1],[1,1,2,2]]

    @Test
    public void testMaximumGoodWithExample4() {
        // Test case: statements = [[2,1,2],[1,2,2],[2,0,2]]
        // Expected result: 2
        int[][] statements = {
                {2,1,1,2},
                {1,2,1,0},
                {2,2,2,1},
                {1,1,2,2}
        };
        int result = solution.maximumGood(statements);
        assertEquals(0, result, "Example 4: Expected maximum good people is 0");
    }
}