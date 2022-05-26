package br.com.urbainski.ecommerce.commons.kafka.consumer;

public interface ConsumerFactory<K, V> {
    AbstractDefaultConsumer<K, V> create();
}
