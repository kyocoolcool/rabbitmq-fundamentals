package myRabbitMQ.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import myRabbitMQ.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String QUEUE_NAME="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //獲取連結
        Connection connection=ConnectionUtils.getConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg="Hello RabbitMQ";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.out.println("[simple] send"+msg);

        channel.close();
        connection.close();

    }
}
