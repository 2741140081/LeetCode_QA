package com.marks.leetcode.data_structure.trie;

/**
 * <p>项目名称: LeetCode_211 </p>
 * <p>文件名称: 添加与搜索单词 - 数据结构设计 </p>
 * <p>描述:
 * 请你设计一个数据结构，支持 添加新单词 和 查找字符串是否与任何先前添加的字符串匹配 。
 *
 * 实现词典类 WordDictionary ：
 * WordDictionary() 初始化词典对象
 * void addWord(word) 将 word 添加到数据结构中，之后可以对它进行匹配
 * bool search(word) 如果数据结构中存在字符串与 word 匹配，则返回 true ；否则，返回  false 。
 * word 中可能包含一些 '.' ，每个 . 都可以表示任何一个字母。
 * </p>
 * <p> AC: 187ms/86.47MB </p>
 * @author marks
 * @version v1.0
 * @date 2025/2/25 16:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class WordDictionary {
    private WordDictionary[] items;
    boolean isEnd;
    public WordDictionary() {
        items = new WordDictionary[26];
    }

    public void addWord(String word) {
        WordDictionary curr = this;
        int n = word.length();
        for(int i = 0; i < n; i++){
            int index = word.charAt(i) - 'a';
            if(curr.items[index] == null)
                curr.items[index] = new WordDictionary();
            curr = curr.items[index];
        }
        curr.isEnd = true;
    }

    public boolean search(String word) {
        return search(this, word, 0);
    }

    private boolean search(WordDictionary curr, String word, int start){
        int n = word.length();
        if(start == n)
            return curr.isEnd;
        char c = word.charAt(start);
        if(c != '.'){
            WordDictionary item = curr.items[c - 'a'];
            return item != null && search(item, word, start+1);
        }

        for(int j = 0; j < 26; j++){
            if(curr.items[j] != null && search(curr.items[j], word, start+1))
                return true;
        }
        return false;
    }
}
