package br.com.urbainski.ecommerce.email;

public class ServiceNewOrderRejectedEmailMain {

    public static void main(String[] args) {
        var consumer = new NewOrderRejectedEmailConsumerService();
        consumer.consume();
    }

}
