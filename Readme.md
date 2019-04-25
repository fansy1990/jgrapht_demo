# 通用DAG作业调度流程

> 整体思路：
```
1. 前端拖拽任务节点，进行工作流创建；
2. 创建完成后，点击运行，会发送DAG到后台，同时前端绑定Rabbit MQ的监听；
3. 后台向Rabbit MQ发送每个节点任务状态；
```

## 1. 前端拖拽实现

## 2. 后台任务调度实现
参考：[https://github.com/dexecutor/dexecutor-core](https://github.com/dexecutor/dexecutor-core)