package mq.utils;

import com.github.dexecutor.core.DexecutorConfig;
import com.github.dexecutor.core.MoreInfoExecutor;
import com.github.dexecutor.core.support.ThreadPoolUtil;
import com.github.dexecutor.core.task.TaskProvider;
import mq.entity.GraphNode;
import mq.entity.NodeStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: fansy
 * @Time: 2019/4/24 16:01
 * @Email: fansy1990@foxmail.com
 */
public class DexecutorUtils {

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
    public static MoreInfoExecutor<GraphNode, String> constructDAG(MoreInfoExecutor<GraphNode, String> executor ){
//         = ();
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

    private static GraphNode get(int i){
        Map<String,String> args = new HashMap<>();
        if(i == 3) {
            args.put("CLASSNAME", "dexecutor.jobs.LongTimeJob");
        }else{
            args.put("CLASSNAME", "dexecutor.jobs.ShortTimeJob");
        }
        return new GraphNode(NodeStatus.INIT, args, String.valueOf(i));
    }

    private static GraphNode get(GraphNode[] graphNodes, int i){
        if(i < graphNodes.length+1){
            return graphNodes[i-1];
        }
        return null;
    }


    /**
     * 返回Executor
     * @param taskProvider
     * @return
     */
    public static MoreInfoExecutor<GraphNode, String> newTaskExecutor(TaskProvider<GraphNode,String> taskProvider) {
        ExecutorService executorService = Executors.newFixedThreadPool(ThreadPoolUtil.ioIntesivePoolSize());
        DexecutorConfig<GraphNode, String> config = new DexecutorConfig<>(executorService, taskProvider);
        MoreInfoExecutor<GraphNode, String> executor = new MoreInfoExecutor<>(config);

        return executor;
    }

}
