package com.marks.tools.war3;

import com.marks.tools.war3.entity.AddressListValueType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * NodeAddressList类的单元测试
 */
public class NodeAddressListTest {

    @Test
    public void testNodeAddressListCreation() {
        NewAddressListEventArgs args = new NewAddressListEventArgs(1, "测试地址", 0x1000L, AddressListValueType.Integer);
        NodeAddressList nodeAddress = new NodeAddressList(args);
        
        assertEquals(1, nodeAddress.getNodeIndex());
        assertEquals("测试地址", nodeAddress.getName());
        assertEquals(0x1000L, nodeAddress.getAddress());
        assertEquals(AddressListValueType.Integer, nodeAddress.getValueType());
    }

    @Test
    public void testNodeAddressListWithMaxValue() {
        NewAddressListEventArgs args = new NewAddressListEventArgs(2, "测试地址2", 0x2000L, AddressListValueType.Float, 100);
        NodeAddressList nodeAddress = new NodeAddressList(args);
        
        assertEquals(2, nodeAddress.getNodeIndex());
        assertEquals("测试地址2", nodeAddress.getName());
        assertEquals(0x2000L, nodeAddress.getAddress());
        assertEquals(AddressListValueType.Float, nodeAddress.getValueType());
        assertEquals(100, nodeAddress.getMaxValue());
    }

    @Test
    public void testNodeAddressListSetters() {
        NewAddressListEventArgs args = new NewAddressListEventArgs(1, "测试地址", 0x1000L, AddressListValueType.Integer);
        NodeAddressList nodeAddress = new NodeAddressList(args);
        
        nodeAddress.setNodeIndex(3);
        nodeAddress.setName("新名称");
        nodeAddress.setAddress(0x3000L);
        nodeAddress.setValueType(AddressListValueType.Char4);
        nodeAddress.setMaxValue(200);
        nodeAddress.setOriginalValue(100);
        nodeAddress.setUnsavedValue(150);
        
        assertEquals(3, nodeAddress.getNodeIndex());
        assertEquals("新名称", nodeAddress.getName());
        assertEquals(0x3000L, nodeAddress.getAddress());
        assertEquals(AddressListValueType.Char4, nodeAddress.getValueType());
        assertEquals(200, nodeAddress.getMaxValue());
        assertEquals(100, nodeAddress.getOriginalValue());
        assertEquals(150, nodeAddress.getUnsavedValue());
    }
}