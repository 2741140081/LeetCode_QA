package com.marks.tools.war3;

import com.marks.tools.war3.entity.AddressListValueType;
import com.marks.tools.war3.service.ITrainerNode;

/**
 * 英雄属性节点类
 */
public class HeroAttributesNode extends TrainerNode implements ITrainerNode {
    
    public HeroAttributesNode(int nodeIndex, GameContext gameContext, NewChildrenEventArgs args) {
        super(nodeIndex, gameContext, args);
    }

    @Override
    public TrainerNodeType getNodeType() {
        return TrainerNodeType.HeroAttributes;
    }

    @Override
    public String getNodeTypeName() {
        return "英雄属性";
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
            "经验值",
            _newChildrenArgs.getHeroAttributesAddress() + 0x8C,
            AddressListValueType.Integer));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "力量",
            _newChildrenArgs.getHeroAttributesAddress() + 0x94,
            AddressListValueType.Integer));
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "敏捷",
            _newChildrenArgs.getHeroAttributesAddress() + 0xA8,
            AddressListValueType.Integer));
        
        // 智力需要特殊处理
        long tmpAddress1 = _newChildrenArgs.getHeroAttributesAddress() + 0x100; // 模拟智力地址
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "智力",
            tmpAddress1,
            AddressListValueType.Integer));
        
        createAddress(new NewAddressListEventArgs(_nodeIndex,
            "可用技能点",
            _newChildrenArgs.getHeroAttributesAddress() + 0x90,
            AddressListValueType.Integer));

        for (long learningAbilityIndex = 1; learningAbilityIndex <= 5; learningAbilityIndex++) {
            createAddress(new NewAddressListEventArgs(_nodeIndex,
                "学习技能" + learningAbilityIndex + " - 名称",
                _newChildrenArgs.getHeroAttributesAddress() + 0xF0 + learningAbilityIndex * 4,
                AddressListValueType.Char4));
            createAddress(new NewAddressListEventArgs(_nodeIndex,
                "学习技能" + learningAbilityIndex + " - 等级",
                _newChildrenArgs.getHeroAttributesAddress() + 0x108 + learningAbilityIndex * 4,
                AddressListValueType.Integer));
            createAddress(new NewAddressListEventArgs(_nodeIndex,
                "学习技能" + learningAbilityIndex + " - 要求",
                _newChildrenArgs.getHeroAttributesAddress() + 0x120 + learningAbilityIndex * 4,
                AddressListValueType.Integer));
        }
    }
}