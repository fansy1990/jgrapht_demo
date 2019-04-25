package dexecutor.traverseaction;

import com.github.dexecutor.core.graph.Node;
import com.github.dexecutor.core.graph.TraversarAction;
import mq.entity.GraphNode;
import mq.utils.MQUtils;

import java.io.IOException;

/**
 * 循环遍历Node，并逐个添加监听
 * @Author: fansy
 * @Time: 2019/4/25 10:39
 * @Email: fansy1990@foxmail.com
 */
public class ConsumerBindTraversarAction<T, R> implements TraversarAction<T, R> {
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
