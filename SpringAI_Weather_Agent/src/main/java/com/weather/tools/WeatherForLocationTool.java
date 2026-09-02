package com.weather.tools;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: WeatherForLocationTool </p>
 * <p>描述: 天气查询工具，根据城市名返回该城市的实时天气（演示用 Mock 数据） </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/1 16:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class WeatherForLocationTool implements BiFunction<String, ToolContext, String> {

    /** 演示用 Mock 天气数据，实际项目中可替换为真实天气 API 调用 */
    private static final Map<String, String> WEATHER_DATA = Map.of(
            "北京", "晴，气温 25°C，北风 2 级，湿度 40%",
            "上海", "多云，气温 28°C，东南风 3 级，湿度 65%",
            "广州", "雷阵雨，气温 31°C，南风 2 级，湿度 85%",
            "深圳", "阵雨转多云，气温 30°C，东风 3 级，湿度 80%",
            "杭州", "晴转多云，气温 27°C，西南风 2 级，湿度 55%"
    );

    @Override
    public String apply(
            @ToolParam(description = "The city name") String city, ToolContext toolContext) {
        if (city == null || city.isBlank()) {
            return "城市名称不能为空，请提供要查询天气的城市";
        }
        String key = city.trim();
        String weather = WEATHER_DATA.get(key);
        if (weather != null) {
            return key + "今日天气：" + weather;
        }
        return "暂无城市「" + key + "」的天气数据（演示环境仅支持：北京、上海、广州、深圳、杭州）";
    }
}
