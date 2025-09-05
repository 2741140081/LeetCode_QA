package com.marks.tools.war3;

import com.marks.tools.war3.entity.AddressListValueType;

/**
 * 节点地址列表项类
 */
public class NodeAddressList {
    private int nodeIndex;
    private String name;
    private long address;
    private AddressListValueType valueType;
    private int maxValue;
    private Object originalValue;
    private Object unsavedValue;

    public NodeAddressList(NewAddressListEventArgs args) {
        this.nodeIndex = args.getNodeIndex();
        this.name = args.getName();
        this.address = args.getAddress();
        this.valueType = args.getValueType();
        this.maxValue = args.getMaxValue();
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

    public Object getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(Object originalValue) {
        this.originalValue = originalValue;
    }

    public Object getUnsavedValue() {
        return unsavedValue;
    }

    public void setUnsavedValue(Object unsavedValue) {
        this.unsavedValue = unsavedValue;
    }
}