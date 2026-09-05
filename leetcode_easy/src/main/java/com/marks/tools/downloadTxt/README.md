# 小说爬虫工具使用说明

## 文件说明

### 1. NovelChapterParser.java
基础版小说章节解析器，提供核心的解析功能：
- 解析小说目录页面
- 提取章节信息（标题、链接、序号）
- 获取单个章节内容
- 批量下载功能
- **智能定位**：优先通过ID `ul#list-chapter` 定位，避免重复结构干扰

### 2. FullNovelDownloader.java  
完整版小说下载器，功能更加完善：
- 支持目录解析和章节下载
- 自动保存为TXT文件
- 支持单独章节文件和完整小说文件两种保存模式
- 内置重试机制和防爬虫策略
- 内容清理和格式化
- **精准定位**：优先通过ID `ul#list-chapter` 精确定位章节列表

### 3. EnhancedNovelParser.java
增强版小说解析器，提供更灵活的定位策略：
- 支持多种定位方式（ID、Class、自定义选择器）
- 可配置的解析策略
- 详细的调试信息输出
- 支持处理重复结构的章节列表
- **多重定位策略**：解决页面中存在多个相似结构的问题

## 使用方法

### 基础使用（NovelChapterParser）

```java
public static void main(String[] args) {
    NovelChapterParser parser = new NovelChapterParser();
    
    try {
        // 解析目录
        List<ChapterInfo> chapters = parser.parseCatalog("https://bcshuku.com/novel50252/");
        
        // 显示章节目录
        chapters.forEach(System.out::println);
        
        // 下载所有章节（间隔1秒）
        parser.downloadAllChapters("https://bcshuku.com/novel50252/", 1000);
        
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

### 完整版使用（FullNovelDownloader）

```java
public static void main(String[] args) {
    FullNovelDownloader downloader = new FullNovelDownloader();
    
    String catalogUrl = "https://bcshuku.com/novel50252/";
    String novelName = "我的小说"; // 替换为实际小说名
    
    // 下载小说（true表示同时保存单独章节文件）
    downloader.downloadNovel(catalogUrl, novelName, true);
}
```

### 增强版使用（EnhancedNovelParser）

```java
public static void main(String[] args) {
    EnhancedNovelParser parser = new EnhancedNovelParser();
    String catalogUrl = "https://bcshuku.com/novel50252/";
    
    try {
        // 使用ID定位（推荐）
        List<ChapterInfo> chapters = parser.parseCatalog(catalogUrl, 
            new ParseConfig(LocatorStrategy.BY_ID));
        
        // 或者使用自定义选择器
        List<ChapterInfo> chapters2 = parser.parseCatalog(catalogUrl, 
            new ParseConfig(LocatorStrategy.CUSTOM_SELECTOR)
                .setCustomSelector("div.chapter-list ul.chapter-items"));
        
        // 或者获取所有匹配的容器
        List<ChapterInfo> allChapters = parser.parseCatalog(catalogUrl, 
            new ParseConfig(LocatorStrategy.BY_CLASS_ALL));
            
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

## 功能特点

### 智能定位策略
- **精确ID定位**：使用 `div#list-chapter > ul` 精确定位，完全匹配您的HTML结构
- **双重验证机制**：先定位div容器，再查找内部ul元素，确保准确性
- **智能降级处理**：当ID定位失败时，自动降级到class定位并给出明确提示
- **多重备选方案**：支持多种定位策略应对不同的页面结构
- **重复结构处理**：能够识别和处理页面中的多个相似结构

### 自动分页处理
- **智能分页检测**：自动识别网站的分页结构
- **连续页面抓取**：自动跟踪"Next"链接获取所有页面
- **多种分页策略**：支持多种分页链接定位方式
- **进度显示**：实时显示当前页码和章节统计
- **防重复抓取**：避免重复解析相同页面

### JavaScript动态内容处理
- **三重内容提取策略**：静态内容 → JavaScript渲染内容 → AJAX动态加载
- **智能AJAX解析**：自动识别和解析页面中的JavaScript AJAX请求
- **加密URL处理**：针对加密参数的智能内容提取
- **参数自动提取**：从JavaScript代码中自动提取API参数
- **Unicode转义处理**：正确处理返回内容中的Unicode转义字符
- **兼容性保障**：同时支持静态内容和动态加载的内容

### 反爬虫策略
- 设置合理的User-Agent
- 添加必要的请求头
- 控制请求频率（可自定义延迟时间）
- 带重试机制的网络连接

### 内容处理
- 自动识别多种内容区域选择器
- 智能内容清理（去除多余空白、格式化文本）
- 支持中文编码处理

### 文件保存
- 自动创建保存目录
- 文件名安全处理（移除非法字符）
- 支持两种保存模式：
  - 单独章节文件（便于分章阅读）
  - 完整小说文件（便于整体阅读）

## 配置说明

### 主要配置项
```java
private static final String BASE_URL = "https://bcshuku.com";  // 基础域名
private static final String SAVE_DIRECTORY = "D:/novels/";     // 保存目录
private static final String USER_AGENT = "...";               // 浏览器标识
```

### 可调整参数
- `timeout`: 连接超时时间（毫秒）
- `delayMs`: 请求间隔时间（毫秒）  
- `maxRetries`: 最大重试次数
- 保存路径和文件命名规则

## 注意事项

1. **遵守网站规则**：请合理设置请求频率，避免给服务器造成压力
2. **版权问题**：仅供学习研究使用，请尊重原作者版权
3. **网络环境**：确保网络连接稳定
4. **存储空间**：确认有足够的磁盘空间保存下载内容
5. **编码问题**：程序默认使用UTF-8编码保存文件

## 故障排除

### 常见问题
1. **连接超时**：增加timeout值或检查网络连接
2. **内容为空**：检查选择器是否匹配目标网站结构
3. **保存失败**：确认保存路径有写入权限
4. **编码乱码**：确保使用UTF-8编码读写文件
5. **定位失败**：使用 `TestFixParser.java` 测试定位逻辑，确认HTML结构

### 定位问题解决方案
如果遇到 `chapterList 为 null` 的问题：
- 运行 `TestFixParser.java` 验证定位逻辑
- 检查目标页面的实际HTML结构
- 确认ID `list-chapter` 是否在div元素上
- 查看控制台输出的详细定位信息

### 调试建议
- 开启详细日志输出
- 分步测试各个功能模块
- 检查目标网站的实际HTML结构
- 适当调整反爬虫参数

## 扩展建议

1. **支持更多网站**：根据不同网站调整解析规则
2. **添加数据库存储**：将小说信息存入数据库管理
3. **图形界面**：开发GUI方便操作
4. **多线程下载**：提高下载效率
5. **进度显示**：添加下载进度条
6. **断点续传**：支持中断后继续下载