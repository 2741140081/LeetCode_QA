package com.marks.tools.war3;

import com.marks.tools.war3.service.ITrainerNode;

import java.util.EventListener;

/**
 * 训练器节点抽象类
 */
public abstract class TrainerNode implements ITrainerNode {
    protected int _nodeIndex;
    protected int _parentIndex;
    protected GameContext _gameContext;
    protected NewChildrenEventArgs _newChildrenArgs;
    
    public TrainerNode(int nodeIndex, GameContext gameContext, NewChildrenEventArgs args) {
        _nodeIndex = nodeIndex;
        _gameContext = gameContext;
        _newChildrenArgs = args.clone();
        _parentIndex = args.getParentNodeIndex();
    }
    
    // 子节点事件
    public interface NewChildrenListener extends EventListener {
        void onNewChildren(Object source, NewChildrenEventArgs args);
    }
    
    // 地址事件
    public interface NewAddressListener extends EventListener {
        void onNewAddress(Object source, NewAddressListEventArgs args);
    }
    
    private NewChildrenListener newChildrenListener;
    private NewAddressListener newAddressListener;
    
    public void setNewChildrenListener(NewChildrenListener listener) {
        this.newChildrenListener = listener;
    }
    
    public void setNewAddressListener(NewAddressListener listener) {
        this.newAddressListener = listener;
    }
    
    protected void createChild(TrainerNodeType nodeType, int parentIndex) {
        if (newChildrenListener != null) {
            NewChildrenEventArgs args = new NewChildrenEventArgs(this);
            args.setNodeType(nodeType);
            args.setParentNodeIndex(parentIndex);
            newChildrenListener.onNewChildren(this, args);
        }
    }
    
    protected void createAddress(NewAddressListEventArgs args) {
        if (newAddressListener != null) {
            newAddressListener.onNewAddress(this, args);
        }
    }
}