package dexecutor.taskprovider;

import com.github.dexecutor.core.task.Task;
import com.github.dexecutor.core.task.TaskProvider;
import mq.entity.GraphNode;

/**
 *
 * 使用随机执行的时间来替换类
 *
 * @Author: fansy
 * @Time: 2019/4/24 16:08
 * @Email: fansy1990@foxmail.com
 */
public class SleepyTaskProvider implements TaskProvider<GraphNode, String> {

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