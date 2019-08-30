package myRabbitMQ.work;

import com.rabbitmq.client.*;
import myRabbitMQ.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive1 {
    private static final String QUEUE_NAME="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection=ConnectionUtils.getConnection();
       Channel channel=connection.createChannel();
       channel.queueDeclare(QUEUE_NAME,false,false,false,null);
       DefaultConsumer consumer=new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               String msg=new String(body,"utf-8");
               System.out.println("[Re1]receive "+msg);
               try {
                   Thread.sleep(2000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }finally {
                   System.out.println("[Re1]done");
               }
           }
       };
       channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
