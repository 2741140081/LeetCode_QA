package com.marks.tools.download_cartoon.utils;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: CartoonConstants </p>
 * <p>描述: 漫画下载工具常量类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/26 15:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public final class CartoonConstants {

    // 线程池配置
    public static final int THREAD_POOL_SIZE = 20;
    public static final int IMAGE_DOWNLOAD_THREAD_POOL_SIZE = 50;

    // 重试配置
    public static final int MAX_RETRY = 3;
    public static final long RETRY_INTERVAL = 5000; // 5秒

    // 图片命名格式
    public static final String IMAGE_NAME_FORMAT = "img%d.jpg";

    // 默认保存路径
    public static final String DEFAULT_SAVE_DIR = "D:\\cartoon_download\\";

    // 数据库表名
    public static final String CHAPTER_TABLE = "cartoon_chapters";
    public static final String IMAGE_TABLE = "cartoon_images";

    // HTTP请求配置
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";
    public static final int TIMEOUT = 30000; // 30秒超时

    // 状态码定义
    public static final int STATUS_PENDING = 0;    // 待处理
    public static final int STATUS_PROCESSING = 1; // 处理中
    public static final int STATUS_COMPLETED = 2;  // 已完成
    public static final int STATUS_FAILED = -1;    // 失败

    private CartoonConstants() {
        // 防止实例化
    }
}
