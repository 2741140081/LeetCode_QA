package com.mangareader.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 新增漫画下载任务请求体
 *
 * @author marks
 * @version v1.0
 */
@Data
public class MangaAddRequest {

    @NotBlank(message = "漫画名称不能为空")
    private String mangaName;

    @NotBlank(message = "漫画网址不能为空")
    private String mangaUrl;
}
