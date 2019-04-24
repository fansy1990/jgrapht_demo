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
import dag.graph_schdule.Demo;
import mq.entity.GraphNode;
import mq.entity.NodeStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 构建图，并对每个节点绑定consumer
 * author : fanzhe
 * email : fansy1990@foxmail.com
 * date : 2019/4/21 PM9:07.
 */
public class ConstructGraphAndConsumer {

    private static class ConsumerBindTraversarAction<T, R> implements TraversarAction<T, R> {

        private final StringBuilder builder;

        public ConsumerBindTraversarAction(final StringBuilder builder) {
            this.builder = builder;
        }

        @Override
        public void onNode(final Node<T, R> node) {
            builder.append(node).append(node.getInComingNodes()).append(" ");
        }

        @Override
        public void onNewPath(int pathNumber) {
            builder.append("\n").append("Path #").append(pathNumber);
        }

        @Override
        public void onNewLevel(int levelNumber) {
            builder.append("\n");
        }
    }
    public static void main(String[] args){

        MoreInfoExecutor<GraphNode, String> executor = constructDAG();

//        executor.

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

    /**
     * Path #0
     1[]
     2[1] 3[1]
     7[2] 9[2] 8[2] 5[3] 6[3]
     10[9]
     Path #1
     11[]
     Path #2
     12[]
     13[12]
     4[3, 13] 14[13]
     * 构造树
     * @return
     */
    public static MoreInfoExecutor<GraphNode, String> constructDAG(){
        MoreInfoExecutor<GraphNode, String> executor = newTaskExecutor();
        GraphNode[] graphNodes = new GraphNode[14];

        for( int i =1 ;i< graphNodes.length+1 ; i++){
            graphNodes[i-1] = get(i);
        }

        executor.addDependency(get(graphNodes,1), get(graphNodes,2));
        executor.addDependency(get(graphNodes,1), get(graphNodes,3));
        executor.addDependency(get(graphNodes,3), get(graphNodes,4));
        executor.addDependency(get(graphNodes,3), get(graphNodes,5));
        executor.addDependency(get(graphNodes,3), get(graphNodes,6));

        executor.addDependency(get(graphNodes,2), get(graphNodes,7));
        executor.addDependency(get(graphNodes,2), get(graphNodes,9));
        executor.addDependency(get(graphNodes,2), get(graphNodes,8));
        executor.addDependency(get(graphNodes,9), get(graphNodes,10));
        executor.addDependency(get(graphNodes,12), get(graphNodes,13));
        executor.addDependency(get(graphNodes,13), get(graphNodes,4));
        executor.addDependency(get(graphNodes,13), get(graphNodes,14));
        executor.addIndependent(get(graphNodes,11));
        return executor;
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

    private static GraphNode get(int i){
        Map<String,String> args = new HashMap<>();
        args.put("classname", "org."+String.valueOf(i));

        return new GraphNode(NodeStatus.INIT, args, String.valueOf(i));
    }

    private static GraphNode get(GraphNode[] graphNodes, int i){
        if(i < graphNodes.length+1){
            return graphNodes[i-1];
        }
        return null;
    }
}
