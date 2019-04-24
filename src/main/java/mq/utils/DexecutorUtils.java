package mq.utils;

import com.github.dexecutor.core.MoreInfoExecutor;
import mq.entity.GraphNode;
import mq.entity.NodeStatus;

import java.util.HashMap;
import java.util.Map;

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
