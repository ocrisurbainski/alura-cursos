package br.com.urbainski.ecommerce.fraude;

public class ServiceFraudeConsumerMain {

    public static void main(String[] args) {
        var consumer = new FraudeDetectorConsumerService();
        consumer.consume();
    }

}
