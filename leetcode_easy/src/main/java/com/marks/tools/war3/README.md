# War3Trainer (Java版魔兽争霸3内存修改器)

这是一个基于Java开发的魔兽争霸3游戏内存修改器示例项目，仿照[C#版本的War3Trainer](https://github.com/tctianchi/War3Trainer)实现。

## 项目介绍

该项目演示了如何使用Java构建一个类似于War3Trainer的游戏内存修改工具的框架结构。它包含了游戏内存访问、数据结构组织、功能模块划分等核心概念。

**注意**: 这只是一个示例项目，实际的功能实现（如Windows进程内存读写）需要额外的JNI/JNA支持。

## 功能特点

1. 游戏进程上下文管理
2. 内存地址组织与管理
3. 功能模块树状结构
4. 游戏资源修改（金币、木材等）
5. 单位属性修改（生命值、魔法值、攻击力等）
6. 英雄属性修改（力量、敏捷、智力等）
7. 物品属性修改

## 项目结构

```
com.marks.tools.war3
├── War3TrainerMain.java        # 程序入口
├── GameContext.java            # 游戏上下文信息
├── GameTrainer.java            # 训练器主控制器
├── War3Common.java             # 游戏通用工具类
├── TrainerNodeType.java        # 功能节点类型枚举
├── AddressListValueType.java   # 地址值类型枚举
├── ITrainerNode.java           # 训练器节点接口
├── TrainerNode.java            # 训练器节点基类
├── 各种功能节点实现类          # 如RootNode, CashNode等
├── 事件参数类                  # NewChildrenEventArgs, NewAddressListEventArgs
└── NodeAddressList.java        # 地址列表项
```

## 使用方法

作为示例项目，可以直接运行[War3TrainerMain](file:///D:/gitProject/LeetCode_QA/leetcode_easy/src/main/java/com/marks/tools/war3/War3TrainerMain.java#L7-L36)类查看功能结构：

```bash
javac com/marks/tools/war3/*.java
java com/marks/tools/war3/War3TrainerMain
```

## 扩展开发

要在实际环境中使用此项目，需要实现以下功能：

1. 使用JNA(Java Native Access)或JNI调用Windows API访问进程内存
2. 实现具体的内存读写方法
3. 根据游戏版本确定正确的内存地址偏移
4. 添加图形用户界面

## 注意事项

1. 游戏内存修改可能违反游戏服务条款
2. 本项目仅用于学习Java设计模式和程序架构
3. 实际使用需要管理员权限
4. 不同游戏版本的内存地址可能不同
5. 某些游戏具有反作弊保护机制

## 学习价值

通过这个项目可以学习到：
1. Java面向对象设计
2. 事件驱动编程
3. 树状数据结构组织
4. 设计模式应用（工厂模式、观察者模式等）
5. Java与原生代码交互基础