package br.com.urbainski.ecommerce.email;

public class ServiceNewOrderEmailMain {

    public static void main(String[] args) {
        var consumer = new NewOrderEmailConsumerService();
        consumer.consume();
    }

}
