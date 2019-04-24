package dexecutor.jobs;

import mq.entity.GraphNode;

/**
 * 执行周期不到1秒
 * @Author: fansy
 * @Time: 2019/4/24 16:24
 * @Email: fansy1990@foxmail.com
 */
public class ShortTimeJob implements IJob{

    @Override
    public void run(GraphNode node) {
        try {

            Thread.sleep(Math.round(Math.random()*1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
