package com.marks.leetcode.data_structure.union_find;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/10 16:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_721 {
    /**
     * @Description:
     * 给定一个列表 accounts，每个元素 accounts[i] 是一个字符串列表，其中第一个元素 accounts[i][0] 是 名称 (name)，其余元素是 emails 表示该账户的邮箱地址。
     *
     * 现在，我们想合并这些账户。如果两个账户都有一些共同的邮箱地址，则两个账户必定属于同一个人。
     * 请注意，即使两个账户具有相同的名称，它们也可能属于不同的人，因为人们可能具有相同的名称。一个人最初可以拥有任意数量的账户，但其所有账户都具有相同的名称。
     *
     * 合并账户后，按以下格式返回账户：每个账户的第一个元素是名称，其余元素是 按字符 ASCII 顺序排列 的邮箱地址。账户本身可以以 任意顺序 返回。
     *
     * tips:
     * 1 <= accounts.length <= 1000
     * 2 <= accounts[i].length <= 10
     * 1 <= accounts[i][j].length <= 30
     * accounts[i][0] 由英文字母组成
     * accounts[i][j] (for j > 0) 是有效的邮箱地址
     * @param accounts
     * @return java.util.List<java.util.List<java.lang.String>>
     * @author marks
     * @CreateDate: 2025/3/10 16:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        List<List<String>> result;
        result = method_01(accounts);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：accounts = [["John", "johnsmith@mail.com", "john00@mail.com"], ["John", "johnnybravo@mail.com"],
     * ["John", "johnsmith@mail.com", "john_newyork@mail.com"], ["Mary", "mary@mail.com"]]
     * 输出：[["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],  ["John", "johnnybravo@mail.com"], ["Mary", "mary@mail.com"]]
     *
     * 1. 这是一个并查集, 但是改怎么存储数据?
     * 2. 怎么判断是否属于同一个账号, 也就是如何合并账号? 看看数据范围? Map<String, String> map
     * 3. 直接一点, 判断 i , j 是否是同一个账号,
     * @param accounts 
     * @return java.util.List<java.util.List<java.lang.String>>
     * @author marks
     * @CreateDate: 2025/3/10 16:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<String>> method_01(List<List<String>> accounts) {
        Map<String, Integer> emailToIndex = new HashMap<String, Integer>();
        Map<String, String> emailToName = new HashMap<String, String>();
        int emailsCount = 0;
        for (List<String> account : accounts) {
            String name = account.get(0);
            int size = account.size();
            for (int i = 1; i < size; i++) {
                String email = account.get(i);
                if (!emailToIndex.containsKey(email)) {
                    emailToIndex.put(email, emailsCount++);
                    emailToName.put(email, name);
                }
            }
        }
        UnionFind uf = new UnionFind(emailsCount);
        for (List<String> account : accounts) {
            String firstEmail = account.get(1);
            int firstIndex = emailToIndex.get(firstEmail);
            int size = account.size();
            for (int i = 2; i < size; i++) {
                String nextEmail = account.get(i);
                int nextIndex = emailToIndex.get(nextEmail);
                uf.union(firstIndex, nextIndex);
            }
        }
        Map<Integer, List<String>> indexToEmails = new HashMap<Integer, List<String>>();
        for (String email : emailToIndex.keySet()) {
            int index = uf.find(emailToIndex.get(email));
            List<String> account = indexToEmails.getOrDefault(index, new ArrayList<String>());
            account.add(email);
            indexToEmails.put(index, account);
        }
        List<List<String>> merged = new ArrayList<List<String>>();
        for (List<String> emails : indexToEmails.values()) {
            Collections.sort(emails);
            String name = emailToName.get(emails.get(0));
            List<String> account = new ArrayList<String>();
            account.add(name);
            account.addAll(emails);
            merged.add(account);
        }
        return merged;
    }

    private static class UnionFind {
        private int[] parent;

        public UnionFind(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] == x) {
                return parent[x];
            } else {
                x = parent[x];
                return find(x);
            }
        }

        public void union(int x, int y) {
            int parentX = find(x);
            int parentY = find(y);
            if (parentX != parentY) {
                parent[parentX] = parentY;
            }
        }

        public boolean isUnion(int x, int y) {
            int parentX = find(x);
            int parentY = find(y);
            return parentX == parentY;
        }

    }
}
