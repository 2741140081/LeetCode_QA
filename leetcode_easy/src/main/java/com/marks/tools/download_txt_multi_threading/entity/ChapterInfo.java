package com.marks.tools.download_txt_multi_threading.entity;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ChapterInfo </p>
 * <p>描述: [章节信息类] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/25 11:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ChapterInfo {
    private String title;
    private String url;
    private int number;
    private String content;

    public ChapterInfo(String title, String url, int number) {
        this.title = title;
        this.url = url;
        this.number = number;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public int getNumber() { return number; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    @Override
    public String toString() {
        return String.format("第%d章: %s", number, title);
    }
}
