package com.marks.leetcode.graph_theory_algorithm.topological_sorting;

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
 * @date 2024/12/27 15:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2115Test {

    @Test
    void findAllRecipes() {
        String[] recipes = {"fe","nvvj","kps","ik","gd","gjpz","cff","ljb","ybxsh","vtu","htsn","jwwxz","znoem","h","mlg","ggd","bkinz","pzjna","pxum"};
        String[][] array = {{"zqg"},{"zqg"},{"t"},{"zqg"},{"bkinz","ggd","ljb","ybxsh","nvvj","pzjna","cff"},{"kps","nvvj","pxum","ik","cff","ybxsh","h"},{"yyym"},{"zqg"},{"htsn","vtu"},{"kfukc"},{"zqg"},{"zqg"},{"a"},{"zqg","gd","ybxsh","ggd","ljb"},{"ybxsh","pxum","h","bkinz"},{"zqg"},{"zqg"},{"mu"},{"zqg"}};
        List<List<String>> ingredients = new ArrayList<>();
        for (String[] strings : array) {
            List<String> tempList = new ArrayList<>();
            for (String value : strings) {
                tempList.add(value);
            }
            ingredients.add(tempList);
        }
        String[] supplies = {"zqg"};
        List<String> allRecipes = new LeetCode_2115().findAllRecipes(recipes, ingredients, supplies);
    }
}