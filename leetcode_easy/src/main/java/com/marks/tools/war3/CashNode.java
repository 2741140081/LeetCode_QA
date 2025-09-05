package com.marks.tools.war3;

import com.marks.tools.war3.entity.AddressListValueType;
import com.marks.tools.war3.service.ITrainerNode;

/**
 * 游戏资源节点类
 */
public class CashNode extends TrainerNode implements ITrainerNode {
    
    public CashNode(int nodeIndex, GameContext gameContext, NewChildrenEventArgs args) {
        super(nodeIndex, gameContext, args);
    }

    @Override
    public TrainerNodeType getNodeType() {
        return TrainerNodeType.Cash;
    }

    @Override
    public String getNodeTypeName() {
        return "游戏资源";
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
        // 模拟游戏内存地址
        long upperAddress = 0xFFFF0000L;
        
        if (upperAddress == 0) {
            return;
        }

        long[] playerSourceBaseAddress = new long[] {
            0,  // To skip index 0
            0x0190, 0x1410, 0x26A0, 0x3920, 0x4BB0,
            0x5E30, 0x70C0, 0x8350, 0x95D0, 0xA860,
            0xBAE0, 0xCD70
        };

        for (int i = 1; i <= 12; i++) {
            createAddress(new NewAddressListEventArgs(_nodeIndex,
                "P" + i + " - 金",
                upperAddress + playerSourceBaseAddress[i] + 0,
                AddressListValueType.Integer,
                10));
            createAddress(new NewAddressListEventArgs(_nodeIndex,
                "P" + i + " - 木",
                upperAddress + playerSourceBaseAddress[i] + 0x80,
                AddressListValueType.Integer,
                10));
            createAddress(new NewAddressListEventArgs(_nodeIndex,
                "P" + i + " - 最大人口",
                upperAddress + playerSourceBaseAddress[i] + 0x180,
                AddressListValueType.Integer));
            createAddress(new NewAddressListEventArgs(_nodeIndex,
                "P" + i + " - 当前人口",
                upperAddress + playerSourceBaseAddress[i] + 0x200,
                AddressListValueType.Integer));
        }
    }
}