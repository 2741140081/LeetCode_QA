package com.marks.tools.war3;

import com.marks.tools.war3.service.ITrainerNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏训练器主类
 */
public class GameTrainer {
    private List<ITrainerNode> _allTrainers;
    private List<NodeAddressList> _allAddress;
    private final Object _addressLock = new Object(); // 用于同步_allAddress的锁对象
    private GameContext _gameContext;
    
    public GameTrainer(GameContext gameContext) {
        _gameContext = gameContext;
        _allTrainers = new ArrayList<>();
        _allAddress = new ArrayList<>();
        
        // 创建根节点
        NewChildrenEventArgs args = new NewChildrenEventArgs(this);
        args.setParentNodeIndex(-1);
        ITrainerNode rootNode = createNode(TrainerNodeType.Root, 0, args);
        _allTrainers.add(rootNode);
        
        // 展开根节点的子节点
        rootNode.createChildren();
    }
    
    public List<ITrainerNode> getAllTrainers() {
        return _allTrainers;
    }
    
    public List<NodeAddressList> getAllAddress() {
        return _allAddress;
    }
    
    private ITrainerNode createNode(TrainerNodeType nodeType, int newNodeIndex, NewChildrenEventArgs e) {
        ITrainerNode newNode;
        
        switch (nodeType) {
            case Root:
                newNode = new RootNode(newNodeIndex, _gameContext, e);
                break;
            case Introduction:
                newNode = new IntroductionNode(newNodeIndex, _gameContext, e);
                break;
            case Cash:
                newNode = new CashNode(newNodeIndex, _gameContext, e);
                break;
            case AllSelectedUnits:
                newNode = new AllSelectedUnitsNode(newNodeIndex, _gameContext, e);
                break;
            case OneSelectedUnit:
                newNode = new OneSelectedUnitNode(newNodeIndex, _gameContext, e);
                break;
            case AttackAttributes:
                newNode = new AttackAttributesNode(newNodeIndex, _gameContext, e);
                break;
            case HeroAttributes:
                newNode = new HeroAttributesNode(newNodeIndex, _gameContext, e);
                break;
            case AllItems:
                newNode = new AllItemsNode(newNodeIndex, _gameContext, e);
                break;
            case OneItem:
                newNode = new OneItemNode(newNodeIndex, _gameContext, e);
                break;
            default:
                throw new IllegalArgumentException("Unsupported node type: " + nodeType);
        }
        
        // 注册事件监听器
        if (newNode instanceof TrainerNode) {
            TrainerNode trainerNode = (TrainerNode) newNode;
            trainerNode.setNewChildrenListener(this::newChildrenEventReaction);
            trainerNode.setNewAddressListener(this::newAddressEventReaction);
        }
        
        return newNode;
    }
    
    private void newChildrenEventReaction(Object source, NewChildrenEventArgs e) {
        synchronized (_allTrainers) {
            int newNodeIndex = _allTrainers.size();
            ITrainerNode newNode = createNode(e.getNodeType(), newNodeIndex, e);
            _allTrainers.add(newNode);
            
            // 自动展开新节点的子节点
            newNode.createChildren();
        }
    }
    
    private void newAddressEventReaction(Object source, NewAddressListEventArgs e) {
        synchronized (_addressLock) {
            _allAddress.add(new NodeAddressList(e));
        }
    }
}