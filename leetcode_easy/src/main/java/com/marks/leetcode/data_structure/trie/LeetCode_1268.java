package com.marks.leetcode.data_structure.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/3 16:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1268 {
    /**
     * @Description:
     * 给你一个产品数组 products 和一个字符串 searchWord ，products  数组中每个产品都是一个字符串。
     *
     * 请你设计一个推荐系统，在依次输入单词 searchWord 的每一个字母后，推荐 products 数组中前缀与 searchWord 相同的最多三个产品。
     * 如果前缀相同的可推荐产品超过三个，请按字典序返回最小的三个。
     *
     * 请你以二维列表的形式，返回在输入 searchWord 每个字母后相应的推荐产品的列表。
     * @param products 
     * @param searchWord
     * @return java.util.List<java.util.List<java.lang.String>>
     * @author marks
     * @CreateDate: 2025/3/3 16:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        List<List<String>> result;
        result = method_01(products, searchWord);
        return result;
    }

    /**
     * @Description:
     * 思路: 使用字典树, 字典树每个节点除了存储 char ch之外, 还需要存储基本的例如 'a'节点, 存储 app, apple, abc, list
     * 并且这个list需要排序, 那么我可以直接使用TreeMap<String, Integer> map, key = products, suggests = count 出现的数量
     * 查看题解后修改
     * AC: 15ms/46.96MB
     * @param products
     * @param searchWord
     * @return java.util.List<java.util.List<java.lang.String>>
     * @author marks
     * @CreateDate: 2025/3/3 16:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<String>> method_01(String[] products, String searchWord) {
        Arrays.sort(products);
        TireTreeMap tire = new TireTreeMap();
        for (String product : products) {
            tire.insert(product);
        }
        List<List<String>> ans = new ArrayList<>();
        TireTreeMap node = tire;
        boolean flag = true;
        for (int i = 0; i < searchWord.length(); i++) {
            char ch = searchWord.charAt(i);
            if (flag) {
                if (node.child[ch - 'a'] != null) {
                    node = node.child[ch - 'a'];
                    ans.add(node.suggests);
                }else {
                    flag = false;
                    ans.add(new ArrayList<>());
                }
            } else {
                ans.add(new ArrayList<>());
            }

        }
        return ans;
    }
}

class TireTreeMap {
    public TireTreeMap[] child; // 字典树

    public List<String> suggests; // 经过该节点的所有的String 集合, 并且按照字典序排序, 方便取前3位

    public TireTreeMap() {
        child = new TireTreeMap[26];
        suggests = new ArrayList<>();
    }

    public void insert(String word) {
        TireTreeMap node = this;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (node.child[ch - 'a'] == null) {
                node.child[ch - 'a'] = new TireTreeMap();
            }
            node = node.child[ch - 'a'];
            if (node.suggests.size() < 3) {
                node.suggests.add(word);
            }
        }
    }
}
