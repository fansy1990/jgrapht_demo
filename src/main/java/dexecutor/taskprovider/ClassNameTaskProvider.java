package dexecutor.taskprovider;

import com.github.dexecutor.core.task.Task;
import com.github.dexecutor.core.task.TaskProvider;
import dexecutor.jobs.IJob;
import mq.entity.GraphNode;
import util.ReflectUtils;

/**
 *
 * 使用参数ClassName来执行
 *
 * @Author: fansy
 * @Time: 2019/4/24 16:08
 * @Email: fansy1990@foxmail.com
 */
public class ClassNameTaskProvider implements TaskProvider<GraphNode, String> {

    public Task<GraphNode, String> provideTask(final GraphNode id) {

        return new Task<GraphNode, String>() {
            public String execute() {
                IJob job = ReflectUtils.getJobInstance(id.getArgs().get("CLASSNAME"));
                job.run(id);
                return id.getId();
            }
        };
    }
}