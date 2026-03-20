package com.marks.kkPlatformGameAuto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marks.kkPlatformGameAuto.entity.ThiefGameEntity;
import com.marks.kkPlatformGameAuto.service.ThiefGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <p>项目名称：LeetCode_QA </p>
 * <p>文件名称：ThiefGameControllerTest </p>
 * <p>描述：小偷游戏控制器测试类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/20
 * @update [序号][日期 YYYY-MM-DD] [更改人姓名][变更描述]
 */
@WebMvcTest(ThiefGameController.class)
@ContextConfiguration(classes = {ThiefGameController.class})
class ThiefGameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ThiefGameService thiefGameService;

    @Autowired
    private ThiefGameController controller;

    @Autowired
    private ObjectMapper objectMapper;

    private ThiefGameEntity testConfig;

    @BeforeEach
    void setUp() {
        // 手动注入 Mock
        ReflectionTestUtils.setField(controller, "thiefGameService", thiefGameService);
    }

    @Test
    @DisplayName("测试执行小偷游戏完整流程 - 成功场景")
    void testExecuteThiefGame_Success() throws Exception {
        // Given: 模拟服务返回成功
        when(thiefGameService.executeThiefGameFlow(any(ThiefGameEntity.class)))
                .thenReturn(true);

        // When & Then: 发送请求并验证响应
        mockMvc.perform(post("/api/thief-game/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testConfig)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("小偷游戏流程执行成功"))
                .andExpect(jsonPath("$.success").value(true));

        // 验证服务方法被调用了一次
        verify(thiefGameService, times(1)).executeThiefGameFlow(any(ThiefGameEntity.class));
    }

    @Test
    @DisplayName("测试执行小偷游戏完整流程 - 失败场景")
    void testExecuteThiefGame_Failure() throws Exception {
        // Given: 模拟服务返回失败
        when(thiefGameService.executeThiefGameFlow(any(ThiefGameEntity.class)))
                .thenReturn(false);

        // When & Then: 发送请求并验证响应
        mockMvc.perform(post("/api/thief-game/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testConfig)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("小偷游戏流程执行失败"))
                .andExpect(jsonPath("$.success").value(false));

        // 验证服务方法被调用了一次
        verify(thiefGameService, times(1)).executeThiefGameFlow(any(ThiefGameEntity.class));
    }

    @Test
    @DisplayName("测试执行小偷游戏完整流程 - 异常场景")
    void testExecuteThiefGame_Exception() throws Exception {
        // Given: 模拟服务抛出异常
        when(thiefGameService.executeThiefGameFlow(any(ThiefGameEntity.class)))
                .thenThrow(new RuntimeException("测试异常"));

        // When & Then: 发送请求并验证响应
        mockMvc.perform(post("/api/thief-game/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testConfig)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("执行异常：测试异常"))
                .andExpect(jsonPath("$.success").value(false));

        // 验证服务方法被调用了一次
        verify(thiefGameService, times(1)).executeThiefGameFlow(any(ThiefGameEntity.class));
    }

    @Test
    @DisplayName("测试选择难度和挑战 - 成功场景")
    void testSelectDifficulty_Success() throws Exception {
        // Given: 准备测试参数
        Map<String, Object> params = new HashMap<>();
        params.put("difficulty", 1);
        params.put("gameMode", 0);
        params.put("challenges", Arrays.asList(1, 2, 3));

        // 模拟服务返回成功
        when(thiefGameService.selectDifficultyAndChallenges(anyInt(), anyInt(), anyList()))
                .thenReturn(true);

        // When & Then: 发送请求并验证响应
        mockMvc.perform(post("/api/thief-game/select-difficulty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("难度选择成功"))
                .andExpect(jsonPath("$.success").value(true));

        // 验证服务方法被调用了一次
        verify(thiefGameService, times(1))
                .selectDifficultyAndChallenges(anyInt(), anyInt(), anyList());
    }

    @Test
    @DisplayName("测试选择难度和挑战 - 失败场景")
    void testSelectDifficulty_Failure() throws Exception {
        // Given: 准备测试参数
        Map<String, Object> params = new HashMap<>();
        params.put("difficulty", 1);
        params.put("gameMode", 0);
        params.put("challenges", Arrays.asList(1, 2, 3));

        // 模拟服务返回失败
        when(thiefGameService.selectDifficultyAndChallenges(anyInt(), anyInt(), anyList()))
                .thenReturn(false);

        // When & Then: 发送请求并验证响应
        mockMvc.perform(post("/api/thief-game/select-difficulty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("难度选择失败"))
                .andExpect(jsonPath("$.success").value(false));

        // 验证服务方法被调用了一次
        verify(thiefGameService, times(1))
                .selectDifficultyAndChallenges(anyInt(), anyInt(), anyList());
    }

    @Test
    @DisplayName("测试挑战存档 BOSS - 成功场景")
    void testChallengeArchiver_Success() throws Exception {
        // Given: 准备测试参数
        Map<String, Object> params = new HashMap<>();
        List<Integer> archiverBuildings = Arrays.asList(1, 2, 3, 4, 5, 6);
        params.put("archiverBuildings", archiverBuildings);

        // 模拟服务返回成功
        when(thiefGameService.challengeArchiverBosses(anyList())).thenReturn(true);

        // When & Then: 发送请求并验证响应
        mockMvc.perform(post("/api/thief-game/challenge-archiver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("挑战存档 BOSS 成功"))
                .andExpect(jsonPath("$.success").value(true));

        // 验证服务方法被调用了一次
        verify(thiefGameService, times(1)).challengeArchiverBosses(anyList());
    }

    @Test
    @DisplayName("测试挑战存档 BOSS - 失败场景")
    void testChallengeArchiver_Failure() throws Exception {
        // Given: 准备测试参数
        Map<String, Object> params = new HashMap<>();
        List<Integer> archiverBuildings = Arrays.asList(1, 2, 3, 4, 5, 6);
        params.put("archiverBuildings", archiverBuildings);

        // 模拟服务返回失败
        when(thiefGameService.challengeArchiverBosses(anyList())).thenReturn(false);

        // When & Then: 发送请求并验证响应
        mockMvc.perform(post("/api/thief-game/challenge-archiver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("挑战存档 BOSS 失败"))
                .andExpect(jsonPath("$.success").value(false));

        // 验证服务方法被调用了一次
        verify(thiefGameService, times(1)).challengeArchiverBosses(anyList());
    }

//    @Test
//    @DisplayName("测试使用完整的 ThiefGameEntity 配置执行游戏")
//    void testExecuteThiefGameWithFullConfig() throws Exception {
//        // Given: 准备完整的测试配置
//        ThiefGameEntity fullConfig = new ThiefGameEntity();
//        fullConfig.setDifficulty(2);
//        fullConfig.setGameMode(1);
//        fullConfig.setChallenges(Arrays.asList(1, 2, 3, 4, 5));
//        fullConfig.setArchiverBuildings(Arrays.asList(1, 2, 3, 4, 5, 6));
//
//        // 模拟服务返回成功
//        when(thiefGameService.executeThiefGameFlow(any(ThiefGameEntity.class)))
//                .thenReturn(true);
//
//        // When & Then: 发送请求并验证响应
//        mockMvc.perform(post("/api/thief-game/execute")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(fullConfig)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.message").value("小偷游戏流程执行成功"))
//                .andExpect(jsonPath("$.success").value(true));
//
//        // 验证服务方法被调用
//        verify(thiefGameService, times(1)).executeThiefGameFlow(any(ThiefGameEntity.class));
//    }
//
//    @Test
//    @DisplayName("测试并发执行多个游戏请求")
//    void testConcurrentExecution() throws Exception {
//        // Given: 准备多个不同的配置
//        ThiefGameEntity config1 = new ThiefGameEntity();
//        config1.setDifficulty(1);
//        config1.setGameMode(0);
//        config1.setChallenges(Arrays.asList(1, 2));
//        config1.setArchiverBuildings(Arrays.asList(1, 2, 3));
//
//        ThiefGameEntity config2 = new ThiefGameEntity();
//        config2.setDifficulty(2);
//        config2.setGameMode(1);
//        config2.setChallenges(Arrays.asList(3, 4, 5));
//        config2.setArchiverBuildings(Arrays.asList(4, 5, 6));
//
//        // 模拟服务对不同配置都返回成功
//        when(thiefGameService.executeThiefGameFlow(any(ThiefGameEntity.class)))
//                .thenReturn(true);
//
//        // When & Then: 发送多个请求
//        mockMvc.perform(post("/api/thief-game/execute")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(config1)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true));
//
//        mockMvc.perform(post("/api/thief-game/execute")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(config2)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true));
//
//        // 验证服务方法被调用了两次
//        verify(thiefGameService, times(2)).executeThiefGameFlow(any(ThiefGameEntity.class));
//    }
}
