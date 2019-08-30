package myRabbitMQ.bean;

import java.io.Serializable;

/**
 * @ClassName Book
 * @Description TODO
 * @Author chris
 * @Date 2019-01-21 11:42
 * @Version 1.0
 **/

public class Book implements Serializable {
    private String name;
    private Integer price;

    public Book() {
    }

    public Book(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}