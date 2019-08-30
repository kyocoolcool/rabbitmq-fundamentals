package myRabbitMQ.workfair;

import com.rabbitmq.client.*;
import myRabbitMQ.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive2 {
    private static final String QUEUE_NAME="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection=ConnectionUtils.getConnection();
       final Channel channel=connection.createChannel();
       channel.queueDeclare(QUEUE_NAME,false,false,false,null);
       channel.basicQos(1);
       DefaultConsumer consumer=new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               String msg=new String(body,"utf-8");
               System.out.println("[Re1]receive "+msg);
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }finally {
                   System.out.println("[Re2]done");
                   channel.basicAck(envelope.getDeliveryTag(),false);
               }
           }
       };
        boolean autoAck=false;
       channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
