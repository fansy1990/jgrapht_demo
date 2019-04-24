package dexecutor.jobs;

import mq.entity.GraphNode;

/**
 * @Author: fansy
 * @Time: 2019/4/24 16:23
 * @Email: fansy1990@foxmail.com
 */
public interface IJob {
    void run(GraphNode node);
}
