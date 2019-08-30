package myRabbitMQ.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {
    /**
     * @Author chris
     * @Description //TODO 
     * @Date 11:34 2019-01-19
     * @Param 
     * @return com.rabbitmq.client.Connection
     **/
    public static Connection getConnection() throws IOException, TimeoutException {
        //設置連結工廠
        ConnectionFactory factory=new ConnectionFactory();
        //host
        factory.setHost("127.0.0.1");
        //port
        factory.setPort(5672);
        //virtualHost
        factory.setVirtualHost("/hostUser");
        //userName
        factory.setUsername("user");
        //userPassword
        factory.setPassword("user");

        return factory.newConnection();
    }
}
