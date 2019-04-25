package mq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 创建MQ连接
 * author : fanzhe
 * email : fansy1990@foxmail.com
 * date : 2019/4/21 PM9:06.
 */
public class MQUtils {

    private static final Logger log = LoggerFactory.getLogger(MQUtils.class);

    private static final String HOST = "localhost";
    private static final String USER="fansy";
    private static final String PASSWORD = "fansy";
    private static Channel channelSend;
    private static Channel channelRecv;

    public static final String JOB_DONE = "JOB_DONE";

    public static Channel getSendChannel(){
        initChannel();
        return channelSend;
    }

    private static Channel getRecvChannel(){
        initChannel();
        return channelRecv;
    }

    /**
     * 初始化Channel
     * @return
     */
    private static void initChannel(){
        if(channelRecv == null || channelSend == null ) {

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            factory.setUsername(USER);
            factory.setPassword(PASSWORD);
            try {
                channelSend = factory.newConnection().createChannel();
                channelRecv = factory.newConnection().createChannel();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 声明
     * @param channel
     * @param queueName
     * @throws IOException
     */
    public static void declareQueue(Channel channel,String queueName) throws IOException {
        channel.queueDeclare(queueName, false, false, false, null);
    }

    /**
     * 绑定消费者: 声明queue，接着绑定消费者，最后消费消息（就是打印）
     * 只能使用channelRecv
     * @param queueName
     * @throws IOException
     */
    public static void bindConsumer( String queueName) throws IOException {
        // 声明
        declareQueue(getRecvChannel(),queueName);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            if(JOB_DONE.equals(message)){
                getRecvChannel().queueDelete(queueName); // 删除 queue
            }
            log.info("{}  Received : '{}'" ,queueName, message );

        };
        getRecvChannel().basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    /**
     * 发消息
     * @param queueName
     * @param msg
     * @throws IOException
     */
    public static void publishMsg( String queueName,String msg) throws IOException {
        getRecvChannel().basicPublish("", queueName, null, msg.getBytes("UTF-8"));
    }

}
