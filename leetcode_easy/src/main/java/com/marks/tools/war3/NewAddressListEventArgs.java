package com.marks.tools.war3;

import com.marks.tools.war3.entity.AddressListValueType;

import java.util.EventObject;

/**
 * 地址列表事件参数类
 */
public class NewAddressListEventArgs extends EventObject {
    private int nodeIndex;
    private String name;
    private long address;
    private AddressListValueType valueType;
    private int maxValue;

    public NewAddressListEventArgs(Object source) {
        super(source);
    }

    public NewAddressListEventArgs(int nodeIndex, String name, long address, AddressListValueType valueType) {
        super(new Object());
        this.nodeIndex = nodeIndex;
        this.name = name;
        this.address = address;
        this.valueType = valueType;
    }

    public NewAddressListEventArgs(int nodeIndex, String name, long address, AddressListValueType valueType, int maxValue) {
        this(nodeIndex, name, address, valueType);
        this.maxValue = maxValue;
    }

    // Getters and setters
    public int getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(int nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAddress() {
        return address;
    }

    public void setAddress(long address) {
        this.address = address;
    }

    public AddressListValueType getValueType() {
        return valueType;
    }

    public void setValueType(AddressListValueType valueType) {
        this.valueType = valueType;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}