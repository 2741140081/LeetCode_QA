package com.marks.tools.war3;

/**
 * 游戏上下文类，存储游戏进程相关信息
 */
public class GameContext {
    private int processId;
    private String processVersion;
    private long thisGameAddress;
    private long unitListAddress;
    private long moveSpeedAddress;
    private long attackAttributesOffset;
    private long heroAttributesOffset;
    private long itemsListOffset;

    public GameContext(int processId, String processVersion) {
        this.processId = processId;
        this.processVersion = processVersion;
    }

    // Getters and setters
    public int getProcessId() { 
        return processId; 
    }
    
    public void setProcessId(int processId) { 
        this.processId = processId; 
    }
    
    public String getProcessVersion() { 
        return processVersion; 
    }
    
    public void setProcessVersion(String processVersion) { 
        this.processVersion = processVersion; 
    }
    
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
}