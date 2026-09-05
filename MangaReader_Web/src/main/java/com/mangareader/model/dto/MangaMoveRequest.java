package com.mangareader.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 漫画移动/添加到文件夹请求 DTO
 *
 * @author marks
 * @version v1.0
 */
@Data
public class MangaMoveRequest {

    @NotNull(message = "漫画ID不能为空")
    private Long mangaId;

    /**
     * 目标文件夹ID，null表示移至未分类
     */
    private Long folderId;
}
