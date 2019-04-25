package mq.client;

import com.github.dexecutor.core.MoreInfoExecutor;
import com.github.dexecutor.core.graph.LevelOrderTraversar;
import com.github.dexecutor.core.graph.Node;
import com.github.dexecutor.core.graph.StringTraversarAction;
import com.github.dexecutor.core.graph.TraversarAction;
import dexecutor.taskprovider.SimpleTaskProvider;
import mq.entity.GraphNode;
import mq.utils.MQUtils;

import java.io.IOException;

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

    /**
     * 只需要覆写 onNode接口
     * @param <T>
     * @param <R>
     */
    private static class ConsumerBindTraversarAction<T, R> implements TraversarAction<T, R> {
        @Override
        public void onNode(final Node<T, R> node) {
            GraphNode t =null;
            // declare queue and bind
            if(node.getValue()  instanceof GraphNode ){
                t = (GraphNode) node.getValue();

                // 声明queue,并绑定consumer
                try {
                    MQUtils.bindConsumer(t.getId());
                } catch (IOException e) {

                }

            }

        }

        @Override
        public void onNewPath(int pathNumber) {
        }

        @Override
        public void onNewLevel(int levelNumber) {
        }
    }
    public static void main(String[] args){

        MoreInfoExecutor<GraphNode, String> executor = constructDAG(newTaskExecutor(new SimpleTaskProvider()));

        executor.print(new LevelOrderTraversar<GraphNode, String>(),new ConsumerBindTraversarAction<GraphNode,String>());

    }


    /**
     * 打印树
     * @param executor
     */
    public static void print(MoreInfoExecutor<GraphNode, String> executor){
        StringBuilder builder = new StringBuilder();
        executor.print(new LevelOrderTraversar<GraphNode, String>(), new StringTraversarAction<GraphNode, String>(builder));
        System.out.println(builder.toString());
    }





}
