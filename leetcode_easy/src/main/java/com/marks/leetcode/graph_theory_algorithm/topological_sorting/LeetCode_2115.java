package com.marks.leetcode.graph_theory_algorithm.topological_sorting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/27 14:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2115 {
    /**
     * @Description: [
     * <p> 你有 n 道不同菜的信息。给你一个字符串数组 recipes 和一个二维字符串数组 ingredients 。
     * 第 i 道菜的名字为 recipes[i] ，如果你有它 所有 的原材料 ingredients[i] ，那么你可以 做出 这道菜。一道菜的原材料可能是 另一道 菜，
     * 也就是说 ingredients[i] 可能包含 recipes 中另一个字符串。
     *
     * <p> 同时给你一个字符串数组 supplies ，它包含你初始时拥有的所有原材料，每一种原材料你都有无限多。
     * <p> 请你返回你可以做出的所有菜。你可以以 任意顺序 返回它们。
     * <p> 注意两道菜在它们的原材料中可能互相包含。
     * <p> tips:
     * n == recipes.length == ingredients.length
     * 1 <= n <= 100
     * 1 <= ingredients[i].length, supplies.length <= 100
     * 1 <= recipes[i].length, ingredients[i][j].length, supplies[k].length <= 10
     * recipes[i], ingredients[i][j] 和 supplies[k] 只包含小写英文字母。
     * 所有 recipes 和 supplies 中的值互不相同。
     * ingredients[i] 中的字符串互不相同。
     * ]
     * @param recipes
     * @param ingredients
     * @param supplies
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2024/12/27 14:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        List<String> result;
        result = method_01(recipes, ingredients, supplies);
        return result;
    }

    /**
     * @Description: [
     * <p> 1. 可能存在环 1 -> 2, 2 -> 1, 会有自环吗？ 不太确定, 但是猜测应该是没有的
     * <p> 2. 是否这种是可能, 即遍历整个supplies[], 然后删除ingredients[i][j] 所有相等的字符串
     * <p> 3. 现在ingredients[] 中剩余的就是拓扑排序,
     * AC:135ms/45.64MB
     * ]
     * @param recipes
     * @param ingredients
     * @param supplies
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2024/12/27 14:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_01(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        for (String supply : supplies) {
            for (List<String> ingredient : ingredients) {
                ingredient.remove(supply);
            }
        }
        int n = recipes.length;
        int[] inDegree = new int[n];
        List<Integer>[] lists = new ArrayList[n];
        List<String> unAble = new ArrayList<>();
        for (int i = 0; i < lists.length; i++) {
            lists[i] = new ArrayList<>();
        }
        for (int i = 0; i < recipes.length; i++) {
            for (String value : ingredients.get(i)) {
                int start = getStartIndex(value, recipes);
                if (start >= 0) {
                    lists[start].add(i);
                    inDegree[i]++;
                }else {
                    unAble.add(recipes[i]);
                }
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0 && !unAble.contains(recipes[i])) {
                queue.offer(i);
                ans.add(recipes[i]);
            }
        }

        while (!queue.isEmpty()) {
            int curr_i = queue.poll();
            for (int j : lists[curr_i]) {
                inDegree[j]--;
                if (inDegree[j] == 0) {
                    queue.offer(j);
                    ans.add(recipes[j]);
                }
            }
        }
        for (String value : unAble) {
            ans.remove(value);
        }
        return ans;
    }

    private int getStartIndex(String value, String[] recipes) {
        for (int i = 0; i < recipes.length; i++) {
            if (value.equals(recipes[i])) {
                return i;
            }
        }
        return -1;
    }
}
