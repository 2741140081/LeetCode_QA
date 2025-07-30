package com.marks.tools.kkplatform;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KKDzPlatformStartGameTest {

    @Test
    void startGame() throws InterruptedException {
        String gameImgPath = "D:\\images\\targetImage\\target_game_sign.png";
        KKDzPlatformStartGame dzPlatformStartGame = new KKDzPlatformStartGame();
        dzPlatformStartGame.startGame(gameImgPath);
    }
}