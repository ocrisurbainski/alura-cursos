package br.com.urbainski.ecommerce.kafka;

import br.com.urbainski.ecommerce.properties.KafkaProperties;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;

import java.util.concurrent.ExecutionException;

public abstract class AbstractDefaultProducer<A> implements AutoCloseable {

    private final KafkaProducer<A, A> producer;

    public AbstractDefaultProducer() {

        this.producer = new KafkaProducer<A, A>(KafkaProperties.getKafkaProducerProperties());
    }

    public void send(A message) {
        try {
            var record = new ProducerRecord<>(getTopic().name(), message, message);
            producer.send(record, getDefaultCallback()).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected Callback getDefaultCallback() {
        return (recordMetadata, e) -> {
            if (e != null) {
                e.printStackTrace();
                return;
            }

            var logMessage = String.format(
                    "kafka mensagem enviada com sucesso :::topic=%s :::partition=%s :::offset=%s :::timestamp=%s",
                    recordMetadata.topic(),
                    recordMetadata.partition(),
                    recordMetadata.offset(),
                    recordMetadata.timestamp());

            getLog().info(logMessage);
        };
    }

    public abstract Topics getTopic();

    public abstract Logger getLog();

    @Override
    public void close() {
        this.producer.close();
    }

}
