package com.marks.tools.war3;

import com.marks.tools.war3.entity.AddressListValueType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * AddressListValueType枚举的单元测试
 */
public class AddressListValueTypeTest {

    @Test
    public void testAddressListValueTypeValues() {
        AddressListValueType[] values = AddressListValueType.values();
        assertEquals(3, values.length);
        
        assertEquals(AddressListValueType.Integer, values[0]);
        assertEquals(AddressListValueType.Float, values[1]);
        assertEquals(AddressListValueType.Char4, values[2]);
    }

    @Test
    public void testAddressListValueTypeValueOf() {
        assertEquals(AddressListValueType.Integer, AddressListValueType.valueOf("Integer"));
        assertEquals(AddressListValueType.Float, AddressListValueType.valueOf("Float"));
        assertEquals(AddressListValueType.Char4, AddressListValueType.valueOf("Char4"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            AddressListValueType.valueOf("NonExistent");
        });
    }
}