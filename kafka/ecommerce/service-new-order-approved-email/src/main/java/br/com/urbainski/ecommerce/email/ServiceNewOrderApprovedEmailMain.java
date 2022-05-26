package br.com.urbainski.ecommerce.email;

public class ServiceNewOrderApprovedEmailMain {

    public static void main(String[] args) {
        var consumer = new NewOrderApprovedEmailConsumerService();
        consumer.consume();
    }

}
