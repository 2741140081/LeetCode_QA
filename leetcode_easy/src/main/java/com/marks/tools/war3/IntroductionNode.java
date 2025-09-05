package com.marks.tools.war3;

import com.marks.tools.war3.service.ITrainerNode;

/**
 * 使用说明节点类
 */
public class IntroductionNode extends TrainerNode implements ITrainerNode {
    
    public IntroductionNode(int nodeIndex, GameContext gameContext, NewChildrenEventArgs args) {
        super(nodeIndex, gameContext, args);
    }

    @Override
    public TrainerNodeType getNodeType() {
        return TrainerNodeType.Introduction;
    }

    @Override
    public String getNodeTypeName() {
        return "使用方法";
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
        // 使用说明节点没有子节点
        // TODO: 可以考虑添加使用说明相关的内容作为静态子节点
    }
}