package com.mangareader.mapper;

import com.mangareader.model.entity.MangaImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaImageMapper </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/18 17:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Mapper
public interface MangaImageMapper {
    List<MangaImage> findByChapterId(@Param("chapterId") Long chapterId);

    /**
     * 分页查询章节图片
     */
    List<MangaImage> findByChapterIdPaged(@Param("chapterId") Long chapterId,
                                          @Param("offset") int offset,
                                          @Param("limit") int limit);

    /**
     * 统计章节图片总数
     */
    int countByChapterId(@Param("chapterId") Long chapterId);

    // 添加批量插入方法
    int batchInsert(@Param("list") List<MangaImage> imageList);

    // 查询待下载任务
    List<MangaImage> selectPendingTasks(@Param("status") Integer status, @Param("limit") Integer limit);

    // 查询超时下载中任务
    List<MangaImage> selectTimeoutDownloadingTasks(@Param("timeout") Integer timeout, @Param("limit") Integer limit);

    // 更新下载状态
    int updateStatus(@Param("imageId") Long imageId, @Param("status") Integer status,
                     @Param("downloadedSize") Long downloadedSize, @Param("errorMsg") String errorMsg);

    // 标记为下载中
    int markAsDownloading(@Param("imageId") Long imageId);

    // 标记为下载成功
    int markAsSuccess(@Param("imageId") Long imageId, @Param("fileSize") Long fileSize);

    // 增加重试次数
    int incrementRetryCount(@Param("imageId") Long imageId);

    // 根据ID查询
    MangaImage selectById(@Param("imageId") Long imageId);

    // 统计待下载任务数
    int countPendingTasks();

    // 统计下载中任务数
    int countDownloadingTasks();

}
