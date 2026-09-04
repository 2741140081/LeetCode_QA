package com.mangareader.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 创建文件夹请求 DTO
 *
 * @author marks
 * @version v1.0
 */
@Data
public class FolderCreateRequest {

    @NotBlank(message = "文件夹名称不能为空")
    private String folderName;
}
