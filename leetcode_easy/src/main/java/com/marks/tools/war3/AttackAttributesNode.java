package com.marks.tools.war3;

import com.marks.tools.war3.entity.AddressListValueType;
import com.marks.tools.war3.service.ITrainerNode;

/**
 * 战斗属性节点类
 */
public class AttackAttributesNode extends TrainerNode implements ITrainerNode {
    
    public AttackAttributesNode(int nodeIndex, GameContext gameContext, NewChildrenEventArgs args) {
        super(nodeIndex, gameContext, args);
    }

    @Override
    public TrainerNodeType getNodeType() {
        return TrainerNodeType.AttackAttributes;
    }

    @Override
    public String getNodeTypeName() {
        return "战斗属性";
    }

    @Override
    public int getNodeIndex() {
        return _nodeIndex;
    }

    @Override
    public int getParentIndex() {
        return _parentIndex;
    }

    @Override
    public void createChildren() {
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "攻击频率比",
            _newChildrenArgs.getAttackAttributesAddress() + 0x1B0,
            AddressListValueType.Float));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "主动攻击范围",
            _newChildrenArgs.getAttackAttributesAddress() + 0x244,
            AddressListValueType.Float));
        
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "攻击1 - 倍乘",
            _newChildrenArgs.getAttackAttributesAddress() + 0x88 + 0 * 4,
            AddressListValueType.Integer));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "攻击1 - 骰子",
            _newChildrenArgs.getAttackAttributesAddress() + 0x94 + 0 * 4,
            AddressListValueType.Integer));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "攻击1 - 面数",
            _newChildrenArgs.getAttackAttributesAddress() + 0xA0 + 0 * 4,
            AddressListValueType.Integer));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "攻击1 - 基础伤害",
            _newChildrenArgs.getAttackAttributesAddress() + 0x7C + 0 * 4,
            AddressListValueType.Integer));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "攻击1 - 附加伤害",
            _newChildrenArgs.getAttackAttributesAddress() + 0xAC + 0 * 4,
            AddressListValueType.Integer));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "攻击1 - 攻击间隔",
            _newChildrenArgs.getAttackAttributesAddress() + 0xC4 + 0 * 4,
            AddressListValueType.Float));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "攻击1 - 射程",
            _newChildrenArgs.getAttackAttributesAddress() + 0x130 + 0 * 4,
            AddressListValueType.Float));
    }
}