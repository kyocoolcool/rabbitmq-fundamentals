package myRabbitMQ.tx;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import myRabbitMQ.utils.ConnectionUtils;

public class TxRecv {

	private static final String QUEUE_NAME="test_queue_tx";
	
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.basicQos(1);
		channel.queueDeclare(QUEUE_NAME,false,false,false,null);
		channel.basicConsume(QUEUE_NAME, false,new DefaultConsumer(channel){
			
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				System.out.println("recv[tx] msg:"+new String(body,"utf-8"));
			}
		});
	}

}
