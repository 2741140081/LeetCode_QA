package com.marks.tools.war3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TrainerNodeType枚举的单元测试
 */
public class TrainerNodeTypeTest {

    @Test
    public void testTrainerNodeTypeValues() {
        TrainerNodeType[] values = TrainerNodeType.values();
        assertEquals(10, values.length);
        
        assertEquals(TrainerNodeType.None, values[0]);
        assertEquals(TrainerNodeType.Root, values[1]);
        assertEquals(TrainerNodeType.Introduction, values[2]);
        assertEquals(TrainerNodeType.Cash, values[3]);
        assertEquals(TrainerNodeType.AllSelectedUnits, values[4]);
        assertEquals(TrainerNodeType.OneSelectedUnit, values[5]);
        assertEquals(TrainerNodeType.AttackAttributes, values[6]);
        assertEquals(TrainerNodeType.HeroAttributes, values[7]);
        assertEquals(TrainerNodeType.AllItems, values[8]);
        assertEquals(TrainerNodeType.OneItem, values[9]);
    }

    @Test
    public void testTrainerNodeTypeValueOf() {
        assertEquals(TrainerNodeType.Root, TrainerNodeType.valueOf("Root"));
        assertEquals(TrainerNodeType.Cash, TrainerNodeType.valueOf("Cash"));
        assertEquals(TrainerNodeType.Introduction, TrainerNodeType.valueOf("Introduction"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            TrainerNodeType.valueOf("NonExistent");
        });
    }
}