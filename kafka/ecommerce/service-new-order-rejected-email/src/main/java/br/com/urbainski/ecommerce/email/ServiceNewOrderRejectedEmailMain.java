package br.com.urbainski.ecommerce.email;

import br.com.urbainski.ecommerce.commons.kafka.consumer.ConsumerRunner;

public class ServiceNewOrderRejectedEmailMain {

    public static void main(String[] args) {
        new ConsumerRunner<>(NewOrderRejectedEmailConsumerService::new).start();
    }

}
