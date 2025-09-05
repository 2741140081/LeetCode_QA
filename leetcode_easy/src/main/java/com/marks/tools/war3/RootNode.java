package com.marks.tools.war3;

import com.marks.tools.war3.service.ITrainerNode;

/**
 * 根节点类
 */
public class RootNode extends TrainerNode implements ITrainerNode {
    public RootNode(int nodeIndex, GameContext gameContext, NewChildrenEventArgs args) {
        super(nodeIndex, gameContext, args);
    }

    @Override
    public TrainerNodeType getNodeType() {
        return TrainerNodeType.Root;
    }

    @Override
    public String getNodeTypeName() {
        return "所有功能";
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
        // This function will fill the following value(s):
        //      _newChildrenArgs.ThisGame
        //      _newChildrenArgs.ThisGameMemory
        War3Common.getGameMemory(_gameContext, _newChildrenArgs);

        createChild(TrainerNodeType.Introduction, _nodeIndex);
        createChild(TrainerNodeType.Cash, _nodeIndex);
        createChild(TrainerNodeType.AllSelectedUnits, _nodeIndex);
    }
}