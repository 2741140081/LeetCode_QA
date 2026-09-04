package com.mangareader.model.dto;

import lombok.Data;

/**
 * 漫画移动/添加到文件夹请求 DTO
 *
 * @author marks
 * @version v1.0
 */
@Data
public class MangaMoveRequest {

    private Long mangaId;

    /**
     * 目标文件夹ID，null表示移至未分类
     */
    private Long folderId;
}
