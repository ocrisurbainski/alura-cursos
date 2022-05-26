package br.com.urbainski.ecommerce.commons.kafka.consumer;

import java.util.concurrent.Executors;

public class ConsumerRunner<K, V> {

    private final ConsumerProvider<K, V> provider;

    public ConsumerRunner(ConsumerFactory<K, V> factory) {
        this.provider = new ConsumerProvider<>(factory);
    }

    public void start(int threadCount) {
        var pool = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            pool.submit(provider);
        }
    }

    public void start() {
        start(1);
    }

}
