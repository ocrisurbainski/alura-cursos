package br.com.urbainski.ecommerce.email;

public class ServiceEmailConsumerMain {

    public static void main(String[] args) {
        var consumer = new EmailConsumerService();
        consumer.consume();
    }

}
