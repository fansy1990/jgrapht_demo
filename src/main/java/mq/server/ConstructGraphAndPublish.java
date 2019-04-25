package mq.server;

import com.github.dexecutor.core.ExecutionConfig;
import com.github.dexecutor.core.MoreInfoExecutor;
import dexecutor.taskprovider.ClassNameTaskProvider;
import mq.entity.GraphNode;

import static mq.utils.DexecutorUtils.constructDAG;
import static mq.utils.DexecutorUtils.newTaskExecutor;

/**
 * 模仿后台代码，发送任务状态
 * @Author: fansy
 * @Time: 2019/4/24 17:26
 * @Email: fansy1990@foxmail.com
 */
public class ConstructGraphAndPublish {
    public static void main(String[] args) {
        // 1. 构造DAG，真实情况应该是从前端获得
        MoreInfoExecutor<GraphNode, String> executor = constructDAG(newTaskExecutor(new ClassNameTaskProvider()));

        // 2. 执行程序，执行过程中会发送消息到Rabbit MQ监听
        executor.execute(ExecutionConfig.TERMINATING);

    }
}
