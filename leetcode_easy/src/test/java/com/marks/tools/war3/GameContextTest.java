package com.marks.tools.war3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * GameContext类的单元测试
 */
public class GameContextTest {

    @Test
    public void testGameContextCreation() {
        GameContext context = new GameContext(1234, "1.28");
        assertEquals(1234, context.getProcessId());
        assertEquals("1.28", context.getProcessVersion());
    }

    @Test
    public void testGameContextSetters() {
        GameContext context = new GameContext(0, "");
        context.setProcessId(5678);
        context.setProcessVersion("1.29");
        context.setThisGameAddress(0x1000);
        context.setUnitListAddress(0x2000);
        context.setMoveSpeedAddress(0x3000);
        context.setAttackAttributesOffset(0x1E4);
        context.setHeroAttributesOffset(0x1EC);
        context.setItemsListOffset(0x1F4);

        assertEquals(5678, context.getProcessId());
        assertEquals("1.29", context.getProcessVersion());
        assertEquals(0x1000L, context.getThisGameAddress());
        assertEquals(0x2000L, context.getUnitListAddress());
        assertEquals(0x3000L, context.getMoveSpeedAddress());
        assertEquals(0x1E4L, context.getAttackAttributesOffset());
        assertEquals(0x1ECL, context.getHeroAttributesOffset());
        assertEquals(0x1F4L, context.getItemsListOffset());
    }
}