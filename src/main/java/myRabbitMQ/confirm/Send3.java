package myRabbitMQ.confirm;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import myRabbitMQ.utils.ConnectionUtils;

public class Send3 {
	private static final String QUEUE_NAME="test_queue_confirm3";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME,false,false,false,null);
		
		//生產者調用confirmSelect，將channel設置為confirm模式
		channel.confirmSelect();
		
		//未確認的消息標示
		final SortedSet<Long> confirmSet=Collections.synchronizedSortedSet(new TreeSet<Long>());
		
		//通道添加監聽
		channel.addConfirmListener(new ConfirmListener() {
		
			//沒有問題的handleAck
			public void handleAck(long deliveryTag, boolean multiple)
					throws IOException {
				if(multiple){
					System.out.println("----handleAck----multiple");
					System.out.println("deliveryTag"+deliveryTag);
					confirmSet.headSet(deliveryTag+1).clear();
				}else{
					System.out.println("----handleAck-----multiple false");
					System.out.println("deliveryTag"+deliveryTag);
					confirmSet.remove(deliveryTag);
				}
			}
			//有問題的觸發handleNack
			//handleNack 
			public void handleNack(long deliveryTag, boolean multiple)
					throws IOException {
				if(multiple){
					System.out.println("---handleNack------multiple");
					confirmSet.headSet(deliveryTag+1).clear();
				}else{
					System.out.println("--handleNack-------multiple false");
					confirmSet.remove(deliveryTag);
				}
			}
		});
		
		String msgStr="ssssss";
		
		while(true){
			long seqNo = channel.getNextPublishSeqNo();
			channel.basicPublish("", QUEUE_NAME, null, msgStr.getBytes());
			confirmSet.add(seqNo);
		}

	}

}
