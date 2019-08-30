package myRabbitMQ.routing;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import myRabbitMQ.utils.ConnectionUtils;

public class Recv1 {
	private static final String EXCHANGE_NAME = "test_exchange_direct";
	private static final String QUEUE_NAME = "test_queue_direct_1";

	public static void main(String[] args) throws IOException, TimeoutException {

		Connection connection = ConnectionUtils.getConnection();
		final Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
	
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");
		
		channel.basicQos(1);
		
		//定義一個消費者
		Consumer consumer=new DefaultConsumer(channel){
			//消息到達觸發這個方法
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
			 
				String msg=new String(body,"utf-8");
				System.out.println("[1] Recv msg:"+msg);
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					System.out.println("[1] done ");
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		
		boolean autoAck=false;//自動應答 false
		channel.basicConsume(QUEUE_NAME,autoAck , consumer);
	}
		
}
