package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * author : fanzhe
 * email : fansy1990@foxmail.com
 * date : 2019/4/21 AM11:16.
 */
public class Send {
//    private final static String QUEUE_NAME = "hello";
    private final static String QUEUE_NAME = "mqtt-subscription-myclientid_qos1";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("fansy");
        factory.setPassword("fansy");

        int sleepTime = 3000 ;

        int msgSize = 10;

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueDeclare(QUEUE_NAME, true, false, true, null);
            String message = "Hello World!";
            String realMessage = null;
            long realSleepTime = 0;
            for( int i =0 ;i< msgSize ; i++) {
                try{
                    realSleepTime = Math.round(sleepTime * Math.random());
                    Thread.sleep(realSleepTime);
                }catch (Exception e){

                }
                realMessage = i +": " +message + Math.random();
                channel.basicPublish("", QUEUE_NAME, null, realMessage.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + realMessage + "'");
            }
            channel.basicPublish("", QUEUE_NAME, null, "DONE".getBytes("UTF-8"));
        }


    }
}
