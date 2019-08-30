package myRabbitMQ.ps;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import myRabbitMQ.utils.ConnectionUtils;

public class Send {

	private static final String  EXCHANGE_NAME="test_exchange_fanout";
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectionUtils.getConnection();
		
		Channel channel = connection.createChannel();
		
		//聲明交換機
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");//分發
		
		//發送消息
		String msg="hello ps";
		
		channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
		
		System.out.println("Send :"+msg);
		
		channel.close();
		connection.close();
	}
}
