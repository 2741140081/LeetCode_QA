package com.marks.tools.war3;

import com.marks.tools.war3.service.ITrainerNode;

/**
 * 魔兽争霸3训练器主程序入口
 */
public class War3TrainerMain {
    public static void main(String[] args) {
        System.out.println("魔兽争霸3 内存训练器 (Java版本)");
        System.out.println("注意：此为示例程序，实际使用需要实现Windows进程内存访问功能");
        
        // 添加FindGame功能
        GameContext context = findGame();
        
        if (context != null) {
            System.out.println("找到游戏进程，PID: " + context.getProcessId() + "，版本: " + context.getProcessVersion());
            
            // 创建训练器
            GameTrainer trainer = new GameTrainer(context);
            
            // 显示功能树
            System.out.println("\n功能树:");
            for (ITrainerNode node : trainer.getAllTrainers()) {
                // 缩进显示层级关系
                StringBuilder indent = new StringBuilder();
                int parentIndex = node.getParentIndex();
                while (parentIndex >= 0) {
                    indent.append("  ");
                    parentIndex = parentIndex > 0 ? trainer.getAllTrainers().get(parentIndex).getParentIndex() : -1;
                }
                
                System.out.println(indent.toString() + "- " + node.getNodeTypeName());
            }
            
            // 显示地址列表
            System.out.println("\n可修改地址列表:");
            for (NodeAddressList addr : trainer.getAllAddress()) {
                System.out.println("  - " + addr.getName() + " (地址: 0x" + 
                    Long.toHexString(addr.getAddress()).toUpperCase() + ")");
            }
        } else {
            System.out.println("未找到游戏进程，请确保魔兽争霸3正在运行");
        }
        
        System.out.println("\n程序运行完成");
    }
    
    /**
     * 查找游戏进程，使用War3ProcessHandler实现
     * @return GameContext 游戏上下文对象，如果未找到则返回null
     */
    private static GameContext findGame() {
        // 使用War3ProcessHandler查找游戏进程
        War3ProcessHandler handler = War3ProcessHandler.findGameRunning("war3", "game.dll");
        if (handler == null) {
            // 尝试查找网易对战平台版本
            handler = War3ProcessHandler.findGameRunning("dzwar3", "game.dll");
        }
        
        if (handler != null) {
            // 如果找到游戏进程，创建一个游戏上下文
            GameContext context = new GameContext(handler.getProcessId(), handler.getProcessVersion());
            context.setThisGameAddress(handler.getThisGameAddress());
            context.setUnitListAddress(handler.getUnitListAddress());
            context.setMoveSpeedAddress(handler.getMoveSpeedAddress());
            context.setAttackAttributesOffset(handler.getAttackAttributesOffset());
            context.setHeroAttributesOffset(handler.getHeroAttributesOffset());
            context.setItemsListOffset(handler.getItemsListOffset());
            return context;
        }
        
        // 如果没有找到游戏进程，返回null
        return null;
    }
}