package com.mangareader.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 漫画批量删除请求
 *
 * @author marks
 * @version v1.0
 */
@Data
public class MangaBatchDeleteRequest {

    @NotEmpty(message = "请选择要删除的漫画")
    private List<Long> mangaIds;
}
