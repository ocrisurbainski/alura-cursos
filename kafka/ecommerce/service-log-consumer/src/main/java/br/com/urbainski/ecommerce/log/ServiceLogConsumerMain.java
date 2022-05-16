package br.com.urbainski.ecommerce.log;

public class ServiceLogConsumerMain {

    public static void main(String[] args) {
        var consumer = new LogConsumerService();
        consumer.consume();
    }

}
