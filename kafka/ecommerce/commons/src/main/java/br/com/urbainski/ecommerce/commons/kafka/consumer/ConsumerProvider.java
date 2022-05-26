package br.com.urbainski.ecommerce.commons.kafka.consumer;

import java.util.concurrent.Callable;

public class ConsumerProvider<K, V> implements Callable<Void> {

    private final ConsumerFactory<K, V> factory;

    public ConsumerProvider(ConsumerFactory<K, V> factory) {
        this.factory = factory;
    }

    @Override
    public Void call() throws Exception {
        var consumer = factory.create();
        consumer.consume();
        return null;
    }

}
