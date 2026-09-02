package com.weather.config;

import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.weather.tools.UserLocationTool;
import com.weather.tools.WeatherForLocationTool;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: WeatherAgentConfig </p>
 * <p>描述: 天气 Agent 装配配置：DashScope 模型 + 系统提示词 + 工具注册 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/1
 */
@Configuration
public class WeatherAgentConfig {

    /** 简单的系统提示词 */
    private static final String SYSTEM_PROMPT = """
            你是一个专业的天气助手。
            - 当用户询问天气时，必须先调用工具获取数据，再基于工具返回的结果回答，不要凭空编造。
            - 如果用户没有说明城市，先调用 getUserLocation 获取用户所在城市，再查询该城市天气。
            - 使用中文，回答简洁、友好。
            - 与天气无关的问题，礼貌地说明自己是天气助手即可。
            """;

    /**
     * 构建天气 Agent（ReactAgent：推理 + 工具调用循环）
     *
     * @param chatModel DashScope starter 自动装配的 ChatModel
     */
    @Bean
    public ReactAgent weatherAgent(ChatModel chatModel) {
        // 用户定位工具：输入用户问题，返回用户所在城市
        ToolCallback userLocationTool = FunctionToolCallback
                .builder("getUserLocation", new UserLocationTool())
                .description("获取用户当前所在的城市。当用户询问天气但没有指定城市时调用。")
                .inputType(String.class)
                .build();

        // 天气查询工具：输入城市名，返回该城市天气
        ToolCallback weatherTool = FunctionToolCallback
                .builder("getWeatherForLocation", new WeatherForLocationTool())
                .description("根据城市名称查询该城市的实时天气信息，输入参数为城市名（如：杭州）。")
                .inputType(String.class)
                .build();

        return ReactAgent.builder()
                .name("weather_agent")
                .model(chatModel)
                .tools(userLocationTool, weatherTool)
                .instruction(SYSTEM_PROMPT)
                .build();
    }
}
