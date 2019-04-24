package dexecutor.jobs;

import mq.entity.GraphNode;

/**
 * 执行周期10秒
 * @Author: fansy
 * @Time: 2019/4/24 16:24
 * @Email: fansy1990@foxmail.com
 */
public class LongTimeJob implements IJob{

    @Override
    public void run(GraphNode node) {
        try {

            Thread.sleep(10*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
