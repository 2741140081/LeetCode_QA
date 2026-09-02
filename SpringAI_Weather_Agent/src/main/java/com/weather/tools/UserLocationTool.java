package com.weather.tools;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.function.BiFunction;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: UserLocationTool </p>
 * <p>描述: 用户定位工具，获取用户当前所在城市（演示用固定返回值） </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/1 16:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class UserLocationTool implements BiFunction<String, ToolContext, String> {
    @Override
    public String apply(
            @ToolParam(description = "User query") String query,
            ToolContext toolContext) {
        // 从上下文获取用户信息（调用方可通过 ToolContext 传入 userId 等）
        String userId = null;
        if (toolContext != null && toolContext.getContext() != null) {
            Object id = toolContext.getContext().get("userId");
            userId = id == null ? null : String.valueOf(id);
        }
        // 演示环境：返回 Mock 定位，实际项目中可根据 userId 查询或调用定位服务
        return "用户当前所在城市：杭州" + (userId == null ? "" : "（userId=" + userId + "）");
    }
}
