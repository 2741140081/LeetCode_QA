package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: Cashier </p>
 * <p>描述:
 * 超市里正在举行打折活动，每隔 n 个顾客会得到 discount 的折扣。
 * 超市里有一些商品，第 i 种商品为 products[i] 且每件单品的价格为 prices[i] 。
 * 结账系统会统计顾客的数目，每隔 n 个顾客结账时，该顾客的账单都会打折，折扣为 discount （也就是如果原本账单为 x ，那么实际金额会变成 x - (discount * x) / 100 ），然后系统会重新开始计数。
 * 顾客会购买一些商品， product[i] 是顾客购买的第 i 种商品， amount[i] 是对应的购买该种商品的数目。
 * 请你实现 Cashier 类：
 * Cashier(int n, int discount, int[] products, int[] prices) 初始化实例对象，参数分别为打折频率 n ，折扣大小 discount ，超市里的商品列表 products 和它们的价格 prices 。
 * double getBill(int[] product, int[] amount) 返回账单的实际金额（如果有打折，请返回打折后的结果）。返回结果与标准答案误差在 10^-5 以内都视为正确结果。
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/22 9:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class Cashier {
    private Map<Integer, Double> map;
    private int count;
    private int n;
    private int discount;

    /**
     * @Description:
     * 1. 每隔 n 个顾客会得到折扣, 需要一个计数器, 判断当前顾客是否满足打折条件, int count 作为计数器, 初始值为 0, 每次执行 getBill 方法时, 先执行 count++, 然后判断 count = (count % n); if (count == 0) { 执行打折 }
     * 2. 使用 map 存储商品 id 和 价格
     * AC: 123ms/84.68MB
     * @param: n
     * @param: discount
     * @param: products
     * @param: prices
     * @return
     * @author marks
     * @CreateDate: 2026/05/22 9:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Cashier(int n, int discount, int[] products, int[] prices) {
        map = new HashMap<>();
        this.n = n;
        count = 0;
        this.discount = discount;
        // 将 商品 id 和价格存储到 map 中
        for (int i = 0; i < products.length; i++) {
            map.put(products[i], (double) prices[i]);
        }
    }

    public double getBill(int[] product, int[] amount) {
        // 先执行 count ++
        count++;
        double sum = 0;
        for (int i = 0; i < product.length; i++) {
            sum += map.get(product[i]) * amount[i];
        }
        // 判断是否满足打折条件
        if (count % n == 0) {
            sum = sum - sum * discount / 100;
            count = 0;
        }
        return sum;
    }

}
