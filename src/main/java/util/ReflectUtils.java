package util;

import dexecutor.jobs.IJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: fansy
 * @Time: 2019/4/24 16:28
 * @Email: fansy1990@foxmail.com
 */
public class ReflectUtils {
    private static final Logger log = LoggerFactory.getLogger(ReflectUtils.class);
    public static IJob getJobInstance(String className) {
        try {
            Class<?> cls = Class.forName(className);
            // 调用newInstance方法创建Person类对象
            Object p = cls.newInstance();
            if (p instanceof IJob) {
                return (IJob) p;
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
}
