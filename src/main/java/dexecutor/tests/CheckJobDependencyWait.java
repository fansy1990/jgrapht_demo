package dexecutor.tests;

import com.github.dexecutor.core.ExecutionConfig;
import com.github.dexecutor.core.MoreInfoExecutor;
import dexecutor.taskprovider.ClassNameTaskProvider;
import mq.entity.GraphNode;

import static mq.utils.DexecutorUtils.constructDAG;
import static mq.utils.DexecutorUtils.newTaskExecutor;

/**
 * 当前任务依赖两个节点，其中一个节点运行完成，是否会触发当前任务？
 * @Author: fansy
 * @Time: 2019/4/24 16:00
 * @Email: fansy1990@foxmail.com
 */
public class CheckJobDependencyWait {
    public static void main(String[] args) {
        MoreInfoExecutor<GraphNode, String> executor = constructDAG(newTaskExecutor(new ClassNameTaskProvider()));

        executor.execute(ExecutionConfig.TERMINATING);

        System.exit(0);
    }
}
