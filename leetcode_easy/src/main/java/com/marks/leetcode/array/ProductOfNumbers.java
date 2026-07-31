package com.marks.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1352. 最后 K 个数的乘积 </p>
 * <p>描述:
 * 设计一个算法，该算法接受一个整数流并检索该流中最后 k 个整数的乘积。
 * 实现 ProductOfNumbers 类：
 * ProductOfNumbers() 用一个空的流初始化对象。
 * void add(int num) 将数字 num 添加到当前数字列表的最后面。
 * int getProduct(int k) 返回当前数字列表中，最后 k 个数字的乘积。你可以假设当前列表中始终 至少 包含 k 个数字。
 * 题目数据保证：任何时候，任一连续数字序列的乘积都在 32 位整数范围内，不会溢出。
 * </p>
 * 进阶：您能否 同时 将 GetProduct 和 Add 的实现改为 O(1) 时间复杂度，而不是 O(k) 时间复杂度？
 * tips:
 * 0 <= num <= 100
 * 1 <= k <= 4 * 10^4
 * add 和 getProduct 最多被调用 4 * 10^4 次。
 * 在任何时间点流的乘积都在 32 位整数范围内。
 * @author marks
 * @version v1.0
 * @date 2026/7/31 9:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ProductOfNumbers {
    private final List<Integer> productList;
    private final List<Integer> zeroCount;
    private int n;

    /**
     * @Description:
     * 1. 使用 int kProduct 记录最后 k 个数的乘积值, 由于只能包含 k 个数, 所以是一个固定长度滑动窗口, 窗口大小为 k
     * 2. int[] nums 记录窗口中末尾的 k 个数, 并且使用循环数组的方式, 添加两个指针 left 指向头部, right 指向待插入位置,
     * 并且 nums 初始值赋值为 -1, 表示未访问过
     * 3. 当执行 add 方法时, 如果 nums[right] != -1, 表示此时已经有 k 个数, 需要删除 头部元素 int prev = nums[left], left++;
     * 然后赋值 nums[right] = num, right++; 对于 kProduct, 需要除以 kProduct = (kProduct / prev) * num;更新结果
     * 4. 但是由于 prev 可能值是 0, 所以需要记录 int countZero 来记录窗口中 0 的个数, 如果 countZero > 0, 表示窗口中存在 0,
     * getProduct() 返回 0.
     * 5. 理解错误, k 不是一个固定值, 是一个查询的参数, 所以需要前缀乘积
     * 6. 当 num == 0 时, 不应该添加前一个数, 没有意义,而且会导致溢出(较大的可能性会发生溢出), 而是添加 1来重置乘积结果.
     * AC: 19ms/85.57MB
     * @return
     * @author marks
     * @CreateDate: 2026/07/31 9:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public ProductOfNumbers() {
        productList = new ArrayList<>();
        zeroCount = new ArrayList<>();
        zeroCount.add(0);
        productList.add(1);
        n = 1;
    }

    public void add(int num) {
        if (num == 0) {
            productList.add(1); // 添加前一个值, 这个是错误的, 正确应该添加 1
            zeroCount.add(zeroCount.get(n - 1) + 1);
        } else {
            // num != 0
            productList.add(productList.get(n - 1) * num);
            zeroCount.add(zeroCount.get(n - 1));
        }
        n++;
    }

    public int getProduct(int k) {
        int right = n - 1;
        int left = right - k;
        int cnt = zeroCount.get(right) - zeroCount.get(left);
        if (cnt > 0) {
            // 最后 k 个数中包含0, 则乘积必定为0
            return 0;
        }
        // 当k个数不包含 0
        return productList.get(right) / productList.get(left);
    }

}
