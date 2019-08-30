package myRabbitMQ.confirm;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import myRabbitMQ.utils.ConnectionUtils;

/**
 * @author Chris
 *普通模式
 */
public class Send2 {
	private static final String QUEUE_NAME="test_queue_confirm1";
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME,false,false,false,null);
		
		//生產者調用confirmSelect，將channel設置為confirm模式
		channel.confirmSelect();
		
		String msgString="hello confirm message batch!";
		//批量發送
		for (int i = 0; i < 10; i++) {
			channel.basicPublish("", QUEUE_NAME, null,msgString.getBytes());
		}
		
		//確認
		if(!channel.waitForConfirms()){
			System.out.println("message send failed");
		}else {
			System.out.println("message send ok");
		}
		
		channel.close();
		connection.close();
	}
}
