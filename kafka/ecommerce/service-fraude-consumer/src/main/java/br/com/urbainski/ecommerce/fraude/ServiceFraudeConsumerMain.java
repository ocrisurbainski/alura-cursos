package br.com.urbainski.ecommerce.fraude;

import br.com.urbainski.ecommerce.commons.kafka.consumer.ConsumerRunner;

public class ServiceFraudeConsumerMain {

    public static void main(String[] args) {
        new ConsumerRunner<>(FraudeDetectorConsumerService::new).start();
    }

}
