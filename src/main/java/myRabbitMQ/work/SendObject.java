package myRabbitMQ.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import myRabbitMQ.bean.Book;
import myRabbitMQ.utils.ConnectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeoutException;

public class SendObject {
    private static final String QUEUE_NAME="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //獲取連結


        Book book=new Book("RabbitLearn",880);

        System.out.println("book:"+book);
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(bos);
        oos.writeObject(book);
        //得到person对象的byte数组
        byte[] bookByteArray = bos.toByteArray();
        System.out.println("before compress:"+bookByteArray.length);
        //将byte数据压缩
//        byte[] zipPersonByteArray = compress(bookByteArray);
//        System.out.println("after compress:"+zipPersonByteArray.length);
//        closeStream(oos);
//        closeStream(bos);
        //从byte数组中还原person对象
//        ByteArrayInputStream bin=new ByteArrayInputStream(personByteArray);
//        ObjectInputStream ois=new ObjectInputStream(bin);
//        Person restorePerson = (Person) ois.readObject();
//        System.out.println(restorePerson);
//        closeStream(ois);
//        closeStream(bin);


        Connection connection=ConnectionUtils.getConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            String msg="Hello RabbitMQ ";
            channel.basicPublish("",QUEUE_NAME,null,bookByteArray);
            System.out.println("[wq]send msg "+  msg);
        channel.close();
        connection.close();

    }
}
