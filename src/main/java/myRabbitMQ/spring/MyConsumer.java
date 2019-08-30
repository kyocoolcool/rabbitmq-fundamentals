package myRabbitMQ.spring;


public class MyConsumer {

	
	   //具體執行業務方法
    public void listen(String foo) {
        System.out.println("消费者： " + foo);
    }
	
}
