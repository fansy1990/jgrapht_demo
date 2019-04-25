package mq.client;

import com.github.dexecutor.core.MoreInfoExecutor;
import com.github.dexecutor.core.graph.LevelOrderTraversar;
import dexecutor.taskprovider.SimpleTaskProvider;
import dexecutor.traverseaction.ConsumerBindTraversarAction;
import mq.entity.GraphNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static mq.utils.DexecutorUtils.constructDAG;
import static mq.utils.DexecutorUtils.newTaskExecutor;

/**
 * 构建图，并对每个节点绑定consumer
 *
 * 相当于 前端
 * author : fanzhe
 * email : fansy1990@foxmail.com
 * date : 2019/4/21 PM9:07.
 */
public class ConstructGraphAndConsumer {
    private static final Logger log = LoggerFactory.getLogger(ConstructGraphAndConsumer.class);

    public static void main(String[] args){
        // 1. 构造DAG
        MoreInfoExecutor<GraphNode, String> executor = constructDAG(newTaskExecutor(new SimpleTaskProvider()));
        // 2. 为DAG每个节点添加监听；
        executor.bind(new LevelOrderTraversar<GraphNode, String>(),new ConsumerBindTraversarAction<GraphNode,String>());
        log.info("Binding Finished ! ");

    }

}
