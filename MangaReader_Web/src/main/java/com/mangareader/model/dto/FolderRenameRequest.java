package com.mangareader.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 重命名文件夹请求 DTO
 *
 * @author marks
 * @version v1.0
 */
@Data
public class FolderRenameRequest {

    @NotBlank(message = "文件夹名称不能为空")
    @Size(max = 50, message = "文件夹名称不能超过50个字符")
    private String folderName;
}
