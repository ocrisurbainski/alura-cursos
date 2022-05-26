package br.com.urbainski.ecommerce.log;

import br.com.urbainski.ecommerce.commons.kafka.consumer.ConsumerRunner;

public class ServiceLogConsumerMain {

    public static void main(String[] args) {
        new ConsumerRunner<>(LogConsumerService::new).start();
    }

}
