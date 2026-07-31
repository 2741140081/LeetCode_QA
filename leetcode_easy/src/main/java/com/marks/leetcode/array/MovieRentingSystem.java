package com.marks.leetcode.array;

import java.util.*;

/**
 * <p>项目名称: LeetCode_1912. 设计电影租借系统 </p>
 * <p>文件名称: MovieRentingSystem </p>
 * <p>描述:
 * 你有一个电影租借公司和 n 个电影商店。
 * 你想要实现一个电影租借系统，它支持查询、预订和返还电影的操作。
 * 同时系统还能生成一份当前被借出电影的报告。
 * 所有电影用二维整数数组 entries 表示，其中 entries[i] = [shopi, moviei, pricei] 表示商店 shopi 有一份电影 moviei 的拷贝，租借价格为 pricei 。
 * 每个商店有 至多一份 编号为 moviei 的电影拷贝。
 * 系统需要支持以下操作：
 *
 * Search：找到拥有指定电影且 未借出 的商店中 最便宜的 5 个 。
 * 商店需要按照 价格 升序排序，如果价格相同，则 shopi 较小 的商店排在前面。
 * 如果查询结果少于 5 个商店，则将它们全部返回。如果查询结果没有任何商店，则返回空列表。
 *
 * Rent：从指定商店借出指定电影，题目保证指定电影在指定商店 未借出 。
 *
 * Drop：在指定商店返还 之前已借出 的指定电影。
 *
 * Report：返回 最便宜的 5 部已借出电影 （可能有重复的电影 ID），
 * 将结果用二维列表 res 返回，其中 res[j] = [shopj, moviej] 表示第 j 便宜的已借出电影是从商店 shopj 借出的电影 moviej 。
 * res 中的电影需要按 价格 升序排序；如果价格相同，则 shopj 较小 的排在前面；如果仍然相同，则 moviej 较小 的排在前面。
 * 如果当前借出的电影小于 5 部，则将它们全部返回。如果当前没有借出电影，则返回一个空的列表。
 * 请你实现 MovieRentingSystem 类：
 *
 * MovieRentingSystem(int n, int[][] entries) 将 MovieRentingSystem 对象用 n 个商店和 entries 表示的电影列表初始化。
 * List<Integer> search(int movie) 如上所述，返回 未借出 指定 movie 的商店列表。
 * void rent(int shop, int movie) 从指定商店 shop 借出指定电影 movie 。
 * void drop(int shop, int movie) 在指定商店 shop 返还之前借出的电影 movie 。
 * List<List<Integer>> report() 如上所述，返回最便宜的 已借出 电影列表。
 * 注意：测试数据保证 rent 操作中指定商店拥有 未借出 的指定电影，且 drop 操作指定的商店 之前已借出 指定电影。
 * </p>
 *
 * tips:
 * 1 <= n <= 3 * 10^5
 * 1 <= entries.length <= 10^5
 * 0 <= shopi < n
 * 1 <= moviei, pricei <= 10^4
 * 每个商店 至多 有一份电影 moviei 的拷贝。
 * search，rent，drop 和 report 的调用 总共 不超过 10^5 次。
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/30 10:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class MovieRentingSystem {
    private final Map<Integer, TreeSet<Pair>> freeMovie;
    private final Map<String, Pair> allMovie;
    private final TreeSet<Pair> rented;
    private final int maxCapacity;

    /**
     * @Description:
     * 1. search 功能, 参数是 movie_id, 排序规则是价格升序并且shop_id 也升序.
     * 2. rent 功能, 参数是 shop_id 和 movie_id, 相当于删除操作
     * 3. drop 功能, 参数是 shop_id 和 movie_id, 相当于添加操作
     * 4. rent 和 drop 有隐藏参数是 price
     * 5. 能否将 shop_id 与 movie_id 合并成一个 key, 因为每一个商店中不同电影的 movie_id是不同的, 所以可以进行合并操作
     * String key = shop_id + "_" + movie_id
     * 6. 需要使用有序并且能进行插入和删除操作, 使用TreeMap<String, int> freeMovie 空闲的电影, TreeMap<String, int> allMovie 所有电影数据,
     * 方便在添加电影时查询价格数据. 由于 rent 时会有重复数据产生, 所以使用有序队列来存储 PriorityQueue<Pair> rentMovie
     * 7. 存在问题, freeMovie 不能直接找指定电影且未出借
     * 8. 应该需要修改结构, 将 freeMovie 改成 Map<Integer, TreeSet<Pair>>, 并且使用标记删除法, 即仅对 key 进行删除标记
     * 9. 找到问题, 对于'已借出'的理解有问题, 即借出再返回, 那么当前电影不是一个已借出电影
     * AC: 502ms/269.95MB
     * @param: n
     * @param: entries
     * @return
     * @author marks
     * @CreateDate: 2026/07/30 10:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public MovieRentingSystem(int n, int[][] entries) {
        freeMovie = new HashMap<>();
        allMovie = new HashMap<>();
        rented = new TreeSet<>();
        maxCapacity = 5;
        for (int[] entry : entries) {
            // entries[i] = [shopi, moviei, pricei]
            Pair pair = new Pair(entry[0], entry[1], entry[2]);
            freeMovie.computeIfAbsent(entry[1], k -> new TreeSet<>()).add(pair);
            String key = entry[0] + "_" + entry[1];
            allMovie.put(key, pair);
        }
    }

    public List<Integer> search(int movie) {
        List<Integer> ans = new ArrayList<>();
        if (!freeMovie.containsKey(movie)) {
            // 返回空列表
            return ans;
        }
        TreeSet<Pair> treeSet = freeMovie.get(movie);
        // 使用 iter
        Iterator<Pair> iterator = treeSet.iterator();
        int count = 0;
        while (iterator.hasNext() && count < maxCapacity) {
            Pair pair = iterator.next();
            ans.add(pair.shopId);
            count++;
        }
        return ans;
    }

    public void rent(int shop, int movie) {
        // 明确租借时, 电影必定存在的且空闲的
        TreeSet<Pair> treeSet = freeMovie.get(movie);
        String key = shop + "_" + movie;
        Pair rentPair = allMovie.get(key);
        treeSet.remove(rentPair); // 删除
        rented.add(rentPair); // 添加到已租借的队列中
    }

    public void drop(int shop, int movie) {
        // 返还操作
        String key = shop + "_" + movie;
        Pair returnPair = allMovie.get(key);
        freeMovie.get(movie).add(returnPair);
        rented.remove(returnPair);
    }

    public List<List<Integer>> report() {
        // 返回已租借过的电影中最便宜的5部电影
        List<List<Integer>> ans = new ArrayList<>();
        // res[j] = [shopj, moviej]
        Iterator<Pair> rentIter = rented.iterator();
        int count = 0;
        while (rentIter.hasNext() && count < maxCapacity) {
            Pair pair = rentIter.next();
            List<Integer> curr = new ArrayList<>();
            curr.add(pair.shopId);
            curr.add(pair.movieId);
            ans.add(curr);
            count++;
        }

        return ans;
    }

    static class Pair implements Comparable<Pair> {
        int shopId;
        int movieId;
        int price;

        public Pair(int shopId, int movieId, int price) {
            this.shopId = shopId;
            this.movieId = movieId;
            this.price = price;
        }

        @Override
        public int compareTo(Pair o) {
            // 都升序排序
            if (this.price == o.price) {
                if (this.shopId == o.shopId) {
                    return this.movieId - o.movieId;
                }
                return this.shopId - o.shopId;
            }
            return this.price - o.price;
        }
    }

}
