package com.mangareader.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(max = 200, message = "漫画名称不能超过200个字符")
    private String mangaName;

    @NotBlank(message = "漫画网址不能为空")
    @Pattern(regexp = "^https?://.*", message = "网址格式不正确")
    private String mangaUrl;
}
