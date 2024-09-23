package com.marks.utils;

/**
 * <p>项目名称: 字典树 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/13 9:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class Trie {
    public boolean isFinished;
    public Trie[] child;

    public Trie() {
        this.isFinished = false;
        this.child = new Trie[26];
    }

    public void insert(String word) {
        Trie node = this;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (node.child[ch - 'a'] == null) {
                node.child[ch - 'a'] = new Trie();
            }
            node = node.child[ch - 'a'];
        }
        node.isFinished = true;
    }

    public boolean search(String word) {
        Trie node = searchPrefix(word);
        return node != null && node.isFinished;
    }

    public boolean startsWith(String word) {
        return searchPrefix(word) != null;
    }

    private Trie searchPrefix(String word) {
        Trie node = this;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (node.child[ch - 'a'] == null) {
                return null;
            }
            node = node.child[ch - 'a'];
        }
        return node;
    }


}
