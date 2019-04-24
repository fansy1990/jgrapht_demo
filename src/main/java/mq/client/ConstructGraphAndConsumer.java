package mq.client;

import com.github.dexecutor.core.DexecutorConfig;
import com.github.dexecutor.core.MoreInfoExecutor;
import com.github.dexecutor.core.graph.LevelOrderTraversar;
import com.github.dexecutor.core.graph.Node;
import com.github.dexecutor.core.graph.StringTraversarAction;
import com.github.dexecutor.core.graph.TraversarAction;
import com.github.dexecutor.core.support.ThreadPoolUtil;
import com.github.dexecutor.core.task.Task;
import com.github.dexecutor.core.task.TaskProvider;
import mq.entity.GraphNode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static mq.utils.DexecutorUtils.constructDAG;

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

        public ConsumerBindTraversarAction() {

        }

        @Override
        public void onNode(final Node<T, R> node) {
            GraphNode t =null;
            if(node.getValue()  instanceof GraphNode ){
                t = (GraphNode) node.getValue();
                System.out.print(t.getId() + "、");
            }

//            for( Node next : node.getInComingNodes()){
//                if(next.getValue()  instanceof GraphNode ){
//                    t = (GraphNode) next.getValue();
//                    System.out.print(t.getId() + "、");
//                }
//            }

        }

        @Override
        public void onNewPath(int pathNumber) {

            System.out.println("\n Path #" + pathNumber);
        }

        @Override
        public void onNewLevel(int levelNumber) {

            System.out.println();;
        }
    }
    public static void main(String[] args){

        MoreInfoExecutor<GraphNode, String> executor = constructDAG(newTaskExecutor());

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


    private static ExecutorService newExecutor() {
        return Executors.newFixedThreadPool(ThreadPoolUtil.ioIntesivePoolSize());
    }

    private static MoreInfoExecutor<GraphNode, String> newTaskExecutor() {
        ExecutorService executorService = newExecutor();

        DexecutorConfig<GraphNode, String> config = new DexecutorConfig<>(executorService, new SleepyTaskProvider());
        MoreInfoExecutor<GraphNode, String> executor = new MoreInfoExecutor<>(config);


        return executor;
    }

    private static class SleepyTaskProvider implements TaskProvider<GraphNode, String> {

        public Task<GraphNode, String> provideTask(final GraphNode id) {

            return new Task<GraphNode, String>() {

                public String execute() {
                    try {
                        long sleepTime = 500 * Math.round(Math.random() * 10);
                        Thread.sleep(sleepTime);
//                        log.info("task id :{}, run time :{}", id, sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return id.getId();
                }
            };
        }
    }


}
