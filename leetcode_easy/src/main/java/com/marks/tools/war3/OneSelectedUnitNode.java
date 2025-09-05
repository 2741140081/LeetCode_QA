package com.marks.tools.war3;

import com.marks.tools.war3.entity.AddressListValueType;
import com.marks.tools.war3.service.ITrainerNode;

/**
 * 单个选中单位节点类
 */
public class OneSelectedUnitNode extends TrainerNode implements ITrainerNode {
    
    public OneSelectedUnitNode(int nodeIndex, GameContext gameContext, NewChildrenEventArgs args) {
        super(nodeIndex, gameContext, args);
    }

    @Override
    public TrainerNodeType getNodeType() {
        return TrainerNodeType.OneSelectedUnit;
    }

    @Override
    public String getNodeTypeName() {
        // return "单位";
        return "0x" + Long.toHexString(_newChildrenArgs.getThisUnitAddress()).toUpperCase() 
            + ": " + getUnitName();
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
     * 获取单位名称
     * TODO: 需要从游戏内存中读取实际的单位名称
     * 当前实现仅为模拟，需要替换为真实的内存读取逻辑
     * @return 单位名称
     */
    public String getUnitName() {
        // 在真实实现中，这里需要从游戏内存中读取单位名称
        // 模拟返回单位名称
        return "单位" + (_newChildrenArgs.getThisUnitAddress() % 1000);
    }

    @Override
    public void createChildren() {
        // 模拟从游戏内存中读取单位属性地址
        _newChildrenArgs.setAttackAttributesAddress(_newChildrenArgs.getThisUnitAddress() + 0x1E4);
        _newChildrenArgs.setHeroAttributesAddress(_newChildrenArgs.getThisUnitAddress() + 0x1EC);

        if (_newChildrenArgs.getAttackAttributesAddress() > 0) {
            createChild(TrainerNodeType.AttackAttributes, _nodeIndex);
            // createChild(TrainerNodeType.UnitAbility, NodeIndex);
            createChild(TrainerNodeType.AllItems, _nodeIndex);
        }

        if (_newChildrenArgs.getHeroAttributesAddress() > 0) {
            createChild(TrainerNodeType.HeroAttributes, _nodeIndex);
        }

        // Unit self properties
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "单位名称",
            _newChildrenArgs.getThisUnitAddress() + 0x30,
            AddressListValueType.Char4));

        long tmpAddress1 = _newChildrenArgs.getThisUnitAddress() + 0x84;
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "HP - 目前",
            tmpAddress1 - 0xC,
            AddressListValueType.Float));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "HP - 最大",
            tmpAddress1,
            AddressListValueType.Float));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "HP - 回复率",
            _newChildrenArgs.getThisUnitAddress() + 0xB0,
            AddressListValueType.Float));

        // MP
        tmpAddress1 = _newChildrenArgs.getThisUnitAddress() + 0x84 + 0x20; // 模拟MP地址
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "MP - 目前",
            tmpAddress1 - 0xC,
            AddressListValueType.Float));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "MP - 最大",
            tmpAddress1,
            AddressListValueType.Float));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "MP - 回复率",
            _newChildrenArgs.getThisUnitAddress() + 0xD4,
            AddressListValueType.Float));
        
        // 装甲
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "盔甲 - 数量",
            _newChildrenArgs.getThisUnitAddress() + 0xE0,
            AddressListValueType.Float));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "盔甲 - 种类",
            _newChildrenArgs.getThisUnitAddress() + 0xE4,
            AddressListValueType.Integer));
        
        // 移动速度
        long moveSpeedAddress = _newChildrenArgs.getThisUnitAddress() + 0x70;
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "移动速度",
            moveSpeedAddress,
            AddressListValueType.Float));
        
        // 坐标
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "坐标 - X",
            _newChildrenArgs.getThisUnitAddress() + 0x100,
            AddressListValueType.Float));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "坐标 - Y",
            _newChildrenArgs.getThisUnitAddress() + 0x104,
            AddressListValueType.Float));
    }
}