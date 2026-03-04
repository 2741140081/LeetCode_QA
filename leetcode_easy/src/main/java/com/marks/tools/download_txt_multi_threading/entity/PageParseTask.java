package com.marks.tools.download_txt_multi_threading.entity;

import org.jsoup.nodes.Document;

/**
 * 页面解析任务实体类
 * 封装单个分页的解析信息，用于多线程并发处理小说章节目录
 * 
 * @author marks
 * @version 1.0
 * @since 2026-02-28
 */
public class PageParseTask {
    
    /** 页面文档对象 */
    private final Document document;
    
    /** 页面序号（从1开始） */
    private final int pageNumber;
    
    /** 该页面章节的起始编号 */
    private final int startChapterNumber;
    
    /**
     * 构造函数
     * 
     * @param document 页面的Jsoup文档对象
     * @param pageNumber 页面序号
     * @param startChapterNumber 该页面章节的起始编号
     */
    public PageParseTask(Document document, int pageNumber, int startChapterNumber) {
        this.document = document;
        this.pageNumber = pageNumber;
        this.startChapterNumber = startChapterNumber;
    }
    
    /**
     * 获取页面文档对象
     * 
     * @return Jsoup Document对象
     */
    public Document getDocument() { 
        return document; 
    }
    
    /**
     * 获取页面序号
     * 
     * @return 页面序号（从1开始）
     */
    public int getPageNumber() { 
        return pageNumber; 
    }
    
    /**
     * 获取该页面章节的起始编号
     * 
     * @return 起始章节编号
     */
    public int getStartChapterNumber() { 
        return startChapterNumber; 
    }
    
    /**
     * 获取该页面预计的章节结束编号
     * 基于每页50章节的假设计算
     * 
     * @return 结束章节编号
     */
    public int getEndChapterNumber() {
        return startChapterNumber + 49; // 50章节 - 1
    }
    
    /**
     * 计算该页面的章节数量范围
     * 
     * @return 章节编号范围描述
     */
    public String getChapterRange() {
        return startChapterNumber + "-" + getEndChapterNumber();
    }
    
    @Override
    public String toString() {
        return String.format("PageParseTask{page=%d, chapters=%s, docHash=%s}", 
                pageNumber, getChapterRange(), 
                document != null ? Integer.toHexString(document.hashCode()) : "null");
    }
}
