package com.marks.tools.war3;

import java.util.EventObject;

/**
 * 子节点创建事件参数类
 */
public class NewChildrenEventArgs extends EventObject {
    private TrainerNodeType nodeType;
    private int parentNodeIndex;
    
    private long thisGameAddress;         // [6FAA4178]
    private long thisGameMemoryAddress;   // [ThisGame + 0xC]
    private long thisUnitAddress;         // Unit ESI
    private long attackAttributesAddress; // [ThisUnit + 1E4]
    private long heroAttributesAddress;   // [ThisUnit + 1EC]
    private long currentItemPackAddress;  // [ThisUnit + 1F4]...

    public NewChildrenEventArgs(Object source) {
        super(source);
    }

    public NewChildrenEventArgs clone() {
        NewChildrenEventArgs retObject = new NewChildrenEventArgs(this.getSource());
        
        retObject.nodeType = this.nodeType;
        retObject.parentNodeIndex = this.parentNodeIndex;
        retObject.thisGameAddress = this.thisGameAddress;
        retObject.thisGameMemoryAddress = this.thisGameMemoryAddress;
        retObject.thisUnitAddress = this.thisUnitAddress;
        retObject.attackAttributesAddress = this.attackAttributesAddress;
        retObject.heroAttributesAddress = this.heroAttributesAddress;
        retObject.currentItemPackAddress = this.currentItemPackAddress;
        
        return retObject;
    }

    // Getters and setters
    public TrainerNodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(TrainerNodeType nodeType) {
        this.nodeType = nodeType;
    }

    public int getParentNodeIndex() {
        return parentNodeIndex;
    }

    public void setParentNodeIndex(int parentNodeIndex) {
        this.parentNodeIndex = parentNodeIndex;
    }

    public long getThisGameAddress() {
        return thisGameAddress;
    }

    public void setThisGameAddress(long thisGameAddress) {
        this.thisGameAddress = thisGameAddress;
    }

    public long getThisGameMemoryAddress() {
        return thisGameMemoryAddress;
    }

    public void setThisGameMemoryAddress(long thisGameMemoryAddress) {
        this.thisGameMemoryAddress = thisGameMemoryAddress;
    }

    public long getThisUnitAddress() {
        return thisUnitAddress;
    }

    public void setThisUnitAddress(long thisUnitAddress) {
        this.thisUnitAddress = thisUnitAddress;
    }

    public long getAttackAttributesAddress() {
        return attackAttributesAddress;
    }

    public void setAttackAttributesAddress(long attackAttributesAddress) {
        this.attackAttributesAddress = attackAttributesAddress;
    }

    public long getHeroAttributesAddress() {
        return heroAttributesAddress;
    }

    public void setHeroAttributesAddress(long heroAttributesAddress) {
        this.heroAttributesAddress = heroAttributesAddress;
    }

    public long getCurrentItemPackAddress() {
        return currentItemPackAddress;
    }

    public void setCurrentItemPackAddress(long currentItemPackAddress) {
        this.currentItemPackAddress = currentItemPackAddress;
    }
}