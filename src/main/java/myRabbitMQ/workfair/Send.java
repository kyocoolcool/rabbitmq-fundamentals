package myRabbitMQ.workfair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import myRabbitMQ.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String QUEUE_NAME="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //獲取連結
        Connection connection=ConnectionUtils.getConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //每個cousumer發送確認消息之前，消息隊列不再發送消息到consumer，一次只處理一個訊息
        //限制發送同一個consumer，不能超過一條訊息
        int prefetchcount=1;
        channel.basicQos(prefetchcount);
        for(int i=0;i<50;i++){
            String msg="Hello RabbitMQ "+i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            Thread.sleep(i*20);
            System.out.println("[wq]send msg "+i+"  "+msg);
        }
        channel.close();
        connection.close();

    }
}
