package myRabbitMQ.work;

import com.rabbitmq.client.*;
import myRabbitMQ.bean.Book;
import myRabbitMQ.utils.ConnectionUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.TimeoutException;

public class ReceiveObject {
    private static final String QUEUE_NAME="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection=ConnectionUtils.getConnection();
       Channel channel=connection.createChannel();
       channel.queueDeclare(QUEUE_NAME,false,false,false,null);
       DefaultConsumer consumer=new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//               String msg=new String(body,"utf-8");
               ByteArrayInputStream bin=new ByteArrayInputStream(body);
               ObjectInputStream ois=new ObjectInputStream(bin);
               Book book = null;
               try {
                   book = (Book) ois.readObject();
               } catch (ClassNotFoundException e) {
                   e.printStackTrace();
               }
               System.out.println(book);
//               System.out.println("[Re1]receive "+msg);
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
