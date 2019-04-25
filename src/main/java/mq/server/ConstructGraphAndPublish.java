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
        MoreInfoExecutor<GraphNode, String> executor = constructDAG(newTaskExecutor(new ClassNameTaskProvider()));

        // 执行程序
        executor.execute(ExecutionConfig.TERMINATING);

    }
}
