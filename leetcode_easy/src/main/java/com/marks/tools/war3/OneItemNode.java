package com.marks.tools.war3;

import com.marks.tools.war3.entity.AddressListValueType;
import com.marks.tools.war3.service.ITrainerNode;

/**
 * 单个物品节点类
 */
public class OneItemNode extends TrainerNode implements ITrainerNode {
    
    public OneItemNode(int nodeIndex, GameContext gameContext, NewChildrenEventArgs args) {
        super(nodeIndex, gameContext, args);
    }

    @Override
    public TrainerNodeType getNodeType() {
        return TrainerNodeType.OneItem;
    }

    @Override
    public String getNodeTypeName() {
        // return "物品";
        return "0x" + Long.toHexString(_newChildrenArgs.getCurrentItemPackAddress()).toUpperCase() 
            + ": " + getItemName();
    }

    @Override
    public int getNodeIndex() {
        return _nodeIndex;
    }

    @Override
    public int getParentIndex() {
        return _parentIndex;
    }

    /**
     * 获取物品名称
     * TODO: 需要从游戏内存中读取实际的物品名称
     * 当前实现仅为模拟，需要替换为真实的内存读取逻辑
     * @return 物品名称
     */
    private String getItemName() {
        // 在真实实现中，这里需要从游戏内存中读取物品名称
        // 模拟返回物品名称
        return "物品" + (_newChildrenArgs.getCurrentItemPackAddress() % 100);
    }

    @Override
    public void createChildren() {
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "物品名称",
            _newChildrenArgs.getCurrentItemPackAddress() + 0x30,
            AddressListValueType.Char4));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "使用次数",
            _newChildrenArgs.getCurrentItemPackAddress() + 0x84,
            AddressListValueType.Integer));
    }
}