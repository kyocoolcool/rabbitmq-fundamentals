package myRabbitMQ.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import myRabbitMQ.utils.ConnectionUtils;

public class Send {
	private static final String EXCHANGE_NAME = "test_exchange_topic";

	public static void main(String[] args) throws IOException, TimeoutException {
		
		
		Connection connection = ConnectionUtils.getConnection();
		
		Channel channel = connection.createChannel();
		
		//exchange
		channel.exchangeDeclare(EXCHANGE_NAME, "topic",true);
		
		String msgString="商品....";
		channel.basicPublish(EXCHANGE_NAME, "goods.delete", MessageProperties.PERSISTENT_TEXT_PLAIN, msgString.getBytes());
		System.out.println("---send "+msgString);

		channel.close();
		connection.close();
	}
}
