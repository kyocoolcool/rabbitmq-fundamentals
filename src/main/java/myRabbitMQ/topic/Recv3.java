package myRabbitMQ.topic;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.*;
import myRabbitMQ.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv3 {
	private static final String EXCHANGE_NAME = "test_exchange_topic";
	private static final String QUEUE_NAME = "test_queue_topic_3";

	public static void main(String[] args) throws IOException, TimeoutException {

		Connection connection = ConnectionUtils.getConnection();
		final Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "goods.add");
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "goods.update");
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "goods.delete");

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
