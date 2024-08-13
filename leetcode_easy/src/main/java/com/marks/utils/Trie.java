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
}
