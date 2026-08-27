package com.mangareader.mapper;

import com.mangareader.model.entity.Manga;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaMapper </p>
 * <p>描述: 漫画数据访问层接口 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/20 14:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Mapper
public interface MangaMapper {

    /**
     * 查询所有漫画
     * @return 漫画列表
     */
    List<Manga> selectAllManga();

    /**
     * 根据ID查询漫画
     * @param mangaId 漫画ID
     * @return 漫画对象
     */
    Manga selectMangaById(@Param("mangaId") Long mangaId);

    /**
     * 新增漫画
     * @param manga 漫画对象
     * @return 影响行数
     */
    int insertManga(Manga manga);

    /**
     * 查询正在下载的漫画
     */
    Manga selectProcessingManga();

    /**
     * 查询待下载的漫画
     */
    Manga selectPendingManga();

    /**
     * 更新漫画状态
     */
    int updateMangaStatus(@Param("mangaId") Long mangaId, @Param("status") Integer status);

    /**
     * 更新心跳时间
     */
    int updateHeartBeat(@Param("mangaId") Long mangaId, @Param("lastHeartBeat") LocalDateTime lastHeartBeat);

    /**
     * 更新已处理章节数
     */
    int updateProcessedChapters(@Param("mangaId") Long mangaId, @Param("processedChapters") Integer processedChapters);

    /**
     * 更新总章节数
     */
    int updateTotalChapters(@Param("mangaId") Long mangaId, @Param("totalChapters") Integer totalChapters);
}
