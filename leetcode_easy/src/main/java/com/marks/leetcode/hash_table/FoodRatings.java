package com.marks.leetcode.hash_table;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: FoodRatings </p>
 * <p>描述:
 * LeetCode 2353.设计食物评分系统
 * 设计一个支持下述操作的食物评分系统：
 * 修改 系统中列出的某种食物的评分。
 * 返回系统中某一类烹饪方式下评分最高的食物。
 * 实现 FoodRatings 类：
 * FoodRatings(String[] foods, String[] cuisines, int[] ratings) 初始化系统。食物由 foods、cuisines 和 ratings 描述，长度均为 n 。
 * foods[i] 是第 i 种食物的名字。
 * cuisines[i] 是第 i 种食物的烹饪方式。
 * ratings[i] 是第 i 种食物的最初评分。
 * void changeRating(String food, int newRating) 修改名字为 food 的食物的评分。
 * String highestRated(String cuisine) 返回指定烹饪方式 cuisine 下评分最高的食物的名字。如果存在并列，返回 字典序较小 的名字。
 * 注意，字符串 x 的字典序比字符串 y 更小的前提是：x 在字典中出现的位置在 y 之前，也就是说，要么 x 是 y 的前缀，或者在满足 x[i] != y[i] 的第一个位置 i 处，x[i] 在字母表中出现的位置在 y[i] 之前。
 *
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/28 9:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class FoodRatings {
    Map<String, Food> foodMap;
    Map<String, PriorityQueue<Food>> topMap;
    Map<String, PriorityQueue<Food>> delMap;
    /**
     * @Description:
     * 1. 一种食物只存在一种烹饪方式吗, 如果存在多种烹饪方式, 那么当调用changeRating 方法时, 需要修改当前事务的所有烹饪方式下的评分.
     * 2. 明白了, 一种食物仅存在一种烹饪方式. 建一个类存储食物信息(烹饪方式 和 评分)
     * 3. 使用 Map<String, Food> 存储食物信息, 然后需要解决分类问题和获取最高评分食物
     * 4. 分类使用 Map<String, PriorityQueue<Food>> topMap 来进行存储. 另外由于食物的评分会被修改, 能否添加一个延迟删除设置.
     * 5. 如何实现懒删除? 待删除的食物同样使用 Map<String, PriorityQueue<Food>> delMap 来进行存储.
     * 6. 只需要判断 delMap.peek() 与 topMap.peek() 的关系. 如果名称相同并且评分相同, 则都执行 pop() 操作, 进行弹出删除.
     * 直到不同 或者 delMap 的优先队列为空
     * AC: 177ms/112.53MB
     * @param: foods
     * @param: cuisines
     * @param: ratings
     * @return
     * @author marks
     * @CreateDate: 2026/05/28 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        foodMap = new HashMap<>();
        topMap = new HashMap<>();
        delMap = new HashMap<>();
        for (int i = 0; i < foods.length; i++) {
            Food food = new Food(foods[i], cuisines[i], ratings[i]);
            foodMap.put(food.name, food);
            if (!topMap.containsKey(cuisines[i])) {
                topMap.put(cuisines[i], new PriorityQueue<>());
                delMap.put(cuisines[i], new PriorityQueue<>());
            }
            topMap.get(cuisines[i]).offer(food);
        }
    }

    public void changeRating(String food, int newRating) {
        Food old = foodMap.get(food); // 将当前 old 放入 delMap 中
        delMap.get(old.cuisine).offer(old);
        foodMap.put(food, new Food(food, old.cuisine, newRating));
        topMap.get(old.cuisine).offer(foodMap.get(food));
    }

    public String highestRated(String cuisine) {
        // 分别获取 topMap 和 delMap 的优先队列
        PriorityQueue<Food> top = topMap.get(cuisine);
        PriorityQueue<Food> del = delMap.get(cuisine);
        while (!top.isEmpty() && !del.isEmpty() && top.peek().name.equals(del.peek().name) && top.peek().rating == del.peek().rating) {
            top.poll();
            del.poll();
        }

        return top.peek().name;
    }

    private class Food implements Comparable<Food> {
        String name;
        String cuisine;
        int rating;

        public Food(String name, String cuisine, int rating) {
            this.name = name;
            this.cuisine = cuisine;
            this.rating = rating;
        }

        @Override
        public int compareTo( Food o) {
            // 按照rating 降序排序, 如果rating 相等, 则按照name 升序排序
            return o.rating - this.rating != 0 ? o.rating - this.rating : this.name.compareTo(o.name);
        }
    }

}
