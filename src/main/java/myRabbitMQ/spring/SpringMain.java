package myRabbitMQ.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {
	
	public static void main(final String... args) throws Exception {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:context.xml");
		//RabbitMQ模板
		
		RabbitTemplate template = ctx.getBean(RabbitTemplate.class);
		//發送消息
		template.convertAndSend("Hello, world!");
		Thread.sleep(1000);// 睡1秒
		ctx.destroy(); //容器銷毀
	}

}
