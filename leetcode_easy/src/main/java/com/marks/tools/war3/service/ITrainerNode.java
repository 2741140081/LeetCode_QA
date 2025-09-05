package com.marks.tools.war3.service;

import com.marks.tools.war3.TrainerNodeType;

/**
 * 训练器节点接口
 */
public interface ITrainerNode {
    TrainerNodeType getNodeType();
    String getNodeTypeName();
    int getNodeIndex();
    int getParentIndex();
    void createChildren();
}