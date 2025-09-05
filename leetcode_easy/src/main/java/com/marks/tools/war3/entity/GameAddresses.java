package com.marks.tools.war3.entity;

/**
 * 游戏地址实体类，封装游戏相关内存地址信息
 */
public class GameAddresses {
    private long thisGameAddress;
    private long unitListAddress;
    private long moveSpeedAddress;
    private long attackAttributesOffset;
    private long heroAttributesOffset;
    private long itemsListOffset;
    private long moveSpeedOffset;

    // Getters and Setters
    public long getThisGameAddress() {
        return thisGameAddress;
    }

    public void setThisGameAddress(long thisGameAddress) {
        this.thisGameAddress = thisGameAddress;
    }

    public long getUnitListAddress() {
        return unitListAddress;
    }

    public void setUnitListAddress(long unitListAddress) {
        this.unitListAddress = unitListAddress;
    }

    public long getMoveSpeedAddress() {
        return moveSpeedAddress;
    }

    public void setMoveSpeedAddress(long moveSpeedAddress) {
        this.moveSpeedAddress = moveSpeedAddress;
    }

    public long getAttackAttributesOffset() {
        return attackAttributesOffset;
    }

    public void setAttackAttributesOffset(long attackAttributesOffset) {
        this.attackAttributesOffset = attackAttributesOffset;
    }

    public long getHeroAttributesOffset() {
        return heroAttributesOffset;
    }

    public void setHeroAttributesOffset(long heroAttributesOffset) {
        this.heroAttributesOffset = heroAttributesOffset;
    }

    public long getItemsListOffset() {
        return itemsListOffset;
    }

    public void setItemsListOffset(long itemsListOffset) {
        this.itemsListOffset = itemsListOffset;
    }

    public long getMoveSpeedOffset() {
        return moveSpeedOffset;
    }

    public void setMoveSpeedOffset(long moveSpeedOffset) {
        this.moveSpeedOffset = moveSpeedOffset;
    }
    
    @Override
    public String toString() {
        return "GameAddresses{" +
                "thisGameAddress=0x" + Long.toHexString(thisGameAddress).toUpperCase() +
                ", unitListAddress=0x" + Long.toHexString(unitListAddress).toUpperCase() +
                ", moveSpeedAddress=0x" + Long.toHexString(moveSpeedAddress).toUpperCase() +
                ", attackAttributesOffset=0x" + Long.toHexString(attackAttributesOffset).toUpperCase() +
                ", heroAttributesOffset=0x" + Long.toHexString(heroAttributesOffset).toUpperCase() +
                ", itemsListOffset=0x" + Long.toHexString(itemsListOffset).toUpperCase() +
                ", moveSpeedOffset=0x" + Long.toHexString(moveSpeedOffset).toUpperCase() +
                '}';
    }
}