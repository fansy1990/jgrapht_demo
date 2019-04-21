package dag.graph_schdule;

import com.github.dexecutor.core.*;
import com.github.dexecutor.core.graph.LevelOrderTraversar;
import com.github.dexecutor.core.graph.Node;
import com.github.dexecutor.core.graph.StringTraversarAction;
import com.github.dexecutor.core.support.ThreadPoolUtil;
import com.github.dexecutor.core.task.Task;
import com.github.dexecutor.core.task.TaskProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * author : fanzhe
 * email : fansy1990@foxmail.com
 * date : 2019/4/20 PM9:11.
 */
public class Demo {
    private static Logger log = LoggerFactory.getLogger(Demo.class);
    public static void main(String[] args) {
        log.info("starting...");
//        seq();
//        par();
        par_seq();
    }

    private static ExecutorService newExecutor() {
        return Executors.newFixedThreadPool(ThreadPoolUtil.ioIntesivePoolSize());
    }

    public static void print(MoreInfoExecutor<Integer,Integer> executor){
        StringBuilder builder = new StringBuilder();
        executor.print(new LevelOrderTraversar<Integer, Integer>(), new StringTraversarAction<Integer, Integer>(builder));
//        log.info("\n", builder.toString());
        System.out.println(builder.toString());
    }

    /**
     * 复杂图
     */
    public static void par_seq(){
        MoreInfoExecutor<Integer, Integer> executor = newTaskExecutor();

        executor.addDependency(1, 2);
        executor.addDependency(1, 2);
        executor.addDependency(1, 3);
        executor.addDependency(3, 4);
        executor.addDependency(3, 5);
        executor.addDependency(3, 6);

        executor.addDependency(2, 7);
        executor.addDependency(2, 9);
        executor.addDependency(2, 8);
        executor.addDependency(9, 10);
        executor.addDependency(12, 13);
        executor.addDependency(13, 4);
        executor.addDependency(13, 14);
        executor.addIndependent(11);
        print(executor);
//        executor.execute(new ExecutionConfig().immediateRetrying(2));
    }

    /**
     * 并行执行
     */
    public static void par(){
        MoreInfoExecutor<Integer, Integer> executor = newTaskExecutor();
        //Building
        executor.addIndependent(1);
        executor.addIndependent(2);
        executor.addIndependent(3);
        executor.addIndependent(4);
        //Execution
        executor.execute(ExecutionConfig.TERMINATING);
    }


    /**
     * 顺序执行
     */
    public static void seq(){
        MoreInfoExecutor<Integer, Integer> executor = newTaskExecutor();
        //Building
        executor.addDependency(1, 2);
        executor.addDependency(2, 3);
        executor.addDependency(3, 4);
        executor.addDependency(4, 5);
        //Execution
        executor.execute(ExecutionConfig.TERMINATING);
    }

    private static MoreInfoExecutor<Integer, Integer> newTaskExecutor() {
        ExecutorService executorService = newExecutor();
//        ExecutionEngine<Integer, Integer> executionEngine = new DefaultExecutionEngine<>(executorService);

        DexecutorConfig<Integer, Integer> config = new DexecutorConfig<>(executorService, new SleepyTaskProvider());
        MoreInfoExecutor<Integer, Integer> executor = new MoreInfoExecutor<>(config);


        return executor;
    }

    private static class SleepyTaskProvider implements TaskProvider<Integer, Integer> {

        public Task<Integer, Integer> provideTask(final Integer id) {

            return new Task<Integer, Integer>() {

                public Integer execute() {
                    try {
                        long sleepTime = 500 * Math.round(Math.random() * 10);
                        Thread.sleep(sleepTime);
                        log.info("task id :{}, run time :{}", id, sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return id;
                }
            };
        }
    }
}
