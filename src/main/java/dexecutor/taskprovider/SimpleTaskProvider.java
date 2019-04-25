package dexecutor.taskprovider;

import com.github.dexecutor.core.task.Task;
import com.github.dexecutor.core.task.TaskLamba;
import com.github.dexecutor.core.task.TaskProvider;
import mq.entity.GraphNode;

/**
 *
 * 简单TaskProvider
 *
 * @Author: fansy
 * @Time: 2019/4/24 16:08
 * @Email: fansy1990@foxmail.com
 */
public class SimpleTaskProvider implements TaskProvider<GraphNode, String> {

    public Task<GraphNode, String> provideTask(final GraphNode id) {

        return new TaskLamba<GraphNode, String>  (() -> id.getId());
    }
}