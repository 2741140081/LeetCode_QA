package com.marks.leetcode.hash_table;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1169 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/26 11:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1169 {

    /**
     * @Description:
     * 如果出现下述两种情况，交易 可能无效：
     * 交易金额超过 $1000
     * 或者，它和 另一个城市 中 同名 的另一笔交易相隔不超过 60 分钟（包含 60 分钟整）
     * 给定字符串数组交易清单 transaction 。
     * 每个交易字符串 transactions[i] 由一些用逗号分隔的值组成，这些值分别表示交易的名称，时间（以分钟计），金额以及城市。
     * 返回 transactions，返回可能无效的交易列表。你可以按 任何顺序 返回答案。
     *
     * tips:
     * transactions.length <= 1000
     * 每笔交易 transactions[i] 按 "{name},{time},{amount},{city}" 的格式进行记录
     * 每个交易名称 {name} 和城市 {city} 都由小写英文字母组成，长度在 1 到 10 之间
     * 每个交易时间 {time} 由一些数字组成，表示一个 0 到 1000 之间的整数
     * 每笔交易金额 {amount} 由一些数字组成，表示一个 0 到 2000 之间的整数
     * @param: transactions
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/05/26 11:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> invalidTransactions(String[] transactions) {
        List<String> result;
        result = method_01(transactions);
        return result;
    }

    /**
     * @Description:
     * 1. 交易金额超过 $1000
     * 2. 当方式1和方式2都存在时, 即有两笔交易, 其中一笔金额超过 1000, 另外一笔是同一个人在不同城市进行的交易, 并且间隔小于 60 分钟, 这种情况下,
     * 这两笔交易都是无效的, 还是后一笔交易是有效的? 由于没有特殊情况说明, 默认这两笔交易都是无效交易.
     * 3. 先根据交易名称进行分类, 然后在相同交易名称下, 根据交易时间进行排序, 在一个list 中, 假设当前位于 i 处, 则于 i - 1 和 i + 1 进行对比,
     * 如果城市相同, 跳过; 如果城市不同, 则判断两者的交易间隔是否小于 60 分钟
     * 4. 找到错误原因了, 只判断 i - 1 和 i + 1 存在 局限性, 需要找到下一个不同的 交易名称来判断, 而不是单纯的 i + 1 进行对比
     * AC: 26ms/52.34MB
     * @param: transactions
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/05/26 11:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_01(String[] transactions) {
        Map<String, List<Transaction>> map = new HashMap<>();
        for (String transaction : transactions) {
            String[] split = transaction.split(",");
            Transaction temp = new Transaction(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]), split[3]);
            // 使用 lambda 表达式进行添加对应temp的交易
            map.computeIfAbsent(temp.name, k -> new ArrayList<>()).add(temp);
        }
        List<String> ans = new ArrayList<>();
        // 遍历map
        for (Map.Entry<String, List<Transaction>> entry : map.entrySet()) {
            List<Transaction> list = entry.getValue();
            // 对 list 进行排序
            list.sort(Transaction::compareTo);
            int n = list.size();
            for (int i = 0; i < n; i++) {
                Transaction cur = list.get(i);
                if (cur.amount > 1000) {
                    // 交易金额超过 $1000, 添加到 ans 中
                    ans.add(cur.toString());
                    continue;
                }
                // 倒序遍历 i - 1 到 0 的元素, 找到最近的 prev.name == cur.name && prev.city != cur.city
                int prev = i - 1;
                while (prev >=0) {
                    if (list.get(prev).name.equals(cur.name) && !list.get(prev).city.equals(cur.city)) {
                        break;
                    }
                    prev--;
                }
                // 当前 i 于 i - 1 和 i + 1 进行对比
                if (prev >= 0 && checkResult(cur, list.get(prev))) {
                    ans.add(cur.toString());
                    continue;
                }
                int next = i + 1;
                while (next < n) {
                    if (list.get(next).name.equals(cur.name) && !list.get(next).city.equals(cur.city)) {
                        break;
                    }
                    next++;
                }
                if (next < n && checkResult(cur, list.get(next))) {
                    ans.add(cur.toString());
                }
            }
        }

        return ans;
    }

    private boolean checkResult(Transaction cur, Transaction another) {
        // 如果名称不同 或者 城市相同, 返回 false
        if (!cur.name.equals(another.name) || cur.city.equals(another.city)) {
            return false;
        }
        // 如果交易间隔大于 60 分钟, 返回 false
        return Math.abs(cur.time - another.time) <= 60;
    }

    private class Transaction implements Comparable<Transaction> {
        String name;
        int time;
        int amount;
        String city;

        // 全参构造器
        public Transaction(String name, int time, int amount, String city) {
            this.name = name;
            this.time = time;
            this.amount = amount;
            this.city = city;
        }

        // toString 方法, 返回交易信息
        @Override
        public String toString() {
            return name + "," + time + "," + amount + "," + city;
        }

        @Override
        public int compareTo(Transaction o) {
            return time - o.time;
        }
    }

}
